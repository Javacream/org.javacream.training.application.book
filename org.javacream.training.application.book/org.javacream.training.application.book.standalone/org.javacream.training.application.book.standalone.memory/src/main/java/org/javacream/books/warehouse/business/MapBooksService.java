package org.javacream.books.warehouse.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.javacream.books.isbngenerator.IsbnGenerator;
import org.javacream.books.warehouse.Book;
import org.javacream.books.warehouse.BookException;
import org.javacream.books.warehouse.BooksService;
import org.javacream.store.StoreService;


public class MapBooksService implements BooksService {

	public MapBooksService(){
		this.books = new HashMap<String, Book>();
	}
	public MapBooksService(IsbnGenerator isbnGenerator,
			Map<String, Book> books, StoreService storeService) {
		super();
		this.isbnGenerator = isbnGenerator;
		this.books = books;
		this.storeService = storeService;
	}


	private IsbnGenerator isbnGenerator;
	private Map<String, Book> books;
	private StoreService storeService;
	
	{
		books = new HashMap<String, Book>();
	}

	
	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	public void setIsbnGenerator(IsbnGenerator isbnGenerator) {
		this.isbnGenerator = isbnGenerator;
	}

	public String newBook(String title) throws BookException {
		String isbn = isbnGenerator.next();
		Book book = new Book();
		book.setIsbn(isbn);
		book.setTitle(title);
		books.put(isbn, book);
		return isbn;
	}

	public Book findBookByIsbn(String isbn) throws BookException {
		Book result = books.get(isbn);
		if (result == null) {
			throw new BookException(BookException.BookExceptionType.NOT_FOUND,
					isbn);
		}
		result.setAvailable(storeService.getStock("books", isbn) > 0);
		
		return SerializationUtils.clone(result);
	}

	public Book updateBook(Book book) throws BookException {
		books.put(book.getIsbn(), SerializationUtils.clone(book)); 
		return book;
	}

	public void deleteBookByIsbn(String isbn) throws BookException {
		Object result = books.remove(isbn);
		if (result == null) {
			throw new BookException(
					BookException.BookExceptionType.NOT_DELETED, isbn);
		}
	}


	public Collection<Book> findAllBooks() {
		return SerializationUtils.clone(new ArrayList<Book>(books.values()));
	}
	public void setBooks(Map<String, Book> books) {
		this.books = books;
	}
	
}
