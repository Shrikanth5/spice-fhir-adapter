FROM openjdk:17
ADD target/*.jar fhir-adapter-service
ENTRYPOINT ["java", "-jar","fhir-adapter-service"]
EXPOSE 80