# PERE company example

### ReadMe file
Hi every one this a sample example requested by [Steffen]() as a part of PERE 
interview process ,

this is a simple spring-boot application to expose CRUD Rest endpoints , 
some of technologies used in this project :
* [MVN](https://maven.apache.org/guides/index.html)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring fox](http://springfox.github.io/springfox/docs/current/#introduction)
* [mockito](https://site.mockito.org/)
* [Docker](https://docs.docker.com/)

### About Project
this project expose [list , create, update, delete] end-points to a simple 
Company entity , beside some other features like search and sort by { name , email}

### Get & Run the Project :
   in an empty folder or directory , open terminal or CMD 
   and enter the following commands in sequance :

 1. git clone --progress -v ""

 2.  cd spring-boot-example/

 3. mvn clean install

 4. docker build -t pere/example-app .

 5. cd docker

 6. docker-compose up -d

  now 2 containers are up & runnig one for postgres DataBase and the other for Application , 
  then you can find end-points documentation at : 
  [company-controller](http://localhost:8080/swagger-ui.html#/company-controller)


