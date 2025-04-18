FROM amazoncorretto:17.0.9-alpine
COPY ./build/libs/ms-client-1.0.jar ms-client.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","/ms-client.jar"]