package org.javacream.books.warehouse.test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.javacream.books.warehouse.BooksService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class BooksServiceTest {

	@PersistenceContext EntityManager entityManager;
	@Inject UserTransaction utx;
	@Before public void init(){
		try {
			utx.begin();
		    entityManager.joinTransaction();
			entityManager.createNativeQuery("create table STOCK (category varchar(1024), item varchar(1024), stock INTEGER, primary key (category, item))").executeUpdate();
			entityManager.createNativeQuery("create table KEYS (actual_key Integer)").executeUpdate();
			entityManager.createNativeQuery("insert into keys values(0)").executeUpdate();
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Inject
	BooksService booksService;

	@Test
	public void testCdi() {
		TestActor.doTest(booksService);

	}

	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class).addPackages(true, "org.javacream").addAsResource("META-INF/beans.xml", "META-INF/beans.xml").addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
		return archive;
	}

}
