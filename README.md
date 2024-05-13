## FHIR-ADAPTER-SERVICE
Together with the essential supporting services, these microservices serve as an 
adapter. Their primary responsibility is to transform spice data into FHIR standards 
before sending it to the FHIR-JPA server.

## Tech stack

* Git
* Java 17.x
* Apache Maven 3.8.x
* Docker 20.10.xx
* Docker-compose 1.29.x
* RabbitMQ
* HAPI-FHIR Starter Project

### Installation ###

To bring the fhir-adapter-service up and running, there are few prerequisite that has to be done.
You can either follow the commands or access the official documentation by clicking the hyperlink.

* To install git in `ubuntu` run the following command or click [Git Official site].

```sh
sudo apt install git
```

* To install java in `ubuntu` run the following command or click [Java Official site].

```sh
sudo apt install openjdk-17-jdk
```

* To download and install `maven` in `ubuntu` click [Maven Official site - Download]

* After installing the maven zip file open the download folder and extract it.

* To set maven home path in Ubuntu run the following command.

```sh
nano ~/.bashrc
```

* Paste the following lines in .bashrc file.

```Environment variable
MAVEN_HOME='/home/ubuntu/Downloads/apache-maven-3.8.8'
PATH="$MAVEN_HOME/bin:$PATH"
export PATH
```

* Then save the .bashrc file.
* To apply the changes in the .bashrc file run the following command.

```sh
. ~/.bashrc
```

* Note: Once you have executed the script mentioned above, please restart the machine.
* To verify the maven installation, run the following command and check the version

```sh
mvn -v
```

* To install docker follow steps in the [Docker Official Docs]
* To install docker-compose in `ubuntu` follow steps in the [Docker Compose Official Docs]

## RabbitMQ

In order to transform the spice object into a FHIR object, it needs to be transmitted to the FHIR adapter. To ensure
asynchronous communication, we're implementing a queue service. We'll employ RabbitMQ for this purpose, leveraging its
open-source capabilities.
Run the application and configure the environment settings in the adapter service's configuration file. Install RabbitMq
by following the steps give in [RabbitMQ Official site -Download].

## HAPI-FHIR Starter Project

After the adapter creates the FHIR request, it proceeds to initiate a service call to the HAPI-FHIR Starter Project for
saving the FHIR resource. This project is open-source, and its setup can be completed by referring to the documentation
provided within the [HAPI FHIR JPA Server].

> **Note:**
> Once the FHIR server setup is complete, its configuration should be updated 
> in the .env file.

## Setup

- Clone the fhir-service repository.

```sh
git clone <<Need to be added later>>
```

### Contribution guidelines ###

To run the application, you should pass the necessary configuration via environment properties.
To achieve this, create a ***.env*** file and pass your own values for the following properties.

> **Note:**
> Please paste the ***.env*** file inside the specified directory.

`/fhir-service/`

***.env*** **file**

```properties
PROJECT_PATH=/home/ubuntu/Documents/fhir-service
HAPI_FHIR_SERVER_PROTOCOL=http
HAPI_FHIR_SERVER_HOSTNAME=example.com
HAPI_FHIR_SERVER_PORT=1234
HAPI_FHIR_SERVER_URI=/fhir/
HAPI_FHIR_SERVER_BASE_URL="${HAPI_FHIR_SERVER_PROTOCOL}://${HAPI_FHIR_SERVER_HOSTNAME}:${HAPI_FHIR_SERVER_PORT}${HAPI_FHIR_SERVER_URI}"
DRIVER_CLASS_NAME=org.postgresql.Driver
DATABASE_URL=jdbc:postgresql://example-db-host:5432/spice-fhir-opensource-local?serverTimezone=UTC&stringtype=unspecified
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=SP!c3UaT23
DATABASE_NAME=spice-fhir-opensource-local
FHIR_LOG_FILENAME='./log/FHIR_ApplicationLog.log'
FHIR_LOG_FILENAME_PATTERN='./log/FHIR_ApplicationLog.%d{yyyy-MM-dd}.log.gz'
FHIR_LOG_CONSOLE_PATTERN='%white(%d{ISO8601}) %highlight(%-5level) [%yellow(%t)] :  %msg%n%throwable'
FHIR_LOG_FILE_PATTERN='%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n'
FHIR_LOG_MAX_HISTORY=30
FHIR_LOG_TOTAL_SIZE_CAP=3GB
FHIR_PROTOCOL='http'
FHIR_LISTENER_PORT=8141
FHIR_LISTENER_DOCKER_SERVICE_NAME='fhir-listener-service'
FHIR_LISTENER_APPLICATION_NAME=fhir-listener-service
FHIR_LISTENER_CONTEXT_PATH='/fhir-listener-service'
FHIR_LISTENER_SERVICE_BASE_URL=${FHIR_PROTOCOL}://${FHIR_LISTENER_DOCKER_SERVICE_NAME}:${FHIR_LISTENER_PORT}
FHIR_ADAPTER_PORT=8142
FHIR_ADAPTER_DOCKER_SERVICE_NAME='fhir-adapter-service'
FHIR_ADAPTER_APPLICATION_NAME=fhir-adapter-service
FHIR_ADAPTER_CONTEXT_PATH='/fhir-adapter-service'
FHIR_ADAPTER_SERVICE_BASE_URL=${FHIR_PROTOCOL}://${FHIR_ADAPTER_DOCKER_SERVICE_NAME}:${FHIR_ADAPTER_PORT}
FHIR_USER_PORT=8143
FHIR_USER_DOCKER_SERVICE_NAME='fhir-user-service'
FHIR_USER_APPLICATION_NAME=fhir-user-service
FHIR_USER_CONTEXT_PATH='/fhir-user-service'
FHIR_USER_SERVICE_BASE_URL=${FHIR_PROTOCOL}://${FHIR_USER_DOCKER_SERVICE_NAME}:${FHIR_USER_PORT}
FHIR_AUTH_PORT=8144
FHIR_AUTH_DOCKER_SERVICE_NAME='fhir-auth-service'
FHIR_AUTH_APPLICATION_NAME=fhir-auth-service
FHIR_AUTH_CONTEXT_PATH='/fhir-auth-service'
FHIR_AUTH_SERVICE_BASE_URL=${FHIR_PROTOCOL}://${FHIR_AUTH_DOCKER_SERVICE_NAME}:${FHIR_AUTH_PORT}
FHIR_PUBLIC_KEY_FILE_NAME=fhir_public_key.der
FHIR_PRIVATE_KEY_FILE_NAME=fhir_private_key.der
FHIR_ACCESS_KEY_ID=0ntqKl2UvsdszddWat1J1JA8rAAFxWXshDSkT5kpHmW+drh2W+HbFiAqL6UGNdB88Kzcpy4maOEZ0J5eoq6nIVL
FHIR_SECRET_ACCESS_KEY=G7ibY6xTS1f1HHDmsdsavgOGFnPmoNCXkHNrIKTqEexauzyM90AtMRqwVFAfqAzQjhS41rPeWpFhqRWyuioCnjkvbuCzEshWT7kPUqWHUv/vDxM7ChrCB6e2I8elAJutOj2JbYfb4ZhBkIeYUAfCloZqZ4zHw8FUwFPj
CLIENT_REGISTRY_ENABLED_COUNTRY=4
RABBITMQ_HOSTNAME=example-rabbitmq-host
RABBITMQ_MANAGEMENT_PORT=9999
RABBITMQ_AMQP_PORT=8888
RABBITMQ_USER_NAME=guest
RABBITMQ_USER_PASSWORD=guest
RABBITMQ_QUEUE_NAME=spice_rabbitmq_queue
RABBITMQ_EXCHANGE_NAME=spice_rabbitmq_exchange
RABBITMQ_ROUTING_KEY=spice_rabitmq_routing_key
```

> Note: The values for the environmental variables should be changed based on the chosen service.

## .env

The `.env` file is used to store environment variables for the project. These variables are used to configure the
application and contain sensitive information such as passwords, API keys, and other credentials.

Please note that the `.env` file should never be committed to version control, as it contains sensitive information that
should not be shared publicly. Instead, you should add the `.env` file to your .gitignore file to ensure that it is not
accidentally committed.

To use the application, you will need to create a `.env` file in the root directory of the project and add the necessary
environment variables. You can refer to the above file for an example of the required variables and their format.

***The values provided in the
instructions are for demonstration purposes only and will not work as-is. You will need to replace them with actual
values that are specific to your environment.***

> Note: After checking out the project, please ensure that you update the relevant properties and values in env.example,
> and then rename it to .env.

## .env Description

`PROJECT_PATH`: Specifies the file path to the project's backend directory on the server.

`HAPI_FHIR_SERVER_PROTOCOL`: Defines the protocol used for communication with the HAPI FHIR server, set to HTTP.

`HAPI_FHIR_SERVER_HOSTNAME`: Indicates the IP address of the host where the HAPI FHIR server is running.

`HAPI_FHIR_SERVER_PORT`: Specifies the port number on which the HAPI FHIR server is listening.

`HAPI_FHIR_SERVER_URI`: Represents the URI path for accessing the FHIR server resources.

`HAPI_FHIR_SERVER_BASE_URL`: Constructs the base URL for the HAPI FHIR server using the protocol, hostname, port, and
URI.

`DRIVER_CLASS_NAME`: Specifies the Java driver class name for connecting to the PostgreSQL database.

`DATABASE_URL`: Contains the JDBC URL for connecting to the PostgreSQL database with timezone and string type specified.

`DATABASE_USERNAME`: Username used for accessing the PostgreSQL database.

`DATABASE_PASSWORD`: Password for the PostgreSQL user specified.

`DATABASE_NAME`: Name of the PostgreSQL database used by the project.

`FHIR_LOG_FILENAME`: File path for storing the log file related to the FHIR application.

`FHIR_LOG_FILENAME_PATTERN`: Pattern for rotating and compressing the log files based on date.

`FHIR_LOG_CONSOLE_PATTERN`: Pattern for formatting the log messages displayed on the console.

`FHIR_LOG_FILE_PATTERN`: Pattern for formatting the log messages written to the log file.

`FHIR_LOG_MAX_HISTORY`: Maximum number of historical log files to retain.

`FHIR_LOG_TOTAL_SIZE_CAP`: Maximum total size of log files allowed, set to 3 gigabytes.

`FHIR_PROTOCOL`: Protocol used for communication with FHIR services, set to HTTP.

`FHIR_LISTENER_PORT`: Port number on which the FHIR listener service is running.

`FHIR_LISTENER_DOCKER_SERVICE_NAME`: Name of the Docker service hosting the FHIR listener.

`FHIR_LISTENER_APPLICATION_NAME`: Name of the FHIR listener application.

`FHIR_LISTENER_CONTEXT_PATH`: Context path for accessing the FHIR listener service.

`FHIR_LISTENER_SERVICE_BASE_URL`: Base URL constructed using the protocol, Docker service name, and port for the FHIR
listener service.

`FHIR_ADAPTER_PORT`: Port number on which the FHIR adapter service is running.

`FHIR_ADAPTER_DOCKER_SERVICE_NAME`: Name of the Docker service hosting the FHIR adapter.

`FHIR_ADAPTER_APPLICATION_NAME`: Name of the FHIR adapter application.

`FHIR_ADAPTER_CONTEXT_PATH`: Context path for accessing the FHIR adapter service.

`FHIR_ADAPTER_SERVICE_BASE_URL`: Base URL constructed using the protocol, Docker service name, and port for the FHIR
adapter service.

`FHIR_USER_PORT`: Port number on which the FHIR user service is running.

`FHIR_USER_DOCKER_SERVICE_NAME`: Name of the Docker service hosting the FHIR user service.

`FHIR_USER_APPLICATION_NAME`: Name of the FHIR user application.

`FHIR_USER_CONTEXT_PATH`: Context path for accessing the FHIR user service.

`FHIR_USER_SERVICE_BASE_URL`: Base URL constructed using the protocol, Docker service name, and port for the FHIR user
service.

`FHIR_AUTH_PORT`: Port number on which the FHIR authentication service is running.

`FHIR_AUTH_DOCKER_SERVICE_NAME`: Name of the Docker service hosting the FHIR authentication service.

`FHIR_AUTH_APPLICATION_NAME`: Name of the FHIR authentication application.

`FHIR_AUTH_CONTEXT_PATH`: Context path for accessing the FHIR authentication service.

`FHIR_AUTH_SERVICE_BASE_URL`: Base URL constructed using the protocol, Docker service name, and port for the FHIR
authentication service.

`FHIR_PUBLIC_KEY_FILE_NAME`: Filename for the public key used in FHIR service.

`FHIR_PRIVATE_KEY_FILE_NAME`: Filename for the private key used in FHIR service.

`FHIR_ACCESS_KEY_ID`: Access key ID used for authentication.

`FHIR_SECRET_ACCESS_KEY`: Secret access key used for authentication.

`CLIENT_REGISTRY_ENABLED_COUNTRY`: Number representing the enabled country in the client registry.

`RABBITMQ_HOSTNAME`: IP address of the RabbitMQ server.

`RABBITMQ_MANAGEMENT_PORT`: Management port for RabbitMQ.

`RABBITMQ_AMQP_PORT`: AMQP port for RabbitMQ.

`RABBITMQ_USER_NAME`: Username for accessing RabbitMQ.

`RABBITMQ_USER_PASSWORD`: Password for accessing RabbitMQ.

`RABBITMQ_QUEUE_NAME`: Name of the queue used in RabbitMQ.

`RABBITMQ_EXCHANGE_NAME`: Name of the exchange used in RabbitMQ.

`RABBITMQ_ROUTING_KEY`: Routing key used in RabbitMQ.

## Deployment

Run the following commands in the below path

Build a clean-install using maven
`/fhir-service`

```sh
mvn clean install
```

Build docker images by the following command

```sh
docker-compose build
```

Once the images are built, run the docker containers by the following docker command

```sh
docker-compose up
```

## Validation

Once the deployment of the application is successful, you can confirm the connectivity of the Back-end by logging into
the application. To accomplish this, you may choose any API Testing tool. In this particular case, the Postman API
Platform was utilized as an example.

### Endpoint

`POST {{ipAdd}}/fhir-auth-service/session`

### Request Body

This endpoint allows user to sign-in into the application.
The request body should be in the **form-data** format and include the following fields:

- `username`: fhiradmin@mtdlabs.com
- `password`: fhirAdmin@123

#### The response contains Authorization. You must use this value in the subsequent requests.

> Note:
> The credentials displayed in the tables are for demonstration purposes only and should not be
> used in a production environment. If you need to remove, modify, or add new user credentials,
> you can create a new script file containing the necessary DDL or DML queries in the below specified
> location. It's important to note that attempting to update the existing script file may result in
> a checksum error.

`fhir-listener-service/src/main/resources/db/migration`

#### The authorization token primarily serves the purpose of authenticating user CRUD operations and generating API key pairs only. Communication between microservices is exclusively authenticated only through the API key pair. This key pair utilized for microservices communication must be generated for a user with administrative privileges. Then the same should then be configured within the .env file.

### Endpoint

`POST {{ipAdd}}/fhir-user-service/api-key-managers/generate`

### Request Body

`
{
"id": 1
}
`

### Request Headers
 `Authorization: {{authToken}}`

The payload must include an ID with an admin role. Set the authorization value to the authToken received from the login response headers.


### Important:
Since adapter services are not utilized by users but by the FHIR services themselves, it is impossible for an application service to authenticate using user credentials. To address this issue, a new security strategy involving API key pairs has been implemented. Consequently, when the application itself makes service calls, it must attach API keys to the request headers. These API keys need to be manually generated by an administrator. The following steps outline the process:

1. During the initial deployment of the application, there will be no default API keys available for service calls.
2. After the first deployment, log in using user credentials to obtain an authentication bearer token.
3. Utilize this token to create an API key pair, ensuring it is created under an admin user.
4. Set the received key pair into the `.env` file.
5. Finally, redeploy the application to enable it to read the API keys from the `.env` file.

### FHIR-Server Integration with fhir-service

The below documentation provides information for integrating the FHIR-Server with the fhir-service
using `hapi-fhir-jpaserver-starter` and enabling authentication through an interceptor.

### Integration Steps

1. **Utilize hapi-fhir-jpaserver-starter**: Start by integrating the FHIR-Server with the fhir-service
   using [hapi-fhir-jpaserver-starter]. This provides a robust foundation for handling FHIR resources and operations.

2. **Enable Authentication**: Authentication for the FHIR-Server endpoints are essential for secure communication. This
   is achieved through an interceptor and API Key Pair.

3. **Implement Authentication Interceptor**: Utilize the provided code snippet to implement an authentication
   interceptor. This interceptor will transmit the access key ID and secret access key to the fhir-service for
   authentication.

4. **Generate Access Key and Secret Key**: In the fhir-service, create an access key ID and secret access key for a FHIR
   user intending to utilize the fhir-server endpoints. These credentials will serve for authentication when
   communicating with the FHIR-Server.

## Code Snippet for Authentication Interceptor

FHIRResources.java

```java
package ca.uhn.fhir.jpa.starter.util;

import java.util.Arrays;
import java.util.List;

public class FHIRResources {
    public static final String FHIR_URI_REGEX = ".*/fhir/";
    public static final String ACCESS_KEY_ID_PARAM = "accessKeyId";
    public static final String SECRET_ACCESS_KEY_PARAM = "secretAccessKey";
   public static final String BACK_SLASH = "/";
   public static final String FHIR = "fhir";
   protected static final List<String> ALL_RESOURCES = Arrays.asList(
           SYSTEM_LEVEL_OPERATIONS, PATIENT, OBSERVATION, PRACTITIONER, PRACTITIONER_ROLE, ACCOUNT,
           ACTIVITY_DEFINITION, ADVERSE_EVENT, ALLERGY_INTOLERANCE, APPOINTMENT, APPOINTMENT_RESPONSE,
           AUDIT_EVENT, BASIC, BINARY, BIOLOGICALLY_DERIVED_PRODUCT, BODY_STRUCTURE, BUNDLE,
           CAPABILITY_STATEMENT, CARE_PLAN, CARE_TEAM, CATALOG_ENTRY, CHARGE_ITEM,
           CHARGE_ITEM_DEFINITION, CLAIM, CLAIM_RESPONSE, CLINICAL_IMPRESSION, CODE_SYSTEM,
           COMMUNICATION, COMMUNICATION_REQUEST, COMPARTMENT_DEFINITION, COMPOSITION, CONCEPT_MAP,
           CONDITION, CONSENT, CONTRACT, COVERAGE, COVERAGE_ELIGIBILITY_REQUEST,
           COVERAGE_ELIGIBILITY_RESPONSE, DETECTED_ISSUE, DEVICE, DEVICE_DEFINITION, DEVICE_METRIC,
           DEVICE_REQUEST, DEVICE_USE_STATEMENT, DIAGNOSTIC_REPORT, DOCUMENT_MANIFEST,
           DOCUMENT_REFERENCE, EFFECT_EVIDENCE_SYNTHESIS, ENCOUNTER, ENDPOINT,
           ENROLLMENT_REQUEST, ENROLLMENT_RESPONSE, EPISODE_OF_CARE, EVENT_DEFINITION, EVIDENCE,
           EVIDENCE_VARIABLE, EXAMPLE_SCENARIO, EXPLANATION_OF_BENEFIT, FAMILY_MEMBER_HISTORY, FLAG,
           GOAL, GRAPH_DEFINITION, GROUP, GUIDANCE_RESPONSE, HEALTHCARE_SERVICE, IMAGING_STUDY,
           IMMUNIZATION, IMMUNIZATION_EVALUATION, IMMUNIZATION_RECOMMENDATION, IMPLEMENTATION_GUIDE,
           INSURANCE_PLAN, INVOICE, LIBRARY, LINKAGE, LIST, LOCATION, MEASURE, MEASURE_REPORT,
           MEDIA, MEDICATION, MEDICATION_ADMINISTRATION, MEDICATION_DISPENSE, MEDICATION_KNOWLEDGE,
           MEDICATION_REQUEST, MEDICATION_STATEMENT, MEDICINAL_PRODUCT, MEDICINAL_PRODUCT_AUTHORIZATION,
           MEDICINAL_PRODUCT_CONTRAINDICATION, MEDICINAL_PRODUCT_INDICATION, MEDICINAL_PRODUCT_INGREDIENT,
           MEDICINAL_PRODUCT_INTERACTION, MEDICINAL_PRODUCT_MANUFACTURED, MEDICINAL_PRODUCT_PACKAGED,
           MEDICINAL_PRODUCT_PHARMACEUTICAL, MEDICINAL_PRODUCT_UNDESIRABLE_EFFECT, MESSAGE_DEFINITION,
           MESSAGE_HEADER, MOLECULAR_SEQUENCE, NAMING_SYSTEM, NUTRITION_ORDER, OBSERVATION_DEFINITION,
           OPERATION_DEFINITION, OPERATION_OUTCOME, ORGANIZATION, ORGANIZATION_AFFILIATION, PARAMETERS,
           PAYMENT_NOTICE, PAYMENT_RECONCILIATION, PERSON, PLAN_DEFINITION, PROCEDURE, PROVENANCE,
           QUESTIONNAIRE, QUESTIONNAIRE_RESPONSE, RELATED_PERSON, REQUEST_GROUP, RESEARCH_DEFINITION,
           RESEARCH_ELEMENT_DEFINITION, RESEARCH_STUDY, RESEARCH_SUBJECT, RISK_ASSESSMENT,
           RISK_EVIDENCE_SYNTHESIS, SCHEDULE, SEARCH_PARAMETER, SERVICE_REQUEST, SLOT, SPECIMEN,
           SPECIMEN_DEFINITION, STRUCTURE_DEFINITION, STRUCTURE_MAP, SUBSCRIPTION, SUBSTANCE,
           SUBSTANCE_NUCLEIC_ACID, SUBSTANCE_POLYMER, SUBSTANCE_PROTEIN, SUBSTANCE_REFERENCE_INFORMATION,
           SUBSTANCE_SOURCE_MATERIAL, SUBSTANCE_SPECIFICATION, SUPPLY_DELIVERY, SUPPLY_REQUEST, TASK,
           TERMINOLOGY_CAPABILITIES, TEST_REPORT, TEST_SCRIPT, VALUE_SET, VERIFICATION_RESULT,
           VISION_PRESCRIPTION
   );
}
```

application.yml

```yaml
app:
 validate_token_api: "http://fhir-service/fhir-user-service/api-key-managers/validate"
```

> Note: Please update the hostname accordingly to match the fhir-service that is currently running.

AuthenticationInterceptor.java

```java
package ca.uhn.fhir.jpa.starter.util;

import ca.uhn.fhir.interceptor.api.Interceptor;
import ca.uhn.fhir.rest.server.interceptor.InterceptorAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Interceptor for handling authentication in FHIR requests.
 * <p>
 * This class intercepts incoming FHIR requests and performs authentication logic before processing.
 * It makes a REST call with access key and secret key for authorization.
 * The class provides custom exceptions for different error scenarios.
 *
 */
@Component
@Interceptor
public class AuthenticationInterceptor extends InterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Value("${app.validate_token_api}")
    private String targetUrl;

    /**
     * Overrides the incomingRequestPreProcessed method to handle authentication logic.
     *
     * @param request  The incoming HttpServletRequest.
     * @param response The HttpServletResponse to be modified if needed.
     * @return True if the request has been pre-processed; false otherwise.
     */
    @Override
    public boolean incomingRequestPreProcessed(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();
        String accessKeyId = request.getHeader(FHIRResources.ACCESS_KEY_ID_PARAM);
        String secretAccessKey = request.getHeader(FHIRResources.SECRET_ACCESS_KEY_PARAM);

        Pattern pattern = Pattern.compile(FHIRResources.FHIR_URI_REGEX);
        Matcher matcher = pattern.matcher(uri);

        log.info("Incoming request URI {} ", request.getRequestURI());

        String remoteHost = request.getRemoteHost();
        String hostAddress = getHostAddress();

        String remoteHostPrefix = getPrefix(remoteHost, 16);
        String localHostPrefix = getPrefix(hostAddress, 16);

        boolean hostCheck = remoteHostPrefix.equals(localHostPrefix);
        if (hostCheck) {
            return super.incomingRequestPreProcessed(request, response);
        } else if (matcher.matches() || isFhirResourceRequest(uri)) {
            try {
                makeRestCallWithAuthorization(accessKeyId, secretAccessKey);
            } catch (IOException e) {
                log.error("Exception during REST call", e);
                throw new UnsupportedOperationException(e);
            }
        }

        return super.incomingRequestPreProcessed(request, response);
    }

    /**
     * Retrieves the host address of the local machine.
     *
     * @return The host address.
     */
    private String getHostAddress() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            return localhost.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("UnknownHostException", e);
            return null;
        }
    }

    /**
     * Checks if the given URI corresponds to a FHIR resource request.
     *
     * @param uri The URI to be checked.
     * @return True if the URI represents a FHIR resource request; false otherwise.
     */
    private boolean isFhirResourceRequest(String uri) {
        List<String> resources = FHIRResources.ALL_RESOURCES;
        return resources.stream().anyMatch(resource -> uri.contains(FHIRResources.BACK_SLASH + FHIRResources.FHIR + FHIRResources.BACK_SLASH + resource));
    }

    /**
     * Makes a REST call with the provided access key ID and secret access key for authorization.
     *
     * @param accessKeyId     The access key ID.
     * @param secretAccessKey The secret access key.
     * @throws IOException If an I/O error occurs.
     */
    private void makeRestCallWithAuthorization(String accessKeyId, String secretAccessKey) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put(FHIRResources.ACCESS_KEY_ID_PARAM, accessKeyId);
        requestBody.put(FHIRResources.SECRET_ACCESS_KEY_PARAM, secretAccessKey);

        if (isValidCredentials(accessKeyId, secretAccessKey)) {
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            RestTemplate restTemplate = new RestTemplate();

            try {
                ResponseEntity<String> authResponse = restTemplate.postForEntity(targetUrl, requestEntity, String.class);

                if (authResponse.getStatusCode().is2xxSuccessful()) {
                    log.info("authentication service status code: {}", authResponse.getStatusCode());
                } else {
                    log.error("Error making REST call. Status code: {}", authResponse.getStatusCodeValue());
                    handleHttpStatusCodeError(authResponse.getStatusCode());
                }
            } catch (HttpClientErrorException.Unauthorized exception) {
                handleUnauthorizedError();
            } catch (HttpStatusCodeException exception) {
                handleHttpStatusCodeError(exception.getStatusCode());
            } catch (Exception exception) {
                handleGenericError();
            }
        } else {
            log.error("Invalid accessKeyId or secretAccessKey");
            throw new UnauthorizedException("Invalid accessKeyId or secretAccessKey");
        }
    }

    /**
     * Checks if the provided access key ID and secret access key are valid.
     *
     * @param accessKeyId     The access key ID.
     * @param secretAccessKey The secret access key.
     * @return True if the credentials are valid; false otherwise.
     */
    private boolean isValidCredentials(String accessKeyId, String secretAccessKey) {
        return Objects.nonNull(accessKeyId) && !accessKeyId.isEmpty() && Objects.nonNull(secretAccessKey) && !secretAccessKey.isEmpty();
    }

    /**
     * Sends an unauthorized response with an error message.
     */
    private void handleUnauthorizedError() {
        throw new UnauthorizedException("Invalid accessKeyId or secretAccessKey.");
    }

    /**
     * Handles an HTTP status code error by throwing an exception with the appropriate message.
     *
     * @param statusCode The HTTP status code.
     */
    private void handleHttpStatusCodeError(HttpStatus statusCode) {
        throw new HttpErrorException("HTTP status code error: " + statusCode);
    }

    /**
     * Handles a generic error by throwing an internal server error exception.
     */
    private void handleGenericError() {
        throw new InternalServerErrorException("An unexpected error occurred.");
    }

    /**
     * Retrieves the prefix of the given IP address with the specified number of bits.
     *
     * @param ipAddress The IP address.
     * @param bits      The number of bits for the prefix.
     * @return The prefix of the IP address.
     */
    private String getPrefix(String ipAddress, int bits) {
        String[] octets = ipAddress.split("\\.");
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < Math.min(4, bits / 8); i++) {
            prefix.append(octets[i]).append(".");
        }
        return prefix.substring(0, prefix.length() - 1);
    }

    /**
     * Custom exception for unauthorized errors.
     */
    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {
            super(message);
        }
    }

    /**
     * Custom exception for HTTP status code errors.
     */
    public static class HttpErrorException extends RuntimeException {
        public HttpErrorException(String message) {
            super(message);
        }
    }

    /**
     * Custom exception for generic internal server errors.
     */
    public static class InternalServerErrorException extends RuntimeException {
        public InternalServerErrorException(String message) {
            super(message);
        }
    }
}
```

## Migration API's
To migrate the user and site use the following API endpoints given below.
>Note :
> These API's use at Once when Server and Service setUp done.
> No need any RequestBody and Headers for the migration endpoints.
### SiteMigration endpoint

``
GET {{ipAdd}}/fhir-adapter-service/migration/site-migration
``
### UserMigration endpoint

``
GET {{ipAdd}}/fhir-adapter-service/migration/user-migration
``
# Sample FHIR-Mapping Reference

The resources utilized by the FHIR adapter service in conjunction with Spice modules, which encompass 
``Patient, Observation, Practitioner, and Organization`` Resources. Moreover, the document includes mappings of FHIR attributes associated with these resources. These mappings are crucial for ensuring smooth interoperability between Spice modules and FHIR-based systems, enabling efficient data exchange and translation.
Access the FHIR attribute mappings via : [spice-fhir-mapping.xlsx]

For the mapping convention used in the Spice Patient module, refer to the ``PatientConverter.java`` file located at [PatientConverter.java],

``fhir-adapter-service/src/main/java/com/mdtlabs/fhir/adapterservice/converter/PatientConverter.java``

This file contains the necessary mapping logic for converting data to FHIR format within the context of the Spice Patient module.


[hapi-fhir-jpaserver-starter]:<https://github.com/hapifhir/hapi-fhir-jpaserver-starter.git>

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

[Git Official site]:<https://git-scm.com/book/en/v2/Getting-Started-Installing-Git>

[Java Official site]:<https://www.oracle.com/in/java/technologies/downloads/#java17>

[Maven Official site - Download]: <https://maven.apache.org/download.cgi>

[Maven Official site - Install]:<https://maven.apache.org/install.html>

[Redis Official Docs]:<https://redis.io/docs/getting-started/installation/>

[Docker Official Docs]: <https://docs.docker.com/engine/install/>

[Docker Compose Official Docs]: <https://docs.docker.com/compose/install/linux/>

[Maven Official site -Download]: <https://maven.apache.org/download.cgi>

[RabbitMQ Official site -Download]:<https://www.rabbitmq.com/docs/download>

[HAPI FHIR JPA Server]:<https://hapifhir.io/hapi-fhir/docs/server_jpa/get_started.html>

[PatientConverter.java]:fhir-adapter-service/src/main/java/com/mdtlabs/fhir/adapterservice/converter/PatientConverter.java
[spice-fhir-mapping.xlsx]:spice-fhir-mapping.xlsx