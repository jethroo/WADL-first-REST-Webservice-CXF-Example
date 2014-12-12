# WADL first REST Webservice with CXF Example

## Preface

If your are looking for a path to choose for your API i would like to recommend 
[API Providers Guide - API Management] (https://s3.amazonaws.com/kinlane-productions/whitepapers/API+Evangelist+-+API+Providers+Guide+-+API+Management.pdf)
by Kin Lane which will introduce tools and ways of thinking about restfull APIs in the last years.

## Introduction

A simple example how to implement a REST web service with Java based CXF and JAX-RS following the contract
first approach. For code first approaches [Jersey](https://jersey.java.net/) might be a better choice for
kick starting due to handy Maven Archetypes.

Contact first means that the implementation is started after a full specification of the REST Webservice has
been elaborated and documented in a well defined format. In this example Web Application Description Language (WADL)
a machine-readable XML description of REST web services is used. A benefit of this approach, besides having a contract document, which can serve as basis for communication between the implementor and the consumer side or to handle change requests, is that one can use tools to generate a first code basis for the implementing the service itself.

This comes in handy because it is less error-prone and it saves a lot of boilerplate code implementation time.

## Prerequisites

In order to beeing able to run this example you need to have installed on your machine:

 * [Maven](http://maven.apache.org/) (2.x or higher)
 * [Java Development Kit](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (1.7 or higher)

optional:

 * [Eclipse](http://www.eclipse.org/)

## Example REST web service

In the rather simple example we want to offer CRUD methods for one resource and an index method to show all existing instances
of this resource. The table below shows the methods and paths for the example resource `Thingy`:

| Method | HTTP Verb | Path           |
| ------ | --------- | -------------- |
| create | POST      | /thingies      |
| read   | GET       | /thingies/{id} |
| update | PUT       | /thingies/{id} |
| delete | DELETE    | /thingies/{id} |
| index  | GET       | /thingies      |

The excerpt of the [WADL](/src/main/resources/example.xml) below shows the specification of the methods `create` and `get`. Last mentioned method specifies which
instance of resource to be retrieved within the request URL such as `GET /things/1` will return a `Thingy` with `id=1` (if existent).

```xml
<resource path="/thingies" id="thingies">
  <doc xml:lang="en" title="thingies" />
  <resource path="{id}">
    <method name="GET" id="onRead">
      <doc xml:lang="en" title="onRead" />
      <request>
        <param name="id" type="xs:int" required="true" default=""
          style="template" xmlns:xs="http://www.w3.org/2001/XMLSchema" />
      </request>
      <response status="200">
        <representation mediaType="application/json" />
      </response>
      <response status="500">
        <representation mediaType="application/json" />
      </response>
      <response status="404">
        <representation mediaType="application/json" />
      </response>
    </method>
    ...
  </resource>
  <method name="POST" id="onCreate">
    <doc xml:lang="en" title="onCreate" />
    <request>
      <param name="attribute_name" type="xs:string" required="true"
        default="" style="query" xmlns:xs="http://www.w3.org/2001/XMLSchema" />
      <representation mediaType="application/json" />
    </request>
    <response status="200">
      <representation mediaType="application/json" />
    </response>
    <response status="400">
      <representation mediaType="application/json" />
    </response>
    <response status="500">
      <representation mediaType="application/json" />
    </response>
  </method>
</resource>
```

## WADLtoJava

`wadl2java` can be used to generate service interfaces, model classes an method stubs out of the service description (WADL). It comes as maven plugin too and is used as such in this project. In this example solely the service interface `Thingies` is generated (not included in this codebase since it is recreated on every clean build and thus git ignored). It defines all methods priviously discussed.

```java
/**
 * Created by Apache CXF WadlToJava code generator
**/
package de.jethroo.rest.example;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/thingies")
public interface Thingies {

    @GET
    @Produces("application/json")
    Response onIndex();

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    Response onCreate(@QueryParam("attribute_name") String attribute_name);

    @GET
    @Produces("application/json")
    @Path("/{id}")
    Response onRead(@PathParam("id") int id);

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{id}")
    Response onUpdate(@PathParam("id") int id, @QueryParam("attribute_name") String attribute_name);

    @DELETE
    @Produces("application/json")
    @Path("/{id}")
    Response onDelete(@PathParam("id") int id);

}
```

This interface can now be implemented along with a model representing a Thingy. (See `/src/main/java/* for details).

## JAX-RS Server

In the [bean.xml](/src/main/webapp/WEB-INF/bean.xml) the service endpoint is configured along with other services and
beans needed for the expample (e.g. a in memory HSQLDB or a Data Access Object for Thingy).

```xml
  <jaxrs:server address="http://localhost:8090/api/v1" docLocation="src/main/resources/example.xml">
    <jaxrs:serviceBeans>
      <bean class="de.jethroo.rest.example.ExampleServiceImpl">
        <property name="dao" ref="thingyDao" />
      </bean>
    </jaxrs:serviceBeans>
  </jaxrs:server>
```
Ţhe jetty maven plugin will look for the settings in `/webapp/WEB-INF` (in the projects target folder) to setup the cxf servlet and the example service.

## Running the example

```
mvn clean install
mvn jetty:run
```

A list of known contexts should be visible at `http://localhost:8080` including this example service (the WADL can be
accessed at [http://localhost:8090/api/v1?_wadl](http://localhost:8090/api/v1?_wadl)). The service can now be tested with SOAP-UI or curl. Since for demonstrating purpose an in memory DB is used it will be dropped at shutdown of jetty.

## License
Hereby released under MIT license.

## Authors/Contributors

- [BlackLane GmbH](http://www.blacklane.com "Blacklane")
- [Carsten Wirth](http://github.com/jethroo)
