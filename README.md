# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## My Architecture Design

https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYRnElknUG4lTlNA+BAIHEiFRsyXM0kgSFyFD8uE3RkM7RS9Rs4ylBQcDh8jqM1VUPGnTUk1SlHUoPUKHxgVKw4C+1LGiWmrWs06W622n1+h1g9W5U6Ai5lCJQpFQSKqJVYFPAmWFI6XGDXDp3SblVZPQN++oQADW6ErU32jsohfgyHM5QATE4nN0y0MxWMYFXHlNa6l6020C3Vgd0BxTF5fP4AtB2OSYAAZCDRJIBNIZLLdvJF4ol6p1JqtAzqBJoIcDcuj3ZfF5vD6L9sgwr5iWw63O+nxPF+SwfgC5wFrKaooOUCAHjysL7oeqLorE2IJoYLphm6ZIUgatJvqMJpEuGFocjA3K8gagrCjAoriq60pJpe8HlPR2iOs6BL4Sy5Set6QYBkGIaseakbUdGMCxsGPE4Z2gFpmhPLZrmmAqZ2xZpsBFbjtWU5BrOzYTn0By6Z22Q9jA-aDr0r4jqMC6Tn006mfO46thZZicKu3h+IEXgoOge4Hr4zDHukmSYDZF5FNQ17SAAoruKX1ClzQtA+qhPt0HmNugf5sip5SFXOWkwR2cFOvKMDTqhEW+hhGLYXKuH8RRBEwOSYDyWJdZFWg5FMm6VHlLRMbidoQphBVxWSRGtXgnJM3yLxiZdWNglGCg3CZANC0jaG3VsYUloyPtFKGPJDHzSZw2bbhybVapzVgBpCB5m9OlXnpln-dZ55gH2A5Dku-meIFG6Qrau7QjAADio6stFp5xSDbK6eUFRIxl2X2KOBWPZVVmvUCJbHVVlP-oldUIQ1oko6MqgI7ErVYc9GoCaSvUUkdpPoKNZoRhd1FTWtcazYx1NLX9nFSwpG1KXhZ188gsQs2osIi5R0lcjytrAMqyOjg68vsfTq3a4y7NYKrFOpuU2ts9CX0-bTCslFcUxE6z4yVP0-soAAktIgdGX0J6ZAaFbmU8OgIKADZx6BPlPCHABy6eLjAjSA0lIKnPFoN2eDvR+6jgcVMHo7h5Hbkx-qpH3BnUxJynacuQnVejDnPc+Xs+eQyu0ProE2A+FA2DcPAuqZGbowpDFZ45MwtXXrUDSE8TwRC8+ld9NnoGrDAhfHE7wLlQfQ59ygA-3Gf0Feyt9WNXAC8oNrnNYtzas7T5n1QWQ05x63GgbGiRslb3SZqAxavNlocQZuUO6ikOo83Vu6GAwlMja1hCfMip1AFi3ZOUT+XpF621mu0HkjhCFiEdgBN65Cv4-zUJpbSVscalnvg3coABGXsABmAALOfEqVtS5gwcj0PhEdBEiPEaPAKE8AiWH2khZIMAABSEAeRL0MAETuIAGyY3Xtjf6uMqiUjvC0EOJN4GHx6LPYAGioBwAgEhKAswQ7hwvjVZhtMb5OKHK49xnjvG+PrtIF+qZLGK0anonk7C0RtX-ttUWhF+qiWOuA86ZCoG8jQfIOacCZxPUtm-RmJTgAZJkIg7J+C-HSHyVJcWk1oHUNKYxFpxDRbextubdB8FMEkPKjaKoyd3GwnCZQSJ0A2mkMupSbAWgqHDNKc3FoJjU6t26DAZIGRUhMVbjAAAZt4YYMCRkM2Uiw5JaB2E5m+jTeJ3CrGlgCcXQo0jy4OS+X5Mea4goBC8G4rsXpYDAGwLPQg8REgrwxqXBJPtKipXSplbKxhJFBOdoCrhyDVogG4HgRksJEBQt-u1UZACsk4NJXCJZE1pDXUXibBAdoBTaAAOQaEth01lB1DAcsMYyPl-T9aCrZSK028lPISqYWcYJlK8AezebBTeANJElxBjIiGQLMBAA

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
