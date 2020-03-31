package it.sevenbits.spring_boot.task_manager.web.controllers;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.core.repository.DatabaseTaskRepository;
import it.sevenbits.spring_boot.task_manager.web.model.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.UpdateTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.service.DataBaseService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskControllerTest {
    private DataBaseService mockDataBaseService;
    private TasksController tasksController;
    private DatabaseTaskRepository mockTaskRepository;

    @Before
    public void init() {
        mockTaskRepository = mock(DatabaseTaskRepository.class);
        mockDataBaseService = mock(DataBaseService.class);
        tasksController = new TasksController(mockDataBaseService);
    }


    @Test
    public void testListTasks() {
        String filter = "done";
        List<Task> mockTask = mock(List.class);
        when(mockDataBaseService.getAllTasks(filter)).thenReturn(mockTask);

        ResponseEntity<List<Task>> answer = tasksController.listTasks(filter);
        verify(mockDataBaseService, times(1)).getAllTasks(filter);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertSame(mockTask, answer.getBody());
    }


    @Test
    public void testCreateTask() {
        AddTaskRequest addTaskRequest = new AddTaskRequest("do homework");
        Task task = mock(Task.class);
        when(mockDataBaseService.createTask(addTaskRequest)).thenReturn(task);

        ResponseEntity<Task> answer = tasksController.create(addTaskRequest);
        verify(mockDataBaseService, times(1)).createTask(addTaskRequest);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertEquals(task, answer.getBody());
    }


    @Test
    public void testGetTask() {
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);
        when(mockDataBaseService.getTask(id)).thenReturn(task);

        ResponseEntity<Task> answer = tasksController.taskForId(id);
        verify(mockDataBaseService, times(1)).getTask(id);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertEquals(task, answer.getBody());
    }


    @Test
    public void testUpdateTask() {
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);
        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest("do homework", "done");
        when(mockDataBaseService.updateTask(id, updateTaskRequest)).thenReturn(task);
        doAnswer(invocationOnMock -> {
            Task argument = invocationOnMock.getArgument(0);
            assertEquals(task, argument);
            return argument;
        }).when(mockTaskRepository).updateTask(id, updateTaskRequest);
        ResponseEntity<Void> answer = tasksController.changeStatus(id, updateTaskRequest);
        verify(mockDataBaseService, times(1)).updateTask(id, updateTaskRequest);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertNull(answer.getBody());
    }


    @Test
    public void testDeleteTask() {
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);
        when(mockDataBaseService.deleteTask(id)).thenReturn(task);
        doAnswer(invocationOnMock -> {
            Task argument = invocationOnMock.getArgument(0);
            assertEquals(task, argument);
            return argument;
        }).when(mockTaskRepository).deleteTask(id);
        ResponseEntity<Void> answer = tasksController.deleteTask(id);
        verify(mockDataBaseService, times(1)).deleteTask(id);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertNull(answer.getBody());
    }

}
