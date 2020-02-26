package be.pxl.student.util;

import be.pxl.student.entity.Account;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class BudgetPlannerImporterTests {

    @Test
    @SneakyThrows
    public void shouldCreateAListOfAccounts() {

        BudgetPlannerFeeder fienFeeder = new BudgetPlannerFeeder();
        BudgetPlannerFeeder janFeeder = new BudgetPlannerFeeder();
        fienFeeder.faker = new Faker();
        fienFeeder.myAccountName = "Fien";
        fienFeeder.myIBANNumber = "BE69771770897312";

        janFeeder.faker = new Faker();
        janFeeder.myAccountName = "Jan";
        janFeeder.myIBANNumber = "BE69771770897313";

        String fileLocation = "src/test/resources/account_payments.csv";

        Method datalinesMethod = BudgetPlannerFeeder.class.getDeclaredMethod("generateLines", int.class);
        Method saveFileMethod = BudgetPlannerFeeder.class.getDeclaredMethod("saveFile", String.class, String[].class);
        saveFileMethod.setAccessible(true);
        datalinesMethod.setAccessible(true);

        String[] returnedDataLines = (String[]) datalinesMethod.invoke(fienFeeder, 5);
        String[] returnedDataLinesJan = (String[]) datalinesMethod.invoke(janFeeder, 5);
        String[] dataLines = Stream.concat(Arrays.stream(returnedDataLines), Arrays.stream(returnedDataLinesJan))
                .toArray(String[]::new);
        saveFileMethod.invoke(fienFeeder, fileLocation, dataLines);

        List<Account> accounts = BudgetPlannerImporter.importPayments(fileLocation);

        Assertions.assertTrue(accounts.size() > 0);

        accounts.forEach(account -> System.out.println(account.toString()));
    }

}
