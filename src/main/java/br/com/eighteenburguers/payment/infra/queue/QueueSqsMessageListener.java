package br.com.eighteenburguers.payment.infra.queue;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import br.com.eighteenburguers.payment.helper.JsonHelper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class QueueSqsMessageListener {

	@NonNull
	private final ApplicationEventPublisher publisher;
	
	@NonNull
	private final ContextConfiguration contextConfiguration;
	
	@SqsListener("${sqs.queue.listener}")
	public void onMessage(Message<String> message) {
		log.info("Received message...");
		final String contextValue = message.getHeaders().get(QueueConfiguration.HEADER_CONTEXT, String.class);
		Class<?> type = contextConfiguration.getContext(contextValue);
		String payload = message.getPayload().replaceAll("^(\"|\")$", "").replace("\\", "");
		log.debug("Received message with payload: {}", payload);
		publisher.publishEvent(JsonHelper.convertToObject(payload, type));
	}
}
