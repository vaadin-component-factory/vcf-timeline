package com.vaadin.componentfactory.timeline;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

  public MainLayout() {
    final DrawerToggle drawerToggle = new DrawerToggle();
    
    final RouterLink readonlyEmptyExample = new RouterLink("Readonly empty timeline", ReadonlyEmptyExample.class);
    final RouterLink addEmptyItemsExample = new RouterLink("Add empty items", AddEmptyItemsExample.class);
    final RouterLink clusteringExample = new RouterLink("Merge overlapped items", ClusteringExample.class);
    final RouterLink resizeItems = new RouterLink("Resize items", ResizeItemsExample.class);
    final RouterLink dndItems = new RouterLink("Drag and drop items", DragAndDropItemsExample.class);
            
    final VerticalLayout menuLayout = new VerticalLayout(readonlyEmptyExample, addEmptyItemsExample, clusteringExample, resizeItems, dndItems);
    addToDrawer(menuLayout);
    addToNavbar(drawerToggle);
  }
   
}