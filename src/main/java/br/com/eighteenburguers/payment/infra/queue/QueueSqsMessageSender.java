package br.com.eighteenburguers.payment.infra.queue;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import br.com.eighteenburguers.payment.helper.JsonHelper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public abstract class QueueSqsMessageSender<T> {
	
	private final SqsTemplate template;
	private final String queue;
	
	protected void sendMessage(T payload, String context) {
		Message<String> message = this.buildMessage(payload, context);
		log.info("Send message to queue {} with payload", this.queue, payload);
		this.template.send(queue, message);
	}
	
	private Message<String> buildMessage(T payload, String context) {
		log.debug("Building message...: {}", payload);
		String json = JsonHelper.convertToJson(payload);
		return MessageBuilder.withPayload(json)
				.setHeader(QueueConfiguration.HEADER_CONTEXT, context)
				.build();
	}
}
