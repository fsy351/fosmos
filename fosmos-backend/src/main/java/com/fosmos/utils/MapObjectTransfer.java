package com.fosmos.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

public class MapObjectTransfer {

    public static Object mapToObject(Map map, Class<?> beanClass){
        if (map == null)
            return null;
        Object object = null;
        try {
            object = beanClass.newInstance();
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                    continue;
                }
                field.setAccessible(true);
                field.set(object, map.get(field.getName()));
            }
        } catch (IllegalAccessException e) {

        } catch (InstantiationException e) {

        }
        return object;
    }
}
