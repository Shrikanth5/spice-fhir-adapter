package com.mdtlabs.fhir.fhirlistenerservice.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.dto.Message;
import com.mdtlabs.fhir.fhirlistenerservice.service.SpiceDataExchangeService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Service class for listening to messages from RabbitMQ.
 * <p>
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
@Service
public class MessageQueueListener {

    private final SpiceDataExchangeService spiceDataExchangeService;

    public MessageQueueListener(SpiceDataExchangeService spiceDataExchangeService) {
        this.spiceDataExchangeService = spiceDataExchangeService;
    }

    /**
     * Method annotated with @RabbitListener to receive messages from the configured queue.
     *
     * @param messageFromQueue The messageFromQueue received from RabbitMQ.
     */
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receiveMessage(String messageFromQueue, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        Logger.logInfo(Constants.RECEIVED_MESSAGE_FROM_QUEUE, messageFromQueue);
        Message message = new Message();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(messageFromQueue);
            String deduplicationId = jsonNode.get(Constants.DEDUPLICATION_ID).asText();
            String body = jsonNode.get(Constants.BODY).asText();

            message.setDeduplicationId(deduplicationId);
            message.setBody(body);

        } catch (Exception exception) {
            Logger.logError("Error processing messageFromQueue: " + exception.getMessage());
        }
        try {
            spiceDataExchangeService.sendDataToFhirAdapter(message);
            channel.basicAck(deliveryTag, false);
        } catch (Exception exception) {
            Logger.logError("Error processing messageFromQueue: " + exception.getMessage());
            channel.basicReject(deliveryTag, false);
        }
    }
}
