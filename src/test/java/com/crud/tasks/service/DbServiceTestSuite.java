package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTestSuite {

    @InjectMocks
    DbService dbService;

    @Mock
    TaskRepository taskRepository;

    private final static Long TASK1_ID = 1L;
    private final static String TASK1_TITLE = "task1_title";
    private final static String TASK1_CONTENT = "task1_content";
    private final static Long TASK2_ID = 2L;
    private final static String TASK2_TITLE = "task2_title";
    private final static String TASK2_CONTENT = "task2_content";

    @Test
    public void shouldGetAllTasks() {
        //When
        dbService.getAllTasks();
        //Then
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void shouldGetTask() {
        //when
        dbService.getAllTasks();
        //then
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void shouldSaveTask() {
        //given
        Task task1 = new Task(TASK1_ID, TASK1_TITLE, TASK1_CONTENT);
        Task task2 = new Task(TASK2_ID, TASK2_TITLE, TASK2_CONTENT);
        //when
        dbService.saveTask(task1);
        dbService.saveTask(task2);
        //then
        verify(taskRepository, times(1)).save(task1);
        verify(taskRepository, times(1)).save(task2);
        verify(taskRepository, times(2)).save(any(Task.class));

    }

    @Test
    public void shouldDeleteTask() {
        //when
        dbService.deleteTaskById(anyLong());
        //then
        verify(taskRepository, times(1)).deleteById(anyLong());
    }
}
