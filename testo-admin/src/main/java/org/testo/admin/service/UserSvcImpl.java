package org.testo.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testo.admin.repository.UserDao;
import org.testo.core.service.ServiceProcessor;
import org.testo.core.utils.Request;
import org.testo.core.utils.Response;

@Service("UserSvcImpl")
public class UserSvcImpl implements ServiceProcessor, UserSvc {

	@Autowired
	protected UserDao userDao;
	
	@Override
	public void process(Request request, Response response) {
		String action = (String) request.getParams().get("action");
		switch (action) {
			case "LIST":
				this.list(request, response);
				break;
			case "ITEM":
				this.item(request, response);
				break;
			case "SAVE":
				this.save(request, response);
				break;
		}
	}

	@Override
	public void item(Request request, Response response){
		userDao.item(request, response);
	}
	
	@Override
	public void list(Request request, Response response) {
		userDao.list(request, response);
	}

	@Override
	public void save(Request request, Response response) {
		userDao.save(request, response);
	}
}
