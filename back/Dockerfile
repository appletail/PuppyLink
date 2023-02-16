FROM adoptopenjdk/openjdk8
COPY ./key.json key.json
ENV GOOGLE_APPLICATION_CREDENTIALS /key.json
ARG JAR_FILE_PATH=target/*.jar
COPY ${JAR_FILE_PATH} app.jar
EXPOSE 8085
CMD ["java", "-jar", "app.jar"]