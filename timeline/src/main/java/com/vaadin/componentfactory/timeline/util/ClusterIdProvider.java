package com.vaadin.componentfactory.timeline.util;

import com.vaadin.componentfactory.timeline.model.Item;
import com.vaadin.flow.function.SerializableFunction;

@FunctionalInterface
public interface ClusterIdProvider extends SerializableFunction<Item, String> {

    @Override
    String apply(Item item);
}
