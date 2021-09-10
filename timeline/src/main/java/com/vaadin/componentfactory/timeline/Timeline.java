package com.vaadin.componentfactory.timeline;

import com.vaadin.componentfactory.timeline.model.AxisOrientation;
import com.vaadin.componentfactory.timeline.model.ClusterOptions;
import com.vaadin.componentfactory.timeline.model.Item;
import com.vaadin.componentfactory.timeline.model.TimelineOptions;
import com.vaadin.componentfactory.timeline.util.ClusterIdProvider;
import com.vaadin.flow.component.AttachEvent;
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
  
  private ClusterIdProvider clusterIdProvider;
  
  public Timeline() {
    setId("visualization");
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
    initClusterOptions();
  }

  private void initTimeline() {
    this.getElement().executeJs("vcftimeline.create($0, $1, $2)", this, "[" + convertItemsToJson() + "]", getTimelineOptions().toJSON()); 
  }
  
  private String convertItemsToJson() {
    return this.items != null ? this.items.stream().map(item -> item.toJSON()).collect(Collectors.joining(",")) : "";
  }
  
  private void initClusterOptions() {
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
  
  public void setEditable(boolean editable) {
    getTimelineOptions().editable = editable;
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
}
