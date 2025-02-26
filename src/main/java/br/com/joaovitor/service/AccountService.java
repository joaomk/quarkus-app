package br.com.joaovitor.service;

import br.com.joaovitor.dto.CreateAccountRequestDTO;
import br.com.joaovitor.enums.AccountStatus;
import br.com.joaovitor.model.Account;
import br.com.joaovitor.model.Customer;
import br.com.joaovitor.repository.AccountRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class AccountService {

    @Inject
    AccountRepository accountRepository;

    @Inject
    CustomerService customerService;

    public Account createAccount(CreateAccountRequestDTO createAccountRequestDTO) {
        Customer customer = customerService.findByDocument(createAccountRequestDTO.getDocument());

        if (accountRepository.existsByCustomer(customer)) {
            throw new BadRequestException("Customer already has an account");
        }

        Account account = new Account(customer);
        account.setStatus(AccountStatus.ACTIVE);
        accountRepository.persist(account);
        return account;
    }

    public Account findByDocument(String document) {
        if (document == null || !document.matches("\\d{11}")) {
            throw new BadRequestException("Document cannot be null and must have 11 digits");
        }
        return accountRepository.findByDocument(document);
    }

    public Account deactivateAccount(String document) {
       return accountRepository.deactivateAccount(document);
    }

    public Account findById(Long id){
        return accountRepository.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Account not found"));
    }


}
