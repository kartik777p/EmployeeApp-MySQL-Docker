FROM openjdk:21
WORKDIR /app

COPY target/EmployeeApp-MySQL-0.0.1-SNAPSHOT.jar /app/EmployeeApp.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","EmployeeApp.jar"]