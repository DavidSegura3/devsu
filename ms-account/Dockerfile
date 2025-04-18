FROM amazoncorretto:17.0.9-alpine
COPY ./build/libs/ms-account-1.0.jar ms-account.jar
EXPOSE 9000
ENTRYPOINT ["java","-jar","/ms-account.jar"]