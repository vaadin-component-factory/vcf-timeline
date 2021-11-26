package com.vaadin.componentfactory.timeline;

import com.vaadin.componentfactory.timeline.model.Item;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Route(value = "zoom-options", layout = MainLayout.class)
public class ZoomOptionsExample extends Div {

  public ZoomOptionsExample() {
    // create items
    Item item1 =
        new Item(
            LocalDateTime.of(2021, 8, 11, 2, 30, 00),
            LocalDateTime.of(2021, 8, 11, 8, 00, 00),
            "Item 1");
    item1.setId("1");

    Item item2 =
        new Item(
            LocalDateTime.of(2021, 8, 11, 9, 00, 00),
            LocalDateTime.of(2021, 8, 11, 17, 00, 00),
            "Item 2");
    item2.setId("2");

    Item item3 =
        new Item(
            LocalDateTime.of(2021, 8, 12, 0, 30, 00),
            LocalDateTime.of(2021, 8, 12, 3, 00, 00),
            "Item 3");
    item3.setId("3");

    Item item4 =
        new Item(
            LocalDateTime.of(2021, 8, 12, 4, 30, 00),
            LocalDateTime.of(2021, 8, 12, 20, 00, 00),
            "Item 4");
    item4.setId("4");

    Item item5 =
        new Item(
            LocalDateTime.of(2021, 8, 12, 21, 30, 00),
            LocalDateTime.of(2021, 8, 13, 01, 15, 00),
            "Item 5");
    item5.setId("5");

    List<Item> items = Arrays.asList(item1, item2, item3, item4, item5);

    items.forEach(i -> {
      i.setEditable(true);
      i.setUpdateTime(true);
    });
 
    // timeline creation
    Timeline timeline = new Timeline(items);

    // setting timeline range
    timeline.setTimelineRange(
        LocalDateTime.of(2021, 8, 10, 00, 00, 00), LocalDateTime.of(2021, 8, 15, 00, 00, 00));

    timeline.setMultiselect(true);

    HorizontalLayout zoomOptionsLayout = new HorizontalLayout();
    zoomOptionsLayout.setMargin(true);
    Button oneDay = new Button("1 day", e -> timeline.setZoomOption(1));
    Button threeDays = new Button("3 days", e -> timeline.setZoomOption(3));
    Button fiveDays = new Button("5 days", e -> timeline.setZoomOption(5));
    zoomOptionsLayout.add(oneDay, threeDays, fiveDays);
    zoomOptionsLayout.setJustifyContentMode(JustifyContentMode.END);
    zoomOptionsLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);

    add(zoomOptionsLayout, timeline);
  }
}
