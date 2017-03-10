package org.javacream.books.isbngenerator.business;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.javacream.books.isbngenerator.IsbnGenerator;
import org.javacream.books.isbngenerator.IsbnGeneratorStrategy;

@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
@IsbnGeneratorStrategy(strategyName="jpa")
public class JpaIsbnGenerator implements IsbnGenerator {
	
	private String prefix;
	private String countryCode;
	
	@PersistenceContext private EntityManager entityManager;
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setCountryCode(String suffix) {
		this.countryCode = suffix;
	}


	@Transactional(Transactional.TxType.REQUIRED)
	public String next() {
				Integer key = (Integer) entityManager.createNativeQuery("select actual_key from keys").getSingleResult();
				Integer newKey = key + 1;
				Query query = entityManager.createNativeQuery("update keys set actual_key= ?");
				query.setParameter(1,  newKey);
				query.executeUpdate();
				return prefix + newKey + countryCode;
	}

}
