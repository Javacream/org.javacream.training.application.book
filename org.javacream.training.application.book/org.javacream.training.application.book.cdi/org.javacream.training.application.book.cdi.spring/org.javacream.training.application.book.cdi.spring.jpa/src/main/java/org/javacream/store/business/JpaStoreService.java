package org.javacream.store.business;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.javacream.store.StoreService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope("singleton")
@Transactional(propagation = Propagation.REQUIRED)
public class JpaStoreService implements StoreService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public int getStock(String category, String item) {
		Query query = entityManager.createNativeQuery("SELECT STOCK FROM STOCK WHERE CATEGORY = ? AND ITEM = ?");
		query.setParameter(1, category);
		query.setParameter(2, item);
		try {
			return (int) query.getSingleResult();
		} catch (RuntimeException e) {
			return 0;
		}
	}
}
