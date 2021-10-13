package com.vaadin.componentfactory.timeline;

import com.vaadin.componentfactory.timeline.model.Item;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

@Route(value = "update-item-content", layout = MainLayout.class)
public class UpdateItemContentExample extends Div {

  public UpdateItemContentExample() {

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

    // timeline creation
    Timeline timeline = new Timeline(items);

    // setting timeline range
    timeline.setTimelineRange(
        LocalDateTime.of(2021, 8, 10, 00, 00, 00), LocalDateTime.of(2021, 8, 15, 00, 00, 00));

    add(timeline);

    ComboBox<Item> itemsSelection = new ComboBox<>("Select item to update: ", items);
    itemsSelection.setItemLabelGenerator(Item::getContent);

    TextField itemNewContent = new TextField("Enter new item content: ");

    Button updateItemButton =
        new Button(
            "Update",
            e -> {
              Item item = itemsSelection.getValue();
              String content = itemNewContent.getValue();
              if (item != null && StringUtils.isNotBlank(content)) {
                timeline.updateItemContent(item.getId(), content);
                itemsSelection.clear();
                itemNewContent.clear();
              } else {
                Notification.show("Select an item and a new content", 5000, Position.MIDDLE);
              }
            });

    HorizontalLayout layout =
        new HorizontalLayout(itemsSelection, itemNewContent, updateItemButton);
    layout.setMargin(true);
    layout.setAlignSelf(Alignment.END, updateItemButton);
    add(layout);
  }
}
