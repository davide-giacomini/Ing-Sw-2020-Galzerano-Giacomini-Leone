# Final Project of Software Engineering - a.a. 2019-2020
![alt text](https://github.com/davide-giacomini/Ing-Sw-2020-Galzerano-Giacomini-Leone/tree/master/src/main/Resources/Images/MainPicture.png)

## List of participants:

 - *Galzerano Arianna - Matricola: 886979*
 - *Giacomini Davide - Matricola: 889237*
 - *Leone Monica - Matricola 889321*
 
## Getting Started
This game was developed as a computer game for the final examination of the Software engineering course at Politecnico di Milano. 
The purpose of the project is the implementation of the board game Santorini with the use of the design Pattern Model View Controller for the realization of the model following the paradigm of object-oriented programming. The final result covers entirely the rules of the game and the interaction with the users is permitted through both a Graphical and a Command Line Interface; For the Network, a traditional approach with the use of the Sockets was used.

More information about the game can be found [here](http://www.craniocreations.it/prodotto/santorini/)
## Prerequisites
The game requires [Java 8] to run.

## Docs
The following documentation includes all the significant documents created to show the work behind our project and an insight of our choices. Among this docs there are the UMLs, the javadoc that describes the code and other useful information.

### UML
The following class diagrams are a representation of the design chosen by our group to implement the game, the initial one shows the ideas on which we based the project that shape up in the final UMLs, divided into a general one and some more detailed ones for the most critical parts.

- [UML Initials](https://github.com/davide-giacomini/Ing-Sw-2020-Galzerano-Giacomini-Leone/tree/master/src/main/deliveries/UML/UMLInitialModel.png)
- [UML Finals](https://github.com/davide-giacomini/Ing-Sw-2020-Galzerano-Giacomini-Leone/tree/master/src/main/deliveries/UML/Model_FinalUML.png)

### JavaDoc
The following documentation includes a description of all of the classes and methods implemented, based on the documentation generator for the Java language, which can be read at the following address: [Javadoc](https://github.com/davide-giacomini/Ing-Sw-2020-Galzerano-Giacomini-Leone/tree/master/src/main/deliveries)

### Libraries e Plugins
|Libraries/Plugin|Description|
|---------------|-----------|
|__maven__|project management tool that is based on POM (project object model)|
|__junit__|unit testing framework for the Java programming language|
|__mockito__|framework for the creation of test double objects (mock objects) in automated unit tests for the purpose of test-driven development|
|__JavaFx__|Java set of graphics and media packages|

<a name="built"></a>
## Built with
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)
* [Java 8]
* [JavaFx](https://openjfx.io)
* [Maven](https://maven.apache.org)
* [JUnit](https://junit.org/junit5/)
* [Mockito](https://site.mockito.org)

### Jars
The following jars have been used for the delivery of the project, they can be used to run the game in accordance with the descriptions given in the introduction. The features realized following the Project-specific are listed in the following section while the detalis on how to run the sistem will be defined in the section __Execution of the jar__. The folder which contains Client and Server is at the following address : [Jars](https://github.com/davide-giacomini/Ing-Sw-2020-Galzerano-Giacomini-Leone/tree/master/src/main/deliveries)

## Features
### Features developed
- Complete rules
- CLI
- GUI
- Socket

### Advanced features developed
- Multiple Games
- Advanced Gods: ``` Chronus, Hera, Hestia, Triton, Zeus  ```

## Execution of the JAR
### Client
In order to correctly run the client jar it is necessary to choose the jar for the Operative System in which it will be ran:
The client is executed with the command: 

For Macos:
```
java -jar client.jar
```
For Windows:
```
java -jar client.jar
```
After this line, on the Terminal it is asked to the user to choose between the Command Line Interface and the Graphical User Interface(all the dependencies for JavaFx are included); The user will have to type a word:
>GUI 
>
or 
>CLI
>

### Server
The execution of the server jar is possible through the following command:
```
java -jar server.jar 
```
