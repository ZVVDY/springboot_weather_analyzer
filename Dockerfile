FROM amazoncorretto:17 as development

VOLUME /tmp

COPY target/springboot_weather_analyzer-0.0.1-SNAPSHOT.jar weather_app.jar

ENV PORT=8082

EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "weather_app.jar"]