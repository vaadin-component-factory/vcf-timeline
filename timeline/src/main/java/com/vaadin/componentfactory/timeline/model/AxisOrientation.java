package com.vaadin.componentfactory.timeline.model;

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
