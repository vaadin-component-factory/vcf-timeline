package com.vaadin.componentfactory.timeline;

import com.vaadin.componentfactory.timeline.model.Item;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import java.time.LocalDateTime;

@Route(value = "clustering", layout = MainLayout.class)
public class ClusteringExample extends VerticalLayout {

  public ClusteringExample() {

    // TODO initial testing

    Item[] items =
        new Item[] {
          new Item(
              LocalDateTime.of(2021, 9, 1, 2, 30, 00),
              LocalDateTime.of(2021, 9, 3, 5, 00, 00),
              "Task 1"),
          new Item(
              LocalDateTime.of(2021, 9, 1, 2, 00, 00),
              LocalDateTime.of(2021, 9, 2, 6, 00, 00),
              "Task 2"),
          new Item(
              LocalDateTime.of(2021, 9, 1, 7, 30, 00),
              LocalDateTime.of(2021, 9, 2, 9, 30, 00),
              "Task 2"),
          new Item(
              LocalDateTime.of(2021, 9, 4, 2, 30, 00),
              LocalDateTime.of(2021, 9, 5, 5, 00, 00),
              "Task 3"),
          new Item(
              LocalDateTime.of(2021, 9, 5, 2, 00, 00),
              LocalDateTime.of(2021, 9, 6, 6, 00, 00),
              "Task 4"),
          new Item(
              LocalDateTime.of(2021, 9, 4, 2, 30, 00),
              LocalDateTime.of(2021, 9, 7, 4, 30, 00),
              "Task 5")
        };

    Timeline timeline = new Timeline(items);

    // set timeline date range
    timeline.setTimelineRange(
        LocalDateTime.of(2021, 8, 25, 00, 00, 00), LocalDateTime.of(2021, 9, 25, 00, 00, 00));

    timeline.setCluster(true);

    timeline.setClusterIdProvider(item -> item.getContent());

    add(timeline);
  }
}
