package org.testo.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.testo.admin.kafka.MetricProducer;
import org.testo.admin.model.Message;
import org.testo.admin.repository.AdminDao;
import org.testo.core.model.WorkUnit;
import org.testo.core.service.ServiceProcessor;
import org.testo.core.utils.Request;
import org.testo.core.utils.Response;

@Service("AdminSvcImpl")
public class AdminSvcImpl implements ServiceProcessor, AdminSvc {

	@Autowired
	protected AdminDao adminDao;
	
	@Autowired
	protected JmsTemplate jmsTemplate;
	
	@Autowired
	protected MetricProducer metricProducer;
	
	
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
		adminDao.item(request, response);
	}

	@Override
	public void list(Request request, Response response) {
		adminDao.list(request, response);
	}

	@Override
	public void save(Request request, Response response) {
		adminDao.save(request, response);

		jmsTemplate.convertAndSend("mailbox", new Message("etst@setise.com",(String) request.getParams().get("jms")));
		
		WorkUnit workUnit = new WorkUnit("test",(String) request.getParams().get("kafka"),request.getParams());
		
		metricProducer.dispatch(workUnit);
	}

}
