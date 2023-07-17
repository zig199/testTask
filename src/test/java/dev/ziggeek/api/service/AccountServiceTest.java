package dev.ziggeek.api.service;

import dev.ziggeek.api.MeshGroupApplicationTests;
import dev.ziggeek.api.model.dto.request.AccountTransferRequest;
import dev.ziggeek.api.service.jobs.AddBalanceJob;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;


@AutoConfigureMockMvc
@ExtendWith(Extension.class)
public class AccountServiceTest extends MeshGroupApplicationTests {


    @MockBean
    private AddBalanceJob addBalanceJob;


    @Autowired
    private AccountService accountService;

    @Test
    void checkFindAccountById() {
        var test = accountService.findAccountBy(1L);
        Assertions.assertNotNull(test);
        Assertions.assertEquals(test.getId(), 1L);
    }


    @Test
    void checkFindAccountByIdWithException() {
        Assertions.assertThrows(RuntimeException.class, () -> accountService.findAccountBy(20L));
    }


    @Test
    void checkAccountNotHaveFunds() {
        var reqt = new AccountTransferRequest();
        reqt.setFromUser(2L);
        reqt.setToUser(1L);
        reqt.setAmount(BigDecimal.valueOf(1000));

        Assertions.assertThrows(RuntimeException.class, () -> accountService.transfer(reqt));
    }


    @Test
    void checkTransferSuccessfully() {
        var reqt = new AccountTransferRequest();
        reqt.setFromUser(1L);
        reqt.setToUser(2L);
        reqt.setAmount(BigDecimal.valueOf(500));

        accountService.transfer(reqt);

        var firstAccount = accountService.findAccountBy(1L);
        var secondAccount = accountService.findAccountBy(2L);

        Assertions.assertEquals(firstAccount.getBalance(), BigDecimal.valueOf(1243));
        Assertions.assertEquals(secondAccount.getBalance(), BigDecimal.valueOf(876));
    }

}
