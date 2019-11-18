# SIRIUS Chat Sample

This is an illustration project used to showcase how to setup and use the
[SIRIUS](https://github.com/scireum/sirius-kernel) libraries.

The task at hands is creating a small web server which provides a chat room
for clients which are connected via web sockets. The task itself is split up
into challenges - some build up onto each other, others are more like side
quests which provide some functionality but can also be skipped.

Also feel free to have a look around, place breakpoints, figure out how
the surrounding framework works and most importantly **HAVE FUN** :)

## SIRIUS

SIRIUS is split up into distinct modules to provide a broad range of use-cases.
The modules are:
* https://github.com/scireum/sirius-kernel - Contains common code and especially the [dependency injector](https://github.com/scireum/sirius-kernel/tree/master/src/main/java/sirius/kernel/di)
* https://github.com/scireum/sirius-web - Contains the the web server along with [**Tagliatelle**](https://github.com/scireum/sirius-web/tree/master/src/main/java/sirius/tagliatelle) which is our template engine
* https://github.com/scireum/sirius-db - Contains a persistence framework which helps to manage [JDBC](https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/) datasources, [MongoDB](https://docs.mongodb.com/), [Elasticsearch](https://www.elastic.co/guide/index.html) or [Redis](https://redis.io/documentation)
* https://github.com/scireum/sirius-biz - Contains a lot of high level frameworks which help to built cloud native business applications

## Setup

To save some time, the project is already setup and also contains some scaffolding for the
challenges. This project can be built using [**Apache Maven**](https://maven.apache.org/) and should be developed using
[**Jetbrains IntelliJ IDEA**](https://www.jetbrains.com/idea/download/).

To understand the inner workings first have a look at the [pom.xml](pom.xml) which tells
maven how to build this project. Then consult the [src/resources/application.conf](src/resources/application.conf)
and [develop.conf](develop.conf) to understand the configuration of the application itself
as well as the developer config which is used when starting the application locally.

(The system configuration is accessible via either [Sirius.getSettings()](https://github.com/scireum/sirius-kernel/blob/master/src/main/java/sirius/kernel/Sirius.java#L607)
or by using the [ConfigValue](https://github.com/scireum/sirius-kernel/blob/master/src/main/java/sirius/kernel/di/std/ConfigValue.java) annotation.
An example is the [WebServer](https://github.com/scireum/sirius-web/blob/master/src/main/java/sirius/web/http/WebServer.java#L82)
provided by **sirius-web**, which uses the configuration to determine which port to 
open etc.)

Speaking of which, SIRIUS applications can be started using a Java Application Run configuration as follows:
- Main Class: `sirius.kernel.Setup`
- VM Options:`-Ddebug=true -Dide=true`

Note one more very important file (**src/resources/component.marker**). Although this file
is empty, is it essential, as it is used by the [Classpath](https://github.com/scireum/sirius-kernel/blob/master/src/main/java/sirius/kernel/Classpath.java)
scanner of the [Dependency Injector](https://github.com/scireum/sirius-kernel/blob/master/src/main/java/sirius/kernel/di/Injector.java)
to discover all classpath roots which participate in the SIRIUS system.

## Challenges

Ok, we're almost good to go. Before diving into the first challenge, start a Debugger using the run configuration created above to make sure that the base system is operational. This will start
a [**Docker**](https://docs.docker.com/) container for [**Redis**](https://redis.io/documentation) and one for [**Elasticsearch**](https://www.elastic.co/guide/index.html) which are required for later
challenges (the stack is configured in [docker-compose.yml](docker-compose.yml)). Once a message
like `System is UP and RUNNING` appears in the console, you should be able to view the chat UI: http://localhost:9000
Note that you can also have a look at the system state via http://localhost:9000/system/state and
even monitor some details via http://localhost:9000/system/console (e.g. by executing the `http` command). 

Note that each challenge has a unique ID (like CHALLENGE-0). You can search in all files ("Find in Path")
using this ID to spot all relevant code positions. You will also find the appropriate .solutions file
which will assist in case you're in trouble :-P

### Hello World (CHALLENGE-0)

The first step is to make the server respond to incoming chat messages. These messages will
simply be sent back to the client. But first, let's find out where the client comes from. Head
over to the [ChatClientController](src/main/java/client/ChatClientController.java) and read the
JavaDoc - then come back here. 

Now that we know how the client is delivered, we can start the challenge. The client connects via
a "web socket" which is more or less a persistent HTTP connection which exchanges messages (called)
frames. (You can review the client code required to open a web socket in JavaScript - but that's
not required for our challenge - [client.js](src/main/resources/assets/client/client.js)). For each
web socket being connected, the [WebSocketDispatcher](src/main/java/server/WebSocketDispatcher.java)
will detect this and initialize a [ChatSession](src/main/java/server/ChatSession.java). Head over
to the session class to read the provided docs and finally to start some coding :)

Once the challenge is completed, you should be able to chat with yourself. :)

### Hello Everyone (CHALLENGE-1)

For the second challenge, head to [ChatSessionRegistry](src/main/java/server/ChatSessionRegistry.java) and
implement the missing parts. Then switch to [ChatSession](src/main/java/server/ChatSession.java) to call
the appropriate methods.

Once this is all done, restart the Debugger and open two or more tabs. You should now be able to chat
with each other.

### Hello HA (CHALLENGE-2)

The third challenge starts by completing the [ChatClusterUplink](src/main/java/server/ChatClusterUplink.java).
Once this is done, change the [ChatSessionRegistry](src/main/java/server/ChatSessionRegistry.java) as noted
in the JavaDoc.

After restarting the server, the chat should continue to work. Now if you specify a shared Redis server in
[develop.conf](develop.conf) you should be able to share your chat with other machines.

### Ahoi World (CHALLENGE-3)

Now is time to package and create a container for our application.
In real life, apps do not run under IDEs ;-)

Execute the Maven **package** Lifecycle, switch to the IntelliJ Terminal and execute the following command:
 `docker build -t sirius-chat .` using the provided [Dockerfile](Dockerfile)

Now you can launch the application container. You will find a [docker-compose.yml](ha/app/docker-compose.yml) file ready to rock & roll.
Just adjust the values for the desired Elasticsearch and Redis DBs, and from the directory hosting the file 
execute `docker-compose -d up`. After started it should be available at the http://localhost:80.

To stop it, run `docker-compose down`

### Side-Quest: HA-Setup (CHALLENGE-4)

This challenge gives a small glance at how a high availability system looks like. The goal is to start 2 or more instances
of our chat app under a docker container, with a `traefik` container performing the load-balancing between them.

Read the Quick Start Guide at [traefik.io](https://docs.traefik.io/getting-started/quick-start/) and fill up the 
[docker-compose.yml](ha/traefik/docker-compose.yml) file in in order to setup a load-balancer for our chat.
To start traefik: `docker-compose up -d traefik`
To start 2 instances of the sirius-chat: `docker-compose up -d scale --sirius-chat-sample=2`

### Side-Quest: Chat-Bots (CHALLENGE-5)

To add chat bots, have a look at the [ChatBot](src/main/java/bots/ChatBot.java) interface and the two
examples ([HelpBot](src/main/java/bots/HelpBot.java), [CalcBot](src/main/java/bots/CalcBot.java)).

To invoke the chat bots, jump back to the [ChatSession](src/main/java/server/ChatSession.java) and
invoke the bots in "handleChatMessage" instead of directly forwarding the message (there is a descriptive comment
there already).

Once that works, make sure to have some fun by either modifying the existing bots or by providing your own.
Maybe add a command to report how many people are in the chat, or when user X wrote the last message 
(both would require to extend ChatSessionRegistry a bit).

### Side-Quest: Rate-Limiting (CHALLENGE-6)

Now that our precious chat server is running, we need to protect it from the cruel outside world.
sirius-biz provides a simple but effective firewall which relies on redis to ensure some rate limiting
(e.g. user X doesn't perform action Y more often than 1000 times per 1 minute). The class is named
**Isenguard** and an example of using it can be found in the [ChatClientController](src/main/java/client/ChatClientController.java).

Two places are naturally interesting to rate limiting **ChatSession.handleChatMessage** to handle
the number of incoming chat message and **ChatSession.onWebsocketOpened** to handle the number of
connection attempts. Obtain a reference to Isenguard using a static field + @Part and enforce
a proper rate limit. Play around with the settings in application.conf (requires a restart) to
ensure that they work.

Note that [Isenguard](https://github.com/scireum/sirius-biz/blob/master/src/main/java/sirius/biz/isenguard/Isenguard.java)
provides a bunch of additional methods. You can use **Isenguard.isRateLimitReached** and its callbacks and notify everyone
that someone reached its limit. Or you can provide a custom ChatBot which reveals the current limit status
for the caller by using **Isenguard.getRateLimitInfo**.

### Side-Quest: Search (CHALLENGE-7) 

Searching and sorting is all that computers do :) Therefore we also want to provide a way to search
in the history of our chat room. Luckily, sirius-biz and sirius-db provide connectivity to all sorts
of databases (MariaDB, MongoDB, Elasticsearch). The latter is interesting for us, as it provides a
complete fulltext search engine out of the box. Have a look at [SearchableChatMessage](src/main/java/search/SearchableChatMessage.java)
which is the entity class (a Java class which defines the fields to be stored in Elastic). The
[SearchMessagesController](src/main/java/search/SearchMessagesController.java) is also readily available
and provides a tiny UI to search in elastic. Also have a look (and later play around) with the template
which is used to actually render the search output [search.html.pasta](src/main/resources/templates/search.html.pasta).

To ingest data into Elastic, jump to the [ChatSessionRegistry](src/main/java/server/ChatSessionRegistry.java) and
implement the TODOs in **distributeMessage**. After restarting the debugger, new chat message should be
visible in the search UI. 


### Docker Toolbox

https://github.com/docker/toolbox/releases


https://docs.docker.com/toolbox/toolbox_install_windows/
