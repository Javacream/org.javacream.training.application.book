package org.javacream.books.isbngenerator.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.javacream.books.isbngenerator.IsbnGenerator;
import org.javacream.books.isbngenerator.IsbnGeneratorStrategy;
import org.javacream.books.isbngenerator.business.JpaIsbnGenerator;

public class IsbnGeneratorProducer {
	@Inject @IsbnGeneratorStrategy(strategyName="jpa")

	private JpaIsbnGenerator jpaIsbnGeneratorImpl;

	@Produces @ApplicationScoped
	public IsbnGenerator create() {
		jpaIsbnGeneratorImpl.setPrefix("ISBN:");
		jpaIsbnGeneratorImpl.setCountryCode("-de");
		return jpaIsbnGeneratorImpl;

	}
}
