package com.mihneacristian.project_tracker.Services;

import com.mihneacristian.project_tracker.DTO.TeamMembersDTO;
import com.mihneacristian.project_tracker.Entities.TeamMembers;
import com.mihneacristian.project_tracker.Repositories.TeamMembersRepository;
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
public class TeamMembersServiceTest {

    @Mock
    private TeamMembersRepository teamMembersRepository;

    @InjectMocks
    private TeamMembersService teamMembersService;

    private TeamMembers member1;
    private TeamMembers member2;
    private TeamMembersDTO memberDTO1;

    @BeforeEach
    void setUp() {
        member1 = new TeamMembers();
        member1.setMemberId(1);
        member1.setFirstName("John");
        member1.setLastName("Doe");
        member1.setEmailAddress("john.doe@example.com");

        member2 = new TeamMembers();
        member2.setMemberId(2);
        member2.setFirstName("Jane");
        member2.setLastName("Smith");
        member2.setEmailAddress("jane.smith@example.com");

        memberDTO1 = new TeamMembersDTO();
        memberDTO1.teamMemberid = 3;
        memberDTO1.teamMemberFirstName = "Alice";
        memberDTO1.teamMemberLastName = "Johnson";
        memberDTO1.teamMemberEmailAddress = "alice.johnson@example.com";
    }

    @Test
    void testGetMemberById_Found() {
        when(teamMembersRepository.findByMemberId(1)).thenReturn(Optional.of(member1));

        TeamMembers result = teamMembersService.getMemberById(1);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(teamMembersRepository, times(1)).findByMemberId(1);
    }

    @Test
    void testGetMemberById_NotFound() {
        when(teamMembersRepository.findByMemberId(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                teamMembersService.getMemberById(99));

        assertEquals("Could not find a member with the id: 99", exception.getMessage());
        verify(teamMembersRepository, times(1)).findByMemberId(99);
    }

    @Test
    void testFindByLastName() {
        when(teamMembersRepository.findByLastName("Doe")).thenReturn(member1);

        TeamMembers result = teamMembersService.findByLastName("Doe");

        assertNotNull(result);
        assertEquals("Doe", result.getLastName());
    }

    @Test
    void testFindByFirstName() {
        when(teamMembersRepository.findByFirstName("Jane")).thenReturn(member2);

        TeamMembers result = teamMembersService.findByFirstName("Jane");

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
    }

    @Test
    void testFindByEmailAdddress() {
        when(teamMembersRepository.findByEmailAddress("john.doe@example.com")).thenReturn(member1);

        TeamMembers result = teamMembersService.findByEmailAdddress("john.doe@example.com");

        assertNotNull(result);
        assertEquals("john.doe@example.com", result.getEmailAddress());
    }

    @Test
    void testSaveTeamMember() {
        TeamMembers memberToSave = new TeamMembers(memberDTO1);
        when(teamMembersRepository.save(any(TeamMembers.class))).thenReturn(memberToSave);

        TeamMembers result = teamMembersService.saveTeamMember(memberDTO1);

        assertNotNull(result);
        assertEquals("Alice", result.getFirstName());
        verify(teamMembersRepository, times(1)).save(any(TeamMembers.class));
    }

    @Test
    void testDeleteTeamMemberById() {
        doNothing().when(teamMembersRepository).deleteById(1);

        teamMembersService.deleteTeamMemberById(1);

        verify(teamMembersRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetAllMembers() {
        when(teamMembersRepository.findAll()).thenReturn(Arrays.asList(member1, member2));

        List<TeamMembersDTO> result = teamMembersService.getAllMembers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).teamMemberFirstName);
        assertEquals("Jane", result.get(1).teamMemberFirstName);
    }

    @Test
    void testUpdateMemberById_Found() {
        TeamMembersDTO updatedDTO = new TeamMembersDTO();
        updatedDTO.teamMemberFirstName = "Updated";
        updatedDTO.teamMemberLastName = "User";
        updatedDTO.teamMemberEmailAddress = "updated@example.com";

        when(teamMembersRepository.findById(1)).thenReturn(Optional.of(member1));
        when(teamMembersRepository.save(any(TeamMembers.class))).thenReturn(member1);

        TeamMembers result = teamMembersService.updateMemberById(1, updatedDTO);

        assertEquals("Updated", result.getFirstName());
        assertEquals("User", result.getLastName());
        assertEquals("updated@example.com", result.getEmailAddress());
        verify(teamMembersRepository, times(1)).findById(1);
        verify(teamMembersRepository, times(1)).save(member1);
    }

    @Test
    void testUpdateMemberById_NotFound() {
        TeamMembersDTO updatedDTO = new TeamMembersDTO();
        when(teamMembersRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                teamMembersService.updateMemberById(99, updatedDTO));

        assertEquals("Could not find member with the id: 99", exception.getMessage());
    }
}
