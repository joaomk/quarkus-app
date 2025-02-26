package br.com.joaovitor.controller;

import br.com.joaovitor.dto.CreateAccountRequestDTO;
import br.com.joaovitor.model.Account;
import br.com.joaovitor.service.AccountService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccountSuccessfully() {
        CreateAccountRequestDTO requestDTO = new CreateAccountRequestDTO();
        Account account = new Account();
        when(accountService.createAccount(requestDTO)).thenReturn(account);

        Response response = accountController.createAccount(requestDTO);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(account, response.getEntity());
        verify(accountService, times(1)).createAccount(requestDTO);
    }

    @Test
    void getAccountSuccessfully() {
        String document = "123456789";
        Account account = new Account();
        when(accountService.findByDocument(document)).thenReturn(account);

        Response response = accountController.getAccount(document);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(account, response.getEntity());
        verify(accountService, times(1)).findByDocument(document);
    }

    @Test
    void deactivateAccountSuccessfully() {
        String document = "123456789";
        Account deactivatedAccount = new Account();
        when(accountService.deactivateAccount(document)).thenReturn(deactivatedAccount);

        Response response = accountController.deactivateAccount(document);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(deactivatedAccount, response.getEntity());
        verify(accountService, times(1)).deactivateAccount(document);
    }

    @Test
    void createAccountWithInvalidData() {
        CreateAccountRequestDTO requestDTO = new CreateAccountRequestDTO();
        doThrow(new IllegalArgumentException("Invalid data")).when(accountService).createAccount(requestDTO);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accountController.createAccount(requestDTO);
        });

        assertEquals("Invalid data", exception.getMessage());
        verify(accountService, times(1)).createAccount(requestDTO);
    }
}