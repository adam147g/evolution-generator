package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    @Test
    void equalsTest() {
        Vector2d v = new Vector2d(1, 2);
        Object o = new Object();
        Vector2d v2 = new Vector2d(1, 2);
        assertFalse(v.equals(o));
        assertTrue(v.equals(v2));
    }

    @Test
    void toStringTest() {
        Vector2d v1 = new Vector2d(2, 4);
        Vector2d v2 = new Vector2d(32, 4244);
        assertEquals(v1.toString(), "(2,4)");
        assertEquals(v2.toString(), "(32,4244)");
    }

    @Test
    void precedesTest() {
        Vector2d a = new Vector2d(3, 6);
        Vector2d b = new Vector2d(7, 8);
        Vector2d c = new Vector2d(2, 5);
        Vector2d d = new Vector2d(2, 7);


        assertTrue(c.precedes(a));
        assertTrue(c.precedes(b));
        assertTrue(c.precedes(d));
        assertFalse(d.precedes(a));
        assertTrue(d.precedes(b));
        assertFalse(b.precedes(a));


    }

    @Test
    void followsTest() {
        Vector2d a = new Vector2d(3, 6);
        Vector2d b = new Vector2d(7, 8);
        Vector2d c = new Vector2d(2, 5);
        Vector2d d = new Vector2d(2, 7);

        assertTrue(a.follows(c));
        assertFalse(a.follows(d));
        assertTrue(b.follows(a));
        assertTrue(b.follows(c));
        assertTrue(b.follows(d));
        assertFalse(a.follows(b));
    }

    @Test
    void upperRightTest() {
        Vector2d v = new Vector2d(2, 3);
        Vector2d u = new Vector2d(1, 4);
        Vector2d t = new Vector2d(2, 4);
        assertEquals(v.upperRight(u), t);
    }

    @Test
    void lowerLeftTest() {
        Vector2d v = new Vector2d(2, 3);
        Vector2d u = new Vector2d(1, 4);
        Vector2d t = new Vector2d(1, 3);
        assertEquals(v.lowerLeft(u), t);

    }

    @Test
    void addTest() {
        Vector2d v = new Vector2d(2, 3);
        Vector2d u = new Vector2d(1, 4);
        Vector2d t = new Vector2d(3, 7);
        assertEquals(v.add(u), t);
    }

    @Test
    void subtractTest() {
        Vector2d v = new Vector2d(2, 3);
        Vector2d u = new Vector2d(1, 4);
        Vector2d t = new Vector2d(1, -1);
        assertEquals(v.subtract(u), t);
    }

    @Test
    void oppositeTest() {
        Vector2d v = new Vector2d(12, 31);
        Vector2d t = new Vector2d(-12, -31);
        assertEquals(v.opposite(), t);
    }
}
