package com.mihneacristian.project_tracker.Services;

import com.mihneacristian.project_tracker.DTO.TypeDTO;
import com.mihneacristian.project_tracker.Entities.Type;
import com.mihneacristian.project_tracker.Repositories.TypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // ✅ replaces openMocks
class TypeServiceTest {

    @InjectMocks
    private TypeService typeService;

    @Mock
    private TypeRepository typeRepository;

    @Test
    void testFindTypeByName_WhenExists() {
        Type type = new Type();
        type.setTypeId(1);
        type.setName("Bug");

        when(typeRepository.findByName("Bug")).thenReturn(Optional.of(type));

        Optional<Type> result = typeService.findTypeByName("Bug");

        assertTrue(result.isPresent());
        assertEquals("Bug", result.get().getName());
        verify(typeRepository, times(1)).findByName("Bug");
    }

    @Test
    void testFindTypeByName_WhenNotExists() {
        when(typeRepository.findByName("Feature")).thenReturn(Optional.empty());

        Optional<Type> result = typeService.findTypeByName("Feature");

        assertFalse(result.isPresent());
        verify(typeRepository, times(1)).findByName("Feature");
    }

    @Test
    void testSaveNewType() {
        Type type = new Type();
        type.setName("Improvement");

        when(typeRepository.save(type)).thenReturn(type);

        typeService.saveNewType(type);

        verify(typeRepository, times(1)).save(type);
    }

    @Test
    void testGetAllTypes() {
        Type type1 = new Type();
        type1.setTypeId(1);
        type1.setName("Bug");

        Type type2 = new Type();
        type2.setTypeId(2);
        type2.setName("Feature");

        List<Type> types = Arrays.asList(type1, type2);
        when(typeRepository.findAll()).thenReturn(types);

        List<TypeDTO> result = typeService.getAllTypes();

        assertEquals(2, result.size());
        assertEquals("Bug", result.get(0).typeName);
        assertEquals("Feature", result.get(1).typeName);
        verify(typeRepository, times(1)).findAll();
    }

    @Test
    void testDeleteTypeById() {
        int id = 1;

        doNothing().when(typeRepository).deleteById(id);

        typeService.deleteTypeById(id);

        verify(typeRepository, times(1)).deleteById(id);
    }
}
