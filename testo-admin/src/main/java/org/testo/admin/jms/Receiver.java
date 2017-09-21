package org.testo.admin.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.testo.admin.model.Message;


@Component
public class Receiver {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@JmsListener(destination = "mailbox", containerFactory = "myFactory")
	public void receiveMessage(Message message){
		logger.info("Received < " + message.getBody() + " >");
	}
}
