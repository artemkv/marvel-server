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

![](design.png)

## Configuration properties

## Environemt variables:

```
SERVER_ADDRESS
SERVER_PORT
```
