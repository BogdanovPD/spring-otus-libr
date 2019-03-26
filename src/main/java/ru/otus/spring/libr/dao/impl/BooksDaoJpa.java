package ru.otus.spring.libr.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.libr.dao.BooksDao;
import ru.otus.spring.libr.entities.Author;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.entities.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("JpaQlInspection")
@Repository
public class BooksDaoJpa implements BooksDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveBook(Book book) {
        entityManager.persist(book);
    }

    @Override
    public Optional<Book> getBookByName(String name) {
        TypedQuery<Book> query
                = entityManager.createQuery("select b from Book b where b.name = :name", Book.class);
        query.setParameter("name", name);
        return query.getResultStream().findAny();
    }

    @Override
    public Optional<Book> getBookById(long id) {
        TypedQuery<Book> query
                = entityManager.createQuery("select b from Book b where b.id = :id", Book.class);
        query.setParameter("id", id);
        return query.getResultStream().findAny();
    }

    @Override
    public List<Book> getAllBooks() {
        TypedQuery<Book> query
                = entityManager.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public List<Book> getAllBooksByAuthor(Author author) {
        TypedQuery<Book> query
                = entityManager.createQuery("select b from Book b where b.author.id = :id", Book.class);
        query.setParameter("id", author.getId());
        return query.getResultList();
    }

    @Override
    public List<Book> getAllBooksByGenre(Genre genre) {
        TypedQuery<Book> query
                = entityManager.createQuery("select b from Book b where b.genre.id = :id", Book.class);
        query.setParameter("id", genre.getId());
        return query.getResultList();
    }

    @Override
    public List<Book> getAllBooksByAuthorAndGenre(Author author, Genre genre) {
        TypedQuery<Book> query
                = entityManager.createQuery("select b from Book b where b.author.id = :authId" +
                " and b.genre.id = :genreId", Book.class);
        query.setParameter("authId", author.getId());
        query.setParameter("genreId", genre.getId());
        return query.getResultList();
    }

    @Override
    public Book loadComments(Book book) {
        TypedQuery<Book> query
                = entityManager.createQuery(
                "select b from Book b left join fetch b.comments c where b.id = :id", Book.class);
        query.setParameter("id", book.getId());
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void deleteAllBooks() {
        Query query = entityManager.createQuery("delete from Book");
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void deleteAllBooksByAuthor(Author author) {
        Query query = entityManager.createQuery("delete from Book b where b.author.id = :id");
        query.setParameter("id", author.getId());
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void deleteAllBooksByGenre(Genre genre) {
        Query query = entityManager.createQuery("delete from Book b where b.genre.id = :id");
        query.setParameter("id", genre.getId());
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void deleteAllCommentsByBook(Book book) {
        Query query = entityManager.createQuery("delete from Comment c where c.book.id = :id");
        query.setParameter("id", book.getId());
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void deleteAllComments() {
        Query query = entityManager.createQuery("delete from Comment");
        query.executeUpdate();
    }
}
