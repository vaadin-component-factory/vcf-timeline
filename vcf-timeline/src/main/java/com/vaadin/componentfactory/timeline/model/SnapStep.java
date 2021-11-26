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

/** Snap for timeline is restricted to these values: one hour, half hour or fifteen minutes. */
public enum SnapStep {
  ONE_HOUR(60),
  HALF_HOUR(30),
  QUARTER(15);

  private Integer minutes;

  private SnapStep(Integer minutes) {
    this.minutes = minutes;
  }

  public Integer getMinutes() {
    return minutes;
  }
}
