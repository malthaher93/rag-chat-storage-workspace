# RAG Chat 

## Overview

This repository contains a microservices-based architecture designed to be scalable, maintainable, and fault-tolerant. The system is composed of multiple independently deployable services that communicate with each other through well-defined APIs.

a microservices architecture based, build in java language [JDK11], using a various framworks such like spring boot, spring cloud, spring data, spring security.


## Tech Stack
This microservices project uses the following technologies:
```
Languages: [Java JDk11]
Frameworks: [Spring Boot, Spring Cloud, Spring Data, Spring Security, etc.]
Databases: [PostgreSQL]
Application server: [Embedded tomcat]
```
## Components
---
### Service 1: [rag-chat-storage-service]
```
a service used to manage sessions and messages
```
#### Context
``` 
	/
```
#### Endpoints
```
	#log 			     || /actuator/logfile
	#health checkup	     || /actuator/health
    #application version || /application/version
    #clear cache         || /cache/clear
	#swagger		     || /swagger-ui/index.html/
```
#### Dependencies
```
	database-server
    rag-chat-security-core-lib
```
#### Environments
```
	#LOCAL	|| http://localhost:8081
```

---
### Library 1: [rag-chat-common-lib]
```
a library for core shared functionality and utilities across service
```
#### Dependencies
```
	N/A
```
---
### Library 2: [rag-chat-security-core-lib]
```
a library for core security functionality and utilities
```
#### Dependencies
```
	rag-chat-common-lib
```
---
## Setup

### Project structure

```
rag-chat-parent
|
|- - - rag-chat-common-lib
|      |
|      |- - - src/main/java
|
|- - - rag-chat-security-core-lib
|      |
|      |- - - src/main/java
|
|- - - rag-chat-storage-service
|      |
|      |- - - src/main/java
|      |- - - src/main/test
|      |- - - src/main/resource
|             |
|             |- - - application.yml
|             |- - - application-local.yml
|             |- - - application-release.yml
```
### API Endpoints
Please check swagger 

### Testing
unit test case for business logic service only defined in MessageServiceTest and SessionServiceTest

### Service Profile
```
local  		-- used on local developer environment
release     -- used on development,staging and production environment
```

### Service Configuration

#### API key authentication application user
in memeory users used for api authentication for business logic endpoints, defined in the APP_API_KEYS property in env file
```
    APP_API_KEYS = fb939c58-6721-43d4-a521-7079d5ba6a7a, 576c5d84-fcab-44d4-858a-cd17f64aa085

    that means will create 2 in memory user 
    1. api key : fb939c58-6721-43d4-a521-7079d5ba6a7a
    2. api key : 576c5d84-fcab-44d4-858a-cd17f64aa085
```
#### Database user
database user for database access, defined in the docker-compose yml file
```
    user : test
    Password : test
```
#### PGAdmin user
client user for to access PGAdmin application, defined in the docker-compose yml file
```
    email : test@test.com
    Password : test
```

#### Properties ::
```
	*SERVICE_PORT                -- determine port of the application service | DEFAULT : 8081
    *DATASOUCE_URL               -- determine JDBC url of PostgreSQL datasorce | DEFAULT : jdbc:postgresql://localhost:5432/ragchat
    *DATASOUCE_USER_NAME         -- determine username of PostgreSQL datasorce | DEFAULT : test
    *DATASOUCE_PASSWORD          -- determine password of PostgreSQL datasorce | DEFAULT : test
    *RATE_LIMIT_COUNT            -- determine limit number of requestes invoke into application endpoints | DEFAULT : 5
    *RATE_LIMIT_REFRESH_PERIOD   -- determine refresh period of rate limit in second | DEFAULT : 1
    *RATE_LIMIT_TIMEOUT          -- determine timeout period of rate limit in milli second | DEFAULT : 0
    *CORS_ALLOWED_ORIGIN         -- determine allowed origin/hostname to invoke the application endpoints | DEFAULT : *
    *APP_API_KEYS                -- determine in memory user by define api key and user id in the following syntax api_keys with comma seperator | DEFAULT : 2 users with example fb939c58-6721-43d4-a521-7079d5ba6a7a,576c5d84-fcab-44d4-858a-cd17f64aa085
    *POSTGRES_USER               -- determine username of PostgreSQL database | DEFAULT : test
    *POSTGRES_PASSWORD           -- determine password of PostgreSQL database | DEFAULT : test
    *POSTGRES_DB                 -- determine name of PostgreSQL database | DEFAULT : ragchat
    *PGADMIN_DEFAULT_EMAIL       -- determine email of PGADMIN user | DEFAULT : test@test.com
    *PGADMIN_DEFAULT_PASSWORD    -- determine password of PGADMIN user | DEFAULT : test
    *ELASTICSEARCH_HOSTS         -- determine ip and port of elasticsearch | DEFAULT : http://elasticsearch:9200
    *LOGSTASH_SERVER=            -- determine ip and port of logstash | DEFAULT : logstash:5000

    
```
#### Example  ::
```
    *SERVICE_PORT=8081
    *DATASOUCE_URL=jdbc:postgresql://database:5432/ragchat
    *DATASOUCE_USER_NAME=test
    *DATASOUCE_PASSWORD=test
    *RATE_LIMIT_COUNT=5
    *RATE_LIMIT_REFRESH_PERIOD=1
    *RATE_LIMIT_TIMEOUT=0
    *CORS_ALLOWED_ORIGIN='*'
    *APP_API_KEYS=fb939c58-6721-43d4-a521-7079d5ba6a7a,    576c5d84-fcab-44d4-858a-cd17f64aa085
    *POSTGRES_USER=test
    *POSTGRES_PASSWORD=test
    *POSTGRES_DB=ragchat
    *PGADMIN_DEFAULT_EMAIL=test@test.com
    *PGADMIN_DEFAULT_PASSWORD=test
    *ELASTICSEARCH_HOSTS=http://elasticsearch:9200
    *LOGSTASH_SERVER=logstash:5000
```

## Deployment

### Build
Build service
```
mvn clean install -P <profile>

example :
#to build and deploy on local environment 
mvn clean install -P 'local'

#to build and deploy on release environment 
mvn clean install -P 'release'
```

Build docker image
```
docker-compose up --build
```

### Deploy

```
 1. build jar with release profile as mentioned upove
 2. build and run docker image

```

##### Services Log File directory path
```
<jar file directory>/logs
```
