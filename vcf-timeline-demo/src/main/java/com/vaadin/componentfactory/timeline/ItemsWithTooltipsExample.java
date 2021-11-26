package com.vaadin.componentfactory.timeline;

import com.vaadin.componentfactory.timeline.model.Item;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Route(value = "tooltips-items", layout = MainLayout.class)
public class ItemsWithTooltipsExample extends Div {

  public ItemsWithTooltipsExample() {

    // create items
    Item item1 =
        new Item(
            LocalDateTime.of(2021, 8, 11, 2, 30, 00),
            LocalDateTime.of(2021, 8, 11, 7, 00, 00),
            "Item 1");
    item1.setId("Item 1");
    item1.setTitle("Tooltip for Item 1");
    item1.setEditable(true);
    item1.setUpdateTime(true);

    Item item2 =
        new Item(
            LocalDateTime.of(2021, 8, 12, 0, 00, 00),
            LocalDateTime.of(2021, 8, 12, 12, 00, 00),
            "Item 2");
    item2.setId("Item 2");
    item2.setTitle("<b>Tooltip for Item 2</b>");

    Item item3 =
        new Item(
            LocalDateTime.of(2021, 8, 13, 2, 30, 00),
            LocalDateTime.of(2021, 8, 14, 1, 00, 00),
            "No tooltip");
    item3.setId("Item 3");

    List<Item> items = Arrays.asList(item1, item2, item3);

    // empty timeline creation
    Timeline timeline = new Timeline(items);

    // setting timeline range
    timeline.setTimelineRange(
        LocalDateTime.of(2021, 8, 10, 00, 00, 00), LocalDateTime.of(2021, 8, 15, 00, 00, 00));

    add(timeline);
  }
}
