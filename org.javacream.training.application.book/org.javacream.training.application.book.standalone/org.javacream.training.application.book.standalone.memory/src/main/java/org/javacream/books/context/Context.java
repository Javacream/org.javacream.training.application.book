package org.javacream.books.context;

import org.javacream.books.isbngenerator.business.RandomIsbnGeneratorImpl;
import org.javacream.books.warehouse.business.MapBooksService;
import org.javacream.store.business.SimpleStoreService;

public abstract class Context {

	private static MapBooksService booksService;
	private static SimpleStoreService storeService;
	private static RandomIsbnGeneratorImpl isbnGenerator;

	static{
		MapBooksService mapBooksService = new MapBooksService();
		RandomIsbnGeneratorImpl randomIsbnGeneratorImpl = new RandomIsbnGeneratorImpl();
		randomIsbnGeneratorImpl.setCountryCode("-de");
		randomIsbnGeneratorImpl.setPrefix("ISBN:");
		mapBooksService.setIsbnGenerator(randomIsbnGeneratorImpl);
		SimpleStoreService simpleStoreService = new SimpleStoreService();
		mapBooksService.setStoreService(simpleStoreService);
		Context.booksService = mapBooksService;
		Context.storeService = simpleStoreService;
		Context.isbnGenerator = randomIsbnGeneratorImpl;

	}

	public static MapBooksService getBooksService() {
		return booksService;
	}

	public static SimpleStoreService getStoreService() {
		return storeService;
	}

	public static RandomIsbnGeneratorImpl getIsbnGenerator() {
		return isbnGenerator;
	}
}
