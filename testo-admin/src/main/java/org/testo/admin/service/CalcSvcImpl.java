package org.testo.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testo.admin.repository.CalcDao;
import org.testo.core.service.ServiceProcessor;
import org.testo.core.utils.ErrorMsg;
import org.testo.core.utils.GlobalConstant;
import org.testo.core.utils.Request;
import org.testo.core.utils.Response;
import org.testo.core.utils.ValidationException;


@Service("CalcSvc")
public class CalcSvcImpl implements ServiceProcessor, CalcSvc {

	@Autowired
	protected ValidatorSvc validatorSvc;
	
	@Autowired
	protected CalcDao calcDao;
	
	@Override
	public void process(Request request, Response response) {
		
		try {
			validatorSvc.validate(request, response);
			
			String action = (String) request.getParams().get("action");
			switch (action) {
				case "EXEC":
					this.exec(request, response);
					break;
				default:
					ErrorMsg.addMsg(response, GlobalConstant.WARN, "The action does not exist!");
					break;
			}
		} catch (ValidationException e) {
			// Validation has occured need to update response
			ErrorMsg.addMsg(response, GlobalConstant.VALIDATION, e.getMessage());
		} catch (Exception e) {
			// Overall failure
			ErrorMsg.addMsg(response, GlobalConstant.FAIL, e.getMessage());
		}
		
	}

	@Override
	public void exec(Request request, Response response){
		try {
			calcDao.item(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
