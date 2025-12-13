package jmlcore;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimpleItemTest {

    @Test
    void testConstructorCreatesItemWithAllFields() {
        // Arrange
        String title = "Test Title";
        String description = "Test Description";
        String status = "Open";
        String type = "Bug";

        // Act
        SimpleItem item = new SimpleItem(title, description, status, type);

        // Assert
        assertEquals(title, item.title);
        assertEquals(description, item.description);
        assertEquals(status, item.status);
        assertEquals(type, item.type);
    }

    @Test
    void testConstructorWithEmptyDescription() {
        // Arrange
        String title = "Test Title";
        String description = "";
        String status = "Open";
        String type = "Task";

        // Act
        SimpleItem item = new SimpleItem(title, description, status, type);

        // Assert
        assertEquals(title, item.title);
        assertEquals(description, item.description);
        assertEquals(status, item.status);
        assertEquals(type, item.type);
    }

    @Test
    void testStrEqWithEqualStrings() {
        // Arrange
        String s1 = "test";
        String s2 = "test";

        // Act
        boolean result = SimpleItem.strEq(s1, s2);

        // Assert
        assertTrue(result);
    }

    @Test
    void testStrEqWithDifferentStrings() {
        // Arrange
        String s1 = "test1";
        String s2 = "test2";

        // Act
        boolean result = SimpleItem.strEq(s1, s2);

        // Assert
        assertFalse(result);
    }

    @Test
    void testStrEqWithDifferentCase() {
        // Arrange
        String s1 = "Test";
        String s2 = "test";

        // Act
        boolean result = SimpleItem.strEq(s1, s2);

        // Assert
        assertFalse(result);
    }

    @Test
    void testStrEqWithEmptyStrings() {
        // Arrange
        String s1 = "";
        String s2 = "";

        // Act
        boolean result = SimpleItem.strEq(s1, s2);

        // Assert
        assertTrue(result);
    }

    @Test
    void testConstructorWithLongTitle() {
        // Arrange
        String title = "This is a very long title that contains many characters";
        String description = "Description";
        String status = "In Progress";
        String type = "Feature";

        // Act
        SimpleItem item = new SimpleItem(title, description, status, type);

        // Assert
        assertEquals(title, item.title);
    }

    @Test
    void testConstructorWithSpecialCharacters() {
        // Arrange
        String title = "Title with @#$%";
        String description = "Description with ñ and é";
        String status = "Done";
        String type = "Bug";

        // Act
        SimpleItem item = new SimpleItem(title, description, status, type);

        // Assert
        assertEquals(title, item.title);
        assertEquals(description, item.description);
    }
}