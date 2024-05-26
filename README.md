<!-- Introduction -->

# Job Board API

Prototype of backend service for a job board platform. The service provides endpoints for managing job offers with
related information, mainly company data.

<!-- ABOUT -->

## About

The service utilizes Spring Data JPA for retrieving data and Spring Web for presenting it over endpoints. The data is
cached thanks to Caffeine. The API is **globally rate limited** using bucket4j library, requests are authorized using **OAuth2 JWT**
with help of Spring Security. API documentation is automatically generated
using **openAPI** and can be accessed via the Swagger UI.

## Docker image

**You can find the docker image of the whole application [here](https://hub.docker.com/layers/kapiaszczyk/job-board/0.1.0/images/sha256-09c38ff2c95e4b528dad32a330f6b5f627994537d9f86ccb958906cf7b52f077?context=repo).**

## Features

- Retrieval of job offers with various levels of detail and filtering options
- Global rate limiting
- Caching layer
- Authorization and authentication using JWT

### Data retrieval

Data stored in a PostgreSQL can be retrieved using various endpoints. All input is validated using Hibernate validator,
and the data is returned in a JSON format. The data can be filtered, sorted and paginated. Most frequently used queries
are cached using Caffeine, which is invalidated after a certain period of time.

### Rate Limiting

Rate limiting is implemented using bucket4j. The limit is globally applied to all requests and refuses handling requests
over the limit. The limit can be set through environment variables.

### Security and authentication

User can register and log in using OAuth 2.0 Resource Server, that is running alongside the application. The user is
authenticated using JWT tokens. Unauthorized users can only view data, while authenticated users have different
privileges based on their role. Passwords stored in the database are hashed. The tokens are encrypted and signed using
RSA keys.

### Filters

Two filters are implement in the application - one for rate limiting and one for logging request and response details.

### Spring Actuator

Information about the application can be accessed via Spring Actuator. Access to these endpoints is restricted to
administrator and superuser accounts.

### Logging

Basic logging of errors and events is implemented using Logback via the slf4j facade. The logger logs human-readable
statements to the console and JSON statements to a file. The file is rolled over daily or when it reaches a certain
size. Archived logs are kept for a month and then deleted.  
**Sensitive data is encrypted and never stored explicitly**.

### Spring AOP

A single aspect is used to log the execution time of all service methods dealing with data.

## Running and accessing the application

The application can be run using included `docker-compose.yaml`. It will be available under the `localhost:8080`
address. For running the application securely, default credentials and keys should be changed. Full documentation is
created by OpenApi and available under the `/swagger-ui.html` or `/v3/api-docs`.

The following endpoints will be available:

| **Endpoint**                                 | **Method** | **Description**                                                                   |
|----------------------------------------------|------------|-----------------------------------------------------------------------------------|
| `/api/v1/technologies/{id}`                  | *PUT*      | Update a technology                                                               |
| `/api/v1/job-offers/{id}`                    | *GET*      | Get a job offer by id                                                             |
| `/api/v1/job-offers/{id}`                    | *PUT*      | Update a job offer                                                                |
| `/api/v1/job-offers/{id}`                    | *DELETE*   | Delete a job offer                                                                |
| `/api/v1/companies/{id}`                     | *PUT*      | Update a company                                                                  |
| `/api/v1/companies/{id}`                     | *DELETE*   | Delete a company                                                                  |
| `/api/v1/technologies`                       | *GET*      | Get all technologies                                                              |
| `/api/v1/technologies`                       | *POST*     | Add a technology                                                                  |
| `/api/v1/job-offers`                         | *GET*      | Get all job offers                                                                |
| `/api/v1/job-offers`                         | *POST*     | Add a new job offer                                                               |
| `/api/v1/companies`                          | *GET*      | Get company by id                                                                 |
| `/api/v1/companies`                          | *POST*     | Add a new company                                                                 |
| `/api/v1/auth/user`                          | *POST*     | Get user details                                                                  |
| `/api/v1/auth/register`                      | *POST*     | Register a new user                                                               |
| `/api/v1/auth/login`                         | *POST*     | Login as a user                                                                   |
| `/api/v1/job-offers/detailed`                | *GET*      | Get all job offers projected to detailed view                                     |
| `/api/v1/job-offers/detailed/{id}`           | *GET*      | Get a job offer by id projected to detailed view                                  |
| `/api/v1/job-offers/criteria`                | *GET*      | Get job offers filtered by criteria                                               |
| `/api/v1/job-offers/criteria-paged`          | *GET*      | Get job offers filtered by criteria, paged and sorted                             |
| `/api/v1/job-offers/criteria-detailed`       | *GET*      | Get job offers filtered by criteria projected to detailed view                    |
| `/api/v1/job-offers/criteria-detailed-paged` | *GET*      | Get job offers filtered by criteria, paged and sorted, projected to detailed view |
| `/api/v1/job-offers/basic`                   | *GET*      | Get all job offers projected to basic view                                        |
| `/api/v1/job-offers/basic/{id}`              | *GET*      | Get a job offer by id projected to basic view                                     |
| `/api/v1/addresses`                          | *GET*      | Get all addresses                                                                 |
| `/api/v1/addresses/companies/{companyName}`  | *GET*      | Get all addresses by company name                                                 |

*This table was generated from openAPI docs
using [this script](https://github.com/kapiaszczyk/python-scripts/blob/main/documentation_tools/extract_openapi_paths.py)*

### Environment variables

The following properties can be set from `docker-compose.yaml`:

| **Environment variable**           | **Default value**                 | **Corresponding property name**    | **Description**                     |
|------------------------------------|-----------------------------------|------------------------------------|-------------------------------------|
| `JDBC_DATABASE_URL`                | jdbc:postgresql://localhost:5432/ | `spring.datasource.url`            | Database url with hostname and port |
| `JDBC_DATABASE_NAME`               | jobboard                          | `spring.datasource.url`            | Name of the database                |
| `JDBC_DATABASE_USERNAME`           | api                               | `spring.datasource.username`       | Database user username              |
| `JDBC_DATABASE_PASSWORD`           | apipassword                       | `spring.datasource.password`       | Database user password              |
| `RATE_LIMIT_BUCKET_CAPACITY`       | 100                               | `rate.limit.bucket.capacity`       | Global bucket capacity              |
| `RATE_LIMIT_REFILL_PERIOD_SECONDS` | 60                                | `rate.limit.refill.period.seconds` | Refill period for the global bucket |
| `RATE_LIMIT_REFILL_TOKENS`         | 100                               | `rate.limit.refill.tokens`         | Tokens to refill                    |
| `CACHE_INITIAL_CAPACITY`           | 100                               | `cache.initial.capacity`           | Cache initial capacity              |
| `CACHE_MAXIMUM_SIZE`               | 500                               | `cache.maximum.size`               | Maximum size of the cache           |
| `CACHE_EXPIRE_AFTER_WRITE_MINUTES` | 1                                 | `cache.expire.after.write.minutes` | Expiration time of the cache        |

*This table was generated from `application.properties` docs
using [this script](https://github.com/kapiaszczyk/python-scripts/blob/main/documentation_tools/properties_env_var_extractor.py)*

## Reliability

Integration tests were written using Mockito and Testcontainers and the application was end-to-end tested using Postman.

## Future improvements

The application is a working prototype that is standalone and not suitable for real-world use. As of the current
version, the following improvements could be made:

- limiting creation of new accounts to a specific group of users
- more detailed tests covering edge cases
- optimization of some operations (mainly inserting and updating data)
- caching improvements
- JWT token refreshing
- and many more

## Security considerations

Since anyone can create any type of account, the application is not suitable for real-world use.

<!-- STACK -->

## Built With

[![bucket4j][bucket4j]][bucket4j-url]
[![PostgreSQL][PostgreSQL]][PostgreSQL-url]
[![Caffeine][Caffeine]][Caffeine]
[![Springdoc][springdoc]][Springdoc-url]
[![Spring Actuator][Spring Actuator]][Spring Actuator-url]
[![Spring AOP][Spring AOP]][Spring AOP-url]
[![Spring Data JPA][Spring Data JPA]][Spring Data JPA-url]
[![Spring Security][Spring Security]][Spring Security-url]
[![Spring Web][Spring Web]][Spring Web-url]
[![Maven][Maven]][Maven-url]
[![Postman][Postman]][Postman-url]
[![Docker][Docker]][Docker-url]
[![Swagger][Swagger]][Swagger-url]

<!-- MARKDOWN LINKS & IMAGES -->

[bucket4j]: https://img.shields.io/badge/bucket4j-orange

[bucket4j-url]: https://github.com/bucket4j/bucket4j

[PostgreSQL]: https://img.shields.io/badge/PostgreSQL-blue

[PostgreSQL-url]: https://www.postgresql.org/

[Caffeine]: https://img.shields.io/badge/Caffeine-pink

[Caffeine-url]: https://github.com/ben-manes/caffeine

[Springdoc]: https://img.shields.io/badge/Springdoc-green

[Springdoc-url]: https://springdoc.org/

[Spring Actuator]: https://img.shields.io/badge/Spring_Boot_Actuator-green

[Spring Actuator-url]: https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html

[Spring AOP]: https://img.shields.io/badge/Spring_Boot_AOP-green

[Spring AOP-url]: https://docs.spring.io/spring-framework/reference/core/aop.html

[Spring Data JPA]: https://img.shields.io/badge/Spring_Boot_Data_JPA-green

[Spring Data JPA-url]: https://docs.spring.io/spring-data/jpa/reference/index.html

[Spring Security]: https://img.shields.io/badge/Spring_Boot_Security-green

[Spring Security-url]: https://docs.spring.io/spring-security/reference/index.html

[Spring Web]: https://img.shields.io/badge/Spring_Boot_Web-green

[Spring Web-url]: https://docs.spring.io/spring-framework/reference/web.html

[Mockito]: https://img.shields.io/badge/Mockito-green

[Mockito-url]: https://site.mockito.org/

[Testcontainers]: https://img.shields.io/badge/Testcontainers-blue

[Testcontainers-url]: https://testcontainers.com/

[Docker]: https://img.shields.io/badge/Docker-blue

[Docker-url]: https://www.docker.com/

[Maven]: https://img.shields.io/badge/Maven-blue

[Maven-url]: https://maven.apache.org/

[Postman]: https://img.shields.io/badge/Postman-orange

[Postman-url]: https://www.postman.com/

[Swagger]: https://img.shields.io/badge/Swagger-green

[Swagger-url]: https://swagger.io/
