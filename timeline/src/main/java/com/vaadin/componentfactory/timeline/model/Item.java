package com.vaadin.componentfactory.timeline.model;

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

import elemental.json.Json;
import elemental.json.JsonObject;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/** Representation of a timeline item. */
public class Item {

  private String id;

  private LocalDateTime start;

  private LocalDateTime end;

  private String content;

  private Boolean editable;

  private Boolean updateTime;

  private Boolean remove;

  private String title;

  private String className;

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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Item other = (Item) obj;
    return Objects.equals(id, other.id);
  }

  public String toJSON() {
    JsonObject js = Json.createObject();
    Optional.ofNullable(getId()).ifPresent(v -> js.put("id", v));
    Optional.ofNullable(getContent()).ifPresent(v -> js.put("content", v));
    Optional.ofNullable(getStart()).ifPresent(v -> js.put("start", v.toString()));
    Optional.ofNullable(getEnd()).ifPresent(v -> js.put("end", v.toString()));

    Optional.ofNullable(getEditable())
        .ifPresent(
            v -> {
              if (v && (getUpdateTime() != null || getRemove() != null)) {
                JsonObject optionsJs = Json.createObject();
                Optional.ofNullable(getUpdateTime()).ifPresent(u -> optionsJs.put("updateTime", u));
                Optional.ofNullable(getRemove()).ifPresent(r -> optionsJs.put("remove", r));
                js.put("editable", optionsJs);
              } else {
                js.put("editable", v);
              }
            });

    Optional.ofNullable(getTitle()).ifPresent(v -> js.put("title", v));
    Optional.ofNullable(getClassName()).ifPresent(v -> js.put("className", v));
    return js.toJson();
  }
}
