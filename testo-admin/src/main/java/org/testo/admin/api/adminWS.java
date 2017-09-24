package org.testo.admin.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.testo.core.service.ServiceProcessor;
import org.testo.core.utils.Request;
import org.testo.core.utils.Response;

@RestController
@RequestMapping("/api/admin/")
public class adminWS {
	
	@Autowired
	protected ApplicationContext context;
	
	@RequestMapping(value = "service", method = RequestMethod.POST)
	public Response service(@RequestBody Request request){
		Response response = new Response();
		
		String service = (String) request.getParams().get("service");
		String className = "";
		switch (service) {
			case "ADMIN_SVC":
				className = "AdminSvc";
				
				break;
			case "CALC_SVC":
				className = "CalcSvc";
				
				break;
			case "USER_SVC":
				className = "UserSvc";
				
				break;
		}
		ServiceProcessor processor = (ServiceProcessor) context.getBean(className);
		
		processor.process(request, response);
		
		return response;
	}
}
