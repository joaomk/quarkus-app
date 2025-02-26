package br.com.joaovitor.service;

import br.com.joaovitor.dto.CreateAccountRequestDTO;
import br.com.joaovitor.model.Account;
import br.com.joaovitor.model.Customer;
import br.com.joaovitor.repository.AccountRepository;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccountSuccessfully() {
        CreateAccountRequestDTO requestDTO = new CreateAccountRequestDTO();
        requestDTO.setDocument("12345678901");

        Customer customer = new Customer();
        when(customerService.findByDocument(requestDTO.getDocument())).thenReturn(customer);

        Account account = new Account(customer);
        doNothing().when(accountRepository).persist(any(Account.class));

        Account createdAccount = accountService.createAccount(requestDTO);

        assertNotNull(createdAccount);
        verify(accountRepository, times(1)).persist(any(Account.class));
    }

    @Test
    void findByDocumentSuccessfully() {
        String document = "12345678901";
        Account account = new Account();
        when(accountRepository.findByDocument(document)).thenReturn(account);

        Account foundAccount = accountService.findByDocument(document);

        assertEquals(account, foundAccount);
    }

    @Test
    void findByDocumentThrowsBadRequestException() {
        String document = "invalid_document";

        assertThrows(BadRequestException.class, () -> {
            accountService.findByDocument(document);
        });
    }

    @Test
    void deactivateAccountSuccessfully() {
        String document = "12345678901";
        Account account = new Account();
        when(accountRepository.deactivateAccount(document)).thenReturn(account);

        Account deactivatedAccount = accountService.deactivateAccount(document);

        assertEquals(account, deactivatedAccount);
    }

    @Test
    void findByIdThrowsNotFoundException() {
        Long id = 1L;
        when(accountRepository.findByIdOptional(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            accountService.findById(id);
        });
    }
}