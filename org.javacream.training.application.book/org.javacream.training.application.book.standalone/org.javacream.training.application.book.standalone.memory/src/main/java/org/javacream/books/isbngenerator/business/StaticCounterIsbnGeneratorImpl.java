package org.javacream.books.isbngenerator.business;

import org.javacream.books.isbngenerator.IsbnGenerator;

public class StaticCounterIsbnGeneratorImpl implements IsbnGenerator {

	private String prefix;
	private String countryCode;
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String suffix) {
		this.countryCode = suffix;
	}
	private static int counter;
	
	
	
	public String next(){
		return prefix + counter++ + countryCode;
	}

	public String getPrefix(){
		return prefix;
	}
	public void setPrefiX(String prefix) {
		this.prefix = prefix;
	}
}
