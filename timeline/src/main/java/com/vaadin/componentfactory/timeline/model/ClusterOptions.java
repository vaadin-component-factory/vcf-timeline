package com.vaadin.componentfactory.timeline.model;

import elemental.json.Json;
import elemental.json.JsonObject;
import java.util.Optional;

public class ClusterOptions {

  /* If true, overlapped items will be grouped to clusters, zooming will change that grouping.*/
  public boolean cluster = false;
      
  /* If true, line from cluster to time axis is displayed when content overflows. */
  public boolean showStipes = false;
  
  /* Cluster item tooltip, will replace {count} with the number of items in the cluster. */
  public String titleTemplate;  
  
  /* Overlapped items will be not grouped until the number of items is within maxItems. 
   * The default value of maxItems is 1, it means that each two overlapped items will be clustered.*/
  public Integer maxItems = 1;
    
  public String toJSON() {
    JsonObject js = Json.createObject();
    if(!cluster) {
      js.put("cluster", cluster);
    } else {
      JsonObject optionsJs = Json.createObject();
      optionsJs.put("showStipes" , showStipes);
      Optional.ofNullable(titleTemplate).ifPresent(v -> js.put("titleTemplate", v)); 
      optionsJs.put("clusterCriteria", "clusterCriteria");
      js.put("cluster", optionsJs);
    }
    return js.toJson();
  }
}
