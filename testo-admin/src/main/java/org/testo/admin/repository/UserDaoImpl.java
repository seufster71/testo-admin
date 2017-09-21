package org.testo.admin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.testo.core.multi.model.User;
import org.testo.core.service.EntityManagerSvc;
import org.testo.core.utils.Request;
import org.testo.core.utils.Response;

@Repository
@Transactional("transactionManagerA")
public class UserDaoImpl implements UserDao {

	@Autowired
	EntityManagerSvc em;
	
	@Override
	public void item(Request request, Response response) {
		if (request.getParams().get("id") != null){
			//User user = em.getMulti().find(User.class, (int) request.getParams().get("id"));
			//response.getParams().put("user", user);
		}
		response.getParams().put("status", "Got it from item user repo");
	}

	@Override
	public void list(Request request, Response response) {
		response.getParams().put("status", "Got it from list user repo");
	}

	@Override
	public void save(Request request, Response response) {
		User user = new User("Tom","Jones");
		//em.getMulti().persist(user);
		response.getParams().put("status", "Saved in user repo " + user.getId());
	}

}
