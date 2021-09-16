package com.vaadin.componentfactory.timeline.model;

import elemental.json.Json;
import elemental.json.JsonObject;

/**
 * Timeline editable options. 
 * 
 */
public class EditableOptions {
  
  /* When true, the items in the timeline can be manipulated. 
   * Only applicable when option selectable is true.
   */
  public boolean editable = false;
  
  /* If true, new items can be created by double tapping an empty space in the Timeline. */
  public boolean add = false;
  
  /* If true, items can be dragged to another moment in time. */
  public boolean updateTime = false;
  
  /* If true, items can be deleted by first selecting them, and then clicking the delete button on the top right of the item.*/
  public boolean remove = false;
  
  /* If true, item specific editable properties are overridden by timeline settings */
  public boolean overrideItems = false; 
  
  public String toJSON() {
    JsonObject js = Json.createObject();
    if(editable || (add || updateTime|| remove || overrideItems)) {
      js.put("editable", optionsToJSON());
    } else {
      js.put("editable", editable);
    }
    return js.toJson();
  }
  
  public JsonObject optionsToJSON() {
    JsonObject optionsJs = Json.createObject();
    optionsJs.put("updateTime" , updateTime);
    optionsJs.put("add" , add);
    optionsJs.put("remove" , remove);
    optionsJs.put("overrideItems", "overrideItems");
    return optionsJs;
  }
  
}
