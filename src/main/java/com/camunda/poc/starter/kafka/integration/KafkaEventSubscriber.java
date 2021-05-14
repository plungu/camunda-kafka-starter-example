package com.camunda.poc.starter.kafka.integration;

import java.util.Arrays;
import java.util.logging.Logger;

import com.camunda.poc.starter.entity.ServiceRequestEntity;
import com.camunda.poc.starter.repo.ServiceRequestRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Profile;

@Profile("integration")
@EnableBinding(KafkaEventChannels.class)
public class KafkaEventSubscriber {

	private final Logger LOGGER = Logger.getLogger(Class.class.getName());

	public KafkaEventSubscriber(){}

	private RuntimeService runtimeService;

	@Autowired
	public KafkaEventSubscriber(RuntimeService runtimeService){
		this.runtimeService = runtimeService;
	}

	@StreamListener("subscribeServiceRequest")
	public void handle(KafkaEvent kafkaEvent) throws Exception {
		LOGGER.info("\n\n Subscriber received Request: " + kafkaEvent.getEventName());

		KafkaRequestMapper kafkaRequestMapper = kafkaEvent.getKafkaRequestMapper();



		if (kafkaEvent.getEventName().equalsIgnoreCase(KafkaEvent.UPDATE_WORKFLOW_EVENT)) {
			runtimeService.correlateMessage("Message_wait-for-bundle", (String) kafkaEvent.getEventParams().get("businessKey"),
					Variables.createVariables().putValue("bundles", Arrays.asList("bundle-000")));
		}

	}

}
