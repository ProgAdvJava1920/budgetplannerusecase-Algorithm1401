package be.pxl.student.entity;

import be.pxl.student.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTests {
    public static String testIban;
    public static String name;
    public static ArrayList<Payment> payments;
    Payment payment;

    @BeforeEach
    void init(){
        testIban = "ABC";
        name = "Fonzy";
        payments = new ArrayList<>();
        payment = new Payment(LocalDateTime.now(),1000,"€","detail","DEF");
        payments.add(payment);
    }

    @Test
    public void accountConstructorAndCreationTest(){
        Account testedAcc = new Account(testIban,name,payments);

        assertAll("Account",
                () -> assertEquals(testIban,testedAcc.getIBAN()),
                () -> assertEquals(name,testedAcc.getName()),
                () -> assertEquals(payments,testedAcc.getPayments()),
                () -> assertTrue(testedAcc instanceof Account)
        );
    }

    @Test
    public void accountPaymentListSizeIsEqualToGivenListSize(){
        Account testedAcc = new Account(testIban,name,payments);

        assertTrue(testedAcc.getPayments().size() == payments.size());


    }
}