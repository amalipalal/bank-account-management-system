import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.InputReader;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class InputReaderTest {

    private InputReader inputReader;

    @Test
    @DisplayName("Should return integer when valid input is given on first try")
    void testReadIntValidFirstTry() {
        Scanner sc = new Scanner("7\n");
        inputReader = new InputReader(sc);

        int result = inputReader.readInt("Enter number", 1, 10);

        assertEquals(7, result);
    }

    @Test
    @DisplayName("Should handle non-numeric input then accept valid integer")
    void testReadIntInvalidThenValid() {
        Scanner sc = new Scanner("abc\n5\n");
        inputReader = new InputReader(sc);

        int result = inputReader.readInt("Enter number", 1, 10);

        assertEquals(5, result);
    }

    @Test
    @DisplayName("Should reject out-of-range integer and accept next valid input")
    void testReadIntOutOfRangeThenValid() {
        Scanner sc = new Scanner("50\n8\n");
        inputReader = new InputReader(sc);

        int result = inputReader.readInt("Enter number", 1, 10);

        assertEquals(8, result);
    }

    @Test
    @DisplayName("Should reject invalid double then accept valid value")
    void testReadDoubleInvalidThenValid() {
        Scanner sc = new Scanner("abc\n-5\n3.14\n");
        inputReader = new InputReader(sc);

        double result = inputReader.readDouble("Enter amount", 0);

        assertEquals(3.14, result);
    }

    @Test
    @DisplayName("Should validate non-empty string using validator")
    void testReadNonEmptyStringValidAfterRetry() {
        Scanner sc = new Scanner("\nJohn\n");
        inputReader = new InputReader(sc);

        String result = inputReader.readNonEmptyString(
                "Enter name",
                input -> input.isEmpty() ? "Cannot be empty" : null
        );

        assertEquals("John", result);
    }
    
    @Test
    @DisplayName("Should return true when user enters Y")
    void testReadYesOrNoTrue() {
        Scanner sc = new Scanner("y\n");
        inputReader = new InputReader(sc);

        assertTrue(inputReader.readYesOrNo("Continue?"));
    }

    @Test
    @DisplayName("Should return false when user enters N")
    void testReadYesOrNoFalse() {
        Scanner sc = new Scanner("n\n");
        inputReader = new InputReader(sc);

        assertFalse(inputReader.readYesOrNo("Continue?"));
    }

    @Test
    @DisplayName("Should reject invalid input then accept Y")
    void testReadYesOrNoInvalidThenValid() {
        Scanner sc = new Scanner("maybe\nY\n");
        inputReader = new InputReader(sc);

        assertTrue(inputReader.readYesOrNo("Continue?"));
    }
}
