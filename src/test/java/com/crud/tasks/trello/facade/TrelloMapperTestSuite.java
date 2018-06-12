package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperSuitTest {

    @Autowired
    private TrelloMapper mapper;

    private final static String BOARD_ID = "boardId";
    private final static String LIST_ID = "listId";
    private final static String BOARD_NAME = "boardName";
    private final static String LIST_NAME = "listName";
    private final static String CARD_NAME = "cardName";
    private final static String CARD_DESCRIPTION = "cardDescription";
    private final static String CARD_POS = "cardPosition";
    private final static String CARD_LISTID = "cardListId";

    private static List<TrelloListDto> createListsDto() {
        List<TrelloListDto> lists = new ArrayList<>();
        TrelloListDto trelloListDto = new TrelloListDto(LIST_ID,LIST_NAME,false);
        lists.add(trelloListDto);
        return lists;
    }

    private static List<TrelloList> createLists() {
        List<TrelloList> lists = new ArrayList<>();
        TrelloList trelloList = new TrelloList(LIST_ID,LIST_NAME,false);
        lists.add(trelloList);
        return lists;
    }

    private static List<TrelloBoardDto> createBoardsDto() {
        List<TrelloBoardDto> boards = new ArrayList<>();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto(BOARD_ID, BOARD_NAME,createListsDto());
        boards.add(trelloBoardDto);
        return boards;
    }

    private static List<TrelloBoard> createBoards() {
        List<TrelloBoard> boards = new ArrayList<>();
        TrelloBoard trelloBoard = new TrelloBoard(BOARD_ID, BOARD_NAME,createLists());
        boards.add(trelloBoard);
        return boards;
    }

    @Test
    public void testMapToBoard() {
        //Given
        createListsDto();
        //When
        List<TrelloBoard> boardsDo = mapper.mapToBoards(createBoardsDto());
        //Then
        assertEquals(BOARD_ID, boardsDo.get(0).getId());
        assertEquals(BOARD_NAME, boardsDo.get(0).getName());
        assertEquals(LIST_ID, boardsDo.get(0).getLists().get(0).getId());
    }

    @Test
    public void testMapToBoardDto() {
        //Given
        createLists();
        //When
        List<TrelloBoardDto> boardsDto = mapper.mapToBoardsDto(createBoards());
        //Then
        assertEquals(BOARD_NAME, boardsDto.get(0).getName());
        assertEquals(LIST_NAME, boardsDto.get(0).getLists().get(0).getName());
        assertEquals(LIST_ID, boardsDto.get(0).getLists().get(0).getId());
    }

    @Test
    public void testMapToList() {
        //Given
        //When
        List<TrelloList> listDo = mapper.mapToList(createListsDto());
        //Then
        assertTrue(listDo.get(0).isClosed() == false);
        assertEquals(LIST_NAME, listDo.get(0).getName());
        assertEquals(LIST_ID, listDo.get(0).getId());
    }

    @Test
    public void testMapToListDto() {
        //Given
        //When
        List<TrelloListDto> listDto = mapper.mapToListDto(createLists());
        //Then
        assertTrue(listDto.get(0).isClosed() == false);
        assertEquals(LIST_NAME, listDto.get(0).getName());
        assertEquals(LIST_ID, listDto.get(0).getId());
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(CARD_NAME,CARD_DESCRIPTION,CARD_POS,CARD_LISTID);
        //When
        TrelloCard trelloCard = mapper.mapToCard(trelloCardDto);
        //Then
        assertEquals(CARD_NAME, trelloCard.getName());
        assertEquals(CARD_LISTID, trelloCard.getListId());
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard(CARD_NAME,CARD_DESCRIPTION,CARD_POS,CARD_LISTID);
        //When
        TrelloCardDto trelloCardDto = mapper.mapToCardDto(trelloCard);
        //Then
        assertEquals(CARD_NAME, trelloCardDto.getName());
        assertEquals(CARD_LISTID, trelloCardDto.getListId());
    }
}
