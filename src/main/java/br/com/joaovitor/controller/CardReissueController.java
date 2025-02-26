package br.com.joaovitor.controller;

import br.com.joaovitor.dto.CardReissueRequestDTO;
import br.com.joaovitor.model.CardReissue;
import br.com.joaovitor.service.CardReissueService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/card-reissue")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CardReissueController {

    @Inject
    CardReissueService cardReissueService;

    @POST
    @Transactional
    public Response requestReissue(@Valid CardReissueRequestDTO requestDTO) {
        CardReissue reissueRequest = cardReissueService.requestReissue(requestDTO.getCardId(), requestDTO.getReason());
        return Response.status(Response.Status.CREATED).entity(reissueRequest).build();
    }
}