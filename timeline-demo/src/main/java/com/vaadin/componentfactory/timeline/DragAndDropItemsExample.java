package com.vaadin.componentfactory.timeline;

import com.vaadin.componentfactory.timeline.model.Item;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Route(value = "drag-and-drop-items", layout = MainLayout.class)
public class DragAndDropItemsExample extends Div {
  
  public DragAndDropItemsExample() {
    // for logging changes
    VerticalLayout log = new VerticalLayout();

    // create items
    Item item1 = new Item(LocalDateTime.of(2021, 8, 11, 2, 30, 00), LocalDateTime.of(2021, 8, 11, 7, 00, 00), "Item 1");
    item1.setId("Item 1");
    item1.setEditable(true);
    item1.setUpdateTime(true);
    
    Item item2 = new Item(LocalDateTime.of(2021, 8, 13, 0, 00, 00), LocalDateTime.of(2021, 8, 13, 12, 00, 00), "Item 2");
    item2.setId("Item 2");
    item2.setEditable(true);
    item2.setUpdateTime(true);
 
    Item item3 = new Item(LocalDateTime.of(2021, 8, 14, 2, 30, 00), LocalDateTime.of(2021, 8, 15, 1, 00, 00), "Item 3");
    item3.setId("Item 3");
    item3.setEditable(true);
    item3.setUpdateTime(true);
    
    Item[] items = new Item[] {item1, item2, item3};
    
    // empty timeline creation
    Timeline timeline = new Timeline(items);
    
    // setting timeline range
    timeline.setTimelineRange(LocalDateTime.of(2021, 8, 10, 00, 00, 00), LocalDateTime.of(2021, 8, 25, 00, 00, 00));
    
    // set multiselet so multiple items can be drag at once
    timeline.setMultiselect(true);
    
    // add listener to get new range values for drag item(s)
    timeline.addItemMoveListener(e -> {
      e.setCancelled(new Random().nextBoolean());
      if(e.isCancelled()) {
        log.add(new Span("Moving item " + e.getItemId() + "is not possible. Moving reverted."));
      } else {
        log.add(new Span("Item: " + e.getItemId() + " was dragged. New start: " + formatDates(e.getNewStart()) + " - New end: " + formatDates(e.getNewEnd())));
      }
    });
    
    timeline.setZoomable(false);
    
    add(timeline, log);
  }  
  
  private String formatDates(LocalDateTime date) {
    return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));
  }

}
