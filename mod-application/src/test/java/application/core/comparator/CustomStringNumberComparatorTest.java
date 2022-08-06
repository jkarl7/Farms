package application.core.comparator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomStringNumberComparatorTest {

    @DisplayName("GIVEN two string inputs WHEN we compare them THEN correct result should be given")
    @Test
    void compareTwoInputStringTest() {
        var comparator = new CustomStringNumberComparator();
        assertEquals(-1, comparator.compare("26", "27"));
        assertEquals(1, comparator.compare("26-1", "26")); // 26-1 is bigger than 26
        assertEquals(-1, comparator.compare("1-1", "14-1"));
        assertEquals(1, comparator.compare("12", "7"));
        assertEquals(0, comparator.compare("7", "7"));
        assertEquals(-1, comparator.compare("14-1", "14-2"));
    }
}
