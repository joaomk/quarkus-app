package br.com.joaovitor.controller;

import br.com.joaovitor.dto.CreateCardRequestDTO;
import br.com.joaovitor.model.Card;
import br.com.joaovitor.service.CardService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/cards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CardController {

    @Inject
    CardService cardService;

    @POST
    @Transactional
    public Response createPhysicalCard(CreateCardRequestDTO createCardRequestDTO) {
        Card physicalCard = cardService.createPhysicalCard(createCardRequestDTO);
        return Response.status(Response.Status.CREATED).entity(physicalCard).build();
    }

    @POST
    @Path("/virtual")
    @Transactional
    public Response createVirtualCard(CreateCardRequestDTO createCardRequestDTO) {
        Card virtualCard = cardService.createVirtualCard(createCardRequestDTO);
        return Response.status(Response.Status.CREATED).entity(virtualCard).build();
    }

    @GET
    @Path("/account/{accountId}")
    public Response listCardsByAccountId(@PathParam("accountId") Long accountId) {
        List<Card> cards = cardService.listCardsByAccountId(accountId);
        return Response.ok(cards).build();
    }

    @POST
    @Path("/ship/{cardId}")
    @Transactional
    public Response shipCard(@PathParam("cardId") Long cardId) {
        Card card = cardService.shipCard(cardId);
        return Response.ok(card).build();
    }

    @POST
    @Path("/confirm-receipt/{cardId}")
    @Transactional
    public Response confirmCardReceipt(@PathParam("cardId") Long cardId) {
        Card card = cardService.confirmCardReceipt(cardId);
        return Response.ok(card).build();
    }

    @POST
    @Path("/activate/{cardId}")
    @Transactional
    public Response activateCard(@PathParam("cardId") Long cardId) {
        Card card = cardService.activateCard(cardId);
        return Response.ok(card).build();
    }

    @POST
    @Path("/block/{cardId}")
    @Transactional
    public Response blockCard(@PathParam("cardId") Long cardId) {
        Card card = cardService.blockCard(cardId);
        return Response.ok(card).build();
    }

    @POST
    @Path("/unblock/{cardId}")
    @Transactional
    public Response unblockCard(@PathParam("cardId") Long cardId) {
        Card card = cardService.unblockCard(cardId);
        return Response.ok(card).build();
    }

}




