package org.example.template;

import java.lang.reflect.RecordComponent;
import java.util.HashMap;
import java.util.Map;

public interface TemplateModel {
  
  default Map<String, Object> toMap() {
    Map<String, Object> map = new HashMap<>();
    
    if (this.getClass().isRecord()) {
      RecordComponent[] components = this.getClass().getRecordComponents();
      for (RecordComponent component : components) {
        try {
          Object value = component.getAccessor().invoke(this);
          map.put(component.getName(), value != null ? value : null);
        } catch (Exception e) {
          e.printStackTrace(); // Handle exceptions appropriately
        }
      }
    }
    
    return map;
  }
}
