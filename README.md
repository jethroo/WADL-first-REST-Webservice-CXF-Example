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
| Create | POST      | /thingies      |
| Read   | GET       | /thingies/{id} |
| Update | PUT       | /thingies/{id} |
| Delete | DELETE    | /thingies/{id} |
| Index  | GET       | /thingies      |


## License
Hereby released under MIT license.

## Authors/Contributors

- [BlackLane GmbH](http://www.blacklane.com "Blacklane")
- [Carsten Wirth](http://github.com/jethroo)
