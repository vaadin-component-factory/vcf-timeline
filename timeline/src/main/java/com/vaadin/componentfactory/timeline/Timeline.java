package com.vaadin.componentfactory.timeline;

import com.vaadin.componentfactory.timeline.model.AxisOrientation;
import com.vaadin.componentfactory.timeline.model.ClusterOptions;
import com.vaadin.componentfactory.timeline.model.EditableOptions;
import com.vaadin.componentfactory.timeline.model.Item;
import com.vaadin.componentfactory.timeline.model.TimelineOptions;
import com.vaadin.componentfactory.timeline.util.ClusterIdProvider;
import com.vaadin.componentfactory.timeline.util.TimelineUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Div;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@NpmPackage(value = "vis-timeline", version = "7.4.9")
@JsModule("./src/vcf-timeline.js")
@CssImport("vis-timeline/styles/vis-timeline-graph2d.min.css")
public class Timeline extends Div {

  private List<Item> items = new ArrayList<>();
  
  private TimelineOptions timelineOptions = new TimelineOptions();
  
  private ClusterOptions clusterOptions = new ClusterOptions();
  
  private EditableOptions editableOptions = new EditableOptions();
  
  private ClusterIdProvider clusterIdProvider;
  
  public Timeline() {
    setId("visualization" + this.hashCode());
    setWidthFull();
  }

  public Timeline(Item ... items) {
    this();
    this.items = Arrays.asList(items);   
  }
      
  protected TimelineOptions getTimelineOptions() {
    return this.timelineOptions;
  }
  
  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    initTimeline();
    addClusterOptions();
  }

  private void initTimeline() {
    this.getElement().executeJs("vcftimeline.create($0, $1, $2)", this, "[" + convertItemsToJson() + "]", getTimelineOptions().toJSON()); 
  }
  
  private String convertItemsToJson() {
    return this.items != null ? this.items.stream().map(item -> item.toJSON()).collect(Collectors.joining(",")) : "";
  }
  
  private void addClusterOptions() {
    this.getElement().executeJs("vcftimeline.setClusterOptions($0, $1)", this, getClusterOptions().toJSON());    
  }
  
  public void setClusterIdProvider(ClusterIdProvider clusterIdProvider) {
    this.clusterIdProvider = Objects.requireNonNull(clusterIdProvider);
    this.items.forEach(i -> i.setClusterId(clusterIdProvider.apply(i)));
  }

  /**
   * Add a new item to the timeline.
   * 
   * @param item the new item to add to the timeline
   */
  public void addItem(Item item) {
    if(clusterIdProvider != null) {
      item.setClusterId(clusterIdProvider.apply(item));
    }
    this.getElement().executeJs("vcftimeline.addItem($0, $1)", this, item.toJSON());
    this.items.add(item);
  }
      
  public void setItems(Item ... items) {
    List<Item> itemsList = this.items = Arrays.asList(items);   
    if(clusterIdProvider != null) {
      itemsList.forEach(i -> i.setClusterId(clusterIdProvider.apply(i)));
    }
    this.getElement().executeJs("vcftimeline.setItems($0, $1)", this, "[" + convertItemsToJson() + "]");
    this.items = itemsList;
  }
  
  public void setTimelineRange(LocalDateTime min, LocalDateTime max) {
    getTimelineOptions().min = min;
    getTimelineOptions().max = max; 
  }
  
  public void setAxisOrientation(AxisOrientation axisOrientation) {
    getTimelineOptions().axisOrientation = axisOrientation.getName();
  }
  
  public void setZoomable(boolean zoomable) {
    getTimelineOptions().zoomable = zoomable;
  }
  
  public void setMoveable(boolean moveable) {
    getTimelineOptions().moveable = moveable;
  }
  
  public void setZoomRange(Long zoomMin, Long zoomMax) {
    getTimelineOptions().zoomMin = zoomMin;
    getTimelineOptions().zoomMax = zoomMax;
  }
    
  public void setSelectable(boolean selectable) {
    getTimelineOptions().selectable = selectable;
  }
  
  public void setShowCurentTime(boolean showCurrentTime) {
    getTimelineOptions().showCurrentTime = showCurrentTime;
  }
      
  @Override
  public void setHeight(String height) {
    getTimelineOptions().height = height;
  }
  
  @Override
  public void setMaxHeight(String maxHeight) {
    getTimelineOptions().maxHeight = maxHeight;
  }
  
  public void setStart(LocalDateTime start) {
    getTimelineOptions().start = start;
  }
  
  public void setStack(boolean stack) {
    getTimelineOptions().stack = stack;
  }
  
  public void setMultiselect(boolean multiselect) {
    getTimelineOptions().multiselect = multiselect;
  }
  
  public void setShowTooltips(boolean showTooltips) {
    getTimelineOptions().showTooltips = showTooltips;
  }
  
  protected ClusterOptions getClusterOptions() {
    return this.clusterOptions;
  }
  
  /**
   * If true, the timeline can merge overlapped items.
   * 
   * @param cluster 
   */
  public void setCluster(boolean cluster) {
    getClusterOptions().cluster = cluster;
  }
  
  /**
   * Defines a template for tooltip for merged items.
   * 
   * @param titleTemplate the tooltip template
   */
  public void setClusterTitleTemplate(String titleTemplate) {
    getClusterOptions().titleTemplate = titleTemplate;
  }
  
  public void setClusterMaxItems(Integer maxItems) {
    getClusterOptions().maxItems = maxItems;
  }
  
  protected EditableOptions getEditableOptions() {
    return editableOptions;
  }
  
  public void setEditable(boolean editable) {
    getEditableOptions().editable = editable;
  }
  
  /** 
   * This option should be set true to allow all timeline items to be resizables or draggables.
   * 
   * @param updateTime
   */
  public void setUpdateTime(boolean updateTime) {
    getEditableOptions().updateTime = updateTime;
  }
  
  public void setOverrideItemsOptions(boolean overrideItems) {
    getEditableOptions().overrideItems = overrideItems;
  }
  
  @ClientCallable
  public void onMove(String itemId, String itemNewStart, String itemNewEnd) {
    LocalDateTime newStart = TimelineUtil.convertLocalDateTime(itemNewStart);
    LocalDateTime newEnd = TimelineUtil.convertLocalDateTime(itemNewEnd);        
    fireItemMoveEvent(itemId, newStart, newEnd, true);    
  }
  
  /**
   * Fires a {@link ItemMoveEvent}.
   * 
   * @param itemId
   * @param newStart
   * @param newEnd
   * @param fromClient
   */
  protected void fireItemMoveEvent(String itemId, LocalDateTime newStart, LocalDateTime newEnd, boolean fromClient) {
    ItemMoveEvent event = new ItemMoveEvent(this, itemId, newStart, newEnd,fromClient);
    fireEvent(event);
    if(event.isCancelled()) {
      // if update is cancelled revert item resizing
      Item item = items.stream()
          .filter(i -> itemId.equals(i.getId()))
          .findFirst().orElse(null);
      if(item != null) {        
        this.getElement().executeJs("vcftimeline.revertMove($0, $1, $2)", this, itemId, item.toJSON());
      }     
    } else {
      //update item in list
      items.stream()
      .filter(i -> itemId.equals(i.getId()))
      .findFirst()
      .ifPresent(item -> {
        item.setStart(newStart); 
        item.setEnd(newEnd);
      });
    }
  }
  
  /**
   * Event thrown when an item is moved (dragged or resized).
   */
  public static class ItemMoveEvent extends ComponentEvent<Timeline> {

    private final String itemId;    
    private final LocalDateTime newStart;    
    private final LocalDateTime newEnd;
    private boolean cancelled = false;

    public ItemMoveEvent(Timeline source, String itemId, LocalDateTime newStart, LocalDateTime newEnd, boolean fromClient) {
      super(source, fromClient);
      this.itemId = itemId;
      this.newStart = newStart;
      this.newEnd = newEnd;
    }     

    public String getItemId() {
      return itemId;
    }
    
    public LocalDateTime getNewStart() {
      return newStart;
    }

    public LocalDateTime getNewEnd() {
      return newEnd;
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
  
  /**
   * Adds a  listener for {@link ItemMoveEvent} to the component.
   *
   * @param listener the listener to be added.
   */
  public void addItemMoveListener(ComponentEventListener<ItemMoveEvent> listener) {
    addListener(ItemMoveEvent.class, listener);
  }
}
