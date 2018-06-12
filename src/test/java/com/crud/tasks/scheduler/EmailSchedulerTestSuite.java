package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailSchedulerTestSuite {

    @InjectMocks
    private EmailScheduler emailScheduler;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AdminConfig adminConfig;


    @Test
    public void sendInformationEmail() {
        //Given
        when(taskRepository.count()).thenReturn(6L);
        when(adminConfig.getAdminMail()).thenReturn("test@test.com");
        //When
        emailScheduler.sendInformationEmail();
        //Then
        verify(taskRepository, times(2)).count();
        verify(adminConfig, times(1)).getAdminMail();
        assertEquals(6L, taskRepository.count());
        assertEquals("test@test.com", adminConfig.getAdminMail());
    }
}
