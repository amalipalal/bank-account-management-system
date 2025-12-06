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
}
