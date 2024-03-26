# ecobasket

Ecobasket handelt es sich um einen OnlineShop

## Getting started

1. Build the Quarkus application:
./mvnw clean compile

2. Run the application:
./mvnw compile quarkus:dev

3. Run tests:
./mvnw test or mvn test


 running on http://localhost:8080/\
 swagger UI can be opened through this http://localhost:8080/q/swagger-ui/#/

### Packaging and running the application
./mvnw package

#### default Admin Account
- Username: admin
- Password: admin
- Role: Admin

##### default user Account for test purpose
- Username: testuser
- Password: testuser
- Role: user

Hint: when the user are created their username is also their password initally.

##### example data

in file changeLog.xml the example data is already given for the testing purpose. 

Hint: in Warenkorb one cannot add the position as the user get the WarenKorb Id initially radomly. 


