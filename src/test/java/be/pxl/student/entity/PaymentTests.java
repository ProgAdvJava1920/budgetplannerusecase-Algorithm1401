package be.pxl.student.entity;

import be.pxl.student.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTests {


    @Test
    public void paymentConstructorAndCreationTest() {
        LocalDateTime testDate = LocalDateTime.now();
        float testAmount = 10000;
        String testCurrency = "€";
        String testDetail = "detail";
        String testReceivingIban = "GHI";
        Payment testedPayment = new Payment(testDate, testAmount, testCurrency, testDetail, testReceivingIban);

        assertAll("Payment",
                () -> assertEquals(testDate, testedPayment.getDate()),
                () -> assertEquals(testAmount, testedPayment.getAmount()),
                () -> assertEquals(testCurrency, testedPayment.getCurrency()),
                () -> assertEquals(testDetail, testedPayment.getDetail()),
                () -> assertEquals(testReceivingIban, testedPayment.getReceivingIBAN()),
                () -> assertTrue(testedPayment instanceof Payment)
        );
    }
}