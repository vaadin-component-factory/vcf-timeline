package com.vaadin.componentfactory.timeline;

import com.vaadin.componentfactory.timeline.model.Item;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import java.time.LocalDateTime;

@Route(value = "add-empty-items", layout = MainLayout.class)
public class AddEmptyItemsExample extends VerticalLayout {

  private Button addItemButton;
  private Item newItem;

  public AddEmptyItemsExample() {

    // example description
    add(new Paragraph("Example showing how to add empty item boxes with a given date/time range"));

    // empty timeline creation
    Timeline timeline = new Timeline();
    timeline.setTimelineRange(
        LocalDateTime.of(2021, 8, 10, 00, 00, 00), LocalDateTime.of(2021, 8, 22, 00, 00, 00));
    timeline.setHeight("200px");

    // add item
    VerticalLayout selectRangeLayout = new VerticalLayout();
    selectRangeLayout.setSpacing(false);
    Paragraph p = new Paragraph("Select range for new item: ");
    p.getElement().getStyle().set("margin-bottom", "5px");
    selectRangeLayout.add(p);

    DateTimePicker datePicker1 = new DateTimePicker("Item start date: ");
    datePicker1.setMin(LocalDateTime.of(2021, 8, 10, 00, 00, 00));
    datePicker1.setMax(LocalDateTime.of(2021, 8, 22, 00, 00, 00));

    DateTimePicker datePicker2 = new DateTimePicker("Item end date: ");
    datePicker2.setMin(LocalDateTime.of(2021, 8, 10, 00, 00, 00));
    datePicker2.setMax(LocalDateTime.of(2021, 8, 22, 00, 00, 00));

    datePicker1.addValueChangeListener(
        e -> newItem = createNewItem(datePicker1.getValue(), datePicker2.getValue()));
    datePicker2.addValueChangeListener(
        e -> newItem = createNewItem(datePicker1.getValue(), datePicker2.getValue()));

    HorizontalLayout horizontalLayout = new HorizontalLayout();
    horizontalLayout.add(datePicker1, datePicker2);

    addItemButton =
        new Button(
            "Add Item",
            e -> {
              timeline.addItem(newItem);
              newItem = null;
              datePicker1.clear();
              datePicker2.clear();
            });
    addItemButton.setDisableOnClick(true);
    addItemButton.setEnabled(false);

    selectRangeLayout.add(horizontalLayout, addItemButton);

    add(timeline, selectRangeLayout);
  }

  private Item createNewItem(LocalDateTime start, LocalDateTime end) {
    if (start != null && end != null) {
      if (start.isBefore(end)) {
        addItemButton.setEnabled(true);
        return new Item(start, end);
      } else {
        Notification.show("End date should be after start date", 5000, Position.MIDDLE);
        return null;
      }
    } else {
      addItemButton.setEnabled(false);
      return null;
    }
  }
}
