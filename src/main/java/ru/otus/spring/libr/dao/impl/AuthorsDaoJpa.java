package ru.otus.spring.libr.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.libr.dao.AuthorsDao;
import ru.otus.spring.libr.entities.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("JpaQlInspection")
@Repository
public class AuthorsDaoJpa implements AuthorsDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveAuthor(Author author) {
        entityManager.persist(author);
    }

    @Override
    public Optional<Author> getAuthorByName(String name) {
        TypedQuery<Author> query
                = entityManager.createQuery("select a from Author a where a.name = :name", Author.class);
        query.setParameter("name", name);
        return query.getResultStream().findAny();
    }

    @Override
    public Optional<Author> getAuthorById(long id) {
        TypedQuery<Author> query
                = entityManager.createQuery("select a from Author a where a.id = :id", Author.class);
        query.setParameter("id", id);
        return query.getResultStream().findAny();
    }

    @Override
    public List<Author> getAllAuthors() {
        TypedQuery<Author> query
                = entityManager.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void deleteAllAuthors() {
        Query query = entityManager.createQuery("delete from Author");
        query.executeUpdate();
    }
}
