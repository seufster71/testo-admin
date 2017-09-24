package org.testo.admin.kafka;

import org.testo.core.model.KafkaMsg;
import org.testo.core.utils.KafkaException;

public interface MetricProducer {

	public void dispatch(KafkaMsg workUnit) throws KafkaException;
}
