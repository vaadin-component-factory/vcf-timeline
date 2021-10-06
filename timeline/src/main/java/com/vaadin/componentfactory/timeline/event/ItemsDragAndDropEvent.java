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
package com.vaadin.componentfactory.timeline.event;

import com.vaadin.componentfactory.timeline.Timeline;
import com.vaadin.componentfactory.timeline.model.Item;
import com.vaadin.flow.component.ComponentEvent;
import java.util.List;

/**
 * Event thrown when an item or a list of items are moved by drag and drop.
 */
public class ItemsDragAndDropEvent extends ComponentEvent<Timeline> {

  private final List<Item> items;
    
  private boolean cancelled = false;

  public ItemsDragAndDropEvent(Timeline source, List<Item> items, boolean fromClient) {
    super(source, fromClient);
    this.items = items;
  }

  public List<Item> getItems() {
    return items;
  }

  public boolean isCancelled() {
    return cancelled;
  }

  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

  public Timeline getTimeline() {
    return (Timeline) source;
  }

}
