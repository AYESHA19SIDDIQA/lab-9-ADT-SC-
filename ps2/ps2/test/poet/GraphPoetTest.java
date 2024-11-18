package poet;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // Make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testSingleBridgeWord() throws IOException {
        GraphPoet poet = new GraphPoet(new File("C:/Users/hp/Downloads/ps2/ps2/test/poet/seven-words.txt"));
        String input = "the quick fox jumped";
        String expectedOutput = "the quick brown fox jumped";
        String actualOutput = poet.poem(input);
        System.out.println("Actual Output: " + actualOutput);
        assertEquals("Expected a single bridge word 'brown'", expectedOutput, actualOutput);
    }
    
    @Test
    public void testMultipleBridges() throws IOException {
        GraphPoet poet = new GraphPoet(new File("C:/Users/hp/Downloads/ps2/ps2/test/poet/seven-words.txt"));
        String input = "quick fox";
        String expectedOutput = "quick brown fox";
        String actualOutput = poet.poem(input);
        System.out.println("Actual Output: " + actualOutput);
        assertEquals("Expected 'brown' as the bridge word", expectedOutput, actualOutput);
    }
    
    @Test
    public void testNoBridgeWord() throws IOException {
        GraphPoet poet = new GraphPoet(new File("C:/Users/hp/Downloads/ps2/ps2/test/poet/seven-words.txt"));
        String input = "hello world";
        String expectedOutput = "hello world";
        String actualOutput = poet.poem(input);
        System.out.println("Actual Output: " + actualOutput);
        assertEquals("Expected no changes to input", expectedOutput, actualOutput);
    }
    
    @Test
    public void testEmptyInput() throws IOException {
        GraphPoet poet = new GraphPoet(new File("C:/Users/hp/Downloads/ps2/ps2/test/poet/seven-words.txt"));
        String input = "";
        String expectedOutput = "";
        String actualOutput = poet.poem(input);
        assertEquals("Expected empty output for empty input", expectedOutput, actualOutput);
    }
    
    @Test
    public void testInputNoBridgesButWordsInCorpus() throws IOException {
        GraphPoet poet = new GraphPoet(new File("C:/Users/hp/Downloads/ps2/ps2/test/poet/seven-words.txt"));
        String input = "the lazy dog";
        String expectedOutput = "the lazy dog";
        String actualOutput = poet.poem(input);
        assertEquals("Expected no changes as no bridges are possible", expectedOutput, actualOutput);
    }

}
