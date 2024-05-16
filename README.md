# SPICE FHIR ADAPTER
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Medtronic-LABS_spice-fhir-adapter&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Medtronic-LABS_spice-fhir-adapter)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Medtronic-LABS_spice-fhir-adapter&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Medtronic-LABS_spice-fhir-adapter)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Medtronic-LABS_spice-fhir-adapter&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=Medtronic-LABS_spice-fhir-adapter)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Medtronic-LABS_spice-fhir-adapter&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Medtronic-LABS_spice-fhir-adapter)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Medtronic-LABS_spice-fhir-adapter&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=Medtronic-LABS_spice-fhir-adapter)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Medtronic-LABS_spice-fhir-adapter&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=Medtronic-LABS_spice-fhir-adapter)
## FHIR ADAPTER - Simplified Data Exchange
`FHIR Powerhouse`: A brand new FHIR (Fast Healthcare Interoperability Resources) adapter bridges the gap between SPICE and this widely used data format. This adapter acts like a translator, effortlessly converting SPICE data into FHIR format.

`Centralized Data Hub`:The converted data is then mapped to corresponding FHIR resources (think data fields) and pushed to a central FHIR database. This creates a central repository for your healthcare information.

`Reaching New Horizons`:The stored FHIR data isn't locked away. It can be easily transferred to DHIS2 (District Health Information System 2) systems used in Africa and Bangladesh. This fosters broader data exchange and accessibility across regions.

`Multilingual Capability`:SPICE now supports multi-language translation for the user interface! This means you can experience SPICE in your preferred language, making it easier than ever to navigate and use the app.

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

`<home path>/fhir-service/`

***.env*** **file**

```properties
PROJECT_PATH=/home/ubuntu/Documents/fhir-service
HAPI_FHIR_SERVER_PROTOCOL=http
HAPI_FHIR_SERVER_HOSTNAME=example.com
HAPI_FHIR_SERVER_PORT=1234
HAPI_FHIR_SERVER_URI=/fhir/
HAPI_FHIR_SERVER_BASE_URL="${HAPI_FHIR_SERVER_PROTOCOL}://${HAPI_FHIR_SERVER_HOSTNAME}:${HAPI_FHIR_SERVER_PORT}${HAPI_FHIR_SERVER_URI}"
DRIVER_CLASS_NAME=org.postgresql.Driver
DATABASE_URL=jdbc:postgresql://example-db-host:5432/example-db?serverTimezone=UTC&stringtype=unspecified
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=password
DATABASE_NAME=example-db
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
FHIR_ACCESS_KEY_ID=accesskey
FHIR_SECRET_ACCESS_KEY=secretkey
CLIENT_REGISTRY_ENABLED_COUNTRY=4
RABBITMQ_HOSTNAME=example-rabbitmq-host
RABBITMQ_MANAGEMENT_PORT=9999
RABBITMQ_AMQP_PORT=8888
RABBITMQ_USER_NAME=guest
RABBITMQ_USER_PASSWORD=guest
RABBITMQ_QUEUE_NAME=spice_rabbitmq_queue
RABBITMQ_EXCHANGE_NAME=spice_rabbitmq_exchange
RABBITMQ_ROUTING_KEY=spice_rabitmq_routing_key
PASSWORD=password
SOURCE_DATABASE_URL=jdbc:postgresql://example-db-host:5432/example-db?serverTimezone=UTC&stringtype=unspecified
SOURCE_DATABASE_USERNAME=postgres
SOURCE_DATABASE_PASSWORD=password
SOURCE_DATABASE_NAME=example-db
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

`PASSWORD`: Password parameter key for security config and the value must be `password`. 
>Note :
> The Below properties are used for the sourceDatabase(SPICE-SERVER Database details) configuration.
`SOURCE_DRIVER_CLASS_NAME`: Specifies the Java driver class name for connecting to the PostgreSQL database.

`SOURCE_DATABASE_URL`: Contains the JDBC URL for connecting to the PostgreSQL database with timezone and string type specified

`SOURCE_DATABASE_USERNAME`: Username used for accessing the PostgreSQL database.

`SOURCE_DATABASE_PASSWORD`: Password for the PostgreSQL user specified.

`SOURCE_DATABASE_NAME`: Name of the PostgreSQL database used by the project.

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

src/main/java/ca/uhn/fhir/jpa/starter/util/FHIRResources.java

```java
package ca.uhn.fhir.jpa.starter.util;

import java.util.Arrays;
import java.util.List;

public class FHIRResources {
   public static final String FHIR_URI_REGEX = ".*/fhir/";
   public static final String ACCESS_KEY_ID_PARAM = "<YOUR_ACCESSKEY_ID_HERE>";
   public static final String SECRET_ACCESS_KEY_PARAM = "YOUR_SECRECT_ACCESS_KEY_HERE>";
   public static final String BACK_SLASH = "/";
   public static final String FHIR = "fhir";
   public static final String ACCESS_KEY_ID_PARAM = "<YOUR_ACCESSKEY_ID_HERE>";
   public static final String SECRET_ACCESS_KEY_PARAM = "YOUR_SECRECT_ACCESS_KEY_HERE>";
   public static final String BACK_SLASH = "/";
   public static final String FHIR = "fhir";
   public static final String PATIENT = "Patient";
   public static final String OBSERVATION = "Observation";
   public static final String PRACTITIONER = "Practitioner";
   public static final String PRACTITIONER_ROLE = "PractitionerRole";
   public static final String ACCOUNT = "Account";
   public static final String ACTIVITY_DEFINITION = "ActivityDefinition";
   public static final String ADVERSE_EVENT = "AdverseEvent";
   public static final String ALLERGY_INTOLERANCE = "AllergyIntolerance";
   public static final String APPOINTMENT = "Appointment";
   public static final String APPOINTMENT_RESPONSE = "AppointmentResponse";
   public static final String AUDIT_EVENT = "AuditEvent";
   public static final String BASIC = "Basic";
   public static final String BINARY = "Binary";
   public static final String BIOLOGICALLY_DERIVED_PRODUCT = "BiologicallyDerivedProduct";
   public static final String BODY_STRUCTURE = "BodyStructure";
   public static final String BUNDLE = "Bundle";
   public static final String CAPABILITY_STATEMENT = "CapabilityStatement";
   public static final String CARE_PLAN = "CarePlan";
   public static final String CARE_TEAM = "CareTeam";
   public static final String CATALOG_ENTRY = "CatalogEntry";
   public static final String CHARGE_ITEM = "ChargeItem";
   public static final String CHARGE_ITEM_DEFINITION = "ChargeItemDefinition";
   public static final String CLAIM = "Claim";
   public static final String CLAIM_RESPONSE = "ClaimResponse";
   public static final String CLINICAL_IMPRESSION = "ClinicalImpression";
   public static final String CODE_SYSTEM = "CodeSystem";
   public static final String COMMUNICATION = "Communication";
   public static final String COMMUNICATION_REQUEST = "CommunicationRequest";
   public static final String COMPARTMENT_DEFINITION = "CompartmentDefinition";
   public static final String COMPOSITION = "Composition";
   public static final String CONCEPT_MAP = "ConceptMap";
   public static final String CONDITION = "Condition";
   public static final String CONSENT = "Consent";
   public static final String CONTRACT = "Contract";
   public static final String COVERAGE = "Coverage";
   public static final String COVERAGE_ELIGIBILITY_REQUEST = "CoverageEligibilityRequest";
   public static final String COVERAGE_ELIGIBILITY_RESPONSE = "CoverageEligibilityResponse";
   public static final String DETECTED_ISSUE = "DetectedIssue";
   public static final String DEVICE = "Device";
   public static final String DEVICE_DEFINITION = "DeviceDefinition";
   public static final String DEVICE_METRIC = "DeviceMetric";
   public static final String DEVICE_REQUEST = "DeviceRequest";
   public static final String DEVICE_USE_STATEMENT = "DeviceUseStatement";
   public static final String DIAGNOSTIC_REPORT = "DiagnosticReport";
   public static final String DOCUMENT_MANIFEST = "DocumentManifest";
   public static final String DOCUMENT_REFERENCE = "DocumentReference";
   public static final String EFFECT_EVIDENCE_SYNTHESIS = "EffectEvidenceSynthesis";
   public static final String ENCOUNTER = "Encounter";
   public static final String ENDPOINT = "Endpoint";
   public static final String ENROLLMENT_REQUEST = "EnrollmentRequest";
   public static final String ENROLLMENT_RESPONSE = "EnrollmentResponse";
   public static final String EPISODE_OF_CARE = "EpisodeOfCare";
   public static final String EVENT_DEFINITION = "EventDefinition";
   public static final String EVIDENCE = "Evidence";
   public static final String EVIDENCE_VARIABLE = "EvidenceVariable";
   public static final String EXAMPLE_SCENARIO = "ExampleScenario";
   public static final String EXPLANATION_OF_BENEFIT = "ExplanationOfBenefit";
   public static final String FAMILY_MEMBER_HISTORY = "FamilyMemberHistory";
   public static final String FLAG = "Flag";
   public static final String GOAL = "Goal";
   public static final String GRAPH_DEFINITION = "GraphDefinition";
   public static final String GROUP = "Group";
   public static final String GUIDANCE_RESPONSE = "GuidanceResponse";
   public static final String HEALTHCARE_SERVICE = "HealthcareService";
   public static final String IMAGING_STUDY = "ImagingStudy";
   public static final String IMMUNIZATION = "Immunization";
   public static final String IMMUNIZATION_EVALUATION = "ImmunizationEvaluation";
   public static final String IMMUNIZATION_RECOMMENDATION = "ImmunizationRecommendation";
   public static final String IMPLEMENTATION_GUIDE = "ImplementationGuide";
   public static final String INSURANCE_PLAN = "InsurancePlan";
   public static final String INVOICE = "Invoice";
   public static final String LIBRARY = "Library";
   public static final String LINKAGE = "Linkage";
   public static final String LIST = "List";
   public static final String LOCATION = "Location";
   public static final String MEASURE = "Measure";
   public static final String MEASURE_REPORT = "MeasureReport";
   public static final String MEDIA = "Media";
   public static final String MEDICATION = "Medication";
   public static final String MEDICATION_ADMINISTRATION = "MedicationAdministration";
   public static final String MEDICATION_DISPENSE = "MedicationDispense";
   public static final String MEDICATION_KNOWLEDGE = "MedicationKnowledge";
   public static final String MEDICATION_REQUEST = "MedicationRequest";
   public static final String MEDICATION_STATEMENT = "MedicationStatement";
   public static final String MEDICINAL_PRODUCT = "MedicinalProduct";
   public static final String MEDICINAL_PRODUCT_AUTHORIZATION = "MedicinalProductAuthorization";
   public static final String MEDICINAL_PRODUCT_CONTRAINDICATION = "MedicinalProductContraindication";
   public static final String MEDICINAL_PRODUCT_INDICATION = "MedicinalProductIndication";
   public static final String MEDICINAL_PRODUCT_INGREDIENT = "MedicinalProductIngredient";
   public static final String MEDICINAL_PRODUCT_INTERACTION = "MedicinalProductInteraction";
   public static final String MEDICINAL_PRODUCT_MANUFACTURED = "MedicinalProductManufactured";
   public static final String MEDICINAL_PRODUCT_PACKAGED = "MedicinalProductPackaged";
   public static final String MEDICINAL_PRODUCT_PHARMACEUTICAL = "MedicinalProductPharmaceutical";
   public static final String MEDICINAL_PRODUCT_UNDESIRABLE_EFFECT = "MedicinalProductUndesirableEffect";
   public static final String MESSAGE_DEFINITION = "MessageDefinition";
   public static final String MESSAGE_HEADER = "MessageHeader";
   public static final String MOLECULAR_SEQUENCE = "MolecularSequence";
   public static final String NAMING_SYSTEM = "NamingSystem";
   public static final String NUTRITION_ORDER = "NutritionOrder";
   public static final String OBSERVATION_DEFINITION = "ObservationDefinition";
   public static final String OPERATION_DEFINITION = "OperationDefinition";
   public static final String OPERATION_OUTCOME = "OperationOutcome";
   public static final String ORGANIZATION = "Organization";
   public static final String ORGANIZATION_AFFILIATION = "OrganizationAffiliation";
   public static final String PARAMETERS = "Parameters";
   public static final String PAYMENT_NOTICE = "PaymentNotice";
   public static final String PAYMENT_RECONCILIATION = "PaymentReconciliation";
   public static final String PERSON = "Person";
   public static final String PLAN_DEFINITION = "PlanDefinition";
   public static final String PROCEDURE = "Procedure";
   public static final String PROVENANCE = "Provenance";
   public static final String QUESTIONNAIRE = "Questionnaire";
   public static final String QUESTIONNAIRE_RESPONSE = "QuestionnaireResponse";
   public static final String RELATED_PERSON = "RelatedPerson";
   public static final String REQUEST_GROUP = "RequestGroup";
   public static final String RESEARCH_DEFINITION = "ResearchDefinition";
   public static final String RESEARCH_ELEMENT_DEFINITION = "ResearchElementDefinition";
   public static final String RESEARCH_STUDY = "ResearchStudy";
   public static final String RESEARCH_SUBJECT = "ResearchSubject";
   public static final String RISK_ASSESSMENT = "RiskAssessment";
   public static final String RISK_EVIDENCE_SYNTHESIS = "RiskEvidenceSynthesis";
   public static final String SCHEDULE = "Schedule";
   public static final String SEARCH_PARAMETER = "SearchParameter";
   public static final String SERVICE_REQUEST = "ServiceRequest";
   public static final String SLOT = "Slot";
   public static final String SPECIMEN = "Specimen";
   public static final String SPECIMEN_DEFINITION = "SpecimenDefinition";
   public static final String STRUCTURE_DEFINITION = "StructureDefinition";
   public static final String STRUCTURE_MAP = "StructureMap";
   public static final String SUBSCRIPTION = "Subscription";
   public static final String SUBSTANCE = "Substance";
   public static final String SUBSTANCE_NUCLEIC_ACID = "SubstanceNucleicAcid";
   public static final String SUBSTANCE_POLYMER = "SubstancePolymer";
   public static final String SUBSTANCE_PROTEIN = "SubstanceProtein";
   public static final String SUBSTANCE_REFERENCE_INFORMATION = "SubstanceReferenceInformation";
   public static final String SUBSTANCE_SOURCE_MATERIAL = "SubstanceSourceMaterial";
   public static final String SUBSTANCE_SPECIFICATION = "SubstanceSpecification";
   public static final String SUPPLY_DELIVERY = "SupplyDelivery";
   public static final String SUPPLY_REQUEST = "SupplyRequest";
   public static final String SYSTEM_LEVEL_OPERATIONS = "System Level Operations";
   public static final String TASK = "Task";
   public static final String TERMINOLOGY_CAPABILITIES = "TerminologyCapabilities";
   public static final String TEST_REPORT = "TestReport";
   public static final String TEST_SCRIPT = "TestScript";
   public static final String VALUE_SET = "ValueSet";
   public static final String VERIFICATION_RESULT = "VerificationResult";
   public static final String VISION_PRESCRIPTION = "VisionPrescription";
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

src/main/java/ca/uhn/fhir/jpa/starter/util/AuthenticationInterceptor.java

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
GET {{ipAdd}}/fhir-adapter-service/migration/site-migrate
``
### UserMigration endpoint

``
GET {{ipAdd}}/fhir-adapter-service/migration/user-migrate
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