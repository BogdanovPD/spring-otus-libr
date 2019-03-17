package ru.otus.spring.libr.util;

import ru.otus.spring.libr.entities.NamedEntity;

import java.util.HashMap;
import java.util.Map;

public class DaoUtil {

    public static Map<String, Object> getNamedEntityMap(NamedEntity namedEntity) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("name", namedEntity.getName());
        return parameters;
    }

}
