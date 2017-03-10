package org.javacream.books.warehouse.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.javacream.books.isbngenerator.IsbnGenerator;
import org.javacream.books.warehouse.Book;
import org.javacream.books.warehouse.BookException;
import org.javacream.books.warehouse.BooksService;
import org.javacream.books.warehouse.entities.BookEntity;
import org.javacream.store.StoreService;

@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class JpaBooksService implements BooksService {

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private IsbnGenerator isbnGenerator;
	@Inject
	private StoreService storeService;

	public String newBook(String title) throws BookException {
		String isbn = isbnGenerator.next();
		BookEntity book = new BookEntity(isbn, title, 0.0);
		entityManager.persist(book);
		return isbn;
	}

	public Book findBookByIsbn(String isbn) throws BookException {
		TypedQuery<BookEntity> query = entityManager.createQuery("select b from Book as b where b.isbn = :isbn",
				BookEntity.class);
		query.setParameter("isbn", isbn);
		try {
			Book result = BookAssembler.assemble(query.getSingleResult());
			setAvailability(result);
			return result;
		} catch (Exception e) {
			throw new BookException(BookException.BookExceptionType.NOT_FOUND, isbn);
		}
	}

	public Book updateBook(Book book) throws BookException {
		entityManager.merge(BookAssembler.assemble(book));
		return book;
	}

	public void deleteBookByIsbn(String isbn) throws BookException {
		try {
			TypedQuery<BookEntity> query = entityManager.createQuery("select b from Book as b where b.isbn = :isbn",
					BookEntity.class);
			query.setParameter("isbn", isbn);
			BookEntity bookEntity = query.getSingleResult();
			entityManager.remove(bookEntity);
		} catch (RuntimeException e) {
			throw new BookException(BookException.BookExceptionType.NOT_DELETED, isbn);

		}
	}

	public Collection<Book> findAllBooks() {
		List<BookEntity> bookEntities = entityManager.createQuery("select book from Book as book", BookEntity.class)
				.getResultList();
		return BookAssembler.assemble(bookEntities);
	}

	private void setAvailability(Book book) {
		book.setAvailable(storeService.getStock("books", book.getIsbn()) > 0);

	}
	
	static class BookAssembler{
		static Book assemble(BookEntity bookEntity){
			Book book = new Book(bookEntity.getIsbn(), bookEntity.getTitle(), bookEntity.getPrice());
			return book;
		}
		static BookEntity assemble(Book book){
			BookEntity bookEntity = new BookEntity(book.getIsbn(), book.getTitle(), book.getPrice());
			return bookEntity;
		}

		static List<Book> assemble(List<BookEntity> bookEntities){
			ArrayList<Book> books = new ArrayList<>();
			bookEntities.forEach((book) -> books.add(assemble(book)));
			return books;
		}

	}
}
