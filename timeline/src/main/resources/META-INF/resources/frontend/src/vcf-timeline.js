
window.vis = require("vis-timeline/standalone/umd/vis-timeline-graph2d.min.js")

window.vcftimeline = {

	create: function(container, itemsJson, optionsJson) {
	  // Create a DataSet 	  
	  var items = new vis.DataSet(JSON.parse(itemsJson));	
		
	  // Configuration for the Timeline
	  var parsedOptions = JSON.parse(optionsJson);

	  var defaultOptions = {
		onMove: function(item, callback) {
			callback(item); 
						
			var startDate = window.vcftimeline._convertDate(item.start);
			var endDate = window.vcftimeline._convertDate(item.end);

			container.$server.onMove(item.id, startDate, endDate);
		  },
		
		// onMoving: function(item, callback) {
		// 	var range = container.timeline.getWindow();
		// 	if(item.start <= range.start) {
		// 		var diff = range.start.valueOf() - item.start.getTime();
		// 		container.timeline.setWindow({
		// 			start: range.start.valueOf() - diff,
		// 			end: range.end.valueOf() - diff,
		// 		});
		// 	} else if(item.end >= range.end) {
		// 		var diff = item.end.getTime() - range.end.valueOf();
		// 		container.timeline.setWindow({
		// 			start: range.start.valueOf() + diff,
		// 			end: range.end.valueOf() + diff,
		// 		});
		// 	}
		// 	callback(item);
		// }

	  };

	  var options = {};
	  Object.assign(options, parsedOptions, defaultOptions);
	
	  // Create Timeline	
   	  var timeline = new vis.Timeline(container, items, options);		 
   	  container.timeline = timeline;
  	},

  	addItem: function(container, newItemJson) {
		container.timeline.itemsData.add(JSON.parse(newItemJson));
		container.timeline.fit();
	},

	setItems: function(container, itemsJson) {
		var items = new vis.DataSet(JSON.parse(itemsJson));
		container.timeline.setItems(items);
		container.timeline.fit();
	},
	
	revertMove: function(container, itemId, itemJson) {
	    var itemData = container.timeline.itemSet.items[itemId].data;
	    var parsedItem = JSON.parse(itemJson);
		itemData.start = parsedItem.start;
		itemData.end = parsedItem.end;
		container.timeline.itemsData.update(itemData);
	},
	
	removeItem: function(container, itemId) {
		container.timeline.itemsData.remove(itemId);
	},

	setClusterOptions: function(container, clusterOptionsJson) {
		var updatedOptions = {};
		var options = container.timeline.options;
		var jsonTransformed = JSON.parse(clusterOptionsJson, function (key, value) {
			if (value && (typeof value === 'string') && value.indexOf("clusterCriteria") === 0) {
				return function(item1 , item2) {
					return item1.clusterId == item2.clusterId;
				};
			}				 
			return value;
		});
		Object.assign(updatedOptions, options, jsonTransformed);
		container.timeline.setOptions(updatedOptions);
	},
	
	_convertDate: function(date) {
		var local = new Date(date);
		local.setMinutes(date.getMinutes() - date.getTimezoneOffset());
		return local.toJSON().slice(0, 19);		
	},
  
}



