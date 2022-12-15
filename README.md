# Assignment-interview
Capgemini Back-end coding assignment
# Account API For Existing Customer
___
### Spring Boot and ReactJS Application

---
This project provides to create account for existing customers.

### Summary
The assessment consists of an API to be used for opening a new “current account” of already existing
customers.

#### Requirements

• The API will expose an endpoint which accepts the user information (customerID,
initialCredit).

• Once the endpoint is called, a new account will be opened connected to the user whose ID is
customerID.

• Also, if initialCredit is not 0, a transaction will be sent to the new account.

• Another Endpoint will output the user information showing Name, Surname, balance, and
transactions of the accounts.
___
The application has 2 apis
* AccountAPI
* CustomerAPI

```html
Before we start we need to initialise some customer data to use the createAccunt API
GET /api/v1/customer/initialise to initialise some Customers 
POST /api/v1/account - creates a new account for existing customer
GET /api/v1/customer/{customerId} - retrieves a customer
GET /api/v1/customer - retrieves all customers
```

JUnit test coverage is 100% as well as integration tests are available.


### Tech Used

---
- Java 17
- Spring Boot
- Spring Data JPA
- Restful API
- OpenAPI documentation
- H2 In memory database

### Prerequisites

---
- Maven
- Java 17

### Run & Build

---

#### Maven
$ cd account/
$ mvn clean install
$ mvn spring-boot:run
```

### Swagger UI will be run on this url
`http://localhost:8089/swagger-ui/index.html`

### H2 database will be run on this url
`http://localhost:8089/h2-ui`
