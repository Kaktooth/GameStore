FROM amazoncorretto:11-alpine-jdk
LABEL maintainer=Xiaomi
WORKDIR /app
COPY build/docker/libs libs/
COPY build/docker/resources resources/
COPY build/docker/classes classes/
ENTRYPOINT ["java", "-cp", "/app/resources:/app/classes:/app/libs/*", "com.store.gamestore.RecommendationApiCoreApplication"]
EXPOSE 8080
