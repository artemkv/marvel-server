# Marvel Server - REST API Service

Wraps the Marvel API to provide custom filtering, sorting, note management and simple analysis.

## Design

Marvel server uses the Marvel Comics API to retrieve the data.

My initial idea was to connect to Marvel Comics API ad-hoc to retrieve the necessary data.
This approach did not work due to several limitations of Marvel Comics API, notably:
* There is no way to order results by number of comics/series (asked Marvel support for a help, no reply)
* Terrible response times: the response time for many calls was way over a minute

So I decided to use the Marvel Comics API to retrieve the original data, build my own projection using only properties I needed and store it locally for quierying.

So in essence, my approach is:
* Request creators from Marvel Comics API, ordered my modified
* Go through every page of the result set and store every creator data in the local db
* Keep track of creator modified date, once we done with a certain date, store this value in the local db to avoid retrieving the same data again
* Schedule db re-sync on regular intervals (every 24 hours) to pick up any changes in Marvel Comics API

Considering the quite small amount of data retrieved (slightly over 6000 creators), this approach have shown to be quite effective. There were 2 extra hacks required:
* The earliest record I could retrieve when providing modifiedSince filter was from 2007-01-01. All records before that were excluded from the results. So I had to do my initial request without modifiedSince filter
* When no modifiedSince filter is specified, Marvel Comics API retrieve first creators with modified from 1969-12-31, then with -0001-11-30. This dates and order does not make much sense, so I normalized all the dates before 2007-01-01 as being of 1970-01-01

The marvel-server project is built out of 3 main parts:
* __Marvel Connector (package net.artemkv.marvelconnector):__ Knows how to speak to Marvel Comics API. Exposes all the data through the MarvelApiRepository, which serves as a facade for the connector, hiding all the implementation details. It returns interfaces to avoid any dependency on JSON objects and converts low-level exceptions into exceptions that match the absraction level of the connector.
* __Marvel Server - REST Api (package net.artemkv.marvelserver.rest):__ Contains controllers, JSON models and business logic for request handling.
* __Marvel Server (package net.artemkv.marvelserver):__ Provides run-time for the service. This is a standard Spring Boot application. Contains logic to update the local db from Marvel Comics API on scheduled intervals.

I used H2 in-memory database configured to persist data on disk to speed up the development. In real life this should be replaced by more robust solution.

![](design.png)

## Build and run the project

This is a standard Maven project, so you can build it using:

```
mvnw clean package
```
And to run it:

```
mvnw spring-boot:run
```

## Configuration properties

```
server.address - the address on which the service will be available (0.0.0.0)
server.port - the port on which the service will be available (8080)
spring.datasource.url - the connection string to connect to H2 database (jdbc:h2:file:~/marveldb;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE)
logging.path - the path to the log file (logs)

marvelapi.url - the base url to connect to Marvel Comics API (http://gateway.marvel.com/)
marvelapi.page_size - the page size when retrieving the data from Marvel Comics API (100)
marvelapi.public_key - the public key required to connect to Marvel Comics API
marvelapi.private_key - the private key required to connect to Marvel Comics API
marvelapi.connection_timeout - the connection timeout when connecting to Marvel Comics API (60)
marvelapi.read_timeout - the read timeout when connecting to Marvel Comics API (60)

marvelservice.retries - the number of retries for a single call when trying to retrieve data from Marvel Comics API (3)
```

## Environemt variables:

Note: Values of environment variables override the configuration parameters.

```
SERVER_ADDRESS - the address on which the service will be available (0.0.0.0)
SERVER_PORT - the port on which the service will be available (8080)
DB_CONNECTION_STRING - the connection string to connect to H2 database (jdbc:h2:file:~/marveldb;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE)
LOGGING_PATH - the path to the log file (logs)

MARVEL_API_URL - the base url to connect to Marvel Comics API (http://gateway.marvel.com/)
MARVEL_API_PAGE_SIZE - the page size when retrieving the data from Marvel Comics API (100)
MARVEL_API_PUBLIC_KEY - the public key required to connect to Marvel Comics API
MARVEL_API_PRIVATE_KEY - the private key required to connect to Marvel Comics API
MARVEL_API_CONNECTION_TIMEOUT - the connection timeout when connecting to Marvel Comics API (60)
MARVEL_API_READ_TIMEOUT - the read timeout when connecting to Marvel Comics API (60)
```
