package org.javacream.books.warehouse.test;

import org.javacream.books.context.Context;
import org.javacream.books.isbngenerator.business.RandomIsbnGeneratorImpl;
import org.javacream.books.warehouse.business.MapBooksService;
import org.javacream.store.business.SimpleStoreService;
import org.junit.Test;

/**
 * 
 * @author Dr. Rainer Sawitzki
 * @company Javacream
 * @mailto training@rainer-sawitzki.de
 * 
 */
public class BooksServiceTest {

	
	@Test
	public void testBusinessObjects() {
		MapBooksService mapBooksService = new MapBooksService();
		RandomIsbnGeneratorImpl randomIsbnGeneratorImpl = new RandomIsbnGeneratorImpl();
		randomIsbnGeneratorImpl.setCountryCode("-de");
		mapBooksService.setIsbnGenerator(randomIsbnGeneratorImpl);
		mapBooksService.setStoreService(new SimpleStoreService());
		randomIsbnGeneratorImpl.setPrefix("TEST:");
		
		TestActor.doTest(mapBooksService);
		
	
	}

	@Test
	public void testContext() {
		TestActor.doTest(Context.getBooksService());
		
	
	}
	

}
