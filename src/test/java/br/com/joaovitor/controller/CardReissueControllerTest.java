package br.com.joaovitor.controller;

import br.com.joaovitor.dto.CardReissueRequestDTO;
import br.com.joaovitor.model.CardReissue;
import br.com.joaovitor.service.CardReissueService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CardReissueControllerTest {

    @InjectMocks
    private CardReissueController cardReissueController;

    @Mock
    private CardReissueService cardReissueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void requestReissueSuccessfully() {
        CardReissueRequestDTO requestDTO = new CardReissueRequestDTO();
        requestDTO.setCardId(1L);
        requestDTO.setReason("Lost card");

        CardReissue cardReissue = new CardReissue();
        when(cardReissueService.requestReissue(requestDTO.getCardId(), requestDTO.getReason())).thenReturn(cardReissue);

        Response response = cardReissueController.requestReissue(requestDTO);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(cardReissue, response.getEntity());
        verify(cardReissueService, times(1)).requestReissue(requestDTO.getCardId(), requestDTO.getReason());
    }
}