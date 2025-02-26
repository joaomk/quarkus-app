package br.com.joaovitor.service;

import br.com.joaovitor.dto.CreateConsumerRequestDTO;
import br.com.joaovitor.model.Address;
import br.com.joaovitor.model.Customer;
import br.com.joaovitor.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.Optional;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    public Customer findByDocument(String document) {
        Optional<Customer> optionalCustomer = customerRepository.findByDocument(document);
        return optionalCustomer.orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    public Customer createCustomer(CreateConsumerRequestDTO createConsumerRequestDTO) {

        if (customerRepository.findByDocument(createConsumerRequestDTO.getDocument()).isPresent()) {
            throw new BadRequestException("Customer with this document already exists");
        }

        Address address = new Address(createConsumerRequestDTO.getAddress().getStreet(), createConsumerRequestDTO.getAddress().getCity(), createConsumerRequestDTO.getAddress().getState(), createConsumerRequestDTO.getAddress().getZipCode(), createConsumerRequestDTO.getAddress().getCountry());

        Customer customer = new Customer(createConsumerRequestDTO.getName(), createConsumerRequestDTO.getDocument(), createConsumerRequestDTO.getPhone(), createConsumerRequestDTO.getEmail(), address);

        customerRepository.persist(customer);

        return customer;
    }
}
