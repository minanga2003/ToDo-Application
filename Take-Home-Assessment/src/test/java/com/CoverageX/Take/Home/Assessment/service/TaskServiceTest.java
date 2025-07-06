package com.CoverageX.Take.Home.Assessment.service;


import com.CoverageX.Take.Home.Assessment.dto.TaskDTO;
import com.CoverageX.Take.Home.Assessment.entity.Task;
import com.CoverageX.Take.Home.Assessment.repository.TaskRepository;
import com.CoverageX.Take.Home.Assessment.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepository repo;

    @InjectMocks
    TaskService svc;

    @Test
    void createTask_savesAndReturnsDto() {
        TaskDTO in = new TaskDTO(null, "T1", "D1", false);
        Task saved = new Task();
        saved.setId(42L);
        saved.setTitle("T1");
        saved.setDescription("D1");
        saved.setCreatedAt(LocalDateTime.now());
        saved.setCompleted(false);

        when(repo.save(any(Task.class))).thenReturn(saved);

        TaskDTO out = svc.createTask(in);

        assertEquals(42L, out.getId());
        assertEquals("T1", out.getTitle());
        verify(repo).save(argThat(t -> t.getTitle().equals("T1")));
    }

    @Test
    void getLatestTasks_returnsOnlyIncompleteOrdered() {
        Task t1 = new Task(); t1.setId(1L); t1.setCompleted(false); t1.setCreatedAt(LocalDateTime.now());
        Task t2 = new Task(); t2.setId(2L); t2.setCompleted(false); t2.setCreatedAt(LocalDateTime.now().minusDays(1));
        when(repo.findByCompletedFalseOrderByCreatedAtDesc(PageRequest.of(0,5)))
                .thenReturn(List.of(t1,t2));

        var dtos = svc.getLatestTasks(5);
        assertEquals(2, dtos.size());
        assertEquals(1L, dtos.get(0).getId());
    }

    @Test
    void markComplete_setsFlagIfPresent() {
        Task t = new Task();
        t.setId(99L);
        t.setCompleted(false);
        when(repo.findById(99L)).thenReturn(Optional.of(t));

        svc.markComplete(99L);

        assertTrue(t.isCompleted());
        verify(repo).save(t);
    }
}
