package com.vaadin.componentfactory.timeline.model;

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
