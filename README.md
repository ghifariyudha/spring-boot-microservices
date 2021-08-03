# Spring Boot Microservices
## Architecture 
![](https://raw.githubusercontent.com/ghifariyudha/spring-microservices/master/images/Architecture.png)
## Tech Stack

 - Java
 - Spring Boot
 - Spring Cloud
 - Hibernate ORM
 - PostgreSQL
 - Apache Kafka
 - Redis
 - Netflix Eureka
 - Zipkin
 - (*Soon*) ELK Stack

## Quickstart
### 1. Message Broker (Apache Kafka)
See documentation [here](https://kafka.apache.org/quickstart)
> Running Port : 9092

### 2. Cache (Redis)
See documentation [here](https://redis.io/documentation)
> Running Port : 6379

### 3. Distributed Tracing (Zipkin)
See documentation [here](https://zipkin.io/pages/quickstart)
> Running Port : 9411

### 4. Build
```
mvn clean install
```

### 5. Service Discovery (eureka-discovery)
```
cd eureka-discovery
mvn spring-boot:run
```
> Running Port : 8001

### 6. API Gateway (gateway-service)
```
cd gateway-service
mvn spring-boot:run
```
> Running Port : 8002

### 7. Authentication (system-service)
```
cd system-service
mvn spring-boot:run
```
| API-GATEWAY | APPLICATION | SWAGGER |
|--|--|--|
| {HOST}:8002/api-gateway/auth  | {HOST}:8003/auth | {HOST}:8003/swagger-ui.html |

### 8. product-service
```
cd product-service
mvn spring-boot:run
```
| API-GATEWAY | APPLICATION | SWAGGER |
|--|--|--|
| {HOST}:8002/api-gateway/product  | {HOST}:8004/product | {HOST}:8004/swagger-ui.html |

### 9. order-service
```
cd order-service
mvn spring-boot:run
```
| API-GATEWAY | APPLICATION | SWAGGER |
|--|--|--|
| {HOST}:8002/api-gateway/order  | {HOST}:8005/order | {HOST}:8005/swagger-ui.html |

### 10. mail-service
```
cd mail-service
mvn spring-boot:run
```
> Running Port : 8006

## Screenshot
- Eureka Discovery

![](https://raw.githubusercontent.com/ghifariyudha/spring-microservices/master/images/EurekaDiscovery.png)
- Swagger

![](https://raw.githubusercontent.com/ghifariyudha/spring-microservices/master/images/Swagger-order-service.png)
- Apache Kafka

![](https://raw.githubusercontent.com/ghifariyudha/spring-microservices/master/images/ApacheKafka.png)
- Redis

![](https://raw.githubusercontent.com/ghifariyudha/spring-microservices/master/images/Redis-CLI.png)
- Zipkin

![](https://raw.githubusercontent.com/ghifariyudha/spring-microservices/master/images/Zipkin.png)
- Received Mail

![](https://raw.githubusercontent.com/ghifariyudha/spring-microservices/master/images/ReceivedMail.png)
