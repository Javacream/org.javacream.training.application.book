package org.javacream.store.business;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.javacream.store.StoreService;

@ApplicationScoped
public class JpaStoreService implements StoreService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(Transactional.TxType.REQUIRED)
	public int getStock(String category, String item) {
		Query query = entityManager
				.createNativeQuery("SELECT STOCK FROM STOCK WHERE CATEGORY = ? AND ITEM = ?");
		query.setParameter(1, category);
		query.setParameter(2, item);
		try {
			return (int) query.getSingleResult();
		} catch (RuntimeException e) {
			return 0;
		}
	}
}
