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

import com.vaadin.componentfactory.timeline.Timeline;
import elemental.json.Json;
import elemental.json.JsonObject;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Representation of the different options that can be configure for a {@link Timeline} component.
 */
public class TimelineOptions {

  /* Minimum Date for the visible range.*/
  public LocalDateTime min;

  /* Maximum Date for the visible range (excluded).*/
  public LocalDateTime max;

  /* Orientation of the timeline axis. */
  public String axisOrientation = AxisOrientation.TOP.getName();

  /* Minimum zoom interval for the visible range in milliseconds. */
  public Long zoomMin = 10L;

  /* Maximum zoom interval for the visible range in milliseconds. */
  public Long zoomMax = 315360000000000L;

  /* Specifies whether the Timeline can be moved and zoomed by dragging the window. */
  public boolean moveable = true;

  /* When true, the items in the timeline can be manipulated.
   * Only applicable when option selectable is true.
   */
  public boolean editable = false;

  /* Specifies whether the Timeline can be zoomed by pinching or scrolling in the window.
   * Only applicable when option moveable is true.
   */
  public boolean zoomable = true;

  /* If true, the items on the timeline can be selected. */
  public boolean selectable = true;

  /* Show a vertical bar at the current time */
  public boolean showCurrentTime = false;

  /* The width of the timeline in pixels or as a percentage. */
  public String width = "100%";

  /* The height of the timeline in pixels or as a percentage.
   * When height is undefined or null, the height of the timeline is
   * automatically adjusted to fit the contents. */
  public String height;

  /* Specifies the maximum height for the Timeline. */
  public String maxHeight;

  /* If false items will not be stacked on top of each other such that they overlap. */
  public boolean stack = false;

  /* The initial start date for the axis of the timeline.
   * If not provided, the earliest date present in the events is taken as start date. */
  public LocalDateTime start;
  
  /* The initial end date for the axis of the timeline. If not provided, 
   * the latest date present in the items set is taken as end date. */
  public LocalDateTime end;
  
  /* If true, multiple items can be selected using ctrl+click, shift+click, or by holding items.
   * Only applicable when option selectable is true. */
  public boolean multiselect = false;

  /* If true, items with titles will display a tooltip.
   * If false, item tooltips are prevented from showing. */
  public boolean showTooltips = true;

  /* By default snap is set to fifteen minutes */
  public Integer snapStep = SnapStep.QUARTER.getMinutes();
  
  public boolean autoZoom = false;
  
  public boolean tooltipOnItemUpdateTime = false;
  
  public String tooltipOnItemUpdateTimeDateFormat;
  
  public String tooltipOnItemUpdateTimeTemplate;

  public String toJSON() {
    JsonObject js = Json.createObject();
    Optional.ofNullable(min).ifPresent(v -> js.put("min", v.toString()));
    Optional.ofNullable(max).ifPresent(v -> js.put("max", v.toString()));
    JsonObject orientationJs = Json.createObject();
    orientationJs.put("axis", axisOrientation);
    orientationJs.put("item", "top");
    js.put("orientation", orientationJs);

    js.put("zoomMin", zoomMin);
    js.put("zoomMax", zoomMax);
    js.put("moveable", moveable);
    js.put("zoomable", zoomable);
    js.put("selectable", selectable);
    js.put("editable", editable);
    js.put("showCurrentTime", showCurrentTime);
    js.put("width", width);

    Optional.ofNullable(height).ifPresent(v -> js.put("height", v));
    Optional.ofNullable(maxHeight).ifPresent(v -> js.put("maxHeight", v));
    js.put("stack", stack);
    Optional.ofNullable(start).ifPresent(v -> js.put("start", v.toString()));
    Optional.ofNullable(end).ifPresent(v -> js.put("end", v.toString()));
    js.put("multiselect", multiselect);
    js.put("showTooltips", showTooltips);
    js.put("snapStep", snapStep);
    js.put("autoZoom", autoZoom);
    
    js.put("tooltipOnItemUpdateTime", tooltipOnItemUpdateTime);
    Optional.ofNullable(tooltipOnItemUpdateTimeDateFormat).ifPresent(v -> js.put("tooltipOnItemUpdateTimeDateFormat", v.toString()));
    Optional.ofNullable(tooltipOnItemUpdateTimeTemplate).ifPresent(v -> js.put("tooltipOnItemUpdateTimeTemplate", v.toString()));

    return js.toJson();
  }
}
