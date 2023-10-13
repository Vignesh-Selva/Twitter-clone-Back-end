FROM openjdk:17-jdk

COPY target/apis-0.0.1-SNAPSHOT.jar twitterclone.jar

ENTRYPOINT ["java", "-jar", "twitterclone.jar"]
