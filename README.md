# Introduction 
Application uses java 17, spring boot 3, maven and h2 database.
Application uses business template pattern i.e every controller action that is exposed in API has to implement a business service template, which makes it very easy to maintain understand and debug, so that way we are forced to implement pattern of validate, execture, handle failure and post execution.
Please go through the AbstractBusinessService to undertand on this business template more.
Application used test containers rather than conventional unit test cases, test cases are written for every controller method that cuts across all services and tests each and every condition in the code anywhere in the application, which makes the test cases alot more logical as I can pass the exact params in my API call and expect what part of my which service to fail or to behave in a certain way, rather than mocking all classes one by one and unit testing them.

Its a conference room booking system for a bank, it has 4 rooms with different capacities, rooms are booked on first come first serve basis.

# Getting Started
1. Installation process
    must have java 17 and maven and docker.
    mvn clean install will install add all the libraries required

# Build and Test

build the application

# Contribute
TODO: Explain how other users and developers can contribute to make your code better. 

If you want to learn more about creating good readme files then refer the following [guidelines](https://docs.microsoft.com/en-us/azure/devops/repos/git/create-a-readme?view=azure-devops). You can also seek inspiration from the below readme files:
- [ASP.NET Core](https://github.com/aspnet/Home)
- [Visual Studio Code](https://github.com/Microsoft/vscode)
- [Chakra Core](https://github.com/Microsoft/ChakraCore)