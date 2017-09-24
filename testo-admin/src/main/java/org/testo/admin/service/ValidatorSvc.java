package org.testo.admin.service;

import org.testo.core.utils.Request;
import org.testo.core.utils.Response;
import org.testo.core.utils.ValidationException;

public interface ValidatorSvc {
	
	void validate(Request request, Response response) throws ValidationException;
}
