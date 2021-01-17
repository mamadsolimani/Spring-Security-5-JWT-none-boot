package ir.com.domain.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import ir.com.domain.entities.UserApp;

@Repository
public class UserRepositoryJpa implements UserRepository {

	@PersistenceContext
	protected EntityManager entityManager;
	
	@Override
	public UserApp create(UserApp userApp) throws Exception {
		
		this.entityManager.persist(userApp);
		
		return userApp;
	}

	@Override
	public UserApp edit(UserApp userApp) throws Exception {
		
		userApp = this.entityManager.merge(userApp);
		
		return userApp;
	}
	
	@Override
	public boolean remove(Long id) throws Exception {

		UserApp userApp = this.entityManager.find(UserApp.class, id);

		this.entityManager.remove(userApp);

		return true;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean exists(String username) throws Exception {
		
		List<UserApp> list = new ArrayList<>();
		Query query = (Query) this.entityManager.createQuery("SELECT b FROM UserApp b where b.username=:username");
		query.setParameter("username", username);
		list = query.getResultList();

		if (list.isEmpty())
			return false;
		
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public UserApp loadUserByUsername(String username) throws Exception {
		
		List<UserApp> list = new ArrayList<>();
		Query query = (Query) this.entityManager.createQuery("SELECT b FROM UserApp b where b.username=:username");
		query.setParameter("username", username);
		list = query.getResultList();

		if (list.isEmpty())
			return null;
		
		return list.get(0);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public UserApp findById(Long id) throws Exception {
		
		List<UserApp> list = new ArrayList<>();
		Query query = (Query) this.entityManager.createQuery("SELECT b FROM UserApp b where b.id=:id");
		query.setParameter("id", id);
		list = query.getResultList();

		if (list.isEmpty())
			return null;
		return list.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UserApp> findAll() throws Exception {
		List<UserApp> list = new ArrayList<>();
		list = this.entityManager.createQuery("SELECT b FROM UserApp b").getResultList();

		return list;
	}

}
