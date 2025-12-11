package com.mihneacristian.project_tracker.Services;

import com.mihneacristian.project_tracker.DTO.ProjectDTO;
import com.mihneacristian.project_tracker.Entities.Project;
import com.mihneacristian.project_tracker.Entities.Status;
import com.mihneacristian.project_tracker.Entities.TeamMembers;
import com.mihneacristian.project_tracker.EntityConverter.ProjectEntityConverter;
import com.mihneacristian.project_tracker.Repositories.ProjectRepository;
import com.mihneacristian.project_tracker.Repositories.StatusRepository;
import com.mihneacristian.project_tracker.Repositories.TeamMembersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private TeamMembersRepository teamMembersRepository;

    @Mock
    private ProjectEntityConverter projectEntityConverter;

    @InjectMocks
    private ProjectService projectService;

    private Project project;
    private Status status;
    private TeamMembers teamMembers;
    private ProjectDTO projectDTO;

    @BeforeEach
    public void setUp() {
        status = new Status("InProgress");
        teamMembers = new TeamMembers(1);
        project = new Project();
        project.setProjectId(1);
        project.setName("Test Project");
        project.setDescription("Test Description");
        project.setStatusOfProject(status);
        project.setTeamMemberOfProject(teamMembers);

        projectDTO = new ProjectDTO();
        projectDTO.projectId = 1;
        projectDTO.projectName = "Test Project";
        projectDTO.description = "Test Description";
        projectDTO.statusName = "InProgress";
        projectDTO.teamMemberId = 1;
    }

    @Test
    public void testIsProjectIdPresent() {
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        Boolean result = projectService.isProjectIdPresent(1);
        assertTrue(result);
    }

    @Test
    public void testGetProjectById() {
        when(projectRepository.findProjectByProjectId(1)).thenReturn(Optional.of(project));
        Project result = projectService.getProjectById(1);
        assertEquals("Test Project", result.getName());
    }

    @Test
    public void testGetProjectByIdNotFound() {
        when(projectRepository.findProjectByProjectId(2)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> projectService.getProjectById(2));
        assertTrue(exception.getMessage().contains("Could not find a project with the id"));
    }

    @Test
    public void testFindByName() {
        List<Project> projects = Collections.singletonList(project);
        when(projectRepository.findByName("Test Project")).thenReturn(projects);
        List<Project> result = projectService.findByName("Test Project");
        assertEquals(1, result.size());
    }

    @Test
    public void testSaveProject_NewStatusAndMember() {
        when(statusRepository.findByStatusName("InProgress")).thenReturn(Optional.empty());
        when(teamMembersRepository.findByMemberId(1)).thenReturn(Optional.empty());
        when(teamMembersRepository.save(any(TeamMembers.class))).thenReturn(teamMembers);
        when(statusRepository.save(any(Status.class))).thenReturn(status);
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project result = projectService.saveProject(projectDTO);
        assertEquals("Test Project", result.getName());
    }

    @Test
    public void testGetAllProjects() {
        List<Project> projects = Collections.singletonList(project);
        when(projectRepository.findAll()).thenReturn(projects);

        List<ProjectDTO> result = projectService.getAllProjects();
        assertEquals(1, result.size());
        assertEquals("Test Project", result.get(0).projectName);
    }

    @Test
    public void testUpdateProjectById() {
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        when(statusRepository.findByStatusName("InProgress")).thenReturn(Optional.of(status));
        when(teamMembersRepository.findByMemberId(1)).thenReturn(Optional.of(teamMembers));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project result = projectService.updateProjectById(1, projectDTO);
        assertEquals("Test Project", result.getName());
    }

    @Test
    public void testUpdateProjectById_NotFound() {
        when(projectRepository.findById(2)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> projectService.updateProjectById(2, projectDTO));
        assertTrue(exception.getMessage().contains("Could not find project with the id"));
    }

    @Test
    public void testDeleteProjectById() {
        doNothing().when(projectRepository).deleteById(1);
        projectService.deleteProjectById(1);
        verify(projectRepository, times(1)).deleteById(1);
    }
}
