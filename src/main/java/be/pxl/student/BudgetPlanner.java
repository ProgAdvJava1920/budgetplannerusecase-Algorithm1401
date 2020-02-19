package be.pxl.student;

import be.pxl.student.entity.Account;
import be.pxl.student.util.BudgetPlannerImporter;

import java.util.List;

public class BudgetPlanner {

    public static void main(String[] args) {

        List<Account> accountsList = BudgetPlannerImporter.importPayments("src/main/resources/account_payments.csv");
        accountsList.forEach(account -> {
            System.out.println(account.getIBAN() + " payments -> ");
            account.getPayments().forEach(payment -> System.out.println(payment.toString()));
        });

    }

}
