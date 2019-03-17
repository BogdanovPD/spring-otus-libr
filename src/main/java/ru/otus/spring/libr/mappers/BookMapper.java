package ru.otus.spring.libr.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.otus.spring.libr.dao.AuthorsDao;
import ru.otus.spring.libr.dao.GenresDao;
import ru.otus.spring.libr.entities.Author;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.entities.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookMapper implements RowMapper<Book> {

    private final AuthorsDao authorsDao;
    private final GenresDao genresDao;

    @Override
    public Book mapRow(ResultSet resultSet, int i)throws SQLException {
        long id=resultSet.getLong("id");
        String name = resultSet.getString("name");
        long authorId = resultSet.getLong("author_id");
        Optional<Author> authorOptional = authorsDao.getAuthorById(authorId);
        long genreId = resultSet.getLong("genre_id");
        Optional<Genre> genreOptional = genresDao.getGenreById(genreId);
        return Book.builder()
                .id(id)
                .name(name)
                .author(authorOptional.orElse(null))
                .genre(genreOptional.orElse(null))
                .build();
    }
}
