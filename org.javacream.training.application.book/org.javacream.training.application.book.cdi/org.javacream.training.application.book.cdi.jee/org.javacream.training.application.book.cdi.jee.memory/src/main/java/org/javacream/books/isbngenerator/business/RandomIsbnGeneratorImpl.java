package org.javacream.books.isbngenerator.business;

import java.util.Random;

import javax.enterprise.context.ApplicationScoped;

import org.javacream.books.isbngenerator.IsbnGenerator;
import org.javacream.books.isbngenerator.IsbnGeneratorStrategy;

@ApplicationScoped
@IsbnGeneratorStrategy(strategyName="random")
public class RandomIsbnGeneratorImpl implements IsbnGenerator {

	private String prefix;
	private String countryCode;
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String suffix) {
		this.countryCode = suffix;
	}
	private Random random;
	
	{
		random = new Random(this.hashCode() + System.currentTimeMillis());
	}
	
	public String next(){
		return prefix + random.nextInt() + countryCode;
	}

	public String getPrefix(){
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
