# Build Stage
FROM sbtscala/scala-sbt:graalvm-ce-22.3.3-b1-java17_1.9.9_3.4.1
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM apache/spark:3.5.1-scala2.12-java17-ubuntu
WORKDIR /working_dir
COPY --from=build /home/app/target/MavenApplication-1.0-SNAPSHOT.jar /app/work/application.jar