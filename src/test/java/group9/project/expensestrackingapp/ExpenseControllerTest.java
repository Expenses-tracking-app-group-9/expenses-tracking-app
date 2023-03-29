package group9.project.expensestrackingapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ExpenseControllerTest {

    @Autowired
    private ExpenseController expenseController;
    @Test
    public void testGetExpensesByUserIdByPaid() throws Exception {
            assertThat(controller).isNotNull();
    }
}