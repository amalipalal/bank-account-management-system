import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import utils.ValidationUtil;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationUtilTest {

    @ParameterizedTest(name = "Invalid name ''{0}'' should return ''{1}''")
    @CsvSource({
            "'', Name cannot be empty.",
            "null, Name cannot be empty.",
            "A, 'Name must contain only letters and spaces, and be at least 2 characters long.'",
            "John3, 'Name must contain only letters and spaces, and be at least 2 characters long.'",
            "J@ne, 'Name must contain only letters and spaces, and be at least 2 characters long.'"
    })
    @DisplayName("Should return error message for invalid name")
    void testValidateNameInvalid(String input, String expectedError) {
        if ("null".equals(input)) input = null;
        assertEquals(expectedError, ValidationUtil.validateName(input));
    }

    @ParameterizedTest(name = "Valid name ''{0}'' should return null")
    @ValueSource(strings = {"John", "Mary Jane", "Alice"})
    @DisplayName("Should not return error message for valid name")
    void testValidateNameValid(String input) {
        assertNull(ValidationUtil.validateName(input));
    }

    @ParameterizedTest(name = "Invalid phone ''{0}'' should return ''{1}''")
    @CsvSource({
            "'', Phone number cannot be empty.",
            "null, Phone number cannot be empty.",
            "0551234567, Phone number must start with +233.",
            "+23312345, Phone number must have 9 digits after +233.",
            "+233123456789, null"
    })
    @DisplayName("Should return error message for invalid phone")
    void testValidatePhoneInvalid(String input, String expectedError) {
        if ("null".equals(input)) input = null;
        if ("null".equals(expectedError)) expectedError = null;
        assertEquals(expectedError, ValidationUtil.validatePhoneNumber(input));
    }

    @ParameterizedTest(name = "Valid phone ''{0}'' should return null")
    @ValueSource(strings = {"+233541234567", "+233271112223"})
    @DisplayName("Should not return error message for valid phone")
    void testValidatePhoneValid(String input) {
        assertNull(ValidationUtil.validatePhoneNumber(input));
    }

    @ParameterizedTest(name = "Invalid account ''{0}'' should return ''{1}''")
    @CsvSource({
            "'', 'Account number cannot be empty.'",
            "ACC12, 'Account number must start with ''ACC'' followed by 3 digits (e.g., ACC123).'",
            "AC1234, 'Account number must start with ''ACC'' followed by 3 digits (e.g., ACC123).'",
            "ACCABC, 'Account number must start with ''ACC'' followed by 3 digits (e.g., ACC123).'"
    })
    @DisplayName("Should return error message for invalid account number")
    void testValidateAccountNumberInvalid(String input, String expectedError) {
        assertEquals(expectedError, ValidationUtil.validateAccountNumber(input));
    }

    @ParameterizedTest(name = "Valid account ''{0}'' should return null")
    @ValueSource(strings = {"ACC001", "ACC123", "ACC999"})
    @DisplayName("Should not return error message for valid account number")
    void testValidateAccountNumberValid(String input) {
        assertNull(ValidationUtil.validateAccountNumber(input));
    }

    @ParameterizedTest(name = "Invalid address ''{0}'' should return ''{1}''")
    @CsvSource({
            "'', Address cannot be empty.",
            "a, 'Address must be 5–100 characters and can include letters, numbers, spaces, commas, periods, apostrophes, and hyphens.'",
            "###, 'Address must be 5–100 characters and can include letters, numbers, spaces, commas, periods, apostrophes, and hyphens.'"
    })
    @DisplayName("Should return error message for invalid address")
    void testValidateAddressInvalid(String input, String expectedError) {
        assertEquals(expectedError, ValidationUtil.validateAddress(input));
    }

    @ParameterizedTest(name = "Valid address ''{0}'' should return null")
    @ValueSource(strings = {
            "123 Main Street, Accra",
            "House 10, East Legon",
            "12B Baker Street, London"
    })
    @DisplayName("Should not return error message for valid address")
    void testValidateAddressValid(String input) {
        assertNull(ValidationUtil.validateAddress(input));
    }
}
