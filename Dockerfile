FROM openjdk:17-jdk-alpine3.14 as build
WORKDIR /workspace/app
COPY gradle gradle
COPY build.gradle settings.gradle gradlew ./
COPY src src
RUN ./gradlew build -x test
RUN mkdir -p build/libs/dependency
RUN cd build/libs/dependency; jar -xf /workspace/app/build/libs/*.jar

FROM openjdk:17-jdk-alpine3.14
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/libs/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","-Dspring.profiles.active=prod","com.edsonmoreirajr.votacao.VotacaoApplication"]