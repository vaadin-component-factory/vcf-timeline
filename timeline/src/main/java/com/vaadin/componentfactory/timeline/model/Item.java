package com.vaadin.componentfactory.timeline.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import elemental.json.Json;
import elemental.json.JsonObject;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Item {
  
  private String id = UUID.randomUUID().toString();

  private LocalDateTime start;
  
  private LocalDateTime end;
  
  private String content;
  
  private String clusterId;
  
  private Boolean editable;
  
  private Boolean updateTime;
  
  private Boolean remove;
  
  public Item() {}
  
  public Item(LocalDateTime start, LocalDateTime end) {
    super();
    this.setStart(start);
    this.setEnd(end);
  }
   
  public Item(LocalDateTime start, LocalDateTime end, String content) {
      this(start, end);
      this.setContent(content);
  }

  public String getId() {
      return id;
  }

  public void setId(String id) {
      this.id = id;
  }

  public LocalDateTime getStart() {
      return start;
  }

  public void setStart(LocalDateTime start) {
      this.start = start;
  }

  public LocalDateTime getEnd() {
      return end;
  }

  public void setEnd(LocalDateTime end) {
      this.end = end;
  }

  public String getContent() {
      return content;
  }

  public void setContent(String content) {
      this.content = content;
  }
  
  public String getClusterId() {
    return clusterId;
  }

  public void setClusterId(String clusterId) {
    this.clusterId = clusterId;
  }  
  
  public Boolean getEditable() {
    return editable;
  }

  public void setEditable(Boolean editable) {
    this.editable = editable;
  }

  public Boolean getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Boolean updateTime) {
    this.updateTime = updateTime;
  }  

  public Boolean getRemove() {
    return remove;
  }

  public void setRemove(Boolean remove) {
    this.remove = remove;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Item other = (Item) obj;
    return Objects.equals(id, other.id);
  }

  public String toJSON() {
      JsonObject js = Json.createObject();      
      Optional.ofNullable(getId()).ifPresent(v -> js.put("id", v));     
      Optional.ofNullable(getContent()).ifPresent(v -> js.put("content", v));       
      Optional.ofNullable(getStart()).ifPresent(v -> js.put("start", v.toString()));
      Optional.ofNullable(getEnd()).ifPresent(v -> js.put("end", v.toString()));
      Optional.ofNullable(getClusterId()).ifPresent(v -> js.put("clusterId", v));
      
      Optional.ofNullable(getEditable()).ifPresent(v -> {
        if(v && (getUpdateTime() != null || getRemove() != null)) {
          JsonObject optionsJs = Json.createObject();
          Optional.ofNullable(getUpdateTime()).ifPresent(u -> optionsJs.put("updateTime" , u));
          Optional.ofNullable(getRemove()).ifPresent(r -> optionsJs.put("remove" , r));
          js.put("editable", optionsJs);
        } else {
          js.put("editable", v);
        }
      });
      return js.toJson();
  }

}
