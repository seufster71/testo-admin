package org.testo.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.testo.admin.kafka.MetricProducerImpl;
import org.testo.admin.model.Message;
import org.testo.admin.repository.AdminDao;
import org.testo.core.model.KafkaMsg;
import org.testo.core.service.ServiceProcessor;
import org.testo.core.utils.ErrorMsg;
import org.testo.core.utils.GlobalConstant;
import org.testo.core.utils.KafkaException;
import org.testo.core.utils.Request;
import org.testo.core.utils.Response;
import org.testo.core.utils.StatusMsg;
import org.testo.core.utils.ValidationException;

@Service("AdminSvc")
public class AdminSvcImpl implements ServiceProcessor, AdminSvc {

	@Autowired
	protected ValidatorSvc validatorSvc;
	
	@Autowired
	protected AdminDao adminDao;
	
	@Autowired
	protected JmsTemplate jmsTemplate;
	
	@Autowired
	protected MetricProducerImpl metricProducer;
	
	@Override
	public void process(Request request, Response response) {
		
		try {
			validatorSvc.validate(request, response);
			
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
	public void exec(Request request, Response response) {
		try {
			jmsTemplate.convertAndSend("mailbox", new Message("testo@testo.com",(String) request.getParams().get("jms")));
			StatusMsg.addMsg(response, GlobalConstant.JMS, "Jms message sent");
			
			KafkaMsg kafkaMsg = new KafkaMsg("test",(String) request.getParams().get("kafka"),request.getParams());
			metricProducer.dispatch(kafkaMsg);
			
			StatusMsg.addMsg(response, GlobalConstant.KAFKA, "Kafka message sent");
			
		} catch (JmsException e) {
			ErrorMsg.addMsg(response, GlobalConstant.JMS, e.getMessage());
		} catch (KafkaException e) {
			ErrorMsg.addMsg(response, GlobalConstant.KAFKA, e.getMessage());
		} catch (Exception e) {
			// Overall failure
			ErrorMsg.addMsg(response, GlobalConstant.KAFKA, e.getMessage());
		}
	}
	
	@Override
	public void item(Request request, Response response) {
		try {
			adminDao.item(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void list(Request request, Response response) {
		try {
			adminDao.list(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void save(Request request, Response response) {
		try {
			adminDao.save(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
}
