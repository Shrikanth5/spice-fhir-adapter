package com.mdtlabs.fhir.adapterservice.fhirclient.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.parser.JsonParser;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.api.RequestTypeEnum;
import ca.uhn.fhir.rest.client.apache.ApacheHttpClient;
import ca.uhn.fhir.rest.client.apache.ApacheRestfulClientFactory;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.exceptions.FhirClientConnectionException;
import ca.uhn.fhir.rest.client.impl.GenericClient;
import ca.uhn.fhir.rest.gclient.IRead;
import ca.uhn.fhir.rest.gclient.IReadExecutable;
import ca.uhn.fhir.rest.gclient.IReadTyped;
import ca.uhn.fhir.rest.gclient.IUpdate;
import ca.uhn.fhir.rest.gclient.IUpdateTyped;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.ValidationResult;
import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import com.mdtlabs.fhir.commonservice.common.exception.FhirValidation;
import com.mdtlabs.fhir.commonservice.common.helper.HelperService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.impl.client.AutoRetryHttpClient;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Account;
import org.hl7.fhir.r4.model.Bundle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * FhirClientImplTest class has the test methods for the FhirClientImpl class.
 * </p>
 *
 * @author Dilip N created on April 02, 2024
 */
@ContextConfiguration(classes = {FhirClientImpl.class})
@ExtendWith(SpringExtension.class)
class FhirClientImplTest {
    @Autowired
    private FhirClientImpl fhirClientImpl;

    @MockBean
    private FhirContext fhirContext;

    @MockBean
    private HelperService helperService;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void testSendResourceToFHIR() throws DataFormatException, FhirClientConnectionException, RestClientException {
        // given
        JsonParser jsonParser = mock(JsonParser.class);
        FhirContext fhirContext = mock(FhirContext.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        FhirClientImpl fhirClientImpl = new FhirClientImpl(fhirContext, restTemplate, new HelperService());
        // when
        when(jsonParser.encodeResourceToString(Mockito.<IBaseResource>any())).thenReturn(TestConstants.SECREAT);
        when(fhirContext.newJsonParser()).thenReturn(jsonParser);
        when(restTemplate.postForEntity(Mockito.<String>any(), Mockito.<Object>any(), Mockito.<Class<Object>>any(),
                (Object[]) any())).thenThrow(new FhirValidation());
        // then
        assertThrows(FhirValidation.class, () -> fhirClientImpl.sendResourceToFHIR(new Account()));
        verify(fhirContext, atLeast(TestConstants.ONE)).newJsonParser();
        verify(jsonParser).encodeResourceToString(Mockito.<IBaseResource>any());
        verify(restTemplate).postForEntity(eq("nullAccount"), Mockito.<Object>any(), Mockito.<Class<Object>>any(),
                (Object[]) any());
    }

    @Test
    void updateResourceFhirValidationTest() {
        // given
        AutoRetryHttpClient theClient = new AutoRetryHttpClient();
        StringBuilder theUrl = new StringBuilder(TestConstants.ONE);
        HashMap<String, List<String>> theIfNoneExistParams = new HashMap<>();
        ApacheHttpClient theHttpClient = new ApacheHttpClient(theClient, theUrl, theIfNoneExistParams,
                TestConstants.RESOURCE_URL, RequestTypeEnum.CONNECT, new ArrayList<>());
        // when
        when(fhirContext.newRestfulGenericClient(Mockito.<String>any()))
                .thenReturn(new GenericClient(fhirContext, theHttpClient, "The Server Base", new ApacheRestfulClientFactory()));
        when(helperService.getAccessSecretKey()).thenThrow(new FhirValidation());
        // then
        assertThrows(FhirValidation.class, () -> fhirClientImpl.updateResource(new Account()));
        verify(helperService).getAccessSecretKey();
    }

    @Test
    void testUpdateResource() {
        // given
        IUpdateTyped iUpdateTyped = mock(IUpdateTyped.class);
        MethodOutcome methodOutcome = new MethodOutcome();
        IUpdate iUpdate = mock(IUpdate.class);
        IGenericClient iGenericClient = mock(IGenericClient.class);
        HashMap<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put(TestConstants.CLIENT_SPICE_WEB, TestConstants.CLIENT_SPICE_WEB);
        // when
        when(iUpdateTyped.execute()).thenReturn(methodOutcome);
        when(iUpdate.resource(Mockito.<IBaseResource>any())).thenReturn(iUpdateTyped);
        doNothing().when(iGenericClient).registerInterceptor(Mockito.<Object>any());
        when(iGenericClient.update()).thenReturn(iUpdate);
        when(fhirContext.newRestfulGenericClient(Mockito.<String>any())).thenReturn(iGenericClient);
        when(helperService.getAccessSecretKey()).thenReturn(stringStringMap);
        MethodOutcome actualUpdateResourceResult = fhirClientImpl.updateResource(new Account());
        // then
        verify(iGenericClient).registerInterceptor(Mockito.<Object>any());
        verify(iGenericClient).update();
        verify(iUpdateTyped).execute();
        verify(iUpdate).resource(Mockito.<IBaseResource>any());
        verify(helperService).getAccessSecretKey();
        assertSame(methodOutcome, actualUpdateResourceResult);
    }

    @Test
    void testSendBundleToHapiServer() throws DataFormatException, FhirClientConnectionException, RestClientException {
        // given
        FhirValidator fhirValidator = mock(FhirValidator.class);
        FhirContext theCtx = mock(FhirContext.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        FhirContext fhirContext = mock(FhirContext.class);
        JsonParser jsonParser = mock(JsonParser.class);
        FhirClientImpl fhirClientImpl = new FhirClientImpl(fhirContext, restTemplate, new HelperService());
        //when
        when(fhirValidator.validateWithResult(Mockito.<IBaseResource>any()))
                .thenReturn(new ValidationResult(theCtx, new ArrayList<>()));
        when(jsonParser.encodeResourceToString(Mockito.<IBaseResource>any())).thenReturn(TestConstants.SECREAT);
        when(fhirContext.newJsonParser()).thenReturn(jsonParser);
        when(fhirContext.newValidator()).thenReturn(fhirValidator);
        when(restTemplate.postForEntity(Mockito.<String>any(), Mockito.<Object>any(), Mockito.<Class<Object>>any(),
                (Object[]) any())).thenThrow(new FhirValidation());
        //then
        assertThrows(FhirValidation.class, () -> fhirClientImpl.sendBundleToHapiServer(new Bundle()));
        verify(fhirContext).newJsonParser();
        verify(fhirContext).newValidator();
        verify(jsonParser).encodeResourceToString(Mockito.<IBaseResource>any());
        verify(fhirValidator).validateWithResult(Mockito.<IBaseResource>any());
        verify(restTemplate).postForEntity(isNull(), Mockito.<Object>any(), Mockito.<Class<Object>>any(), (Object[]) any());
    }

    @Test
    void testGetResourceDetails() throws FhirClientConnectionException {
        // given
        IReadExecutable<Account> iReadExecutable = mock(IReadExecutable.class);
        Account account = new Account();
        IReadTyped<Account> iReadTyped = mock(IReadTyped.class);
        IRead iRead = mock(IRead.class);
        IGenericClient iGenericClient = mock(IGenericClient.class);
        FhirContext fhirContext = mock(FhirContext.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        // when
        when(iReadExecutable.execute()).thenReturn(account);
        when(iReadTyped.withUrl(Mockito.<String>any())).thenReturn(iReadExecutable);
        when(iRead.resource(Mockito.<Class<Account>>any())).thenReturn(iReadTyped);
        when(iGenericClient.read()).thenReturn(iRead);
        doNothing().when(iGenericClient).registerInterceptor(Mockito.<Object>any());
        when(fhirContext.newRestfulGenericClient(Mockito.<String>any())).thenReturn(iGenericClient);
        FhirClientImpl fhirClientImpl = new FhirClientImpl(fhirContext, restTemplate, new HelperService());
        Object actualResourceDetails = fhirClientImpl.getResourceDetails(TestConstants.RESOURCE_URL, new Account());
        // then
        verify(fhirContext).newRestfulGenericClient(isNull());
        verify(iGenericClient).read();
        verify(iGenericClient, atLeast(TestConstants.ONE)).registerInterceptor(Mockito.<Object>any());
        verify(iReadExecutable).execute();
        verify(iRead).resource(Mockito.<Class<Account>>any());
        verify(iReadTyped).withUrl(eq(TestConstants.RESOURCE_URL));
        assertSame(account, actualResourceDetails);
    }
}
