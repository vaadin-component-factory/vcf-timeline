package com.vaadin.componentfactory.timeline.model;

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
      return js.toJson();
  }

}
