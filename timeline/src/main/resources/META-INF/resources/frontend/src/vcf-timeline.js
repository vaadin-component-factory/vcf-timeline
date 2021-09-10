
window.vis = require("vis-timeline/standalone/umd/vis-timeline-graph2d.min.js")

window.vcftimeline = {

	create: function(container, itemsJson, optionsJson) {
	  // Create a DataSet 	  
	  var items = new vis.DataSet(JSON.parse(itemsJson));	
		
	  // Configuration for the Timeline
	  var options = JSON.parse(optionsJson);
	
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
}

