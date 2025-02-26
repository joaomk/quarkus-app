package br.com.joaovitor.controller;

import br.com.joaovitor.dto.CreateAccountRequestDTO;
import br.com.joaovitor.model.Account;
import br.com.joaovitor.service.AccountService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountController {

    @Inject
    AccountService accountService;
    
    @POST
    @Transactional
    public Response createAccount(@Valid CreateAccountRequestDTO createAccountRequestDTO) {
        Account account = accountService.createAccount(createAccountRequestDTO);
        return Response.status(Response.Status.CREATED).entity(account).build();
    }

    @GET
    @Path("/{document}")
    public Response getAccount(@PathParam("document") String document) {
        Account account = accountService.findByDocument(document);
        return Response.status(Response.Status.OK).entity(account).build();
    }


    @PUT
    @Transactional
    @Path("/deactivate/{document}")
    public Response deactivateAccount(@PathParam("document") String document) {
        Account deactivatedAccount =  accountService.deactivateAccount(document);
        return Response.status(Response.Status.OK).entity(deactivatedAccount).build();

    }
}

