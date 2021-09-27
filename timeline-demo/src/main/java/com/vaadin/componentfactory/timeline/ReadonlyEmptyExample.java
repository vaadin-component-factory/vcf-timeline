package com.vaadin.componentfactory.timeline;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import java.time.LocalDateTime;

@Route(value = "", layout = MainLayout.class)
public class ReadonlyEmptyExample extends VerticalLayout {

  public ReadonlyEmptyExample() {

    // example description
    add(new Paragraph("Example showing a readonly-empty timeline with a given date/time range"));

    // empty timeline creation
    Timeline timeline = new Timeline();

    // setting timeline range
    timeline.setTimelineRange(
        LocalDateTime.of(2021, 8, 10, 00, 00, 00), LocalDateTime.of(2021, 8, 15, 00, 00, 00));

    // by default, timeline is not editable (readonly)

    add(timeline);
  }
}
