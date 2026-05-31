FROM eclipse-temurin:17-jdk
COPY ThreeKingdom.java /app/ThreeKingdom.java
WORKDIR /app
RUN javac ThreeKingdom.java
EXPOSE 8080
CMD ["java", "ThreeKingdom"]
