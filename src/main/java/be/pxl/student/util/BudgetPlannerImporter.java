package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Util class to import csv file
 */

@UtilityClass
public final class BudgetPlannerImporter {

    public static List<Account> importPayments(String fileLocation) {
        List<Account> fileAccounts = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(fileLocation))) {

            String line = reader.readLine();
            line = reader.readLine();
            while(line!=null) {

                String[] lineValues = line.split(",");
                Optional<Account> optionalExistingAccount = accountsContainsIBAN(fileAccounts, lineValues[1]);

                if(optionalExistingAccount.isPresent()) {
                    Payment payment = readPayment(lineValues);
                    Account account = optionalExistingAccount.get();
                    account.getPayments().add(payment);
                }else{
                    Account account = readAccount(lineValues);
                    fileAccounts.add(account);
                }
                line = reader.readLine();
            }

        }catch(IOException ex) {
            ex.printStackTrace();
        }
        return fileAccounts;
    }

    public static Optional<Account> accountsContainsIBAN(Collection<Account> accountsCollection, String IBAN){
        return accountsCollection.stream()
                .filter(account -> account.getIBAN().equals(IBAN))
                .findFirst();
    }

    public static Account readAccount(String[] lineValues) {
        String accountName = lineValues[0];
        String bankAccount = lineValues[1];

        return new Account(bankAccount, accountName, new ArrayList<>(Arrays.asList(readPayment(lineValues))));
    }

    public static Payment readPayment(String[] lineValues) {
        String counterAccount = lineValues[2];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        LocalDateTime transactionTime = LocalDateTime.parse(lineValues[3], formatter);
        float transactionAmount = Float.parseFloat(lineValues[4]);
        String currency = lineValues[5];
        String detail = lineValues[6];
        return new Payment(transactionTime, transactionAmount, currency, detail, counterAccount);
    }

}
