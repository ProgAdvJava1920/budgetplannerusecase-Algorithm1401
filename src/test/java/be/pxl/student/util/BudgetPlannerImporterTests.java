package be.pxl.student.util;

import be.pxl.student.entity.Account;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class BudgetPlannerImporterTests {

    @Test
    @SneakyThrows
    public void shouldCreateAListOfAccounts() {

        BudgetPlannerFeeder feeder = new BudgetPlannerFeeder();
        feeder.faker = new Faker();
        feeder.myAccountName = "Fien";
        feeder.myIBANNumber = "BE69771770897312";

        String fileLocation = "src/test/resources/account_payments.csv";

        Method datalinesMethod = BudgetPlannerFeeder.class.getDeclaredMethod("generateLines", int.class);
        Method saveFileMethod = BudgetPlannerFeeder.class.getDeclaredMethod("saveFile", String.class, String[].class);
        saveFileMethod.setAccessible(true);
        datalinesMethod.setAccessible(true);

        Object returnedDataLines = datalinesMethod.invoke(feeder, 5);
        String[] dataLines = (String[]) returnedDataLines;
        saveFileMethod.invoke(feeder, fileLocation, dataLines);

        List<Account> accounts = BudgetPlannerImporter.importPayments(fileLocation);

        Assertions.assertTrue(accounts.size() > 0);

        accounts.forEach(account -> System.out.println(account.toString()));
    }

}
