WADL first REST Webservice with CXF Example
===========================================

A simple example how to implement a REST web service with Java based CXF and JAX-RS following the contract
first approach. For code first approaches [Jersey](https://jersey.java.net/) might be a better choice for
kick starting due to handy Maven Archetypes.

Contact first means that the implementation is started after a full specification of the REST Webservice has
been elaborated and documented in a well defined format. In this example Web Application Description Language (WADL)
a machine-readable XML description of REST web services is used. A benefit of this approach, besides having a contract document, which can serve as basis for communication between the implementor and the consumer side or to handle change requests, is that one can use tools to generate a first code basis for the implementing the service itself.

This comes in handy because it is less error-prone and it saves a lot of boilerplate code implementation time.

# Prerequisites

In order to beeing able to run this example you need to have installed on your machine:

 * [Maven](http://maven.apache.org/) (2.x or higher)
 * [Java Runtime Environment](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (1.7 or higher)

optional:

 * [Eclipse](http://www.eclipse.org/)

# Example REST web service

In the rather simple example we want to offer CRUD methods for one resource and an index method to show all existing instances
of this resource. The table below shows the methods and paths for the example resource `Thingy`:

| Method | HTTP Verb | Path           |
| ------ | --------- | -------------- |
| create | POST      | /thingies      |
| read   | GET       | /thingies/{id} |
| update | PUT       | /thingies/{id} |
| delete | DELETE    | /thingies/{id} |
| index  | GET       | /thingies      |

The excerpt of the [WADL](WADL-first-REST-Webservice-CXF-Example/blob/master/src/main/resources/example.xml) below shows the specification of the methods `create` and `get`. Last mentioned method specifies which
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


## License
Hereby released under MIT license.

## Authors/Contributors

- [BlackLane GmbH](http://www.blacklane.com "Blacklane")
- [Carsten Wirth](http://github.com/jethroo)
