package ru.otus.spring.libr.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.libr.entities.Author;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.entities.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.otus.spring.libr.util.DaoUtil.*;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int i)throws SQLException {
        long id = resultSet.getLong(BOOK_ID);
        String name = resultSet.getString(BOOK_NAME);
        long authorId = resultSet.getLong(AUTHOR_ID);
        String authorName = resultSet.getString(AUTHOR_NAME);
        long genreId = resultSet.getLong(GENRE_ID);
        String genreName = resultSet.getString(GENRE_NAME);
        return Book.builder()
                .id(id)
                .name(name)
                .author(
                        Author.builder()
                                .id(authorId)
                                .name(authorName)
                                .build())
                .genre(
                        Genre.builder()
                                .id(genreId)
                                .name(genreName)
                                .build())
                .build();
    }
}
