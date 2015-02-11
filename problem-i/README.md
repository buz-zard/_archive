----

## Task

**Subject area:**

* People and their relatives.

**Webpage interface with 2 forms:**

* people list - table with 3 columns: name, surname & date of birth.
* edit form of selected person, where one can also see it's relatives.

**Required technologies:**

* Java 1.6+
* Project building/packaging using Maven 3
* 3 level architecture (using Spring MVC framework): 
  * `jsp` in the view layer.
  * In the logic layer („Domain“, „Business logic“, „Service“) no frameworks are required.
  * Use Hibernate DAO entities in persistence layer with the help of annotations.
* Automatic JUnit tests.

Some simplification rules for input are given to determine person's gender and relationships easier using his/her surname.

----

## Implementation

<p align="center">
  <img src="/problem-i/static/spring.png" height="30%" width="30%"/>
  <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
  <img src="/problem-i/static/angularjs.png" height="30%" width="30%"/>
  <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
  <img src="/problem-i/static/bootstrap.png" height="15%" width="15%"/>
</p>

A Java [Spring-Boot][spring_url] REST web service application with [AngularJS][angular_url]+[Bootstrap][boostrap_url] as a front-end technology - all in one executable jar, built with [Maven][maven_url]. Spring application serves both REST api web service and Angular single-page html application.

* **Requirements**:
  + JDK 1.7
  + Maven 3

* **Compiling** the app:
  +  `mvn clean package spring-boot:repackage`

* **Running** the app (after the application has started, it can be accesed at [`http://localhost:8888/`][localhost]):
  + From source: `mvn spring-boot:run`
  + From source with _sample_ data: `mvn spring-boot:run -Drun.arguments="test"`
  + From compiled executable jar: `java -jar solutioni-1.0.jar` 
  + From compiled executable jar with _sample_ data: `java -jar solutioni-1.0.jar test` 

* **Testing** the app:
  + Unit tests (34): `mvn test`
  + Integration tests (2): `mvn failsafe:integration-test`

Unit test coverage (in general test coverage percentage is higher, but Cobertura also checks Lombok's generated code):

<img src="/problem-i/static/coverage.png"/>

Maven generated [info site][mvn_site], [javadocs][mvn_javadocs] and Cobertura test coverage [report][mvn_testcoverage] can be creatd with: `mvn site`

* **RESTful** Web Service:

HTTP method|URI|Request Body|Purpose
:---:|---|---|---
GET|/person/all.json||Retrieve a list all people records from the database.
GET|/person/get/**{id}**.json||Retrieve a person record from the database by id.
POST|/person/save.json|Person object as JSON|Save a new person record to the databse.
POST|/person/update.json|Person object as JSON|Update an existing person record in the databse.
DELETE|/person/delete/**{id}**.json||Delete a person record from the database by id.
GET|/person/relationships/**{id}**.json||Get all possible relatives records from the databse for a specific person.

* **Sample** application screen:

<p align="center">
  <img src="/problem-i/static/sample.png"/>
</p>

----
[localhost]: http://localhost:8888/
[spring_url]: http://projects.spring.io/spring-boot/
[angular_url]: https://angularjs.org/
[boostrap_url]: http://getbootstrap.com/
[maven_url]: http://maven.apache.org/
[mvn_site]: http://buz-zard.github.io/uni/projects/problem-i/site/
[mvn_javadocs]: http://buz-zard.github.io/uni/projects/problem-i/site/apidocs/index.html
[mvn_testcoverage]: http://buz-zard.github.io/uni/projects/problem-i/site/cobertura/index.html

