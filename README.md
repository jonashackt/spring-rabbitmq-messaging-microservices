# spring-rabbitmq-messaging
======================================================================================
[![Build Status](https://travis-ci.org/jonashackt/spring-rabbitmq-messaging.svg?branch=master)](https://travis-ci.org/jonashackt/spring-rabbitmq-messaging)

Example project showing how to build a scalable microservice architecture using Spring Boot &amp; RabbitMQ

We´re using [RabbitMQ Docker image](https://hub.docker.com/_/rabbitmq/) here. So if you fire it up with `docker-compose up -d`, you can easily login to the management gui at http://localhost:15672 using `guest` & `guest` as credentials.

### Build sequence matters

At the weatherservice we´re also using [testcontainers](https://www.testcontainers.org/) to fully instanciate every microservice needed to test the whole interaction with RabbitMQ.

Therefore the sequence of module build inside our [pom.xml](pom.xml) here is crucial:

```
	<modules>
		<module>weathermodel</module>
		<module>weatherbackend</module>
		<module>weatherservice</module>
	</modules>
```
First the shared domain & event classes are packaged into a .jar file, so that every service is able to use it.

Then the weatherbackend is build and tested - which does everything in the context of one microservice.

### Testcontainers only inside weatherbackend

Although we could also use [docker-compose.yml](docker-compose.yml) right here in the weatherbackend test classes, this could lead to errors - because testcontaiers would also try to spin up a `weatherbackend` Docker container, which we don´t have at build time of the weatherbackend itself (cause the Spring Boot jar isn´t ready right then).

But there´s something like the `org.testcontainers.containers.GenericContainer` we can use to spin up a RabbitMQ without a docker-compose.yml. Just have a look into the test class [SendAndReceiveTest](weatherbackend/src/test/java/de/jonashackt/SendAndReceiveTest.java):

```
...
import org.junit.ClassRule;
import org.junit.Rule;
...
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
...

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeatherBackendApplication.class)
@ContextConfiguration(initializers = {SendAndReceiveTest.Initializer.class})
public class SendAndReceiveTest {

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            TestPropertyValues.of(
                    "spring.rabbitmq.host=" + rabbitMq.getContainerIpAddress(),
                    "spring.rabbitmq.port=" + rabbitMq.getMappedPort(5672))
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @ClassRule
    public static GenericContainer rabbitMq = new GenericContainer("rabbitmq:management")
            .withExposedPorts(5672)
            .waitingFor(Wait.forListeningPort());
```

As Testcontainers doesn´t guarantee that the RabbitMQ container is reachable under the host name `localhost` (see https://github.com/testcontainers/testcontainers-java/issues/669#issuecomment-385873331) and therefore the test execution leads to `ConnectionRefused` Exceptions from the Spring Boot Autoconfiguraiton trying to reach RabbitMQ on this host, we need to go a different path.

But since we need to configure the RabbitMQ host url and port in the early stage of SpringBootTest initialization, we need the help of a `org.springframework.context.ConfigurableApplicationContext` to dynamically set our `spring.rabbitmq.host` property. Togehter with [TestPropertyValues](https://dzone.com/articles/testcontainers-and-spring-boot) this could be done easily as seen in the code.


### Testcontainers, the 'real' docker-compose.yml and the weatherservice

The final `weatherservice` build then uses the successful build output of the `weatherbackend` inside the corresponding [Dockerfile](weatherbackend/Dockerfile):

```
...
# Add Spring Boot app.jar to Container
ADD "target/weatherbackend-0.0.1-SNAPSHOT.jar" app.jar
...
```

Now the service definition inside the [docker-compose.yml](docker-compose.yml) again uses that Dockerfile to spin up a microservice Docker container containing the `weatherbackend`:

```
version: '3.7'

services:

 rabbitmq:
  image: rabbitmq:management
  ports:
  - "5672:5672"
  - "15672:15672"
  tty:
    true

 weatherbackend:
   build: ./weatherbackend
   ports:
   - "8090"
   tty:
     true
   restart:
     unless-stopped
```
