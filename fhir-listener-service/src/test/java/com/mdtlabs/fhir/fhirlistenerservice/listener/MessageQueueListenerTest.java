package com.mdtlabs.fhir.fhirlistenerservice.listener;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mdtlabs.fhir.commonservice.common.model.dto.Message;
import com.mdtlabs.fhir.fhirlistenerservice.service.SpiceDataExchangeService;
import com.rabbitmq.client.AddressResolver;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQConnection;
import com.rabbitmq.client.impl.ConnectionParams;
import com.rabbitmq.client.impl.ConsumerWorkService;
import com.rabbitmq.client.impl.Frame;
import com.rabbitmq.client.impl.FrameHandlerFactory;
import com.rabbitmq.client.impl.LogTrafficListener;
import com.rabbitmq.client.impl.recovery.AutorecoveringChannel;
import com.rabbitmq.client.impl.recovery.AutorecoveringConnection;
import com.rabbitmq.client.impl.recovery.RecoveryAwareChannelN;

import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadFactory;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MessageQueueListenerTest {
    /**
     * Method under test:
     * {@link MessageQueueListener#receiveMessage(String, Channel, long)}
     */
    @Test
    void testReceiveMessage() throws IOException {
        // Arrange
        SpiceDataExchangeService spiceDataExchangeService = mock(SpiceDataExchangeService.class);
        doNothing().when(spiceDataExchangeService).sendDataToFhirAdapter(Mockito.<Message>any());
        MessageQueueListener messageQueueListener = new MessageQueueListener(spiceDataExchangeService);
        AMQConnection connection = mock(AMQConnection.class);
        doNothing().when(connection).flush();
        doNothing().when(connection).writeFrame(Mockito.<Frame>any());
        when(connection.willCheckRpcResponseType()).thenReturn(true);
        when(connection.getTrafficListener()).thenReturn(new LogTrafficListener());
        when(connection.getChannelRpcTimeout()).thenReturn(10);
        RecoveryAwareChannelN delegate = new RecoveryAwareChannelN(connection, 10,
                new ConsumerWorkService(ForkJoinPool.commonPool(), mock(ThreadFactory.class), 10));

        // Act
        messageQueueListener.receiveMessage("jane.doe@example.org",
                new AutorecoveringChannel(new AutorecoveringConnection(new ConnectionParams(), mock(FrameHandlerFactory.class),
                        mock(AddressResolver.class)), delegate),
                1L);

        // Assert
        verify(spiceDataExchangeService).sendDataToFhirAdapter(Mockito.<Message>any());
        verify(connection, atLeast(1)).getChannelRpcTimeout();
    }

    /**
     * Method under test:
     * {@link MessageQueueListener#receiveMessage(String, Channel, long)}
     */
    @Test
    void testReceiveMessageException() throws IOException {
        // Arrange
        SpiceDataExchangeService spiceDataExchangeService = mock(SpiceDataExchangeService.class);
        doNothing().when(spiceDataExchangeService).sendDataToFhirAdapter(Mockito.<Message>any());
        MessageQueueListener messageQueueListener = new MessageQueueListener(spiceDataExchangeService);
        AMQConnection connection = mock(AMQConnection.class);
        doThrow(new IOException("FHIR QUEUE: Received message \n{} ")).when(connection).writeFrame(Mockito.<Frame>any());
        when(connection.willCheckRpcResponseType()).thenReturn(true);
        when(connection.getTrafficListener()).thenReturn(new LogTrafficListener());
        when(connection.getChannelRpcTimeout()).thenReturn(10);
        RecoveryAwareChannelN delegate = new RecoveryAwareChannelN(connection, 10,
                new ConsumerWorkService(ForkJoinPool.commonPool(), mock(ThreadFactory.class), 10));

        // Act and Assert
        assertThrows(IOException.class,
                () -> messageQueueListener.receiveMessage("jane.doe@example.org",
                        new AutorecoveringChannel(new AutorecoveringConnection(new ConnectionParams(),
                                mock(FrameHandlerFactory.class), mock(AddressResolver.class)), delegate),
                        1L));
        verify(spiceDataExchangeService).sendDataToFhirAdapter(Mockito.<Message>any());
        verify(connection, atLeast(1)).getChannelRpcTimeout();

    }

    /**
     * Method under test:
     * {@link MessageQueueListener#receiveMessage(String, Channel, long)}
     */
    @Test
    void testReceiveMessageTimeout() throws IOException {

        // Arrange
        SpiceDataExchangeService spiceDataExchangeService = mock(SpiceDataExchangeService.class);
        doNothing().when(spiceDataExchangeService).sendDataToFhirAdapter(Mockito.<Message>any());
        MessageQueueListener messageQueueListener = new MessageQueueListener(spiceDataExchangeService);
        AMQConnection connection = mock(AMQConnection.class);
        doNothing().when(connection).flush();
        doNothing().when(connection).writeFrame(Mockito.<Frame>any());
        when(connection.willCheckRpcResponseType()).thenReturn(true);
        when(connection.getTrafficListener()).thenReturn(new LogTrafficListener());
        when(connection.getChannelRpcTimeout()).thenReturn(10);
        RecoveryAwareChannelN delegate = new RecoveryAwareChannelN(connection, 10,
                new ConsumerWorkService(ForkJoinPool.commonPool(), mock(ThreadFactory.class), 10));

        // Act
        messageQueueListener.receiveMessage("42",
                new AutorecoveringChannel(new AutorecoveringConnection(new ConnectionParams(), mock(FrameHandlerFactory.class),
                        mock(AddressResolver.class)), delegate),
                1L);

        // Assert
        verify(spiceDataExchangeService).sendDataToFhirAdapter(Mockito.<Message>any());
        verify(connection, atLeast(1)).getChannelRpcTimeout();
    }



}
