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

/**
 * Per vis library documentation:
 *
 * <p>Orientation of the timeline axis: 'top', 'bottom' (default), 'both', or 'none'. If orientation
 * is 'bottom', the time axis is drawn at the bottom. When 'top', the axis is drawn on top. When
 * 'both', two axes are drawn, both on top and at the bottom. In case of 'none', no axis is drawn at
 * all.
 */
public enum AxisOrientation {
  TOP("top"),
  BOTTOM("bottom"),
  NONE("none"),
  BOTH("both");

  private String name;

  private AxisOrientation(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
