package org.testo.admin.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.testo.admin.model.Product;
import org.testo.core.service.EntityManagerSvc;
import org.testo.core.utils.GlobalConstant;
import org.testo.core.utils.Request;
import org.testo.core.utils.Response;
import org.testo.core.utils.StatusMsg;

@Repository
@Transactional("transactionManagerA")
public class AdminDaoImpl implements AdminDao {

	@Autowired
	EntityManagerSvc em;
	
	@Override
	public void item(Request request, Response response) throws Exception {
		if (request.getParams().get("id") != null){
			Product product = em.getInstance().find(Product.class, (int) request.getParams().get("id"));
			response.getParams().put("product", product);
		}
	    StatusMsg.addMsg(response, GlobalConstant.SUCCESS, "Response from item in admin repository");
	}

	@Override
	public void list(Request request, Response response) throws Exception {
		StatusMsg.addMsg(response, GlobalConstant.SUCCESS, "Response from list from admin repository");
	}

	@Override
	public void save(Request request, Response response) throws Exception {
		Product product = new Product("49349","test product");
		em.getInstance().persist(product);
		StatusMsg.addMsg(response, GlobalConstant.SUCCESS, "Saved in admin repository " + product.getId());
	}

}
