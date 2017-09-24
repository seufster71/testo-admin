package org.testo.admin.kafka;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.testo.core.model.KafkaMsg;
import org.testo.core.utils.KafkaException;

@Service("MetricProducer")
public class MetricProducerImpl implements MetricProducer {

	@Autowired
    private KafkaTemplate<String, KafkaMsg> msgKafkaTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(MetricProducerImpl.class);

    public void dispatch(KafkaMsg kafkaMsg) throws KafkaException {
        try {
            SendResult<String, KafkaMsg> sendResult = msgKafkaTemplate.sendDefault(kafkaMsg.getId(), kafkaMsg).get();
            
            RecordMetadata recordMetadata = sendResult.getRecordMetadata();
          
            LOGGER.info("topic = {}, partition = {}, offset = {}, kafkaMsg = {}", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset(), kafkaMsg);
        } catch (Exception e) {
            throw new KafkaException(e.getMessage());
        }
    }
}
