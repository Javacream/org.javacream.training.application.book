package org.javacream.books.warehouse.test;

import javax.inject.Inject;

import org.javacream.books.warehouse.BooksService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class BooksServiceTest {

	@Inject
	BooksService booksService;

	@Test
	public void testCdi() {
		TestActor.doTest(booksService);

	}
	
	@Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addPackages(true, "org.javacream")
            .addAsResource("META-INF/beans.xml","META-INF/beans.xml");
    }


}
