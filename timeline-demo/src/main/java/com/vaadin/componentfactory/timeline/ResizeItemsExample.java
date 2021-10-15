package com.vaadin.componentfactory.timeline;

import com.vaadin.componentfactory.timeline.model.Item;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Route(value = "resize-items", layout = MainLayout.class)
public class ResizeItemsExample extends Div {

  public ResizeItemsExample() {
    // for logging changes
    VerticalLayout log = new VerticalLayout();

    // create items
    Item item1 =
        new Item(
            LocalDateTime.of(2021, 8, 11, 2, 30, 00),
            LocalDateTime.of(2021, 8, 11, 7, 00, 00),
            "Readonly");
    item1.setId("1");
    item1.setEditable(false); // Lock an activity so it can't be edited

    Item item2 =
        new Item(
            LocalDateTime.of(2021, 8, 13, 0, 00, 00),
            LocalDateTime.of(2021, 8, 13, 12, 00, 00),
            "Resizable");
    item2.setId("2");
    item2.setEditable(true);
    item2.setUpdateTime(true); // allow resizing by updateTime property

    Item item3 =
        new Item(
            LocalDateTime.of(2021, 8, 15, 2, 30, 00),
            LocalDateTime.of(2021, 8, 16, 1, 00, 00),
            "Resizable 2");
    item3.setId("3");
    item3.setEditable(true);
    item3.setUpdateTime(true);

    List<Item> items = Arrays.asList(item1, item2, item3);

    // empty timeline creation
    Timeline timeline = new Timeline(items);

    // setting timeline range
    timeline.setTimelineRange(
        LocalDateTime.of(2021, 8, 10, 00, 00, 00), LocalDateTime.of(2021, 8, 25, 00, 00, 00));

    // add listener to get new resized item range values
    timeline.addItemResizeListener(
        e ->
            log.add(
                new Span(
                    "Item: "
                        + e.getItemId()
                        + " was resized. New start: "
                        + formatDates(e.getNewStart())
                        + " - New end: "
                        + formatDates(e.getNewEnd()))));

    add(timeline, log);
  }

  private String formatDates(LocalDateTime date) {
    return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
  }
}
