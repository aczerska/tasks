package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TrelloValidatorTestSuite {
    @InjectMocks
    private TrelloValidator trelloValidator;

    @Test
    public void testValidateCardWithTestInName() {
        //Given
        TrelloCard card = new TrelloCard("test", "card_description", "top", "card_listId");
        //When
        trelloValidator.validateCard(card);
        //Then
    }

    @Test
    public void testValidateCardWithoutTestInName() {
        //Given
        TrelloCard card = new TrelloCard("card_name", "card_description", "top", "card_listId");
        //When
        trelloValidator.validateCard(card);
        //Then
    }

    @Test
    public void testValidateTrelloBoards() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        List<TrelloBoard> trelloBoards = Arrays.asList(
                new TrelloBoard("1", "board_name", trelloLists),
                new TrelloBoard("2", "test", trelloLists)
        );
        //When
        List<TrelloBoard> validatedBoards = trelloValidator.validateTrelloBoards(trelloBoards);
        //Then
        assertEquals(1, validatedBoards.size());
        assertEquals("1", validatedBoards.get(0).getId());
        assertEquals("board_name", validatedBoards.get(0).getName());
        assertEquals(0, validatedBoards.get(0).getLists().size());
    }
}
