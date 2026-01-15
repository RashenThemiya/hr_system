# hr_system
mvn spring-boot:run
mvn clean package


docker build -t mhartvishwajith448/hr-app:latest .
docker push mhartvishwajith448/hr-app:latest
docker run -p 8080:8080 mhartvishwajith448/hr-app:latest
