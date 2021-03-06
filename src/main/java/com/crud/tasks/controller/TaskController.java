package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/task")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private DbService dbService;

    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "getTasks"
    )
    public List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(dbService.getAllTasks());
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "getTask"
    )
    public TaskDto getTask(@RequestParam Long taskId) throws TaskNotFoundException {
        return taskMapper.mapToTaskDto(dbService.getTaskById(taskId).orElseThrow(TaskNotFoundException::new));
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "deleteTask"
    )
        public void deleteTask(@RequestParam Long taskId) throws TaskNotFoundException {
            dbService.deleteTaskById(taskId).orElseThrow(TaskNotFoundException::new);

    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "updateTask"
    )
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        return taskMapper.mapToTaskDto(dbService.saveTask(taskMapper.mapToTask(taskDto)));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "createTask",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void createTask(@RequestBody TaskDto taskDto) {
        dbService.saveTask(taskMapper.mapToTask(taskDto));

    }
}
