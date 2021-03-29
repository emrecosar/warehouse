# Warehouse

It is a warehouse software. This software should hold articles, and the articles should contain an identification
number, a name and available stock.

It should be possible to load articles into the software from a file, see the attached inventory.json.

The warehouse software should also have products, products are made of different articles. Products should have a name,
price, and a list of articles of which they are made from with a quantity.

The products should also be loaded from a file, see the attached products.json.

### The warehouse should have at least the following functionality;

- Get all products and quantity of each that is an available with the current inventory
- Remove(Sell) a product and update the inventory accordingly

### Assumptions

- `product name` is the key to operate on product entities.
- Every product is made of at least one article.
- `article id` is the key to operate on article entities.
- When a product is sold, the product is not removed from warehouse. Admin user can remove a product from products.
- While listing the products, all products will be returned with quantities even there is a shortage in it's related
  articles.
- While listing the product(s), the product's quantity is being calculated on-the-fly everytime it's requested.
- There is no input about the price of a product/article. So, every product's price is calculated by its total number of
  items before storing in the warehouse. All item's price valued as "1.00" without any currency in this example.
- When the service starts, it first loads the `inventory.json` and then `products.json` from `/src/main/resources/json`
  directory into the service.
- Basic Auth is in place to consume the API.
- There are 2 roles in the service, `ADMIN` and `USER`.
    - `ADMIN` can sell products, add/remove products/articles
    - `USER` can list products/articles

There is always rooms for improvements but due to limited time, I list the possible improvements;

- Store credentials in a secret manager
- Input validation
- Update Article stock
- Improve product quantity calculation logic
- Search Product or Article
- ...

## Tech Stack

* Java 11
* Spring Boot 2.4.4
* JUnit 5

## How to Run

### Run with maven

Build and Run:

```bash
mvn clean package spring-boot:run
```

Also;

Build:

```bash
mvn clean package
```

Run tests only:

```bash
mvn clean test
```

Run the application:

```bash
mvn spring-boot:run
```

### Run dockerized application with Docker

```bash
./mvnw spring-boot:build-image
docker run -p 8080:8080 docker.io/library/warehouse:1.0.0
```

### Run as JAR file

Build:

```bash
mvn clean package
```

Run the application:

```bash
java -jar target/warehouse-1.0.0.jar
```

### Swagger API Documentation

- Open API 3.0

```bash
http://localhost:8080/warehouse/v3/api-docs/
```

- Swagger UI

```bash
http://localhost:8080/warehouse/swagger-ui.html
```

### Test Reports

Test reports are generated under `target/surfire-reports`