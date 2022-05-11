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
import java.util.stream.Collectors;

@Route(value = "drag-and-drop-items", layout = MainLayout.class)
public class DragAndDropItemsExample extends Div {

  public DragAndDropItemsExample() {
    // for logging changes
    VerticalLayout log = new VerticalLayout();

    // create items
    Item item1 =
        new Item(
            LocalDateTime.of(2021, 8, 11, 2, 30, 00),
            LocalDateTime.of(2021, 8, 11, 7, 00, 00),
            "Item 1");
    item1.setId("1");
    item1.setEditable(true);
    item1.setUpdateTime(true);

    Item item2 =
        new Item(
            LocalDateTime.of(2021, 8, 13, 0, 00, 00),
            LocalDateTime.of(2021, 8, 13, 12, 00, 00),
            "Item 2");
    item2.setId("2");
    item2.setEditable(true);
    item2.setUpdateTime(true);

    Item item3 =
        new Item(
            LocalDateTime.of(2021, 8, 14, 2, 30, 00),
            LocalDateTime.of(2021, 8, 15, 1, 00, 00),
            "Item 3");
    item3.setId("3");
    item3.setEditable(true);
    item3.setUpdateTime(true);
    
    Item item4 =
        new Item(
            LocalDateTime.of(2021, 8, 16, 1, 30, 00),
            LocalDateTime.of(2021, 8, 17, 1, 00, 00),
            "Item 4");
    item4.setId("4");
    item4.setEditable(true);
    item4.setUpdateTime(true);

    List<Item> items = Arrays.asList(item1, item2, item3, item4);

    // empty timeline creation
    Timeline timeline = new Timeline(items);

    // setting timeline range
    timeline.setTimelineRange(
        LocalDateTime.of(2021, 8, 1, 00, 00, 00), LocalDateTime.of(2021, 8, 25, 00, 00, 00));

    // set multiselet so multiple items can be drag at once
    timeline.setMultiselect(true);

    // add listener to get new range values for drag item(s)
    timeline.addItemsDragAndDropListener(
        e -> {
          e.setCancelled(cancelMove(e.getItems()));
          if (e.isCancelled()) {
            if(e.getItems().size() == 1) {
              log.add(
                  new Span("Moving item " + e.getItems().get(0).getContent() + " is not allowed. Moving reverted."));
            } else {
              log.add(
                  new Span("Moving items " + e.getItems().stream().map(Item::getContent).collect(Collectors.joining(",")) + " is not allowed. Moving reverted."));
            }
          } else {
            
            for(Item item: e.getItems()) {
              log.add(                  
                  new Span(
                    "Item: "
                        + item.getContent()
                        + " was dragged. New start: "
                        + formatDates(item.getStart())
                        + " - New end: "
                        + formatDates(item.getEnd())));
            }
          }
        });

    timeline.setZoomable(false);

    add(timeline, log);    
  }

  private boolean cancelMove(List<Item> items) {
    return items.stream().anyMatch(i -> i.getId().equals("1") || i.getId().equals("3"));
  }
  
  private String formatDates(LocalDateTime date) {
    return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
  }
}
