package jmlcore;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimpleItemDTOTest {

    @Test
    void testConstructorSetsTitle() {
        // Arrange
        String title = "Test Title";
        String description = "Test Description";

        // Act
        SimpleItemDTO dto = new SimpleItemDTO(title, description);

        // Assert
        assertEquals(title, dto.title);
    }

    @Test
    void testConstructorSetsDescription() {
        // Arrange
        String title = "Test Title";
        String description = "Test Description";

        // Act
        SimpleItemDTO dto = new SimpleItemDTO(title, description);

        // Assert
        assertEquals(description, dto.description);
    }

    @Test
    void testConstructorWithEmptyDescription() {
        // Arrange
        String title = "Test Title";
        String description = "";

        // Act
        SimpleItemDTO dto = new SimpleItemDTO(title, description);

        // Assert
        assertEquals(title, dto.title);
        assertEquals(description, dto.description);
    }

    @Test
    void testConstructorWithSingleCharacterTitle() {
        // Arrange
        String title = "T";
        String description = "Description";

        // Act
        SimpleItemDTO dto = new SimpleItemDTO(title, description);

        // Assert
        assertEquals(title, dto.title);
        assertEquals(description, dto.description);
    }

    @Test
    void testConstructorWithLongStrings() {
        // Arrange
        String title = "This is a very long title that exceeds normal length";
        String description = "This is a very long description with lots of details";

        // Act
        SimpleItemDTO dto = new SimpleItemDTO(title, description);

        // Assert
        assertEquals(title, dto.title);
        assertEquals(description, dto.description);
    }

    @Test
    void testConstructorWithSpecialCharacters() {
        // Arrange
        String title = "Title @#$%";
        String description = "Desc with ñ é ü";

        // Act
        SimpleItemDTO dto = new SimpleItemDTO(title, description);

        // Assert
        assertEquals(title, dto.title);
        assertEquals(description, dto.description);
    }
}