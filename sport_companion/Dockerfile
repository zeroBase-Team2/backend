FROM amazoncorretto:17 AS builder

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

COPY module-api module-api
COPY module-domain module-domain
COPY module-core module-core

# ./gradlew 파일 권한 변경
RUN chmod +x ./gradlew

# gradle 빌드
RUN ./gradlew :module-api:bootJar
FROM amazoncorretto:17

COPY --from=builder module-api/build/libs/*.jar app.jar


ENTRYPOINT [ "java","-jar","/app.jar" ]
VOLUME /tmp