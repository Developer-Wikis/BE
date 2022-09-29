FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar --Djasypt.encryptor.password=1234
ENTRYPOINT ["java","-jar","/app.jar"]
