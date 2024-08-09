package org.example.jpahibernatehomework.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.jpahibernatehomework.model.Book;
import org.example.jpahibernatehomework.model.request.BookRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public class BookRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public BookRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Transactional
    public Book addNewBook(BookRequest bookRequest) {
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setDescription(bookRequest.getDescription());
        book.setPublicationYear(bookRequest.getPublicationYear());
        entityManager.persist(book);
        return book;
    }

    public List<Book> getAllBook() {
        return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    public Book getBookById(UUID bookId) {
        return entityManager.find(Book.class,bookId);
    }

    public List<Book> getBookByTitle(String title) {
        String lowerCaseTitle = "%" + title.toLowerCase() + "%";
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b WHERE LOWER(b.title) LIKE :title", Book.class);
        query.setParameter("title", lowerCaseTitle);
        return query.getResultList();
    }

    @Transactional
    public void deleteBookById(UUID bookId) {
        Book book = entityManager.find(Book.class,bookId);
        if(book != null){
            entityManager.remove(book);
        }
    }

    @Transactional
    public Book updateBookById(UUID bookId, BookRequest bookRequest) {
        Book book = entityManager.find(Book.class,bookId);
        entityManager.detach(book);
        if(book != null){
            book.setTitle(bookRequest.getTitle());
            book.setDescription(bookRequest.getDescription());
            book.setAuthor(bookRequest.getAuthor());
            book.setPublicationYear(bookRequest.getPublicationYear());
            entityManager.merge(book);
        }
        return book;
    }
}
