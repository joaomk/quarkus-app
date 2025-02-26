package br.com.joaovitor.controller;

import br.com.joaovitor.dto.CreateConsumerRequestDTO;
import br.com.joaovitor.model.Customer;
import br.com.joaovitor.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerController {

    @Inject
    CustomerService customerService;

    @POST
    @Transactional
    public Response createCustomer(@Valid CreateConsumerRequestDTO createConsumerRequestDTO) {
        Customer customer = customerService.createCustomer(createConsumerRequestDTO);
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }
}