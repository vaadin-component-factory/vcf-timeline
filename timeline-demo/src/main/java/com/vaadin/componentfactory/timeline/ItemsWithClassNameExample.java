package com.vaadin.componentfactory.timeline;

import com.vaadin.componentfactory.timeline.model.Item;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@CssImport(value = "./styles/timeline-items-style.css")
@Route(value = "classname-items", layout = MainLayout.class)
public class ItemsWithClassNameExample extends Div {

  public ItemsWithClassNameExample() {
    // create items
    Item item1 =
        new Item(
            LocalDateTime.of(2021, 8, 11, 2, 30, 00),
            LocalDateTime.of(2021, 8, 11, 7, 00, 00),
            "red");
    item1.setId("1");
    item1.setClassName("red");

    Item item2 =
        new Item(
            LocalDateTime.of(2021, 8, 11, 8, 00, 00),
            LocalDateTime.of(2021, 8, 12, 1, 00, 00),
            "green");
    item2.setId("2");
    item2.setClassName("green");

    Item item3 =
        new Item(
            LocalDateTime.of(2021, 8, 12, 3, 30, 00),
            LocalDateTime.of(2021, 8, 12, 8, 00, 00),
            "orange");
    item3.setId("3");
    item3.setClassName("orange");

    List<Item> items = Arrays.asList(item1, item2, item3);

    // empty timeline creation
    Timeline timeline = new Timeline(items);

    // setting timeline range
    timeline.setTimelineRange(
        LocalDateTime.of(2021, 8, 10, 00, 00, 00), LocalDateTime.of(2021, 8, 15, 00, 00, 00));

    add(timeline);
  }
}
