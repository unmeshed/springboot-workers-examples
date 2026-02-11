# Unmeshed Spring Boot Workers Examples

This guide shows the minimal, generic setup to run Unmeshed workers in any Spring Boot application.

## Core setup

1. Add component scanning for Unmeshed SDK packages.
2. Create a `UnmeshedClient` bean.
3. Define worker methods using `@WorkerFunction` in Spring-managed beans.

## 1) Enable component scanning

In your Spring Boot main class, include `io.unmeshed.client` in `@ComponentScan`.

```java
@SpringBootApplication
@ComponentScan(basePackages = {"io.unmeshed.client", "<your.app.base.package>"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

## 2) Create `UnmeshedClient` bean

Create a configuration class:

```java
@Configuration
public class UnmeshedClientConfig {

    @Value("${unmeshed.engine.url}")
    private String engineUrl;

    @Value("${unmeshed.auth.id}")
    private String authId;

    @Value("${unmeshed.auth.token}")
    private String authToken;

    @Bean
    public UnmeshedClient unmeshedClient() {
        ClientConfig config = ClientConfig.builder(authId, authToken)
                .baseUrl(engineUrl)
                .workRequestBatchSize(200)
                .initialDelayMillis(20)
                .stepTimeoutMillis(1000L * 60 * 60 * 24 * 365)
                .build();

        return new UnmeshedClient(config);
    }
}
```

## 3) Configure properties

In `application.properties` or environment variables:

```properties
unmeshed.engine.url=http://localhost:8080
unmeshed.auth.id=<your-auth-id>
unmeshed.auth.token=<your-auth-token>
```

## 4) Create worker methods

Define workers in a Spring bean (`@Service` or `@Component`) with `@WorkerFunction`:

```java
@Service
public class GenericWorkers {

    @WorkerFunction(name = "my-worker", namespace = "default")
    public Map<String, Object> run(Map<String, Object> input) {
        return Map.of("success", true, "input", input);
    }
}
```

You can also use typed input/output classes instead of `Map<String, Object>`.

## 5) Use placeholders and SpEL in `@WorkerFunction`

You can keep worker names and steps configurable using property placeholders, and use SpEL (`#{...}`) for dynamic values.

### Property placeholders (with default)

```java
@WorkerFunction(
    name = "${unmeshed.worker.first.name:first-worker}",
    namespace = "${unmeshed.namespace:spring}",
    maxInProgressExpression = "${unmeshed.worker.first.max-in-progress:100}",
    workStepNames = {
        "${unmeshed.worker.first.step1:first-worker-1}",
        "${unmeshed.worker.first.step2:first-worker-2}"
    }
)
```

### SpEL examples

```java
@WorkerFunction(
    name = "#{'${unmeshed.worker.second.name:second-worker}' + '.' + (systemEnvironment['HOST'] ?: 'local').toLowerCase()}",
    namespace = "#{(systemEnvironment['USER'] ?: 'spring')}",
    workStepNames = {
        "#{ (systemEnvironment['HOST'] != null) ? 'hosted-step' : 'local-step' }",
        "#{ 'STEP-' + '${unmeshed.worker.second.name:second-worker}'.toUpperCase() }"
    }
)
```

Example properties:

```properties
unmeshed.namespace=spring
unmeshed.worker.first.max-in-progress=200
unmeshed.worker.second.max-in-progress=200
```

## Run

```bash
./gradlew bootRun
```
