# Cucumber BDD framework with Serenity reports

## Overview
This project uses Selenium, Cucumber BDD, and Serenity Reports for automated testing.

## Prerequisites
- Java 17 or higher
- Maven 3.6v or higher
- Selenium 4.20v
- Cucumber 7.16v
- Serenity 4.1.4v

## How to Run
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
