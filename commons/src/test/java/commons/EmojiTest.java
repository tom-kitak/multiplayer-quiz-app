package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmojiTest {

    Emoji emoji1;
    Emoji emoji2;
    Emoji emoji3;

    @BeforeEach
    public void setUp(){
        emoji1 = new Emoji("happy");
        emoji2 = new Emoji("sad");
        emoji3 = new Emoji("happy");
    }

    @Test
    void testConstructor(){
        assertNotNull(emoji1);
    }

    @Test
    void getType() {
        assertEquals("sad", emoji2.getType());
    }

    @Test
    void setType() {
        Emoji emoji4 = new Emoji("shocked");
        emoji4.setType("angry");
        assertEquals("angry", emoji4.getType());
    }

    @Test
    void testEquals() {
        assertEquals(emoji1, emoji3);
    }

    @Test
    void testNotEquals(){
        assertNotEquals(emoji1, emoji2);
    }

    @Test
    void testHashCode() {
        assertEquals(emoji1.hashCode(), emoji3.hashCode());
    }

    @Test
    void testDifferentHash(){
        Emoji emoji4 = new Emoji("sad");
        assertNotEquals(emoji1.hashCode(), emoji4.hashCode());
    }

    @Test
    void testToString(){
        String test = emoji2.toString();
        assertTrue(test.contains("Emoji")&&test.contains("type")&&test.contains("sad"));
    }
}
