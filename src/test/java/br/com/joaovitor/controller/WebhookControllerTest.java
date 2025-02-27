package br.com.joaovitor.controller;

import br.com.joaovitor.dto.CvvWebhookDTO;
import br.com.joaovitor.dto.DeliveryWebhookDTO;
import br.com.joaovitor.service.WebhookService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

class WebhookResourceTest {

    WebhookController webhookController;
    WebhookService webhookService;

    @BeforeEach
    void setUp() {
        webhookService = Mockito.mock(WebhookService.class);
        webhookController = new WebhookController();
        webhookController.webhookService = webhookService;
    }

    @Test
    void testHandleDeliveryWebhook() {
        doNothing().when(webhookService).processDeliveryWebhook(Mockito.any(DeliveryWebhookDTO.class));

        DeliveryWebhookDTO deliveryWebhookDTO = new DeliveryWebhookDTO();
        Response response = webhookController.processDeliveryWebhook(deliveryWebhookDTO);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testHandleCvvWebhook() {
        doNothing().when(webhookService).processCvvWebhook(Mockito.any(CvvWebhookDTO.class));

        CvvWebhookDTO cvvWebhookDTO = new CvvWebhookDTO();
        Response response = webhookController.processCvvWebhook(cvvWebhookDTO);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}