FROM eclipse-temurin:21-jdk
EXPOSE 8085
COPY target/SuperVisionCadenFrioV2-0.0.1.jar myApp.jar
ENTRYPOINT ["java", "-jar", "myApp.jar"]