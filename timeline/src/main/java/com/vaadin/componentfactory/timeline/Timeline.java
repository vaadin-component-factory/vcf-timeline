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

package com.vaadin.componentfactory.timeline;

import com.vaadin.componentfactory.timeline.event.ItemRemoveEvent;
import com.vaadin.componentfactory.timeline.event.ItemResizeEvent;
import com.vaadin.componentfactory.timeline.event.ItemsDragAndDropEvent;
import com.vaadin.componentfactory.timeline.model.AxisOrientation;
import com.vaadin.componentfactory.timeline.model.Item;
import com.vaadin.componentfactory.timeline.model.SnapStep;
import com.vaadin.componentfactory.timeline.model.TimelineOptions;
import com.vaadin.componentfactory.timeline.util.TimelineUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.internal.Pair;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Timeline component definition. Timeline uses vis-timeline component to display data in time (see
 * more at https://github.com/visjs/vis-timeline).
 */
@SuppressWarnings("serial")
@NpmPackage(value = "vis-timeline", version = "7.4.9")
@JsModule("./src/arrow.js")
@JsModule("./src/vcf-timeline.js")
@CssImport("vis-timeline/styles/vis-timeline-graph2d.min.css")
@CssImport("./styles/timeline.css")
public class Timeline extends Div {

  private List<Item> items = new ArrayList<>();

  private TimelineOptions timelineOptions = new TimelineOptions();

  private List<String> selectedItemsIdsList = new ArrayList<>();
  
  private Map<String, Pair<LocalDateTime, LocalDateTime>> movedItemsMap = new HashMap<>();
  
  private Map<String, Pair<LocalDateTime, LocalDateTime>> movedItemsOldValuesMap = new HashMap<>();
  
  public Timeline() {
    setId("visualization" + this.hashCode());
    setWidthFull();
    setClassName("timeline");
  }

  public Timeline(List<Item> items) {
    this();
    this.items = new ArrayList<>(items);
  }

  protected TimelineOptions getTimelineOptions() {
    return this.timelineOptions;
  }

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    selectedItemsIdsList = new ArrayList<>();
    movedItemsMap = new HashMap<>();
    movedItemsOldValuesMap = new HashMap<>();
    initTimeline();
  }

  private void initTimeline() {
    this.getElement()
        .executeJs(
            "vcftimeline.create($0, $1, $2)",
            this,
            "[" + convertItemsToJson() + "]",
            getTimelineOptions().toJSON());
  }

  private String convertItemsToJson() {
    return this.items != null
        ? this.items.stream().map(item -> item.toJSON()).collect(Collectors.joining(","))
        : "";
  }

  /**
   * Add a new item to the timeline.
   *
   * @param item the new item to add to the timeline
   */
  public void addItem(Item item) {
    this.getElement().executeJs("vcftimeline.addItem($0, $1)", this, item.toJSON());
    this.items.add(item);
  }

  public void setItems(List<Item> items) {
    this.items = new ArrayList<>(items);
    this.getElement()
        .executeJs("vcftimeline.setItems($0, $1)", this, "[" + convertItemsToJson() + "]");
  }

  /**
   * Return the list of items that are currently part of the timeline.
   *
   * @return the list of items of the timeline
   */
  public List<Item> getItems() {
    return items;
  }

  /**
   * Sets visible range for timeline.
   *
   * @param min minimum date
   * @param max maximum date
   */
  public void setTimelineRange(LocalDateTime min, LocalDateTime max) {
    getTimelineOptions().min = min;
    getTimelineOptions().max = max;
    updateTimelineOptions();
  }

  /**
   * Sets orientation of the timeline axis. By default axis is on top.
   *
   * @param axisOrientation orientation of the timeline axis
   */
  public void setAxisOrientation(AxisOrientation axisOrientation) {
    getTimelineOptions().axisOrientation = axisOrientation.getName();
    updateTimelineOptions();
  }

  /**
   * Sets whether the timeline can be zoomed by pinching or scrolling in the window. By default,
   * timeline is zoomable. Option moveable shoul be true.
   *
   * @param zoomable true if timeline is zoomable
   */
  public void setZoomable(boolean zoomable) {
    getTimelineOptions().zoomable = zoomable;
    updateTimelineOptions();
  }

  /**
   * Sets wheter the timeline can be moved by dragging the window. By default, timeline is moveable.
   *
   * @param moveable true if timeline is moveable
   */
  public void setMoveable(boolean moveable) {
    getTimelineOptions().moveable = moveable;
    updateTimelineOptions();
  }

  /**
   * Sets zoom range for timeline.
   *
   * @param zoomMin minimum zoom interval
   * @param zoomMax maximum zoom interval
   */
  public void setZoomRange(Long zoomMin, Long zoomMax) {
    getTimelineOptions().zoomMin = zoomMin;
    getTimelineOptions().zoomMax = zoomMax;
    updateTimelineOptions();
  }

  /**
   * Sets wheter the items in the timeline can be selected. By default, items are selectables.
   *
   * @param selectable true if times can be selected
   */
  public void setSelectable(boolean selectable) {
    getTimelineOptions().selectable = selectable;
    updateTimelineOptions();
  }

  /**
   * Sets whether a vertical bar at current time is displayed. By default, not current time is
   * displayed.
   *
   * @param showCurrentTime true if current time is shown
   */
  public void setShowCurentTime(boolean showCurrentTime) {
    getTimelineOptions().showCurrentTime = showCurrentTime;
    updateTimelineOptions();
  }

  /**
   * Sets the height of the timeline. Value can be in pixles or as percentaje (e.g. "300px"). When
   * height is undefined or null, the height of the timeline is automatically adjusted to fit the
   * contents.
   */
  @Override
  public void setHeight(String height) {
    getTimelineOptions().height = height;
    updateTimelineOptions();
  }

  /** Sets the maximum height for the timeline. */
  @Override
  public void setMaxHeight(String maxHeight) {
    getTimelineOptions().maxHeight = maxHeight;
    updateTimelineOptions();
  }

  /**
   * Sets the initial start date for the axis of the timeline. If it's not provided, the earliest
   * date present in the events is taken as start date.
   * 
   * If autoZoom is true, this option will be override.
   *
   * @param start initial start date
   */
  public void setStart(LocalDateTime start) {
    getTimelineOptions().start = start;
    updateTimelineOptions();
  }
  
  /**
   * Sets whether all range should be visible at once. 
   * Only works if a range was defined by calling {@link #setTimelineRange}.
   * It will set start and end for timeline axis. 
   * 
   * @param autoZoom true if autozoom is allowed
   */
  public void setAutoZoom(boolean autoZoom) {
    getTimelineOptions().autoZoom = autoZoom;
    updateTimelineOptions();
  }
  
  /**
   * The initial end date for the axis of the timeline. If not provided, the latest date present 
   * in the items set is taken as end date.
   * 
   * If autoZoom is true, this option will be override.
   * 
   * @param end initial end date
   */
  public void setEnd(LocalDateTime end) {
    getTimelineOptions().end = end;
    updateTimelineOptions();
  }

  /**
   * Sets whether items will be stack on top of each other if they overlap. By default item will not
   * stack.
   *
   * @param stack true if items should stack
   */
  public void setStack(boolean stack) {
    getTimelineOptions().stack = stack;
    updateTimelineOptions();
  }

  /**
   * Sets whether multiple items can be selected. Option selectable should be true. By default,
   * multiselect is disabled.
   *
   * @param multiselect true if multiselect is allowed
   */
  public void setMultiselect(boolean multiselect) {
    getTimelineOptions().multiselect = multiselect;
    updateTimelineOptions();
  }  
  
  /**
   * Sets whether tooltips will be displaying for items with defined titles. By default, tooltips
   * will be visibles.
   *
   * @param showTooltips true if tooltips should be shown
   */
  public void setShowTooltips(boolean showTooltips) {
    getTimelineOptions().showTooltips = showTooltips;
    updateTimelineOptions();
  }
 
  /**
   * Updates content of an existing item.
   *
   * @param itemId id of item to be updated
   * @param newContent new item content
   */
  public void updateItemContent(String itemId, String newContent) {
    this.getElement()
        .executeJs("vcftimeline.updateItemContent($0, $1, $2)", this, itemId, newContent);
    items.stream()
        .filter(i -> itemId.equals(i.getId()))
        .findFirst()
        .ifPresent(
            item -> {
              item.setContent(newContent);
            });
  }

  /**
   * Sets snap value. It can be an hour, half an hour or fifteen minutes. By default it is set at
   * fifteeen minutes.
   *
   * @param snapStep snap value
   */
  public void setSnapStep(SnapStep snapStep) {
    getTimelineOptions().snapStep = snapStep.getMinutes();
    updateTimelineOptions();
  }

  /**
   * Sets zoom option for timeline.
   *
   * @param zoomOption integer representing days for zooming
   */
  public void setZoomOption(Integer zoomOption) {
    this.getElement().executeJs("vcftimeline.setZoomOption($0, $1)", this, zoomOption);
    updateTimelineOptions();
  }

  /**
   * Updates timeline options after timeline creation.
   */
  private void updateTimelineOptions() {
    if(this.getElement().getNode().isAttached()) {
      this.getElement().executeJs("vcftimeline.setOptions($0, $1)", this, getTimelineOptions().toJSON());
    }
  }
  
  /**
   * Call from client when an item is moved (dragged and dropped or resized).
   * 
   * @param itemId id of the moved item
   * @param itemNewStart new start date of the moved item
   * @param itemNewEnd new end date of the moved item
   * @param resizedItem true if item was resized 
   */
  @ClientCallable
  public void onMove(String itemId, String itemNewStart, String itemNewEnd, boolean resizedItem) {
    LocalDateTime newStart = TimelineUtil.convertLocalDateTime(itemNewStart);
    LocalDateTime newEnd = TimelineUtil.convertLocalDateTime(itemNewEnd);
    
    if(resizedItem) {
      fireItemResizeEvent(itemId, newStart, newEnd, true);
    } else {
      handleDragAndDrop(itemId, newStart, newEnd, true);
    }    
  }

  /**
   * Fires a {@link ItemResizeEvent}.
   *
   * @param itemId id of the item that was moved
   * @param newStart new start date for the item
   * @param newEnd new end date for the item
   * @param fromClient if event comes from client
   */
  protected void fireItemResizeEvent(
      String itemId, LocalDateTime newStart, LocalDateTime newEnd, boolean fromClient) {
    ItemResizeEvent event = new ItemResizeEvent(this, itemId, newStart, newEnd, fromClient);    
    RuntimeException exception = null;
    
    try {
      fireEvent(event);
    } catch (RuntimeException e) {
      exception = e;
      event.setCancelled(true);
    }    
    
    if (event.isCancelled()) {
      // if update is cancelled, revert item resizing
      revertMove(itemId);
      // if exception was catch, re-throw exception
      if(exception != null) {
        throw exception;
      }
    } else {
      // update item in list
      updateItemRange(itemId, newStart, newEnd);
    }      
  }
  
  /**
   * Handle item moved by drag and drop.
   * 
   * @param itemId id of the item that was moved
   * @param newStart new start date for the item
   * @param newEnd new end date for the item
   * @param fromClient if event comes from client
   */
  protected void handleDragAndDrop(String itemId, LocalDateTime newStart, LocalDateTime newEnd, boolean fromClient)  {
    // save current moved item - itemId - new start and new end
    movedItemsMap.put(itemId, new Pair<>(newStart, newEnd));
    // save original start and end for the moved item
    Item movedItem = items.stream().filter(i -> itemId.equals(i.getId())).findFirst().get();
    movedItemsOldValuesMap.put(itemId, new Pair<>(movedItem.getStart(), movedItem.getEnd()));
    
    // if all selected items have been processed
    if(selectedItemsIdsList.size() == movedItemsMap.size()){
      // update items with new start and end range values
      updateMovedItemsRange();
      List<Item> updatedItems = items.stream()
          .filter(item -> movedItemsMap.keySet().contains(item.getId())).collect(Collectors.toList());
      ItemsDragAndDropEvent event = new ItemsDragAndDropEvent(this, updatedItems, fromClient);      
      RuntimeException exception = null;
      
      try {
        fireEvent(event);
      } catch (RuntimeException e) {
        exception = e;
        event.setCancelled(true);
      }    
      
      if(event.isCancelled()) {
        // if move is cancelled, revert move for all dragged items
        revertMovedItemsRange();
        movedItemsMap.clear();
        movedItemsOldValuesMap.clear();
        // if exception was catch, re-throw the exception for error handling
        if(exception != null) {
          throw exception;
        }
      } else {
        // if move not cancelled, keep updated values
        movedItemsMap.clear();
        movedItemsOldValuesMap.clear();
      }      
    }
  }  
  
  private void revertMovedItemsRange() {
    for (String itemId : movedItemsOldValuesMap.keySet()) {
      items.stream().filter(i -> itemId.equals(i.getId())).findFirst().ifPresent(item -> {
        item.setStart(movedItemsOldValuesMap.get(itemId).getFirst());
        item.setEnd(movedItemsOldValuesMap.get(itemId).getSecond());
      });
    }
    
    for (String itemId : movedItemsOldValuesMap.keySet()) {
      revertMove(itemId);
    }
  }
  
  private void revertMove(String itemId) {
    Item item =
        items.stream()
            .filter(i -> itemId.equals(i.getId()))
            .findFirst()
            .orElse(null);
    if (item != null) {
      this.getElement()
          .executeJs("vcftimeline.revertMove($0, $1, $2)", this, itemId, item.toJSON());
    }
  }
  
  private void updateMovedItemsRange() {
    for(String itemId: movedItemsMap.keySet()) {
      updateItemRange(itemId, movedItemsMap.get(itemId).getFirst(), movedItemsMap.get(itemId).getSecond());
    }        
  }
  
  private void updateItemRange(String itemId, LocalDateTime newStart, LocalDateTime newEnd) {
    items.stream()
    .filter(i -> itemId.equals(i.getId()))
    .findFirst()
    .ifPresent(
        item -> {
          item.setStart(newStart);
          item.setEnd(newEnd);
        });
  }

  /**
   * Adds a listener for {@link ItemResizeEvent} to the component.
   *
   * @param listener the listener to be added
   */
  public void addItemResizeListener(ComponentEventListener<ItemResizeEvent> listener) {
    addListener(ItemResizeEvent.class, listener);
  }
    
  /**
   * Removes an item.
   *
   * @param item item to be removed.
   */
  public void removeItem(Item item) {
    this.getElement().executeJs("vcftimeline.removeItem($0, $1)", this, item.getId());
  }

  /**
   * Call from client when an item is removed.
   *
   * @param itemId id of the removed item
   */
  @ClientCallable
  public void onRemove(String itemId) {
    fireItemRemoveEvent(itemId, true);
  }

  /**
   * Fires a {@link ItemRemoveEvent}.
   *
   * @param itemId id of the removed item
   * @param fromClient if event comes from client
   */
  public void fireItemRemoveEvent(String itemId, boolean fromClient) {
    ItemRemoveEvent event = new ItemRemoveEvent(this, itemId, fromClient);
    // update items list
    items.removeIf(item -> itemId.equals(item.getId()));
    fireEvent(event);
  }

  /**
   * Adds a listener for {@link ItemRemoveEvent} to the component.
   *
   * @param listener the listener to be added.
   */
  public void addItemRemoveListener(ComponentEventListener<ItemRemoveEvent> listener) {
    addListener(ItemRemoveEvent.class, listener);
  }
  
  /**
   * Call from client when items are selected.
   * 
   * @param selectedItemsIds list of selected items
   */
  @ClientCallable
  public void onSelect(String selectedItemsIds) {
    selectedItemsIdsList.clear();   
    selectedItemsIdsList.addAll(Arrays.asList(selectedItemsIds.split(",")));
  }
   
  /**
   * Adds a listener for {@link ItemsDragAndDropEvent} to the component.
   * 
   * @param listener the listener to be added
   */
  public void addItemsDragAndDropListener(ComponentEventListener<ItemsDragAndDropEvent> listener) {
    addListener(ItemsDragAndDropEvent.class, listener);
  }
}
