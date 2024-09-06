# Product Management

Product Management is service build with Spring Boot that allows users to manage products in their inventory.


### Features

- Create a product;
- Retrieve a product by its id;
- Retrieve all registered products;
- Delete a product of a given id.

### Getting Started

Following the instructions down below you'll get a copy of the project, so you can run it from your local machine.

#### Requirements

Before running this project locally, make sure you have installed:

- **Java 17** or later: You can download it from [here](https://adoptopenjdk.net/) or use a package manager like `sdkman`.
- **Gradle**: You can install Gradle by following the instructions [here](https://gradle.org/install/).
- **Git**: Ensure Git is installed and accessible from the command line.

To verify your installations, run the following commands:

```bash
java -version
gradle -version
git --version
```

### Installing

1. Clone the repository to your local machine:
   `$ git clone https://github.com/FelipeTomazEC/product-management.git`

2. Enter in the directory of the project:
   `$ cd product-management`

3. Build the application:
   `$ ./gradlew build`

4. Start the application:
 `$ ./gradlew bootRun`

5. That's all. The REST API will be available at http://localhost:8080;

