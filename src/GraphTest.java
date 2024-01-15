import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    @Test //Test out the strongest Connection
    void test01 () throws PersonNotFoundException {
        System.out.println("Test strongestConnection()");
        EnhancedSocialConnections s = new Graph();
        s.addPerson("M");
        s.addPerson("J");
        s.addPerson("T");
        s.addPerson("K");
        s.addPerson("A");
        s.addPerson("F");
        s.addPerson("C");

        s.connectPeople("M", "J", 2);
        s.connectPeople("M", "T", 2);
        s.connectPeople("M", "K", 8);
        s.connectPeople("K", "A", 1);

        List<String> result = s.getStrongestConnection("M");
        List<String> expected = new LinkedList<>();
        expected.add("J");
        expected.add("T");
        Collections.sort(expected);
        System.out.println(result + " : " + expected);
        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test //Check empty remove
    void Test02() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("Imelda");

        boolean result1 = newGraph.removePerson("Imelda");
        assertEquals(true, result1);

        List<String> result2 = newGraph.getConnections("Imelda");
        if (result2 == null) assertEquals(null, result2);
        else assertEquals(null, result2.toArray());
    }

    @Test //Check remove method
    void Test03() throws PersonNotFoundException {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("John");
        newGraph.addPerson("Jenna");
        newGraph.addPerson("Jacob");

        newGraph.connectPeople("John", "Jenna");
        newGraph.connectPeople("John", "Jacob");
        newGraph.connectPeople("Jenna", "Jacob");

        List<String> result2 = newGraph.getConnections("Jacob");
        List<String> result2Expected = new LinkedList<>();
        result2Expected.add("Jenna");
        result2Expected.add("John");
        System.out.println("Getting connections of Jacob, result: " + result2 + " expected: [Jenna, John]");
        assertArrayEquals(result2.toArray(), result2Expected.toArray());

        newGraph.removePerson("Jenna");
        System.out.println("Remove Jenna");
        List<String> result1 = newGraph.getConnections("John");
        List<String> result1Expected = new LinkedList<>();
        result1Expected.add("Jacob");
        System.out.println("Getting connections of John, result: " + result1 + " expected: [Jacob]");
        assertArrayEquals(result1Expected.toArray(), result1.toArray());

        List<String> result3 = newGraph.getConnections("Jacob");
        List<String> result3Expected = new LinkedList<>();
        result3Expected.add("John");
        System.out.println("Getting connections of Jacob, result: " + result3 + " expected: [John]");
        assertArrayEquals(result3Expected.toArray(), result3.toArray());

        newGraph.removePerson("John");
        System.out.println("Remove John");
        List<String> result4 = newGraph.getConnections("Jacob");
        String[] result4expected = {};
        System.out.println("Getting connections of Jacob, result: " + result4 + " expected: []");
        assertArrayEquals(result4expected, result4.toArray());
    }

    @Test //Test areWeAllConnected
    void Test04() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("M");
        newGraph.addPerson("J");
        newGraph.addPerson("T");
        newGraph.addPerson("S");
        newGraph.addPerson("K");
        newGraph.addPerson("V");
        newGraph.addPerson("N");
        newGraph.addPerson("L");
        newGraph.addPerson("Z");
        newGraph.addPerson("C");
        newGraph.addPerson("B");
        newGraph.addPerson("D");
        newGraph.addPerson("O");
        newGraph.addPerson("F");

        newGraph.connectPeople("M", "J");
        newGraph.connectPeople("J", "S");
        newGraph.connectPeople("S", "T");
        newGraph.connectPeople("T", "K");
        newGraph.connectPeople("T", "V");
        newGraph.connectPeople("L", "N");
        newGraph.connectPeople("J", "L");
        newGraph.connectPeople("L", "Z");
        newGraph.connectPeople("L", "B");
        newGraph.connectPeople("Z", "B");
        newGraph.connectPeople("C", "N");
        newGraph.connectPeople("C", "B");
        newGraph.connectPeople("D", "C");
        newGraph.connectPeople("D", "N");
        newGraph.connectPeople("C", "O");
        newGraph.connectPeople("J", "T");
        newGraph.connectPeople("F", "O");

        boolean result = newGraph.areWeAllConnected();
        System.out.println("Graph all connected? expected: true result: " + result);
        assertEquals(true, result);

        newGraph.addPerson("I");
        System.out.println("Added new node I: no connections");
        boolean result2 = newGraph.areWeAllConnected();
        System.out.println("Graph all connected? expected: false result: " + result2);
        assertEquals(false, result2);
    }

    @Test //Test mimimumDegOfSep
    void Test05() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("Jack");
        newGraph.addPerson("Imelda");

        newGraph.connectPeople("Jack", "Imelda");

        int result = newGraph.getMinimumDegreeOfSeparation("Jack", "Imelda");

        assertEquals(1, result);
    }

    @Test //Test mimimumDegOfSep
    void Test06() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("M");
        newGraph.addPerson("J");
        newGraph.addPerson("T");

        newGraph.connectPeople("M", "J");
        newGraph.connectPeople("J", "T");

        int result = newGraph.getMinimumDegreeOfSeparation("M", "T");
        assertEquals(2, result);

        int result2 = newGraph.getMinimumDegreeOfSeparation("M", "M");
        assertEquals(0, result2);
    }

    @Test //Test mimimumDegOfSep
    void Test07() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("M");
        newGraph.addPerson("J");
        newGraph.addPerson("T");
        newGraph.addPerson("S");
        newGraph.addPerson("K");
        newGraph.addPerson("V");
        newGraph.addPerson("N");
        newGraph.addPerson("L");

        newGraph.connectPeople("M", "J");
        newGraph.connectPeople("J", "T");
        newGraph.connectPeople("J", "S");
        newGraph.connectPeople("S", "T");
        newGraph.connectPeople("T", "K");
        newGraph.connectPeople("T", "V");
        newGraph.connectPeople("L", "N");
        newGraph.connectPeople("J", "L");

        int result = newGraph.getMinimumDegreeOfSeparation("J", "K");
        assertEquals(2, result);

        int result2 = newGraph.getMinimumDegreeOfSeparation("M", "K");
        assertEquals(3, result2);
    }

    @Test //Test mimimumDegOfSep
    void Test08() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("M");
        newGraph.addPerson("J");
        newGraph.addPerson("T");
        newGraph.addPerson("S");
        newGraph.addPerson("K");
        newGraph.addPerson("V");
        newGraph.addPerson("N");
        newGraph.addPerson("L");
        newGraph.addPerson("Z");
        newGraph.addPerson("C");
        newGraph.addPerson("B");
        newGraph.addPerson("D");
        newGraph.addPerson("O");
        newGraph.addPerson("F");

        newGraph.connectPeople("M", "J");
        newGraph.connectPeople("J", "S");
        newGraph.connectPeople("S", "T");
        newGraph.connectPeople("T", "K");
        newGraph.connectPeople("T", "V");
        newGraph.connectPeople("L", "N");
        newGraph.connectPeople("J", "L");
        newGraph.connectPeople("L", "Z");
        newGraph.connectPeople("L", "B");
        newGraph.connectPeople("Z", "B");
        newGraph.connectPeople("C", "N");
        newGraph.connectPeople("C", "B");
        newGraph.connectPeople("D", "C");
        newGraph.connectPeople("D", "N");
        newGraph.connectPeople("C", "O");
        newGraph.connectPeople("J", "T");
        newGraph.connectPeople("F", "O");

        int result = newGraph.getMinimumDegreeOfSeparation("J", "K");
        assertEquals(2, result);
    }

    @Test //Test mimimumDegOfSep
    void Test09() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("M");
        newGraph.addPerson("J");
        newGraph.addPerson("T");
        newGraph.addPerson("S");
        newGraph.addPerson("K");
        newGraph.addPerson("V");
        newGraph.addPerson("N");
        newGraph.addPerson("L");
        newGraph.addPerson("Z");
        newGraph.addPerson("C");
        newGraph.addPerson("B");
        newGraph.addPerson("D");
        newGraph.addPerson("O");
        newGraph.addPerson("F");

        newGraph.connectPeople("M", "J");
        newGraph.connectPeople("J", "S");
        newGraph.connectPeople("S", "T");
        newGraph.connectPeople("T", "K");
        newGraph.connectPeople("T", "V");
        newGraph.connectPeople("L", "N");
        newGraph.connectPeople("J", "L");
        newGraph.connectPeople("L", "Z");
        newGraph.connectPeople("L", "B");
        newGraph.connectPeople("Z", "B");
        newGraph.connectPeople("C", "N");
        newGraph.connectPeople("C", "B");
        newGraph.connectPeople("D", "C");
        newGraph.connectPeople("D", "N");
        newGraph.connectPeople("C", "O");
        newGraph.connectPeople("J", "T");
        newGraph.connectPeople("F", "O");

        int result2 = newGraph.getMinimumDegreeOfSeparation("M", "K");
        assertEquals(3, result2);
    }
    @Test //Test mimimumDegOfSep
    void Test10() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("M");
        newGraph.addPerson("J");
        newGraph.addPerson("T");
        newGraph.addPerson("S");
        newGraph.addPerson("K");
        newGraph.addPerson("V");
        newGraph.addPerson("N");
        newGraph.addPerson("L");
        newGraph.addPerson("Z");
        newGraph.addPerson("C");
        newGraph.addPerson("B");
        newGraph.addPerson("D");
        newGraph.addPerson("O");
        newGraph.addPerson("F");

        newGraph.connectPeople("M", "J");
        newGraph.connectPeople("J", "S");
        newGraph.connectPeople("S", "T");
        newGraph.connectPeople("T", "K");
        newGraph.connectPeople("T", "V");
        newGraph.connectPeople("L", "N");
        newGraph.connectPeople("J", "L");
        newGraph.connectPeople("L", "Z");
        newGraph.connectPeople("L", "B");
        newGraph.connectPeople("Z", "B");
        newGraph.connectPeople("C", "N");
        newGraph.connectPeople("C", "B");
        newGraph.connectPeople("D", "C");
        newGraph.connectPeople("D", "N");
        newGraph.connectPeople("C", "O");
        newGraph.connectPeople("J", "T");
        newGraph.connectPeople("F", "O");

        int result3 = newGraph.getMinimumDegreeOfSeparation("C", "K");
        assertEquals(5, result3);
    }
    @Test //Test mimimumDegOfSep
    void Test11() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("M");
        newGraph.addPerson("J");
        newGraph.addPerson("T");
        newGraph.addPerson("S");
        newGraph.addPerson("K");
        newGraph.addPerson("V");
        newGraph.addPerson("N");
        newGraph.addPerson("L");
        newGraph.addPerson("Z");
        newGraph.addPerson("C");
        newGraph.addPerson("B");
        newGraph.addPerson("D");
        newGraph.addPerson("O");
        newGraph.addPerson("F");

        newGraph.connectPeople("M", "J");
        newGraph.connectPeople("J", "S");
        newGraph.connectPeople("S", "T");
        newGraph.connectPeople("T", "K");
        newGraph.connectPeople("T", "V");
        newGraph.connectPeople("L", "N");
        newGraph.connectPeople("J", "L");
        newGraph.connectPeople("L", "Z");
        newGraph.connectPeople("L", "B");
        newGraph.connectPeople("Z", "B");
        newGraph.connectPeople("C", "N");
        newGraph.connectPeople("C", "B");
        newGraph.connectPeople("D", "C");
        newGraph.connectPeople("D", "N");
        newGraph.connectPeople("C", "O");
        newGraph.connectPeople("J", "T");
        newGraph.connectPeople("F", "O");

        int result4 = newGraph.getMinimumDegreeOfSeparation("L", "T");
        assertEquals(2, result4);
    }
    @Test //Test mimimumDegOfSep
    void Test12() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("M");
        newGraph.addPerson("J");
        newGraph.addPerson("T");
        newGraph.addPerson("S");
        newGraph.addPerson("K");
        newGraph.addPerson("V");
        newGraph.addPerson("N");
        newGraph.addPerson("L");
        newGraph.addPerson("Z");
        newGraph.addPerson("C");
        newGraph.addPerson("B");
        newGraph.addPerson("D");
        newGraph.addPerson("O");
        newGraph.addPerson("F");

        newGraph.connectPeople("M", "J");
        newGraph.connectPeople("J", "S");
        newGraph.connectPeople("S", "T");
        newGraph.connectPeople("T", "K");
        newGraph.connectPeople("T", "V");
        newGraph.connectPeople("L", "N");
        newGraph.connectPeople("J", "L");
        newGraph.connectPeople("L", "Z");
        newGraph.connectPeople("L", "B");
        newGraph.connectPeople("Z", "B");
        newGraph.connectPeople("C", "N");
        newGraph.connectPeople("C", "B");
        newGraph.connectPeople("D", "C");
        newGraph.connectPeople("D", "N");
        newGraph.connectPeople("C", "O");
        newGraph.connectPeople("J", "T");
        newGraph.connectPeople("F", "O");

        int result5 = newGraph.getMinimumDegreeOfSeparation("C", "V");
        assertEquals(5, result5);
    }
    @Test //Test mimimumDegOfSep
    void Test13() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("M");
        newGraph.addPerson("J");
        newGraph.addPerson("T");
        newGraph.addPerson("S");
        newGraph.addPerson("K");
        newGraph.addPerson("V");
        newGraph.addPerson("N");
        newGraph.addPerson("L");
        newGraph.addPerson("Z");
        newGraph.addPerson("C");
        newGraph.addPerson("B");
        newGraph.addPerson("D");
        newGraph.addPerson("O");
        newGraph.addPerson("F");

        newGraph.connectPeople("M", "J");
        newGraph.connectPeople("J", "S");
        newGraph.connectPeople("S", "T");
        newGraph.connectPeople("T", "K");
        newGraph.connectPeople("T", "V");
        newGraph.connectPeople("L", "N");
        newGraph.connectPeople("J", "L");
        newGraph.connectPeople("L", "Z");
        newGraph.connectPeople("L", "B");
        newGraph.connectPeople("Z", "B");
        newGraph.connectPeople("C", "N");
        newGraph.connectPeople("C", "B");
        newGraph.connectPeople("D", "C");
        newGraph.connectPeople("D", "N");
        newGraph.connectPeople("C", "O");
        newGraph.connectPeople("J", "T");
        newGraph.connectPeople("F", "O");

        int result6 = newGraph.getMinimumDegreeOfSeparation("D", "M");
        assertEquals(4, result6);
    }
    @Test //Test mimimumDegOfSep
    void Test14() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("M");
        newGraph.addPerson("J");
        newGraph.addPerson("T");
        newGraph.addPerson("S");
        newGraph.addPerson("K");
        newGraph.addPerson("V");
        newGraph.addPerson("N");
        newGraph.addPerson("L");
        newGraph.addPerson("Z");
        newGraph.addPerson("C");
        newGraph.addPerson("B");
        newGraph.addPerson("D");
        newGraph.addPerson("O");
        newGraph.addPerson("F");

        newGraph.connectPeople("M", "J");
        newGraph.connectPeople("J", "S");
        newGraph.connectPeople("S", "T");
        newGraph.connectPeople("T", "K");
        newGraph.connectPeople("T", "V");
        newGraph.connectPeople("L", "N");
        newGraph.connectPeople("J", "L");
        newGraph.connectPeople("L", "Z");
        newGraph.connectPeople("L", "B");
        newGraph.connectPeople("Z", "B");
        newGraph.connectPeople("C", "N");
        newGraph.connectPeople("C", "B");
        newGraph.connectPeople("D", "C");
        newGraph.connectPeople("D", "N");
        newGraph.connectPeople("C", "O");
        newGraph.connectPeople("J", "T");
        newGraph.connectPeople("F", "O");

        int result7 = newGraph.getMinimumDegreeOfSeparation("Z", "V");
        assertEquals(4, result7);
    }
    @Test //Test mimimumDegOfSep
    void Test15() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("M");
        newGraph.addPerson("J");
        newGraph.addPerson("T");
        newGraph.addPerson("S");
        newGraph.addPerson("K");
        newGraph.addPerson("V");
        newGraph.addPerson("N");
        newGraph.addPerson("L");
        newGraph.addPerson("Z");
        newGraph.addPerson("C");
        newGraph.addPerson("B");
        newGraph.addPerson("D");
        newGraph.addPerson("O");
        newGraph.addPerson("F");

        newGraph.connectPeople("M", "J");
        newGraph.connectPeople("J", "S");
        newGraph.connectPeople("S", "T");
        newGraph.connectPeople("T", "K");
        newGraph.connectPeople("T", "V");
        newGraph.connectPeople("L", "N");
        newGraph.connectPeople("J", "L");
        newGraph.connectPeople("L", "Z");
        newGraph.connectPeople("L", "B");
        newGraph.connectPeople("Z", "B");
        newGraph.connectPeople("C", "N");
        newGraph.connectPeople("C", "B");
        newGraph.connectPeople("D", "C");
        newGraph.connectPeople("D", "N");
        newGraph.connectPeople("C", "O");
        newGraph.connectPeople("J", "T");
        newGraph.connectPeople("F", "O");

        int result9 = newGraph.getMinimumDegreeOfSeparation("O", "V");
        assertEquals(6, result9);
    }
    @Test //Test mimimumDegOfSep
    void Test16() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("M");
        newGraph.addPerson("J");
        newGraph.addPerson("T");
        newGraph.addPerson("S");
        newGraph.addPerson("K");
        newGraph.addPerson("V");
        newGraph.addPerson("N");
        newGraph.addPerson("L");
        newGraph.addPerson("Z");
        newGraph.addPerson("C");
        newGraph.addPerson("B");
        newGraph.addPerson("D");
        newGraph.addPerson("O");
        newGraph.addPerson("F");

        newGraph.connectPeople("M", "J");
        newGraph.connectPeople("J", "S");
        newGraph.connectPeople("S", "T");
        newGraph.connectPeople("T", "K");
        newGraph.connectPeople("T", "V");
        newGraph.connectPeople("L", "N");
        newGraph.connectPeople("J", "L");
        newGraph.connectPeople("L", "Z");
        newGraph.connectPeople("L", "B");
        newGraph.connectPeople("Z", "B");
        newGraph.connectPeople("C", "N");
        newGraph.connectPeople("C", "B");
        newGraph.connectPeople("D", "C");
        newGraph.connectPeople("D", "N");
        newGraph.connectPeople("C", "O");
        newGraph.connectPeople("J", "T");
        newGraph.connectPeople("F", "O");

        int result10 = newGraph.getMinimumDegreeOfSeparation("B", "Z");
        assertEquals(1, result10);
    }
    @Test //Test mimimumDegOfSep
    void Test17() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("M");
        newGraph.addPerson("J");
        newGraph.addPerson("T");
        newGraph.addPerson("S");
        newGraph.addPerson("K");
        newGraph.addPerson("V");
        newGraph.addPerson("N");
        newGraph.addPerson("L");
        newGraph.addPerson("Z");
        newGraph.addPerson("C");
        newGraph.addPerson("B");
        newGraph.addPerson("D");
        newGraph.addPerson("O");
        newGraph.addPerson("F");

        newGraph.connectPeople("M", "J");
        newGraph.connectPeople("J", "S");
        newGraph.connectPeople("S", "T");
        newGraph.connectPeople("T", "K");
        newGraph.connectPeople("T", "V");
        newGraph.connectPeople("L", "N");
        newGraph.connectPeople("J", "L");
        newGraph.connectPeople("L", "Z");
        newGraph.connectPeople("L", "B");
        newGraph.connectPeople("Z", "B");
        newGraph.connectPeople("C", "N");
        newGraph.connectPeople("C", "B");
        newGraph.connectPeople("D", "C");
        newGraph.connectPeople("D", "N");
        newGraph.connectPeople("C", "O");
        newGraph.connectPeople("J", "T");
        newGraph.connectPeople("F", "O");

        int result12 = newGraph.getMinimumDegreeOfSeparation("F", "T");
        assertEquals(6, result12);
    }
    @Test //Test mimimumDegOfSep
    void Test18() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("M");
        newGraph.addPerson("J");
        newGraph.addPerson("T");
        newGraph.addPerson("S");
        newGraph.addPerson("K");
        newGraph.addPerson("V");
        newGraph.addPerson("N");
        newGraph.addPerson("L");
        newGraph.addPerson("Z");
        newGraph.addPerson("C");
        newGraph.addPerson("B");
        newGraph.addPerson("D");
        newGraph.addPerson("O");
        newGraph.addPerson("F");

        newGraph.connectPeople("M", "J");
        newGraph.connectPeople("J", "S");
        newGraph.connectPeople("S", "T");
        newGraph.connectPeople("T", "K");
        newGraph.connectPeople("T", "V");
        newGraph.connectPeople("L", "N");
        newGraph.connectPeople("J", "L");
        newGraph.connectPeople("L", "Z");
        newGraph.connectPeople("L", "B");
        newGraph.connectPeople("Z", "B");
        newGraph.connectPeople("C", "N");
        newGraph.connectPeople("C", "B");
        newGraph.connectPeople("D", "C");
        newGraph.connectPeople("D", "N");
        newGraph.connectPeople("C", "O");
        newGraph.connectPeople("J", "T");
        newGraph.connectPeople("F", "O");

        int result11 = newGraph.getMinimumDegreeOfSeparation("J", "T");
        assertEquals(1, result11);
    }
    @Test //Test mimimumDegOfSep
    void Test19() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("M");
        newGraph.addPerson("J");
        newGraph.addPerson("T");
        newGraph.addPerson("S");
        newGraph.addPerson("K");
        newGraph.addPerson("V");
        newGraph.addPerson("N");
        newGraph.addPerson("L");
        newGraph.addPerson("Z");
        newGraph.addPerson("C");
        newGraph.addPerson("B");
        newGraph.addPerson("D");
        newGraph.addPerson("O");
        newGraph.addPerson("F");

        newGraph.connectPeople("M", "J");
        newGraph.connectPeople("J", "S");
        newGraph.connectPeople("S", "T");
        newGraph.connectPeople("T", "K");
        newGraph.connectPeople("T", "V");
        newGraph.connectPeople("L", "N");
        newGraph.connectPeople("J", "L");
        newGraph.connectPeople("L", "Z");
        newGraph.connectPeople("L", "B");
        newGraph.connectPeople("Z", "B");
        newGraph.connectPeople("C", "N");
        newGraph.connectPeople("C", "B");
        newGraph.connectPeople("D", "C");
        newGraph.connectPeople("D", "N");
        newGraph.connectPeople("C", "O");
        newGraph.connectPeople("J", "T");
        newGraph.connectPeople("F", "O");

        int result8 = newGraph.getMinimumDegreeOfSeparation("C", "O");
        assertEquals(1, result8);
    }
    @Test
    void Test20() throws PersonNotFoundException {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("M");

        newGraph.connectPeople("M", "J");
        newGraph.getMinimumDegreeOfSeparation("M", "J");
    }
    @Test //Test mimimumDegOfSep
    void Test21() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("M");
        newGraph.addPerson("J");
        newGraph.addPerson("T");
        newGraph.addPerson("S");
        newGraph.addPerson("K");
        newGraph.addPerson("V");
        newGraph.addPerson("N");
        newGraph.addPerson("L");
        newGraph.addPerson("Z");
        newGraph.addPerson("C");
        newGraph.addPerson("B");
        newGraph.addPerson("D");
        newGraph.addPerson("O");
        newGraph.addPerson("F");

        newGraph.connectPeople("M", "J");
        newGraph.connectPeople("J", "S");
        newGraph.connectPeople("S", "T");
        newGraph.connectPeople("T", "K");
        newGraph.connectPeople("T", "V");
        newGraph.connectPeople("L", "N");
        newGraph.connectPeople("J", "L");
        newGraph.connectPeople("L", "Z");
        newGraph.connectPeople("L", "B");
        newGraph.connectPeople("Z", "B");
        newGraph.connectPeople("C", "N");
        newGraph.connectPeople("C", "B");
        newGraph.connectPeople("D", "C");
        newGraph.connectPeople("D", "N");
        newGraph.connectPeople("C", "O");
        newGraph.connectPeople("J", "T");
        newGraph.connectPeople("F", "O");

        boolean result = newGraph.areWeAllConnected();
        System.out.println("Graph all connected?");
        assertEquals(true, result);
    }

    @Test //Test mimimumDegOfSep
    void Test22() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("M");
        newGraph.addPerson("J");
        newGraph.addPerson("T");
        newGraph.addPerson("S");
        newGraph.addPerson("K");
        newGraph.addPerson("V");
        newGraph.addPerson("N");
        newGraph.addPerson("L");
        newGraph.addPerson("Z");
        newGraph.addPerson("C");
        newGraph.addPerson("B");
        newGraph.addPerson("D");
        newGraph.addPerson("O");
        newGraph.addPerson("F");

        newGraph.connectPeople("M", "J");
        newGraph.connectPeople("J", "S");
        newGraph.connectPeople("S", "T");
        newGraph.connectPeople("T", "K");
        newGraph.connectPeople("T", "V");
        newGraph.connectPeople("L", "N");
        newGraph.connectPeople("J", "L");
        newGraph.connectPeople("L", "Z");
        newGraph.connectPeople("L", "B");
        newGraph.connectPeople("Z", "B");
        newGraph.connectPeople("C", "N");
        newGraph.connectPeople("C", "B");
        newGraph.connectPeople("D", "C");
        newGraph.connectPeople("D", "N");
        newGraph.connectPeople("C", "O");
        newGraph.connectPeople("J", "T");
        newGraph.connectPeople("F", "O");

        List<String> result = newGraph.getConnectionsToDegree("J", 1);
        List<String> expected = new LinkedList<>();
        expected.add("M");
        expected.add("L");
        expected.add("T");
        expected.add("S");
        Collections.sort(expected);
        assertEquals(expected, result);
    }
    @Test //Test mimimumDegOfSep
    void Test23() throws PersonNotFoundException
    {
        SocialConnections newGraph = new Graph();
        newGraph.addPerson("M");
        newGraph.addPerson("J");
        newGraph.addPerson("T");
        newGraph.addPerson("S");
        newGraph.addPerson("K");
        newGraph.addPerson("V");
        newGraph.addPerson("N");
        newGraph.addPerson("L");
        newGraph.addPerson("Z");
        newGraph.addPerson("C");
        newGraph.addPerson("B");
        newGraph.addPerson("D");
        newGraph.addPerson("O");
        newGraph.addPerson("F");

        newGraph.connectPeople("M", "J");
        newGraph.connectPeople("J", "S");
        newGraph.connectPeople("S", "T");
        newGraph.connectPeople("T", "K");
        newGraph.connectPeople("T", "V");
        newGraph.connectPeople("L", "N");
        newGraph.connectPeople("J", "L");
        newGraph.connectPeople("L", "Z");
        newGraph.connectPeople("L", "B");
        newGraph.connectPeople("Z", "B");
        newGraph.connectPeople("C", "N");
        newGraph.connectPeople("C", "B");
        newGraph.connectPeople("D", "C");
        newGraph.connectPeople("D", "N");
        newGraph.connectPeople("C", "O");
        newGraph.connectPeople("J", "T");
        newGraph.connectPeople("F", "O");

        List<String> result = newGraph.getConnectionsToDegree("J", 5);
        List<String> expected = new LinkedList<>();
        expected.add("M");
        expected.add("L");
        expected.add("T");
        expected.add("S");

        expected.add("B");
        expected.add("V");
        expected.add("K");
        expected.add("Z");
        expected.add("N");

        expected.add("D");
        expected.add("C");

        expected.add("O");
        expected.add("F");
        Collections.sort(expected);
        System.out.println("Excpected list: " + expected + " actual: " + result);
        assertEquals(expected, result);
    }

    @Test
    void Test24() throws PersonNotFoundException {
        SocialConnections s = new Graph();
        s.addPerson("A");
        s.addPerson("B");
        s.addPerson("C");
        s.addPerson("D");
        s.addPerson("E");
        s.addPerson("F");
        s.addPerson("G");
        s.addPerson("H");
        s.addPerson("I");
        s.addPerson("J");
        s.addPerson("K");
        s.addPerson("L");
        s.addPerson("M");

        s.connectPeople("A", "B");
        s.connectPeople("A", "C");
        s.connectPeople("A", "D");
        s.connectPeople("A", "E");

        s.connectPeople("B", "C");
        s.connectPeople("B", "D");
        s.connectPeople("B", "F");
        s.connectPeople("B", "M");


        s.connectPeople("C", "G");
        s.connectPeople("C", "H");
        s.connectPeople("C", "E");

        s.connectPeople("D", "E");
        s.connectPeople("D", "K");
        s.connectPeople("D", "L");


        s.connectPeople("E", "J");
        s.connectPeople("E", "I");

        s.connectPeople("M", "F");
        s.connectPeople("M", "L");

        s.connectPeople("K", "J");
        s.connectPeople("K", "L");

        s.connectPeople("I", "H");
        s.connectPeople("I", "J");

        s.connectPeople("G", "H");
        s.connectPeople("G", "F");

        List<String> result = s.getConnectionsToDegree("A", 1);
        List<String> expected = s.getConnections("A");
        Collections.sort(result);
        System.out.println(result + "\n" + expected);
    }

    @Test
    void Test25() throws PersonNotFoundException {
        SocialConnections s = new Graph();
        s.addPerson("A");
        s.addPerson("B");
        s.addPerson("C");
        s.addPerson("D");
        s.addPerson("E");
        s.addPerson("F");
        s.addPerson("G");
        s.addPerson("H");
        s.addPerson("I");
        s.addPerson("J");
        s.addPerson("K");
        s.addPerson("L");
        s.addPerson("M");

        s.connectPeople("A", "B");
        s.connectPeople("A", "C");
        s.connectPeople("A", "D");
        s.connectPeople("A", "E");

        s.connectPeople("B", "C");
        s.connectPeople("B", "D");
        s.connectPeople("B", "F");
        s.connectPeople("B", "M");


        s.connectPeople("C", "G");
        s.connectPeople("C", "H");
        s.connectPeople("C", "E");

        s.connectPeople("D", "E");
        s.connectPeople("D", "K");
        s.connectPeople("D", "L");


        s.connectPeople("E", "J");
        s.connectPeople("E", "I");

        s.connectPeople("M", "F");
        s.connectPeople("M", "L");

        s.connectPeople("K", "J");
        s.connectPeople("K", "L");

        s.connectPeople("I", "H");
        s.connectPeople("I", "J");

        s.connectPeople("G", "H");
        s.connectPeople("G", "F");

        List<String> result = s.getConnectionsToDegree("A", 1);
        List<String> expected = s.getConnections("A");
        Collections.sort(result);
        System.out.println(result + "\n" + expected);
    }

    @Test
    void Test26() throws PersonNotFoundException {
        EnhancedSocialConnections s = new Graph();
        s.addPerson("a");
        s.addPerson("i");
        s.addPerson("k");
        s.addPerson("d");
        s.addPerson("c");
        s.addPerson("e");
        s.addPerson("g");

        s.connectPeople("a", "i", 12);
        s.connectPeople("a", "d", 2);
        s.connectPeople("a", "c", 1);

        s.connectPeople("d", "k", 5);
        s.connectPeople("d", "e", 1);

        s.connectPeople("k", "i", 6);

        s.connectPeople("c", "i", 13);
        s.connectPeople("c", "e", 1);

        s.connectPeople("e", "g", 2);
        s.connectPeople("g", "i",3);

        System.out.println(s.getStrongestPath("a", "i"));


        s.connectPeople("c", "e", 13);
        System.out.println(s.getStrongestPath("a", "i"));
    }

    @Test
    void Test27() throws PersonNotFoundException {
        EnhancedSocialConnections s = new Graph();
        s.addPerson("a");
        s.addPerson("i");
        s.addPerson("k");
        s.addPerson("d");
        s.addPerson("c");
        s.addPerson("e");
        s.addPerson("g");

        s.connectPeople("a", "i", 12);
        s.connectPeople("a", "d", 2);
        s.connectPeople("a", "c", 1);

        s.connectPeople("d", "k", 5);
        s.connectPeople("d", "e", 1);

        s.connectPeople("k", "i", 6);

        s.connectPeople("c", "i", 13);
        s.connectPeople("c", "e", 1);

        s.connectPeople("e", "g", 2);
        s.connectPeople("g", "i",3);

        System.out.println(s.getWeakestPath("a", "i"));
    }

    @Test
    void Test28() throws PersonNotFoundException {
        EnhancedSocialConnections s = new Graph();
        s.addPerson("a");
        s.addPerson("b");
        s.addPerson("c");
        s.addPerson("d");
        s.addPerson("f");
        s.addPerson("g");
        s.addPerson("h");
        s.addPerson("i");
        s.addPerson("j");

        s.connectPeople("a", "j", 2);
        s.connectPeople("a", "b", 3);
        s.connectPeople("a", "d", 12);

        s.connectPeople("d", "i", 11);
        s.connectPeople("i", "c", 9);

        s.connectPeople("c", "b", 7);

        s.connectPeople("b", "h", 9);
        s.connectPeople("h", "f", 8);

        s.connectPeople("b", "f", 4);
        s.connectPeople("g", "f",3);

        System.out.println("weakest path: " + s.getWeakestPath("a", "h"));
        System.out.println("strongest path: " + s.getStrongestPath("a", "j"));
        System.out.println("strongest path: " + s.getStrongestPath("a", "h"));
        System.out.println("are we all connected?: " + s.areWeAllConnected());
        System.out.println("shortest path: " + s.getMinimumDegreeOfSeparation("j", "g") + " expected: [j, a, b, f, g] which is 4 edges");
        System.out.println("get strongest connections: " + s.getStrongestConnection("c"));
        System.out.println("get connections: " + s.getConnections("b"));
        System.out.println("get connections to n degree: " + s.getConnectionsToDegree("a", 2));
    }
    @Test
    void Test29() throws PersonNotFoundException {
        EnhancedSocialConnections s = new Graph();
        s.addPerson("a");
        s.addPerson("b");
        s.addPerson("c");
        s.addPerson("d");
        s.addPerson("e");
        s.addPerson("f");
        s.addPerson("g");
        s.addPerson("h");
        s.addPerson("i");
        s.addPerson("j");

        s.connectPeople("a", "j", 2);
        s.connectPeople("a", "b", 3);
        s.connectPeople("a", "d", 12);

        s.connectPeople("d", "i", 11);
        s.connectPeople("i", "c", 9);

        s.connectPeople("c", "b", 7);

        s.connectPeople("b", "h", 9);
        s.connectPeople("h", "f", 8);

        s.connectPeople("b", "f", 4);
        s.connectPeople("g", "f",3);

        s.removePerson("e");

        System.out.println("weakest path: " + s.getWeakestPath("a", "h"));
        System.out.println("strongest path: " + s.getStrongestPath("a", "j"));
        System.out.println("strongest path: " + s.getStrongestPath("a", "h"));
        System.out.println("are we all connected?: " + s.areWeAllConnected());
        System.out.println("shortest path: " + s.getMinimumDegreeOfSeparation("j", "g") + " expected: [j, a, b, f, g] which is 4 edges");
        System.out.println("get strongest connections: " + s.getStrongestConnection("c"));
        System.out.println("get connections: " + s.getConnections("b"));
        System.out.println("get connections to n degree: " + s.getConnectionsToDegree("a", 2));
    }

    @Test
    void Test30() throws PersonNotFoundException {
        EnhancedSocialConnections s = new Graph();
        s.addPerson("a");
        s.addPerson("b");
        s.addPerson("c");
        s.addPerson("d");
        s.addPerson("f");
        s.addPerson("g");
        s.addPerson("h");
        s.addPerson("i");
        s.addPerson("j");

        s.connectPeople("a", "j");
        s.connectPeople("a", "b");
        s.connectPeople("a", "d");

        s.connectPeople("d", "i");
        s.connectPeople("i", "c");

        s.connectPeople("c", "b");

        s.connectPeople("b", "h");
        s.connectPeople("h", "f");

        s.connectPeople("b", "f");
        s.connectPeople("g", "f");

        System.out.println(s.getStrongestPath("a","g") +" "+ s.getMinimumDegreeOfSeparation("a", "g"));
    }
}