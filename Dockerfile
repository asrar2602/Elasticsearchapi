FROM eclipse-temurin:17

LABEL mentainer="asrar60@gmail.com"

WORKDIR /app_elasticsearchapi

COPY build/libs/Elasticsearchapi-0.0.1-SNAPSHOT.jar /app_elasticsearchapi/elasticsearchapi.jar

ENTRYPOINT ["java", "-jar", "elasticsearchapi.jar"]