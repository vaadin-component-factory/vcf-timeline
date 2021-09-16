package com.vaadin.componentfactory.timeline.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimelineUtil {
  
  public static LocalDateTime convertLocalDateTime(String stringDate) {
      return LocalDateTime.parse(stringDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }

}

