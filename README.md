# roman-numeral-converter
This is a Spring Boot Application exposes a REST API to convert a given integer to its corresponding Roman Numeral

<!-- TOC -->
* [roman-numeral-converter](#roman-numeral-converter)
  * [Design Architecture](#design-architecture)
  * [Development Tools](#development-tools)
  * [Steps to Build and Run the stack without devops functionalities](#steps-to-build-and-run-the-stack-without-devops-functionalities)
  * [Steps to Build and Run the stack with devops functionalities(ELK)](#steps-to-build-and-run-the-stack-with-devops-functionalitieselk)
  * [Kibana setup port 5601](#kibana-setup-port-5601)
    * [Kibana- View logs](#kibana--view-logs-)
    * [Kibana - View Application Performance Metrics](#kibana---view-application-performance-metrics)
  * [Setup Grafana - To View the Graphical representation of Application metrics - port 3000](#setup-grafana---to-view-the-graphical-representation-of-application-metrics---port-3000)
  * [Testing](#testing)
    * [Run Unit tests](#run-unit-tests)
    * [Run Acceptance tests](#run-acceptance-tests)
    * [Load testing report](#load-testing-report)
    * [Manual test results](#manual-test-results)
      * [Valid case](#valid-case)
      * [Invalid Testcase 1](#invalid-testcase-1)
      * [Invalid Testcase 2](#invalid-testcase-2)
  * [How to un-install Stack?](#how-to-un-install-stack)
<!-- TOC -->

## Design Architecture
![roman-numeral-converter-architecture.png](screenshots/roman-numeral-converter-architecture.png)

## Development Tools
* spring-boot 3.3.1
* Java 21
* Maven
* Junit 5
* Mockito
* ElasticSearch
* Logstash
* Kibana
* Prometheus
* Grafana
* Docker

## Steps to Build and Run the stack without devops functionalities

1. Clone the git repo
`
git clone https://github.com/ElavarasiMarkandan/roman-numeral-converter.git
`
2. Run the `RomanNumeralConverterApplication` class


## Steps to Build and Run the stack with devops functionalities(ELK)
1. Clone the git repo
`
git clone https://github.com/ElavarasiMarkandan/roman-numeral-converter.git
`

2. Below tools/packages needs to be installed as a Pre-requisite to run the application
> java -version 
> 
> mvn -v
> 
> docker -v
> 
> docker-compose -v

3. All the necessary commands to run the application is available in startStack shell script.
   you can just run the shell script to start the application with Devops functionalities. 
   This script runs the mvn package and then uses docker compose to bring the new docker infrastructure.
   Your screen will look like this after 2 or 3 minutes , use refresh command until all the services shows as up and healthy

![serviceStatus.png](screenshots/serviceStatus.png)


> cd docker
> 
> sh startStack.sh


To Refresh the status of the docker container service use below command
```
docker-compose -f docker-compose.yml ps
```

4. Verify if the ELK and Application services are up and running,

> APM Server - http://localhost:8200/
>
> Elasticsearch - http://localhost:9200/
>
> Grafana - http://localhost:3000/
> 
> Kibana - http://localhost:5601/
>
> logstash - http://localhost:9600/
> 
> Prometheus - http://localhost:9090/
>
> roman-numeral-converter Application - http://localhost:8080/actuator/health
>
> Application swagger - http://localhost:8080/swagger-ui.html

## Kibana setup port 5601

### Kibana- View logs 

> http://localhost:5601/

1. Kibana home screen will look like below

   ![KibanaHome.png](screenshots/KibanaHome.png)

2. Create index pattern `logstash-*` to visualize logs as shown below

   ![KibanaIndex.png](screenshots/KibanaIndex.png)

3. After configuring the index-pattern, Click on 'Discover' tab, to check the application logs

   ![KibanaLogstash.png](screenshots/KibanaLogstash.png)

### Kibana - View Application Performance Metrics

Click on 'APM' tab, to check the application metrics

> http://localhost:5601/app/apm

Select application to view the Transaction, Errors and Metrics

![KibanaAPMTransaction.png](screenshots/KibanaAPMTransaction.png)

![KibanaAPMError.png](screenshots/KibanaAPMError.png)

![KibanaAPMMetrics.png](screenshots/KibanaAPMMetrics.png)

## Setup Grafana - To View the Graphical representation of Application metrics - port 3000

> http://localhost:3000/

1. Provide default username/password as admin/admin. After logging in, you should see a home screen like below, Select `Add your first datasource`,

   ![PrometheusDataSource.png](screenshots/PrometheusDataSource.png)


2. Configure Prometheus datasource as below

   ![ConfigurePrometheus.png](screenshots/ConfigurePrometheus.png)


3. Import any dashboard template from Grafana Dashboards. I picked template ID 4701(JVM Micrometer)

   ![ImportDashboard.png](screenshots/ImportDashboard.png)


4. Once it is loaded, Grafana dashboard will be up and running with JVM metrics as shown below,

   ![GrafanaChart.png](screenshots/GrafanaChart.png)



## Testing

Health Check

> http://localhost:8080/actuator/health

### Run Unit tests

From application root directory, run
> mvn clean install

```
Test Cases:

IntegerToRomanNumeralConverterControllerTest
- Happy Path case - testIntegerToRomanNumeralConverter_Success 
- Error case 1- input number less than min value < 1 - testIntegerToRomanNumeralConverter_InputOutOfRange_LessThanMinError
- Error case 2- input number greater than max value > 255 - testIntegerToRomanNumeralConverter_InputOutOfRange_GreaterThanMaxError
- Error case 3- input number data type is other than int - testIntegerToRomanNumeralConverter_InputInvalidType
- Error case 4- Mock RunTimeException - testIntegerToRomanNumeralConverter_internalServerError

IntegerToRomanNumeralConverterServiceImplTest
- Different valid input value assertions - testIntegerToRomanNumeralConversion_Success

```

### Run Acceptance tests

1. ATs can be integrated as one of the step into the CI/CD pipeline with test cases, which confirms application starts
   and run without any error and can be deployed to next stage
2. Use the below command, to run the acceptance tests,

> mvn test -Dtest=IntegerToRomanNumeralConverterAT

```
Acceptance Test Cases

IntegerToRomanNumeralConverterAT
- Validate content type is application/json - testContentTypeIsApplicationJson
- Happy Path case to convert valid int to its corresponding roman numeral- testRomanNumeralConversion_Success
- Error case 1, to validate out of range conversions. Valid range 1 to 255 - testRomanNumeralConversion_OutOfRange_Error
- Error case 2, to validate invalid data type, valid data type is int - testRomanNumeralConversion_InvalidDataType_Error
```
### Load testing report
Simple Load testing results using Postman. Attached the load test results which I ran with a 50% load of Happy Path and 50% load of Error case
[View the PDF](load-testing-report/roman-numeral-converter-performance-report-1.pdf)

### Manual test results

#### Valid case
`
curl -X GET "http://localhost:8080/romannumeral?query=50" -H "accept: application/json"
`

Success Response
```
Http Status Code: 200
{
  "input": "50",
  "output": "L"
}
```

#### Invalid Testcase 1
`
curl -X GET "http://localhost:8080/romannumeral?query=256" -H "accept: application/json"
`

Error Response
```
Http Status Code: 400
{
  "statusCode": 400,
  "errorMessage": "Invalid input, out of range. Enter an integer value in the range from 1 to 255"
}
```
#### Invalid Testcase 2
`
curl -X GET "http://localhost:8080/romannumeral?query=xx" -H "accept: application/json"
`

Error Response
```
Http Status Code: 400
{
  "statusCode": 400,
  "errorMessage": "Invalid input type, enter an integer value in the range from 1 to 255"
}
```

## How to un-install Stack?

To un-install the application, all the required commands are available in stopStack shell script, to stop the whole application stack
along with the devops capabilities, your screen will look like this once the stack is stopped all the devops functionalities are removed
![stopStatus.png](screenshots/stopStatus.png)

```
cd docker
sh stopStack.sh
```
