# Introduction

Simulates linkedin's "Degree" of connections using different types of graphs, using this you can check what is the shortest connection between two users, longest path between two users by following their chain of connections.

All the implemented Data-Structures are in the Graph.java file, EnhancedSocialConnections and SocialConnections are abstract classes and interfaces respectively to be used with Graph.java.

# How to use
Download the src code and run with an IDE, I will work on an interface when I get more free time off school

Implement by calling
EnhancedSocialConnections _name_ = new Graph()

Add nodes or "users" using
addPerson("String")

Connect nodes using
connectPeople("String1", "String2", level_of_connection)

There are more methods to be used which have proper documentation in Graph.java
