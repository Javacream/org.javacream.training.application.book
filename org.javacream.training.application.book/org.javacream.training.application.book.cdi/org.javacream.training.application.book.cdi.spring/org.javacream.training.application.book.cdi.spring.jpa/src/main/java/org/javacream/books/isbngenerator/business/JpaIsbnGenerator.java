package org.javacream.books.isbngenerator.business;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.javacream.books.isbngenerator.IsbnGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope("singleton")
@Transactional(propagation=Propagation.REQUIRED)
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



	public String next() {
				Integer key = (Integer) entityManager.createNativeQuery("select actual_key from keys").getSingleResult();
				Integer newKey = key + 1;
				Query query = entityManager.createNativeQuery("update keys set actual_key= ?");
				query.setParameter(1,  newKey);
				query.executeUpdate();
				return prefix + newKey + countryCode;
	}

}
