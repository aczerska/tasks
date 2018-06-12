package com.crud.tasks.trello.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTestSuite {
    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    public void shouldFetchEmptyList() {
        //Given
        when(trelloClient.getTrelloBoards()).thenReturn(new ArrayList<>());
        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloService.fetchTrelloBoards();
        //Then
        Assert.assertNotNull(trelloBoardDtos);
        Assert.assertEquals(0, trelloBoardDtos.size());
    }

    @Test
    public void shouldFetchTrelloBoards() {
        //Given
        List<TrelloListDto> list = new ArrayList<>();

        List<TrelloBoardDto> trelloBoardDtoList = Arrays.asList(
                new TrelloBoardDto("1", "board_name1", list),
                new TrelloBoardDto("2", "board_name2", list),
                new TrelloBoardDto("3", "board_name3", list)
        );

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtoList);
        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloService.fetchTrelloBoards();
        //Then
        Assert.assertEquals(3, trelloBoardDtos.size());
    }

    @Test
    public void testCreateTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("card_name", "card_description", "top", "card_listId");
        CreatedTrelloCardDto newCard = new CreatedTrelloCardDto("1", "card_name", "http://test.com");
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(newCard);
        //When
        CreatedTrelloCardDto createdTrelloCardDto = trelloService.createTrelloCard(trelloCardDto);
        //Then
        Assert.assertEquals("1", createdTrelloCardDto.getId());
        Assert.assertEquals(trelloCardDto.getName(), createdTrelloCardDto.getName());
        Assert.assertEquals("http://test.com", createdTrelloCardDto.getShortUrl());
    }
}
