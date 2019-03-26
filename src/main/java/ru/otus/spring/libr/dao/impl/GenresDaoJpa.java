package ru.otus.spring.libr.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.libr.dao.GenresDao;
import ru.otus.spring.libr.entities.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("JpaQlInspection")
@Repository
public class GenresDaoJpa implements GenresDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveGenre(Genre genre) {
        entityManager.persist(genre);
    }

    @Override
    public Optional<Genre> getGenreByName(String name) {
        TypedQuery<Genre> query
                = entityManager.createQuery("select g from Genre g where g.name = :name", Genre.class);
        query.setParameter("name", name);
        return query.getResultStream().findAny();
    }

    @Override
    public Optional<Genre> getGenreById(long id) {
        TypedQuery<Genre> query
                = entityManager.createQuery("select g from Genre g where g.id = :id", Genre.class);
        query.setParameter("id", id);
        return query.getResultStream().findAny();
    }

    @Override
    public List<Genre> getAllGenres() {
        TypedQuery<Genre> query
                = entityManager.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void deleteAllGenres() {
        Query query = entityManager.createQuery("delete from Genre");
        query.executeUpdate();
    }
}
