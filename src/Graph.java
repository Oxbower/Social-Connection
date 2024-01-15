import java.util.*;
import java.util.stream.Collectors;

/**
 * Concrete class used by EnhancedSocialConnections and SocialConnections classes
 * @see EnhancedSocialConnections
 * @see SocialConnections
 * @author Mark Santiago
 * */
public class Graph extends EnhancedSocialConnections
{
    /**
     * Implements comparator class for use in priority queue
     * */
    public class CompareVertexStrong implements Comparator<Person>
    {
        @Override
        public int compare(Person o1, Person o2) {
            if (o1.distance > o2.distance) return 1;
            else if (o2.distance > o1.distance) return -1;
            return 0;
        }
    }

    /**
     * Edge class for adjacency list, was going to use arrays, but it got really messy,
     * so I created a class instead to act as my container
     * */
    public class Edges
    {
        Person owner;
        Person connectedTo;
        int level;
        boolean visited;
        /**
         * @param owner of the node
         * @param connectedTo who is the owner connected to
         * @param level set connection level (weights)
         * */
        public Edges (Person owner, Person connectedTo, int level)
        {
            this.owner = owner;
            this.connectedTo = connectedTo;
            this.level = level;
        }
    }

    /**
     * Person class for person object duhh
     * */
    public class Person
    {
        int distance;
        Person parent;
        String name;
        boolean visited;
        List<Edges> connections;
        public Person (String name)
        {
            this.name = name;
            this.connections = new LinkedList<>();
        }
    }

    //Holds allPerson and allEdges
    List<Person> allPerson;
    List<Edges> allEdges;

    public Graph ()
    {
        this.allPerson = new LinkedList<>();
        this.allEdges = new LinkedList<>();
    }

    /**
     * Checks if the user exists in the graph
     * @param name of the user we are looking for
     * @return checks if the user exists or not
     * */
    private boolean userExists (String name)
    {
        return allPerson.stream().anyMatch(Person -> Person.name.equals(name));
    }

    /**
     * grabs person node in the graph
     * @param name of the user we are looking for
     * @return the node of the user
     * */
    private Person findUser (String name)
    {
        try {return allPerson.stream().filter(Person -> Person.name.equals(name)).findFirst().get();}
        catch (NoSuchElementException e) {return null;}
    }

    /**
     * finds if this person is connected to another person of a given name
     * @param person check if this person node is connected to the given name
     * @param name of the person we are looking for in this persons connection
     * @return vertex of this user that matches name
     * */
    private Edges findConnectionVertex (Person person, String name)
    {
        try{return person.connections.stream().filter(Edges -> Edges.connectedTo.name.equals(name)).findFirst().get();}
        catch (NoSuchElementException e) {return null;}
    }

    /**
     * Resets all the nodes visited property to false and parent to null
     * */
    private void resetNodeProperties ()
    {
        allPerson.stream().forEach(Person -> Person.parent = null);
        allPerson.stream().forEach(Person -> Person.visited = false);
        allEdges.stream().forEach(Edges -> Edges.visited = false);
        allPerson.stream().forEach(Person -> Person.distance = 0);
    }

    /**
     * Connects two people together and gives their connection a weight, if both person are already connected
     * then update their level instead
     * @param firstPerson name of the person that we are connecting with the second person
     * @param secondPerson name of the person that we are connecting with the first person
     * @param level weight of each edge
     * */
    @Override
    public void connectPeople(String firstPerson, String secondPerson, int level) throws PersonNotFoundException
    {
        //Undirected connection
        try
        {
            Person per_1 = findUser(firstPerson);
            Person per_2 = findUser(secondPerson);
            if (per_1 == null)
                throw new PersonNotFoundException(firstPerson + " not found!");
            if (per_2 == null)
                throw new PersonNotFoundException(secondPerson + " not found!");

            //TODO check if the users are already connected
            //I only have to check for one of the users since its an undirected graph
            if (per_1.connections.stream().noneMatch(Edges -> Edges.connectedTo.name.equals(secondPerson)))
            {
                Edges personOneConnection = new Edges(per_1, per_2, level);
                Edges personTwoConnection = new Edges(per_2, per_1, level);
                allEdges.add(personOneConnection);
                allEdges.add(personTwoConnection);
                per_1.connections.add(personOneConnection);
                per_2.connections.add(personTwoConnection);
                return;
            }
            //Update level if connection already exists
            Edges personOneUpdateLevel = findConnectionVertex(per_1, secondPerson);
            Edges personTwoUpdateLevel = findConnectionVertex(per_2, firstPerson);
            if (personOneUpdateLevel == null || personTwoUpdateLevel == null) throw new PersonNotFoundException("Fatal error in connection :(");
            personOneUpdateLevel.level = level;
            personTwoUpdateLevel.level = level;
        }
        catch (PersonNotFoundException e)
        {
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * returns a list containing all of this persons strongest connections (lowest weight),
     * part of EnchancedSocialConnections
     * @see EnhancedSocialConnections
     * @param name of the person we want to get the strongest connections of
     * @return list containing the strongest connection of given person
     * */
    @Override
    public List<String> getStrongestConnection(String name) throws PersonNotFoundException
    {
        try
        {
            Person person = findUser(name);
            if (person == null) throw new PersonNotFoundException(name + " not found!");

            int level = Integer.MAX_VALUE;
            List<String> strongestConnections = new LinkedList<>();

            for (Edges Edges : person.connections)
            {
                level = Math.min(level, Edges.level);
            }
            for (Edges Edges : person.connections)
            {
                if (Edges.level == level)
                {
                    strongestConnections.add(Edges.connectedTo.name);
                }
            }
            Collections.sort(strongestConnections);
            resetNodeProperties();
            return strongestConnections;
        }
        catch(PersonNotFoundException e)
        {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * finds the strongest path(the lowest level possible) between these two nodes, part of EnhancedSocialConnections
     * @see EnhancedSocialConnections
     * @param firstPerson starting node
     * @param secondPerson end node
     * @return list containing the strongest path between the two nodes
     * */
    @Override
    public List<String> getStrongestPath(String firstPerson, String secondPerson) throws PersonNotFoundException
    {
        try
        {
            resetNodeProperties();
            Person start = findUser(firstPerson);
            Person end = findUser(secondPerson);
            if (start == null) throw new PersonNotFoundException(firstPerson + " not found!");
            if (end == null) throw new PersonNotFoundException(secondPerson + " not found!");
            LinkedList<String> strongestPath = new LinkedList<>();
            strongestPathBFS(start);
            Person path = end;

            while (path != null)
            {
                strongestPath.addFirst(path.name);
                path = path.parent;
            }
            resetNodeProperties();
            return strongestPath;
        }
        catch(PersonNotFoundException e)
        {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * Helper method of getStrongestPath only accessible in this class
     * @param start our starting node where we branch out from
     * */
    private void strongestPathBFS (Person start)
    {
        allPerson.stream().forEach(Person -> Person.distance = Integer.MAX_VALUE);
        //Pass in all the start nodes Edges
        Comparator<Person> comparison = new CompareVertexStrong();
        //Priority Queue all lowest distance put in front of the heap
        PriorityQueue<Person> bfsQueue = new PriorityQueue<>(comparison);
        bfsQueue.addAll(start.connections.stream().map(Edges -> Edges.connectedTo).collect(Collectors.toList()));
        //Set distance to infinity
        start.distance = 0;
        bfsQueue.add(start);

        //We have to visit all nodes to determine the shortest path
        while (bfsQueue.size() > 0)
        {
            Person node = bfsQueue.remove();
            if (node.visited) continue;
            node.visited = true;
            //visit all this Edges connections
            for (Edges connection : node.connections)
            {
                int newDistance = node.distance + connection.level;
                //if new distance is less than the next nodes weight
                if (newDistance < connection.connectedTo.distance)
                {
                    connection.connectedTo.parent = node;
                    connection.connectedTo.distance = newDistance;
                    bfsQueue.add(connection.connectedTo);
                }
            }
        }
    }

    /**
     * Finds the weakest path(the largest level possible) from start to end, part of EnhanceSocialConnections
     * @see EnhancedSocialConnections
     * @param firstPerson starting node
     * @param secondPerson node we are looking for
     * @return List containing the weakest path of the graph
     * */
    //global list to hold all the possible paths from start to end then compute the shortest path
    ArrayList<ArrayList> containerShortestPaths = new ArrayList<>();
    @Override
    public List<String> getWeakestPath(String firstPerson, String secondPerson) throws PersonNotFoundException
    {
        try
        {
            resetNodeProperties();
            Person start = findUser(firstPerson);
            Person end = findUser(secondPerson);
            if (start == null) throw new PersonNotFoundException(firstPerson + " not found!");
            if (end == null) throw new PersonNotFoundException(secondPerson + " not found!");
            ArrayList<String> strongestPath;
            ArrayList path = new ArrayList();
            weakestPathDFS(start, end, 0, path);
            int pathLength;
            int index = 0;

            try
            {
                pathLength = (int)containerShortestPaths.get(0).get(containerShortestPaths.get(0).size() - 1);
            }
            catch (IndexOutOfBoundsException e)
            {
                System.out.println("No connection!");
                return null;
            }

            for (int i = 0; i < containerShortestPaths.size(); i++)
            {
                int newLength = (int)containerShortestPaths.get(i).get(containerShortestPaths.get(i).size() - 1);
                if (pathLength < newLength)
                {
                    index = i;
                    pathLength = newLength;
                }
            }

            strongestPath = containerShortestPaths.get(index);
            containerShortestPaths.removeAll(containerShortestPaths);
            resetNodeProperties();
            return strongestPath;
        }
        catch(PersonNotFoundException e)
        {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * recursive helper method for getWeakestPath(), this is only accessible to this class
     * @param start our node to branch out from
     * @param end node we are looking for
     * @param length level of each connection after each recursion
     * @param path building the path between each node
     * */
    private void weakestPathDFS (Person start, Person end, int length, ArrayList path)
    {
        path.add(start.name);
        start.visited = true;
        if (start.name.equals(end.name))
        {
            end.visited = false;
            path.add(length);
            //System.out.println("current path: " + path);
            ArrayList deepCopy = new ArrayList();
            deepCopy.addAll(path);
            containerShortestPaths.add(deepCopy);
            path.remove(path.size() - 1);
            return;
        }
        for (Edges edge : start.connections)
        {
            if (!edge.connectedTo.visited) {
                weakestPathDFS(edge.connectedTo, end, length + edge.level, path);
                path.remove(path.size() - 1);
            }
        }
        start.visited = false;
    }

    /**
     * Adds a new person to the graph, part of SocialConnections class
     * @see SocialConnections
     * @param name of the person we want to add to the graph
     * @return true or false depending if the user was successfully added
     * */
    @Override
    public boolean addPerson(String name)
    {
        if (userExists(name))
        {
            return false;
        }
        Person newPerson = new Person(name);
        allPerson.add(newPerson);
        return true;
    }

    /**
     * removes the user from the graph and all its connections to and from, part of SocialConnections class
     * @see SocialConnections
     * @param name of the person we want to remove from the graph
     * @return true or false depending on if the user was successfully removed
     * */
    @Override
    public boolean removePerson(String name) throws PersonNotFoundException
    {
        try
        {
            Person person = findUser(name);
            if (person == null) throw new PersonNotFoundException(name + " not found!");

            //Remove all the connections to user
            for (Edges Edges : person.connections)
            {
                for (int i = 0; i < Edges.connectedTo.connections.size(); i++)
                {
                    if (Edges.connectedTo.connections.get(i).connectedTo.name.equals(name))
                    {
                        Edges.connectedTo.connections.remove(i);
                        break;
                    }
                }
            }
            //Remove person from the main user list
            for (int i = 0; i < allPerson.size(); i++)
            {
                if (allPerson.get(i).name.equals(name))
                {
                    allPerson.remove(i);
                    break;
                }
            }
            //Remove person and its connections from allVertex
            for (int i = 0; i < allEdges.size(); i++)
            {
                if (allEdges.get(i).connectedTo.name.equals(name) || allEdges.get(i).owner.name.equals(name))
                {
                    allEdges.remove(i);
                    i--;
                }
            }
            return true;
        }
        catch (PersonNotFoundException e)
        {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }

    /**
     * Get all of this users connections and return it as a list, part of SocialConnections class
     * @see SocialConnections
     * @param name of the person we want to get the connection of
     * @return list containing all of this.persons connections
     * */
    @Override
    public List<String> getConnections(String name) throws PersonNotFoundException
    {
        try
        {
            Person person = findUser(name);
            if (person == null) throw new PersonNotFoundException(name + " not found!");

            List<String> connections = new LinkedList<>();
            for (Edges Edges : person.connections)
            {
                connections.add(Edges.connectedTo.name);
            }
            Collections.sort(connections);
            return connections;
        }
        catch (PersonNotFoundException e)
        {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * Gives the shortest path from start to target, part of SocialConnections class
     * @see SocialConnections
     * @param firstPerson starting node
     * @param secondPerson node we are looking for
     * @return value giving the amount of edges we have to travel to get to destination via the shortest amount of edges
     * */
    @Override
    public int getMinimumDegreeOfSeparation(String firstPerson, String secondPerson) throws PersonNotFoundException
    {
        try
        {
            resetNodeProperties();
            Person start = findUser(firstPerson);
            Person end = findUser(secondPerson);
            if (start == null) throw new PersonNotFoundException(firstPerson + " not found!");
            if (end == null) throw new PersonNotFoundException(secondPerson + " not found!");

            shortestPathBFS(start, end);
            List<String> path = new LinkedList<>();
            Person followPath = start;
            while (followPath != null)
            {
                path.add(followPath.name);
                followPath = followPath.parent;
            }
            resetNodeProperties();
            System.out.println("Path minimum degree of separation: " + path);
            return path.size() - 1;
        }
        catch(PersonNotFoundException e)
        {
            //If person doesn't exist return -1
            System.out.println(e.getLocalizedMessage());
            return -1;
        }
    }
    /**
     * helper method for BFS shortest path, only usable inside this class
     * @param start node we are coming from
     * @param end node we are looking for
     * */
    private void shortestPathBFS(Person start, Person end)
    {
        Queue<Person> bfsQueue = new LinkedList<>();
        bfsQueue.addAll(end.connections.stream().map(Edges -> Edges.connectedTo).collect(Collectors.toList()));
        bfsQueue.stream().forEach(Person -> Person.visited = true);
        bfsQueue.stream().forEach(Person -> Person.parent = end);
        end.visited = true;
        while (bfsQueue.size() > 0)
        {
            Person current = bfsQueue.remove();
            List<Person> adjacentVertex = new LinkedList<>();
            adjacentVertex.addAll(current.connections.stream().map(Person -> Person.connectedTo).collect(Collectors.toList()));
            current.visited = true;
            for (Person foreachConnection : adjacentVertex)
            {
                if (!foreachConnection.visited)
                {
                    foreachConnection.parent = current;
                    if (foreachConnection.name.equals(start.name))
                    {
                        return;
                    }
                    foreachConnection.visited = true;
                    bfsQueue.add(foreachConnection);
                }
            }
        }
    }

    /**
     * Returns a list containing all users of n distance away from starting point, part of SocialConnections class
     * @see SocialConnections
     * @param name of the starting node
     * @param maxLevel how many layers of BFS we do before returning
     * @return list containing all of these persons connections up to maxLevel degree of separation
     * */
    @Override
    public List<String> getConnectionsToDegree(String name, int maxLevel) throws PersonNotFoundException
    {
        try
        {
            resetNodeProperties();
            Person person = findUser(name);
            if (person == null) throw new PersonNotFoundException(name + " not found!");
            List<String> connections = new LinkedList<>();

            Queue<Person> bfsQueue = new LinkedList<>();
            bfsQueue.addAll(person.connections.stream().map(Person -> Person.connectedTo).collect(Collectors.toList()));
            bfsQueue.forEach(Person -> Person.visited = true);

            List<Person> holdingQueue = new LinkedList<>();
            while (bfsQueue.size() > 0)
            {
                Person current = bfsQueue.remove();
                List<Person> adjacencyList = new LinkedList<>();
                adjacencyList.addAll(current.connections.stream().map(Edges -> Edges.connectedTo).collect(Collectors.toList()));

                for (Person foreach : adjacencyList)
                {
                    if (!foreach.visited)
                    {
                        foreach.visited = true;
                        if (maxLevel > 1) holdingQueue.add(foreach);
                    }
                }
                if (bfsQueue.size() == 0)
                {
                    maxLevel--;
                    bfsQueue.addAll(holdingQueue);
                    holdingQueue = new LinkedList<>();
                }
                if (!connections.contains(current.name) && !current.name.equals(name)) connections.add(current.name);
            }
            Collections.sort(connections);
            resetNodeProperties();
            return connections;
        }
        catch (PersonNotFoundException e)
        {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * checks if all the nodes in this graph are connected, part of SocialConnections class
     * @see SocialConnections
     * @return true or false depending on if the graph is all connected
     * */
    @Override
    public boolean areWeAllConnected()
    {
        resetNodeProperties();
        if (allPerson.size() == 0)
        {
            System.out.println("Graph is empty! returning false");
            return false;
        }
        allConnectedHelper(allPerson.get(0));
        for (Person person : allPerson)
        {
            if (!person.visited)
            {
                System.out.println(person.name);
                return false;
            }
        }
        resetNodeProperties(); //Reset visited properties
        return true;
    }

    /**
     * helper method for areWeAllConnected method only accessible from this class
     * @param person we are starting a dfs from
     * */
    private void allConnectedHelper(Person person)
    {
        person.visited = true;
        for (Edges connectedPerson : person.connections)
        {
            if (!connectedPerson.visited) {
                connectedPerson.visited = true;
                allConnectedHelper(connectedPerson.connectedTo);
            }
        }
    }
}
