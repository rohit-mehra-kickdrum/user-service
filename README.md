Spring Boot JWT Authentication with Swagger and AWS Deployment
--------------------------------------------------------------

This project demonstrates a Spring Boot application implementing JWT token-based authentication and authorization using Spring Security. Additionally, it includes:

-   **Swagger Integration:** Provides interactive API documentation for improved understanding of the application's functionalities.
-   **AWS CI/CD Pipeline:** Utilizes AWS CodeBuild and CodePipeline to automate the build, test, and deployment process.
-   **Deployment Target:** Deploys the application to a Tomcat server on AWS Elastic Beanstalk.

### Project Structure

The project is organized as follows:

-   `src/main/java`: Contains all Java source code for the application logic.
-   `src/main/resources`: Houses configuration files, application properties, and Swagger documentation.
-   `pom.xml`: Defines project dependencies and build configuration.
-   `buildspec.yml`: Specifies build steps for AWS CodeBuild.


### Prerequisites

-   Java 11+
-   Maven
-   AWS Account with CodeBuild, CodePipeline, and Elastic Beanstalk services configured.

### Getting Started

1.  **Clone the project:**


```
https://github.com/rohit-mehra-kickdrum/user-service.git

```


2.  **Install dependencies:**


```
mvn clean install

```

2.  **Configuration:**

-   Update application properties (`src/main/resources/application.properties`) with your specific configurations (database connection details, AWS credentials, etc.)
-   Configure AWS services (CodeBuild, CodePipeline, Elastic Beanstalk) for deployment automation (refer to their respective documentation).

3.  **Run the application:**


```
mvn spring-boot:run

```


**Access the API documentation:**

-   By default, Swagger should be accessible at `http://localhost:5000/swagger-ui/index.html`.

### Deployment (Optional)

1.  Configure the pipeline in AWS CodePipeline.
2.  This pipeline will trigger a build in CodeBuild based on your chosen source control trigger.
3.  The build process will use `buildspec.yml` to build, test, and package the application.
4.  Upon successful build, the pipeline will deploy the application to your Elastic Beanstalk environment.


This project provides a starting point for building a secure Spring Boot application with JWT authentication, Swagger integration, and AWS deployment. Explore the codebase further to understand the implementation details.
