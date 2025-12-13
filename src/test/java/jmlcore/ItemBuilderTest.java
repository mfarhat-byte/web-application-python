package jmlcore;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemBuilderTest {

    @Test
    void testBuildCreatesSimpleItemFromDTO() {
        // Arrange
        SimpleItemDTO dto = new SimpleItemDTO("Test Title", "Test Description");
        String status = "Open";
        String type = "Bug";

        // Act
        SimpleItem result = ItemBuilder.build(dto, status, type);

        // Assert
        assertNotNull(result);
        assertEquals(dto.title, result.title);
        assertEquals(dto.description, result.description);
        assertEquals(status, result.status);
        assertEquals(type, result.type);
    }

    @Test
    void testBuildWithDifferentStatus() {
        // Arrange
        SimpleItemDTO dto = new SimpleItemDTO("Title", "Description");
        String status = "In Progress";
        String type = "Task";

        // Act
        SimpleItem result = ItemBuilder.build(dto, status, type);

        // Assert
        assertEquals("In Progress", result.status);
        assertEquals("Task", result.type);
    }

    @Test
    void testBuildWithEmptyDescription() {
        // Arrange
        SimpleItemDTO dto = new SimpleItemDTO("Title", "");
        String status = "Done";
        String type = "Feature";

        // Act
        SimpleItem result = ItemBuilder.build(dto, status, type);

        // Assert
        assertEquals("", result.description);
    }

    @Test
    void testBuildPreservesTitleFromDTO() {
        // Arrange
        String originalTitle = "Original Title";
        SimpleItemDTO dto = new SimpleItemDTO(originalTitle, "Description");
        String status = "Open";
        String type = "Bug";

        // Act
        SimpleItem result = ItemBuilder.build(dto, status, type);

        // Assert
        assertEquals(originalTitle, result.title);
    }

    @Test
    void testBuildWithLongStrings() {
        // Arrange
        String longTitle = "This is a very long title with many words";
        String longDescription = "This is a very long description with many details";
        SimpleItemDTO dto = new SimpleItemDTO(longTitle, longDescription);
        String status = "Blocked";
        String type = "Epic";

        // Act
        SimpleItem result = ItemBuilder.build(dto, status, type);

        // Assert
        assertEquals(longTitle, result.title);
        assertEquals(longDescription, result.description);
        assertEquals("Blocked", result.status);
        assertEquals("Epic", result.type);
    }

    @Test
    void testBuildWithSpecialCharacters() {
        // Arrange
        SimpleItemDTO dto = new SimpleItemDTO("Title @#$%", "Desc with ñ");
        String status = "Review";
        String type = "Story";

        // Act
        SimpleItem result = ItemBuilder.build(dto, status, type);

        // Assert
        assertEquals("Title @#$%", result.title);
        assertEquals("Desc with ñ", result.description);
    }

    @Test
    void testBuildReturnsDifferentInstance() {
        // Arrange
        SimpleItemDTO dto = new SimpleItemDTO("Title", "Description");
        String status = "Open";
        String type = "Bug";

        // Act
        SimpleItem result1 = ItemBuilder.build(dto, status, type);
        SimpleItem result2 = ItemBuilder.build(dto, status, type);

        // Assert
        assertNotSame(result1, result2, "Each build should return a new instance");
        assertEquals(result1.title, result2.title);
    }

    @Test
    void testBuildWithSingleCharacterTitle() {
        // Arrange
        SimpleItemDTO dto = new SimpleItemDTO("T", "Description");
        String status = "New";
        String type = "Task";

        // Act
        SimpleItem result = ItemBuilder.build(dto, status, type);

        // Assert
        assertEquals("T", result.title);
    }
}