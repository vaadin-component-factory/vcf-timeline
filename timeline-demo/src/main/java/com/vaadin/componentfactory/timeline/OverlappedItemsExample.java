package com.vaadin.componentfactory.timeline;

import com.vaadin.componentfactory.timeline.model.Item;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Route(value = "overlapped-items", layout = MainLayout.class)
public class OverlappedItemsExample extends Div {
  
  public OverlappedItemsExample() {
    // create items
    Item item1 = new Item(LocalDateTime.of(2021, 9, 9, 2, 30, 00),
        LocalDateTime.of(2021, 9, 9, 6, 00, 00), "Item 1");
    item1.setId("1");
    item1.setEditable(true);
    item1.setUpdateTime(true);

    Item item2 = new Item(LocalDateTime.of(2021, 9, 9, 4, 00, 00),
        LocalDateTime.of(2021, 9, 9, 5, 00, 00), "Item 2");
    item2.setId("2");
    item2.setEditable(false);
    item2.setClassName("readonly");

    Item item3 = new Item(LocalDateTime.of(2021, 9, 9, 22, 30, 00),
        LocalDateTime.of(2021, 9, 10, 00, 30, 00), "Item 3");
    item3.setId("3");
    item3.setEditable(true);
    item3.setUpdateTime(true);

    Item item4 = new Item(LocalDateTime.of(2021, 9, 10, 1, 30, 00),
        LocalDateTime.of(2021, 9, 10, 3, 00, 00), "Item 4");
    item4.setId("4");
    item4.setEditable(true);
    item4.setUpdateTime(true);

    List<Item> items = Arrays.asList(item1, item2, item3, item4);
    
    // timeline creation
    Timeline timeline = new Timeline(items);

    // setting timeline range
    timeline.setTimelineRange(
        LocalDateTime.of(2021, 9, 8, 00, 00, 00), LocalDateTime.of(2021, 9, 11, 00, 00, 00));
    
    add(timeline);
  }

}
