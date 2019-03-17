package ru.otus.spring.libr.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.libr.entities.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet resultSet, int i)throws SQLException {
        long id=resultSet.getLong("id");
        String name=resultSet.getString("name");
        return Author.builder()
                .id(id)
                .name(name)
                .build();
    }
}
