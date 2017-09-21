package org.testo.admin.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.testo.admin.model.Product;
import org.testo.core.service.EntityManagerSvc;
import org.testo.core.utils.Request;
import org.testo.core.utils.Response;

@Repository
@Transactional("transactionManagerA")
public class AdminDaoImpl implements AdminDao {

	@Autowired
	EntityManagerSvc em;
	
	@Override
	public void item(Request request, Response response) {
		if (request.getParams().get("id") != null){
			Product product = em.getInstance().find(Product.class, (int) request.getParams().get("id"));
			response.getParams().put("product", product);
		}
		response.getParams().put("status", "Got it item from admin repo");
	}

	@Override
	public void list(Request request, Response response) {
		response.getParams().put("status", "Got it list from admin repo");
	}

	@Override
	public void save(Request request, Response response) {
		Product product = new Product("49349","test product");
		em.getInstance().persist(product);
		response.getParams().put("status", "Saved in admin repo " + product.getId());
	}

}
