# Backend

## Pre-requisites
- Development set-up:
  - Java 17
  - Docker (for local building & testing)

## Coding conventions

---
### Code Directory
Code directory structure follows what Spring documentation mentions as the [typical spring codebase layout](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using.structuring-your-code.locating-the-main-class), which is a domain-based file clustering. For the sake of code maintainability, though, we'll add another level of grouping for the controllers, service, etc. This will help us when the logic in a domain is too complicated and/or we need to version our APIs.

Simply put, the code structure is as follows:
```agsl
org
 +- teamseven
     +- hms
        +- backend
            +- MyApplication.java
            |
            +- domain
            |   +- controller
            |   +- dto
            |   +- service
            |   +- repository
            |   +- entity
            |   +- annotation
            +- shared
                +- annotation
                +- exception
```
`shared/` should contain all the utilities and/or classes to be reused across multiple domains.

Each domain's subdirectory bears the following responsibilities:
**(TO BE DECIDED / REVIEWED)**

| subdirectory | responsibilities                                                                      |
|--------------|---------------------------------------------------------------------------------------|
| controller   | endpoints                                                                             |
| DTO          | data transfer objects to return & receive from clients (i.e request & response specs) |
| service      | business logic                                                                        |
| entity       | database object models (follow table structures)                                      |
| repository   | database queries                                                                      |
| annotation   | custom annotations belonging to a certain domain (not used across multiple domains    |


### Controllers
- All endpoint paths should follow the Rest API standards, which suggest endpoints to be written in plural nouns.
- Additionally, for versioning and prefix, endpoints are to be written prefixed by `/api/v{version-number}`. The `sample` package has several endpoints for reference.
- All responses should be wrapped using the same data structure, namely `ResponseWrapper`.
- Logic on controller should be minimum and should reside on the `service` directory.

### Exception Handling
- All exceptions thrown / unhandled by service will be caught by `RestExceptionHandler` class.
- Please refrain from adding try-catch statements in controller classes.
- To customize a new error response to hand over to the client, feel free to add a new handler method to said class.
- For every new handler method added to `RestExceptionHandler`, please make sure that the response code returned to the client is true to the nature of the exception, e.g: in case of `NoHostFoundException`, make sure to return `500` instead of `400`. This is strict especially if you're handling a Java built-in exception instead of a custom one.
- Don't expose any data / server / internal exception trace to the client, return a generic error message instead. If necessary, log the error trace.

### Custom controller access validation
Often times we might need to guard access to endpoints. To do that, we can make use of Spring support of [Aspect-Oriented Programming](https://docs.spring.io/spring-framework/docs/3.0.x/spring-framework-reference/html/aop.html).

A sample of this custom validation can be seen as implemented by the classes `SampleAccessValidated` and `SampleAccessValidatedAspect`. 

They're attached to the endpoint `/api/v1/samples/access-validations` as follows:
```
    @SampleAccessValidated
    @GetMapping("/access-validations")
    public ResponseEntity<ResponseWrapper> getWithValidatedAccess() {
        return ResponseEntity.ok(
                new ResponseWrapper.Success<>(sampleService.getSampleResponse())
        );
    }
```
Have a try to find out how the validation works as a guard :rotating_light:

If an access validation logic is only applied to a certain domain, then placing the `annotation/` directory inside the domain-specific directory is also acceptable.

## Database migration
All database changes are run automatically by Flyway on service boot up.

All DDL scripts are kept inside `resources/db/migration`. Deployment scripts are prefixed by `V`, whereas revert scripts have `U` as their prefixes.
Naming convention is as follows
```
{U|V}{version_num}__{description_may_be_separated_by_underscores}.sql
```

## Running the application
To run, use the following command:
```agsl
./mvnw [clean] spring-boot:run
```
Omit `clean` if you don't need to clean the old build files generated.

Alternatively, you can also try running the application using Docker :D. This exposes the service on port 8080
```agsl
docker compose up -d
```
