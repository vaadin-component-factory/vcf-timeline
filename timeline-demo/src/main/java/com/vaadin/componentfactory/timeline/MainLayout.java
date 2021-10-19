package com.vaadin.componentfactory.timeline;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

  public MainLayout() {
    final DrawerToggle drawerToggle = new DrawerToggle();

    final RouterLink readonlyEmptyExample =
        new RouterLink("Readonly empty timeline", ReadonlyEmptyExample.class);
    final RouterLink addEmptyItemsExample =
        new RouterLink("Add empty items", AddEmptyItemsExample.class);
    final RouterLink resizeItems = new RouterLink("Resize items", ResizeItemsExample.class);
    final RouterLink dndItems =
        new RouterLink("Drag and drop items", DragAndDropItemsExample.class);
    final RouterLink tooltipsExample =
        new RouterLink("Items with tooltips", ItemsWithTooltipsExample.class);
    final RouterLink classNameExample =
        new RouterLink("Items with classnames", ItemsWithClassNameExample.class);
    final RouterLink readonlyExample = new RouterLink("Readonly timeline", ReadonlyExample.class);
    final RouterLink updateItemContentExample =
        new RouterLink("Update item content", UpdateItemContentExample.class);
    final RouterLink zoomOptionsExample = new RouterLink("Zoom options", ZoomOptionsExample.class);
    final RouterLink overlappExample = new RouterLink("Overlapping example", OverlappedItemsExample.class);
    final RouterLink tooltipOnUpdateExample = 
        new RouterLink("Tooltip on item update ", TooltipOnUpdateExample.class);

    final VerticalLayout menuLayout = new VerticalLayout(readonlyEmptyExample, addEmptyItemsExample,
        resizeItems, dndItems, tooltipsExample, classNameExample, readonlyExample,
        updateItemContentExample, zoomOptionsExample, overlappExample, tooltipOnUpdateExample);
    addToDrawer(menuLayout);
    addToNavbar(drawerToggle);
  }
}
