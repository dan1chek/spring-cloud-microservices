package com.javastart.depositservice.service;

import com.javastart.deposit.repository.DepositRepository;
import com.javastart.deposit.rest.AccountResponseDTO;
import com.javastart.deposit.rest.AccountServiceClient;
import com.javastart.deposit.rest.BillResponseDTO;
import com.javastart.deposit.rest.BillServiceClient;
import com.javastart.deposit.service.DepositService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.testng.asserts.Assertion;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class DepositServiceTest {

    @Mock
    private DepositRepository depositRepository;

    @Mock
    private AccountServiceClient accountServiceClient;

    @Mock
    private BillServiceClient billServiceClient;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private DepositService depositService;

    @Test
    public void depositServiceTest_withBillId(){
        BillResponseDTO billResponseDTO = crateBillResponseDTO();
        Mockito.when(billServiceClient.getBillById(ArgumentMatchers.anyLong())).thenReturn(billResponseDTO);
        Mockito.when(accountServiceClient.getAccountById(ArgumentMatchers.anyLong())).thenReturn(createAccountResponseDTO());
        depositService.deposit(null,1l,BigDecimal.valueOf(1000));
    }


    private AccountResponseDTO createAccountResponseDTO(){
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setAccountId(1l);
        accountResponseDTO.setBills(Arrays.asList(1l, 2l, 3l));
        accountResponseDTO.setCreationDate(OffsetDateTime.now());
        accountResponseDTO.setEmail("lori.cat@zxz");
        accountResponseDTO.setName("Lori");
        accountResponseDTO.setPhone("+123456789");
        return accountResponseDTO;
    }

    private BillResponseDTO crateBillResponseDTO(){
        BillResponseDTO billResponseDTO = new BillResponseDTO();
        billResponseDTO.setAccountId(1l);
        billResponseDTO.setAmount(BigDecimal.valueOf(1000));
        billResponseDTO.setBillId(1l);
        billResponseDTO.setCreationDate(OffsetDateTime.now());
        billResponseDTO.setIsDefault(true);
        billResponseDTO.setOverdraftEnabled(true);
        return billResponseDTO;
    }
}
