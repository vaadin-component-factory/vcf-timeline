import Arrow from './arrow.js';

window.vis = require("vis-timeline/standalone/umd/vis-timeline-graph2d.min.js")

window.vcftimeline = {

	create: function(container, itemsJson, optionsJson) {
	  // parsed items 	  
	  var parsedItems = JSON.parse(itemsJson);

	  // Create a DataSet
	  var items = new vis.DataSet(parsedItems);	
	
	  // Configuration for the Timeline
	  var parsedOptions = JSON.parse(optionsJson);

	  var snapStep = parsedOptions.snapStep;
	  delete parsedOptions.snapStep

	  var defaultOptions = {
		onMove: function(item, callback) {
			callback(item); 
						
			var startDate = window.vcftimeline._convertDate(item.start);
			var endDate = window.vcftimeline._convertDate(item.end);

			window.vcftimeline._updateConnections(container);

			container.$server.onMove(item.id, startDate, endDate);
		},

		onMoving: function(item, callback) {
			var range = container.timeline._timeline.getWindow();
			if(item.start <= range.start) {
				var diff = (range.start.valueOf() - item.start.getTime()) * 5;
				container.timeline._timeline.setWindow({
					start: range.start.valueOf() - diff,
					end: range.end.valueOf() - diff,
				});
			} else if(item.end >= range.end) {
				var diff = (item.end.getTime() - range.end.valueOf()) * 5;
				container.timeline._timeline.setWindow({
					start: range.start.valueOf() + diff,
					end: range.end.valueOf() + diff,
				});
			}
						
			callback(item);
		},

		snap: function (date, scale, step) {
			var hour = snapStep * 60 * 1000;
			return Math.round(date / hour) * hour;
		},

	  };

	  var options = {};
	  Object.assign(options, parsedOptions, defaultOptions);

	  // Create Timeline	
	  var timeline = new vis.Timeline(container, items, options);
      		
      const line_timeline = new Arrow(timeline);
	  container.timeline = line_timeline;

	  container.timeline._timeline.on("changed", () => {
            this._updateConnections(container);
        }); 
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
	}
}



