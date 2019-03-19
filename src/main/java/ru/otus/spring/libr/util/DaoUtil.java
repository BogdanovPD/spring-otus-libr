package ru.otus.spring.libr.util;

import ru.otus.spring.libr.entities.NamedEntity;

import java.util.HashMap;
import java.util.Map;

public class DaoUtil {

    public static final String BOOK_ID = "book_id";
    public static final String BOOK_NAME = "book_name";
    public static final String AUTHOR_ID = "author_id";
    public static final String AUTHOR_NAME = "author_name";
    public static final String GENRE_ID = "genre_id";
    public static final String GENRE_NAME = "genre_name";

    public static Map<String, Object> getNamedEntityMap(NamedEntity namedEntity) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("name", namedEntity.getName());
        return parameters;
    }

}
