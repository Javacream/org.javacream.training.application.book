package org.javacream.store.business;

import org.javacream.store.StoreService;

public class SimpleStoreService implements StoreService {
	private int stock;
	
	@Override
	public int getStock(String category, String item) {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}
