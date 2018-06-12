package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTestSuite {


    @Autowired
    private TaskMapper taskMapper;

    private final static Long TASK1_ID = 1L;
    private final static String TASK1_TITLE = "task1_title";
    private final static String TASK1_CONTENT = "task1_content";
    private final static Long TASK2_ID = 2L;
    private final static String TASK2_TITLE = "task2_title";
    private final static String TASK2_CONTENT = "task2_content";

    @Test
    public void mapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(TASK1_ID, TASK1_TITLE, TASK1_CONTENT);
        Task task = new Task(TASK1_ID, TASK1_TITLE, TASK1_CONTENT);
        //When
        Task mappedTask = taskMapper.mapToTask(taskDto);
        //Then
        assertEquals(TASK1_ID, mappedTask.getId());
    }

    @Test
    public void mapToTaskDto() {
        //Given
        Task task = new Task(TASK1_ID, TASK1_TITLE, TASK1_CONTENT);
        TaskDto taskDto = new TaskDto(TASK1_ID, TASK1_TITLE, TASK1_CONTENT);
        //When
        TaskDto mappedTaskDto = taskMapper.mapToTaskDto(task);
        //Then
        assertEquals(TASK1_TITLE, mappedTaskDto.getTitle());
    }

    @Test
    public void mapToTaskDtoList() {
        //Given
        Task task1 = new Task(TASK1_ID, TASK1_TITLE, TASK1_CONTENT);
        Task task2 = new Task(TASK2_ID, TASK2_TITLE,TASK2_CONTENT);
        TaskDto task1Dto = new TaskDto(TASK1_ID, TASK1_TITLE, TASK1_CONTENT);
        TaskDto task2Dto = new TaskDto(TASK2_ID, TASK2_TITLE, TASK2_CONTENT);
        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(task1Dto);
        taskDtoList.add(task2Dto);
        //When
        List<TaskDto> mappedTaskDtoList = taskMapper.mapToTaskDtoList(taskList);
        //Then
        assertEquals(TASK1_CONTENT, mappedTaskDtoList.get(0).getContent());
    }

}
