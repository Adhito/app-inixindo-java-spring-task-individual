FROM openjdk:11
ADD target/maybank-springboot-mvc-0.0.1-SNAPSHOT.jar app-javaspring-individual.jar	
ENTRYPOINT ["java", "-jar", "app-javaspring-individual.jar"]
