package com.mihneacristian.project_tracker.Services;

import com.mihneacristian.project_tracker.DTO.ItemDTO;
import com.mihneacristian.project_tracker.Entities.*;
import com.mihneacristian.project_tracker.Repositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private TeamMembersRepository teamMembersRepository;

    @Mock
    private TypeRepository typeRepository;

    @InjectMocks
    private ItemService itemService;

    // ----------------------
    // isItemIdPresent Tests
    // ----------------------
    @Test
    void testIsItemIdPresent_true() {
        when(itemRepository.findById(1)).thenReturn(Optional.of(new Item()));
        assertTrue(itemService.isItemIdPresent(1));
    }

    @Test
    void testIsItemIdPresent_false() {
        when(itemRepository.findById(1)).thenReturn(Optional.empty());
        assertFalse(itemService.isItemIdPresent(1));
    }

    // ----------------------
    // findByItemId Tests
    // ----------------------
    @Test
    void testFindByItemId_found() {
        Item item = new Item();
        when(itemRepository.findByItemId(1)).thenReturn(Optional.of(item));
        assertEquals(item, itemService.findByItemId(1));
    }

    @Test
    void testFindByItemId_notFound() {
        when(itemRepository.findByItemId(1)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> itemService.findByItemId(1));
        assertTrue(exception.getMessage().contains("Could not find a project with the id"));
    }

    // ----------------------
    // getAllItems Test
    // ----------------------
    @Test
    void testGetAllItems() {
        // Mock related entities
        TeamMembers tm = mock(TeamMembers.class);
        when(tm.getMemberId()).thenReturn(1);
        when(tm.getFirstName()).thenReturn("John");
        when(tm.getLastName()).thenReturn("Doe");
        when(tm.getEmailAddress()).thenReturn("john.doe@example.com");

        Status status = mock(Status.class);
        when(status.getStatusName()).thenReturn("Open");

        Type type = mock(Type.class);
        when(type.getName()).thenReturn("Bug");

        // Mock the Item
        Item item = mock(Item.class);
        when(item.getItemId()).thenReturn(1);
        when(item.getTitle()).thenReturn("Test Item");
        when(item.getDescription()).thenReturn("Desc");
        when(item.getTeamMemberOfItem()).thenReturn(tm);
        when(item.getStatusOfItem()).thenReturn(status);
        when(item.getTypeOfItem()).thenReturn(type);

        // Mock repository behavior
        when(itemRepository.findAll()).thenReturn(Collections.singletonList(item));

        // Call the service
        List<ItemDTO> itemsDTO = itemService.getAllItems();

        // Assertions
        assertEquals(1, itemsDTO.size());
        ItemDTO dto = itemsDTO.get(0);
        assertEquals("Test Item", dto.title);
        assertEquals("Desc", dto.description);
        assertEquals("Open", dto.statusOfItem);
        assertEquals("Bug", dto.typeOfItem);
        assertEquals(1, dto.teamMemberId);
        assertEquals("John", dto.teamMemberOfProjectFirstName);
        assertEquals("Doe", dto.teamMemberOfProjectLastName);
        assertEquals("john.doe@example.com", dto.teamMemberOfProjectEmailAddress);
    }

    // ----------------------
    // saveNewItem Test
    // ----------------------
    @Test
    void testSaveNewItem_existingEntities() {
        ItemDTO dto = new ItemDTO();
        dto.itemId = 1;
        dto.title = "New Item";
        dto.description = "Description";
        dto.statusOfItem = "Open";
        dto.typeOfItem = "Bug";
        dto.teamMemberId = 1;

        TeamMembers tm = new TeamMembers(1);
        Status status = new Status("Open");
        Type type = new Type("Bug");

        when(teamMembersRepository.findByMemberId(1)).thenReturn(Optional.of(tm));
        when(statusRepository.findByStatusName("Open")).thenReturn(Optional.of(status));
        when(typeRepository.findByName("Bug")).thenReturn(Optional.of(type));
        when(itemRepository.save(any(Item.class))).thenAnswer(i -> i.getArgument(0));

        Item savedItem = itemService.saveNewItem(dto);
        assertEquals("New Item", savedItem.getTitle());
        assertEquals("Description", savedItem.getDescription());
    }

    // ----------------------
    // updateItemById Test
    // ----------------------
    @Test
    void testUpdateItemById_success() {
        ItemDTO dto = new ItemDTO();
        dto.title = "Updated";
        dto.description = "Updated Desc";
        dto.statusOfItem = "Open";
        dto.typeOfItem = "Bug";
        dto.teamMemberId = 1;

        TeamMembers tm = new TeamMembers(1);
        Status status = new Status("Open");
        Type type = new Type("Bug");
        Item item = new Item();

        when(itemRepository.findById(1)).thenReturn(Optional.of(item));
        when(statusRepository.findByStatusName("Open")).thenReturn(Optional.of(status));
        when(typeRepository.findByName("Bug")).thenReturn(Optional.of(type));
        when(teamMembersRepository.findByMemberId(1)).thenReturn(Optional.of(tm));
        when(itemRepository.save(any(Item.class))).thenAnswer(i -> i.getArgument(0));

        Item updated = itemService.updateItemById(1, dto);
        assertEquals("Updated", updated.getTitle());
        assertEquals("Updated Desc", updated.getDescription());
    }

    @Test
    void testUpdateItemById_notFound() {
        ItemDTO dto = new ItemDTO();
        when(itemRepository.findById(1)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> itemService.updateItemById(1, dto));
        assertTrue(exception.getMessage().contains("Could not find item with the id"));
    }

    // ----------------------
    // deleteItemById Test
    // ----------------------
    @Test
    void testDeleteItemById() {
        doNothing().when(itemRepository).deleteById(1);
        itemService.deleteItemById(1);
        verify(itemRepository, times(1)).deleteById(1);
    }
}
