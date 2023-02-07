FROM adoptopenjdk/openjdk8
ARG JAR_FILE_PATH=target/*.jar
COPY ${JAR_FILE_PATH} app.jar
CMD ["java", "-jar", "app.jar"]