# Timeline component for Vaadin Flow

Timeline component uses [vis-timeline](https://visjs.github.io/vis-timeline/docs/timeline/) component to display data in time.

This component is part of Vaadin Component Factory.

## Description 

Timeline component provides support to the following features:

- Create a timeline with a defined visible range.
- Make items readonly.
- Update items content.
- Give items a style by defining a class name.
- Edit an item by resizing it.
- Edit an item by drag and drop.
- Multiple items selection.
- Possiblity to define zoom options (e.g. 1 day, 3 days, 5 days).
- Items are shown connected by an horizontal lines between them. (*)
- Show tooltips for items.

(*) Horizontal lines implementation is based on [timeline-arrows](https://github.com/javdome/timeline-arrows).

## Development instructions

Build the project and install the add-on locally:
```
mvn clean install
```
Starting the demo server:

Go to timeline-demo and run:
```
mvn jetty:run
```

This deploys demo at http://localhost:8080

## How to use it

Create a new component Timeline:

```java
Timeline timeline = new Timeline(items);
```

## Examples

## Missing features or bugs

You can report any issue or missing feature on github: https://github.com/vaadin-component-factory/timeline
