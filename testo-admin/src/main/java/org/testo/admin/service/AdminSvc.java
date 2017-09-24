package org.testo.admin.service;

import org.testo.core.service.BaseSvc;
import org.testo.core.utils.Request;
import org.testo.core.utils.Response;

public interface AdminSvc extends BaseSvc {

	void exec(Request request, Response response);
}
