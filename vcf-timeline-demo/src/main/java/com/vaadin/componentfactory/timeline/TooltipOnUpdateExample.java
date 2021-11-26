package com.vaadin.componentfactory.timeline;

import com.vaadin.componentfactory.timeline.model.Item;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Route(value = "tooltip-on-update", layout = MainLayout.class)
public class TooltipOnUpdateExample extends Div {
  
  public TooltipOnUpdateExample() {
    
    // create items
    Item item1 = new Item(LocalDateTime.of(2021, 9, 9, 2, 15, 00),
        LocalDateTime.of(2021, 9, 9, 5, 00, 00), "Item 1");
    item1.setId("1");
    item1.setEditable(true);
    item1.setUpdateTime(true);

    Item item2 = new Item(LocalDateTime.of(2021, 9, 9, 10, 00, 00),
        LocalDateTime.of(2021, 9, 9, 15, 00, 00), "Item 2");
    item2.setId("2");
    item2.setEditable(true);
    item2.setUpdateTime(true);

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
    Timeline timeline1 = new Timeline(items);

    // setting timeline range
    timeline1.setTimelineRange(
        LocalDateTime.of(2021, 9, 8, 00, 00, 00), LocalDateTime.of(2021, 9, 11, 00, 00, 00));
    
    // set tooltip on update (default one) 
    timeline1.setTooltipOnItemUpdateTime(true);
    
    Paragraph paragraph1 = new Paragraph("Timeline with Tooltip on item update with default format.");
    
    VerticalLayout timeline1Layout = new VerticalLayout(paragraph1, timeline1);
    timeline1Layout.getElement().getStyle().set("margin-bottom", "50px");
    add(timeline1Layout);
    
    // timeline creation
    Timeline timeline2 = new Timeline(items);

    // setting timeline range
    timeline2.setTimelineRange(
        LocalDateTime.of(2021, 9, 8, 00, 00, 00), LocalDateTime.of(2021, 9, 11, 00, 00, 00));
    
    // set tooltip on update & template with date format
    timeline2.setTooltipOnItemUpdateTime(true);
    timeline2.setTooltipOnItemUpdateTimeTemplate("Starting at item.start. </br> Endind at item.end.");
    timeline2.setTooltipOnItemUpdateTimeDateFormat("YYYY-MM-DD h:mm a");    
    
    Paragraph paragraph2 = new Paragraph("Timeline with Tooltip on item update defined by a template and a date format.");
        
    add(new VerticalLayout(paragraph2, timeline2));    
    
    setHeight("600px");
  }

}
