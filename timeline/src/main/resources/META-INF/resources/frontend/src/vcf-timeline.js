/*-
 * #%L
 * Timeline
 * %%
 * Copyright (C) 2021 Vaadin Ltd
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import Arrow from './arrow.js';

window.vis = require("vis-timeline/standalone/umd/vis-timeline-graph2d.min.js")

window.vcftimeline = {

	create: function(container, itemsJson, optionsJson) {
	  // parsed items 	  
	  var parsedItems = JSON.parse(itemsJson);

	  // Create a DataSet
	  var items = new vis.DataSet(parsedItems);	
		 
	  // Get options for timeline configuration	
	  var options = this._processOptions(container, optionsJson);

	  // Create Timeline	
	  var timeline = new vis.Timeline(container, items, options);
      		
      const line_timeline = new Arrow(timeline);
	  container.timeline = line_timeline;

	  container.timeline._timeline.on("changed", () => {
		this._updateConnections(container);
		this._updateTimelineHeight(container);
	  }); 

	  container.timeline._timeline.on('select', (properties) => {
		container.$server.onSelect(properties.items);
	  });

	  setInterval(function(){
		var isDragging = container.timeline._timeline.itemSet.touchParams.itemIsDragging;
		if (isDragging) {
			var ix = container.timeline._timeline.itemSet.touchParams.itemProps[0].initialX; 
			var item = container.timeline._timeline.itemSet.touchParams.selectedItem;
			var range = container.timeline._timeline.getWindow();
			var widthInPixels = container.timeline._timeline.body.domProps.lastWidth;
			var widthInMilliseconds = range.end.valueOf() - range.start.valueOf();
			if(item.data.start <= range.start && range.start > new Date(options.min)) {
				container.timeline._timeline.setWindow(
					new Date(range.start.valueOf() - (widthInMilliseconds / 50)),
					new Date(range.end.valueOf() - (widthInMilliseconds / 50)),
					{animation: false}
				);
				container.timeline._timeline.itemSet.touchParams.itemProps[0].initialX = ix + (widthInPixels / 50);
				item.data.start = new Date(item.data.start.valueOf() - (widthInMilliseconds / 50));
				item.data.end = new Date(item.data.end.valueOf() - (widthInMilliseconds / 50));
			} else if(item.data.end >= range.end && range.end < new Date(options.max)) {
				container.timeline._timeline.setWindow(
					new Date(range.start.valueOf() + (widthInMilliseconds / 50)),
					new Date(range.end.valueOf() + (widthInMilliseconds / 50)),
					{animation: false}
				);
				container.timeline._timeline.itemSet.touchParams.itemProps[0].initialX = ix - (widthInPixels / 50);
				item.data.start = new Date(item.data.start.valueOf() + (widthInMilliseconds / 50));
				item.data.end = new Date(item.data.end.valueOf() + (widthInMilliseconds / 50));
			}
		}
	  }, 100);
  	},

	_processOptions: function(container, optionsJson){
	  var parsedOptions = JSON.parse(optionsJson);

	  var snapStep = parsedOptions.snapStep;
	  delete parsedOptions.snapStep;

	  var autoZoom = parsedOptions.autoZoom;
	  delete parsedOptions.autoZoom;

	  var defaultOptions = {
		onMove: function(item, callback) {
			var oldItem = container.timeline._timeline.itemSet.itemsData.get(item.id);
			var isResizedItem = oldItem.end.getTime() - oldItem.start.getTime() !=  item.end.getTime() - item.start.getTime();
			var moveItem = true;

			if(isResizedItem && (item.start.getTime() >= item.end.getTime() || item.end.getTime() <= item.start.getTime())){
				moveItem = false;
			}

			if(moveItem) {
				callback(item); 							
				var startDate = window.vcftimeline._convertDate(item.start);
				var endDate = window.vcftimeline._convertDate(item.end);
				//update connections
				window.vcftimeline._updateConnections(container);
				//call server
				container.$server.onMove(item.id, startDate, endDate, isResizedItem);
			} else {
				// undo resize 
				callback(null);
			}
		},

		

		snap: function (date, scale, step) {
			var hour = snapStep * 60 * 1000;
			return Math.round(date / hour) * hour;
		},
	  };

	  var options = {};
	  Object.assign(options, parsedOptions, defaultOptions);

	  if(autoZoom && options.min && options.max){
		  options.start = options.min;
		  options.end = options.max;
	  }

	  return options;
	},

	setOptions: function(container, optionsJson) {
		var options = this._processOptions(container, optionsJson)
		container.timeline._timeline.setOptions(options);
	},

  	addItem: function(container, newItemJson) {
		container.timeline._timeline.itemsData.add(JSON.parse(newItemJson));
		container.timeline._timeline.fit();
	},

	setItems: function(container, itemsJson) {
		var items = new vis.DataSet(JSON.parse(itemsJson));
		container.timeline._timeline.setItems(items);
		container.timeline._timeline.fit();
	},
	
	revertMove: function(container, itemId, itemJson) {
	    var itemData = container.timeline._timeline.itemSet.items[itemId].data;
	    var parsedItem = JSON.parse(itemJson);
		itemData.start = parsedItem.start;
		itemData.end = parsedItem.end;
		container.timeline._timeline.itemsData.update(itemData);
	},
	
	removeItem: function(container, itemId) {
		container.timeline._timeline.itemsData.remove(itemId);
		container.$server.onRemove(itemId);
	},

	updateItemContent: function(container, itemId, newContent) {
		var itemData = container.timeline._timeline.itemSet.items[itemId].data;
		itemData.content = newContent;
		container.timeline._timeline.itemsData.update(itemData);
	},
	
	setZoomOption: function(container, zoomDays) {
		var startDate;
		var selectedItems = container.timeline._timeline.getSelection();
		if(selectedItems.length > 0){
			var selectedItem = selectedItems.length > 1 ? this._sortItems(selectedItems)[0] : selectedItems[0];
			startDate = container.timeline._timeline.itemSet.items[selectedItem].data.start;
		} else {
			var range = container.timeline._timeline.getWindow();
			startDate = range.start;
		}

		var start = vis.moment(startDate);
		start.hour(0);
		start.minutes(0);
		start.seconds(0);

		var end = vis.moment(startDate);
		end.add(zoomDays, 'days');
		
		container.timeline._timeline.setWindow({
			start: start,
			end: end,
		});
	},
	
	_convertDate: function(date) {
		var local = new Date(date);
		local.setMinutes(date.getMinutes() - date.getTimezoneOffset());
		return local.toJSON().slice(0, 19);		
	},  
	
	_sortItems: function(items) {
	  var sortedItems = items.sort(function(item1, item2) {
		var item1_date = new Date(item1.start), item2_date = new Date(item2.start);
		return item1_date - item2_date;
	  });
	  return sortedItems;
	},

	_createConnections: function(items) {
	  // Sort items in order to be able to create connections for timeline-arrow
	  // (horizontal line)
	  var sortedItems = this._sortItems(items);

      // Create connections for items
	  var connections = [];
	  for(let i = 0; i < sortedItems.length-1; i++) {
		  var element = sortedItems[i];
		  var nextElement = sortedItems[i + 1];

		  var id = i + 1;
		  var id_item_1 = element.id;
		  var id_item_2= nextElement.id;

		  var item = {}
		  item ["id"] = id;
		  item ["id_item_1"] = id_item_1;
		  item ["id_item_2"] = id_item_2;

		  connections.push(item);			  	  
	  }		
	  return connections;
	},
	
	_updateConnections: function(container) {
		var connections = this._createConnections(container.timeline._timeline.itemsData.get());
		container.timeline.setDependencies(connections);
	},

	_updateTimelineHeight: function(container) {
		if(container.timelineHeight == undefined){
			container.timelineHeight = container.timeline._timeline.dom.container.getBoundingClientRect().height;
		}
		if(container.timeline._timeline.options.height == undefined){
			container.timeline._timeline.options.height = container.timelineHeight;
		}
	}
}



