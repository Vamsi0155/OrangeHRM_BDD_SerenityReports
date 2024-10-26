# Cucumber BDD framework with Serenity reports

## Overview
This repository contains a Cucumber BDD framework integrated with Serenity Reports for testing web applications. The framework follows behavior-driven development principles, providing a clear and structured approach to writing test scenarios. OrangeHRM, a practice web application, is used as the subject of UI automation testing.
### Key Features
#### Cucumber BDD:
- Enables writing human-readable test scenarios that bridge the gap between non-technical stakeholders and developers.
#### Serenity Reports:
- Provides detailed, narrative-style test reports with test case histories and visual documentation.
#### Parallel Testing:
- Supports parallel execution of tests to optimize time and resources.
#### Selenium Grid Integration:
- Includes the necessary configuration for running tests on Selenium Grid, enabling cross-browser and cross-platform compatibility testing.
#### Page Object Model (POM):
- Helps structure test code for maintainability and reusability.
#### Data-Driven Testing:
- Supports parameterized tests using external data sources for greater test coverage.
#### Cross-Browser Support:
- Tests can be executed on different browsers like Chrome, Firefox, etc.

## Prerequisites
- Java 17 or higher
- Maven 3.6v or higher
- Junit 5.0v or higher
- Selenium 4.20v
- Cucumber 7.16v
- Serenity 4.1.4v
- Selenium-Grid (latest version)

## How to run form cmd line
In the pom.xml, we have configured serenity reports as well as single-page reports.

### To build the Project
To build the project and download all dependencies, run the whole suite following Maven command:
```bash
mvn clean install
```

### To run specific feature, use the following command:
```bash
mvn clean install -Dcucumber.features=src/test/resources/Admin_Module/UserOperation.feature
```

### To run multiple features, use the following command:
```bash
mvn clean install -Dcucumber.features=src/test/resources/Admin_Module/UserOperation.feature,src/test/resources/Admin_Module/Multi_User_Creation.feature
```

### To run features with Tag's, use the following command:
```bash
mvn clean install -Dcucumber.filter.tags="@Regression"
```

### To run features with Tag's, use the following command:
```bash
mvn clean install -Dcucumber.features=src/test/resources/Admin_Module/UserOperation.feature -Dcucumber.filter.tags="@Regression"
```

## Jenkins CI/CD

#### General Section:
 Select "This project is parameterized" and set below parameters.
1. For 1st parameter, select String parameter and set below details:
 - Name: Features
 - Default value: src/test/resources/features
 - Description: -- Pass the features path here by separated coma (,). -- By default, it will pick up all features.
2. For 2nd parameter, select choice parameter and set below details:
 - Name: Tags
 - Choices: Regression, Smoke, Sanity
 - Description: -- Choose the tags. By default Regression
#### Build Section:
1. For Root POM, give as "pom.xml"
2. For Goals and Options, use the following command:
```bash
clean install -Dcucumber.features={$Features} -Dcucumber.filter.tags="@{$Tags}"
```
3. Go to "Advanced" and check the "use custom workspace". Add project directory path.

Note: Add project path in the custom workspace field and Also remainings as per requirements.


## Selenium Grid
Selenium Grid is a tool that allows the execution of tests on multiple machines or virtual machines across different browsers, operating systems, and devices. This enables parallel test execution, reducing the time taken to run a test suite, and supports compatibility testing across various environments.

### Key Concepts in Selenium Grid
#### Hub:
The central point that manages and distributes the test execution across different nodes. It receives the test request and routes it to the appropriate node for execution.
#### Node:
The machines or virtual machines that are connected to the Hub. These nodes execute the tests. Each node can be configured with different operating systems and browsers.

### Types of Test Execution in Selenium Grid
#### Parallel Execution
Run multiple tests simultaneously across different environments to speed up test suites.
#### Cross-Browser Testing
Tests can be executed on different browsers (Chrome, Firefox, Safari, etc.) across different operating systems to ensure compatibility.
#### Cross-Platform Testing
Tests can be executed on different OS (Windows, macOS, Linux) and mobile devices, ensuring platform compatibility.


## Selenium Grid 4.0
The latest version, offering more flexibility and improvements over previous versions.

### Features
Grid 4 can operate in Standalone, Hub and Node, or Distributed modes,
#### Standalone mode:
A simplified mode where you can run everything in one place (ideal for local development).
#### Hub and Node mode:
Traditional mode where a Hub distributes tests to multiple Nodes.
#### Distributed mode:
Components of the Grid are broken up into multiple processes that can run independently (more scalable).

### Standalone Setup (Single machine):
1. Download "selenium-server-4.23.1.jar" from official site and place it.
2. Open cmd and run below command to start Grid,
~~~bash
java -jar selenium-server-4.23.1.jar standalone
~~~
3. Open URL to see grid/sessions: http://localhost:4444/  (or) http://<your-ip>:4444/

### Hub & Node setup (Multiple machines):
1. Download on both Hub & Node machines "selenium-server-4.23.1.jar" and place it.
2. Open cmd and run below cmd to make machine as Hub,
~~~bash
java -jar selenium-server-4.23.1.jar hub
~~~
3. Run below cmd to make machine as Node,
~~~bash
java -jar selenium-server-4.23.1.jar node --hub http://<hub-ip>:4444
~~~
4. Open URL to see grid/sessions: http://localhost:4444/  (or) http://<hub-ip>:4444/


## Docker
Docker is a Platform as a Service that provides OS-level virtualization. It makes it easy to create, deploy, and run applications using containers. Containers act as a freshly installed OS, each with its own software, libraries, and configurations.

### Selenium Grid Setup with Docker Containers (Manually)
1. Pull Selenium-hub image by following command
```bash
docker pull selenium/hub
```

2. Pull FireFox image by following command
```bash
docker pull selenium/node-firefox
```

3. Pull Chrome image by following command
```bash
docker pull selenium/node-chrome
```

4. Verify the pulled all Images
```bash
docker images
```

5. Create a network grid
```bash
docker network create <grid_name>
```
For example,
```bash
docker network create grid
```

6. Create a Hub
```bash
docker run -d -p <ports_range>:<ports_range> --net <grid_name> --name <hub_name> <img_name>
```
For example,
```bash
docker run -d -p 4442-4444:4442-4444 --net grid --name selenium-hub selenium/hub
```

7. Create a Node's
```bash
docker run -d --net <grid_name> -e SE_EVENT_BUS_HOST=<hub_name> -e SE_EVENT_BUS_PUBLISH_PORT=<port_num> -e SE_EVENT_BUS_SUBSCRIBE_PORT=<port_num> <img_name>
```
For Chrome browser,
```bash
docker run -d --net grid -e SE_EVENT_BUS_HOST=selenium-hub -e SE_EVENT_BUS_PUBLISH_PORT=4442 -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 selenium/node-chrome
```
For Firefox browser,
```bash
docker run -d --net grid -e SE_EVENT_BUS_HOST=selenium-hub -e SE_EVENT_BUS_PUBLISH_PORT=4442 -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 selenium/node-firefox
```

8. When you are done using the Grid, and the containers have exited, the network can be removed with the following command
```bash
docker network rm grid
```


### Selenium Grid Setup with 'docker-compose.yaml' file
1. Create a file docker-compose.yaml with Required configurations. For more visit: https://github.com/seleniumHQ/docker-selenium

2. Run docker-compose.yaml from cmd
```bash
docker-compose up
```

3. To check hub & nodes running status:
http://localhost:4444/grid/console

4. Run the automation suite/tests

5. To increase number of nodes for the browser
```bash
docker-compose scale chrome=3
```

6. To stop the grid and cleanup the created containers as well as runs
```bash
docker-compose down
```

