package org.testo.admin.service;

import org.springframework.stereotype.Service;
import org.testo.core.utils.Request;
import org.testo.core.utils.Response;
import org.testo.core.utils.ValidationException;

@Service("ValidatorSvc")
public class ValidatorSvcImpl implements ValidatorSvc {

	@Override
	public void validate(Request request, Response response) throws ValidationException {
		try {
			// validate request
			if (request.getParams().containsKey("action")) {
				String action = (String) request.getParams().get("action");
				if ( action == null || (action != null && action.isEmpty()) ){
					throw new ValidationException("Validation failed action param is empty");
				} else {
					if (!action.matches("^[a-zA-Z]{3,20}$")){
						throw new ValidationException("Validation failed action param is not following correct format see documentation");
					}
				}
			} else {
				throw new ValidationException("Validation failed missing action param");
			}
			
			if (request.getParams().containsKey("service")) {
				String service = (String) request.getParams().get("service");
				if ( service == null || (service != null && service.isEmpty()) ){
					throw new ValidationException("Validation failed service param is empty");
				} else {
					if (!service.matches("^[a-zA-Z0-9_]{3,20}$")){
						throw new ValidationException("Validation failed service param is not following correct format see documentation");
					}
				}
			} else {
				throw new ValidationException("Validation failed missing service param");
			}
		} catch (Exception e) {
			throw new ValidationException("Validation failed total failure");
		}
	}

	
}
