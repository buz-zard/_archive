----

`mvn spring-boot:run`

`mvn clean install spring-boot:repackage`

----

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

----
