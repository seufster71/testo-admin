package org.testo.admin.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.testo.core.service.MicroServiceClient;
import org.testo.core.service.ServiceProcessor;
import org.testo.core.service.ValidatorSvc;
import org.testo.core.utils.ErrorMsg;
import org.testo.core.utils.GlobalConstant;
import org.testo.core.utils.Request;
import org.testo.core.utils.Response;
import org.testo.core.utils.ValidationException;

@RestController
@RequestMapping("/api/admin/")
public class adminWS {
	
	@Autowired
	protected ValidatorSvc validatorSvc;
	
	@Autowired
	protected ApplicationContext context;
	
	@Autowired
	protected MicroServiceClient microServiceClient;
	
	@RequestMapping(value = "service", method = RequestMethod.POST)
	public Response service(@RequestBody Request request){
		Response response = new Response();
		
		try {
			validatorSvc.validate(request, response);
			
			String service = (String) request.getParams().get("service");
			String className = "";
			switch (service) {
				case "ADMIN_SVC":
					className = "AdminSvc";
					break;
				case "CALC_SVC":
					// use remote service
					request.getParams().put(GlobalConstant.MICROSERVICENAME, "service-calc");
					request.getParams().put(GlobalConstant.MICROSERVICEPATH, "api/calc/service");
					microServiceClient.process(request, response);
					break;
				case "USER_SVC":
					className = "UserSvc";
					break;
			}
			if (!"".equals(className)) {
				ServiceProcessor processor = (ServiceProcessor) context.getBean(className);
				
				processor.process(request, response);
			}
		} catch (ValidationException e) {
			// Validation has occured need to update response
			ErrorMsg.addMsg(response, GlobalConstant.VALIDATION, e.getMessage());
		} catch (Exception e) {
			// Overall failure
			ErrorMsg.addMsg(response, GlobalConstant.FAIL, e.getMessage());
		}
		
		return response;
	}
}
