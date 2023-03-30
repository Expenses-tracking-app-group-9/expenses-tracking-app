package group9.project.expensestrackingapp;

import org.junit.jupiter.api.Test;

class ExpenseControllerTest {
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseController expenseController;

    ExpenseControllerTest(UserRepository userRepository, ExpenseRepository expenseRepository, ExpenseController expenseController) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
        this.expenseController = expenseController;
    }



}