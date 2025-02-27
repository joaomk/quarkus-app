package br.com.joaovitor.controller;

import br.com.joaovitor.dto.CvvWebhookDTO;
import br.com.joaovitor.dto.DeliveryWebhookDTO;
import br.com.joaovitor.service.WebhookService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("/webhooks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WebhookController {

    @Inject
    WebhookService webhookService;

    @POST
    @Path("/delivery")
    @Operation(summary = "Processa a entrega do cartão", description = "Recebe o webhook da transportadora com o status de entrega do cartão.")
    @Transactional
    public Response processDeliveryWebhook(DeliveryWebhookDTO deliveryWebhookDTO) {
        webhookService.processDeliveryWebhook(deliveryWebhookDTO);
        return Response.ok("Delivery webhook processed").build();
    }

    @POST
    @Path("/cvv")
    @Operation(summary = "Atualiza o CVV do cartão virtual", description = "Recebe o webhook da processadora de cartões com o novo CVV e a data de expiração do cartão virtual.")
    @Transactional
    public Response processCvvWebhook(CvvWebhookDTO cvvWebhookDTO) {
        webhookService.processCvvWebhook(cvvWebhookDTO);
        return Response.ok("CVV webhook processed").build();
    }
}
