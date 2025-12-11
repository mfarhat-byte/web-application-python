package com.mihneacristian.project_tracker.Services;

import com.mihneacristian.project_tracker.DTO.StatusDTO;
import com.mihneacristian.project_tracker.Entities.Status;
import com.mihneacristian.project_tracker.Repositories.StatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatusServiceTest {

    @Mock
    private StatusRepository statusRepository;

    @InjectMocks
    private StatusService statusService;

    private Status status1;
    private Status status2;

    @BeforeEach
    void setUp() {
        status1 = new Status();
        status1.setStatusId(1);
        status1.setStatusName("Open");

        status2 = new Status();
        status2.setStatusId(2);
        status2.setStatusName("Closed");
    }

    @Test
    void testGetStatusByName_Found() {
        when(statusRepository.findByStatusName("Open")).thenReturn(Optional.of(status1));

        Status result = statusService.getStatusByName("Open");

        assertNotNull(result);
        assertEquals("Open", result.getStatusName());
        verify(statusRepository, times(1)).findByStatusName("Open");
    }

    @Test
    void testGetStatusByName_NotFound() {
        when(statusRepository.findByStatusName("InProgress")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                statusService.getStatusByName("InProgress"));

        assertEquals("Could not find Status with name: InProgress", exception.getMessage());
        verify(statusRepository, times(1)).findByStatusName("InProgress");
    }

    @Test
    void testGetAllStatus() {
        when(statusRepository.findAll()).thenReturn(Arrays.asList(status1, status2));

        List<StatusDTO> result = statusService.getAllStatus();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Open", result.get(0).statusName);
        assertEquals("Closed", result.get(1).statusName);

        verify(statusRepository, times(1)).findAll();
    }

    @Test
    void testSaveNewStatus() {
        when(statusRepository.save(status1)).thenReturn(status1);

        statusService.saveNewStatus(status1);

        verify(statusRepository, times(1)).save(status1);
    }

    @Test
    void testDeleteStatusById() {
        doNothing().when(statusRepository).deleteById(1);

        statusService.deleteStatusById(1);

        verify(statusRepository, times(1)).deleteById(1);
    }
}
