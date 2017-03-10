package org.javacream.books.isbngenerator.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.javacream.books.isbngenerator.IsbnGenerator;
import org.javacream.books.isbngenerator.IsbnGeneratorStrategy;
import org.javacream.books.isbngenerator.business.RandomIsbnGeneratorImpl;

public class IsbnGeneratorProducer {
	@Inject @IsbnGeneratorStrategy(strategyName="random")

	private RandomIsbnGeneratorImpl randomIsbnGeneratorImpl;

	@Produces @ApplicationScoped
	public IsbnGenerator create() {
		randomIsbnGeneratorImpl.setPrefix("ISBN:");
		randomIsbnGeneratorImpl.setCountryCode("-de");
		return randomIsbnGeneratorImpl;

	}
}
