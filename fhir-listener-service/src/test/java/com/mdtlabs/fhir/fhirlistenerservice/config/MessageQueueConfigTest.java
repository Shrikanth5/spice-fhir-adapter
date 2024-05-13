package com.mdtlabs.fhir.fhirlistenerservice.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.concurrent.ThreadFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MessageQueueConfig.class})
@ExtendWith(SpringExtension.class)
class MessageQueueConfigTest {
    @MockBean
    private ConnectionFactory connectionFactory;

    @Autowired
    private MessageQueueConfig messageQueueConfig;

    /**
     * Method under test: {@link MessageQueueConfig#exchange()}
     */
    @Test
    void testExchange() {

        // Arrange and Act
        TopicExchange actualExchangeResult = (new MessageQueueConfig()).exchange();

        // Assert
        assertNull(actualExchangeResult.getName());
        assertFalse(actualExchangeResult.isAutoDelete());
        assertTrue(actualExchangeResult.isDurable());
    }

    /**
     * Method under test: {@link MessageQueueConfig#messageConverter()}
     */
    @Test
    void testMessageConverter() {
        // Arrange, Act and Assert
        assertTrue(messageQueueConfig.messageConverter() instanceof Jackson2JsonMessageConverter);
    }

    /**
     * Method under test: {@link MessageQueueConfig#amqpTemplate(ConnectionFactory)}
     */
    @Test
    void testAmqpTemplate() {
        // Arrange, Act and Assert
        assertTrue(messageQueueConfig.amqpTemplate(new CachingConnectionFactory()) instanceof RabbitTemplate);
    }

    /**
     * Method under test: {@link MessageQueueConfig#amqpTemplate(ConnectionFactory)}
     */
    @Test
    void testAmqpTemplateConnectionFactory() {
        // Arrange
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setConnectionThreadFactory(mock(ThreadFactory.class));

        // Act and Assert
        assertTrue(messageQueueConfig.amqpTemplate(connectionFactory) instanceof RabbitTemplate);
    }
}
