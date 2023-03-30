package group9.project.expensestrackingapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class ExpenseRepositoryTests {
	@Autowired
	private ExpenseController myExpenseController;

	@Autowired
	private ExpenseRepository myExpenseRepo;

	@Autowired
	private UserRepository myUserRepo;

	@Test
	void testGetExpensesByUserIdByPaid() {
		User newUser = new User();
		newUser.setUsername("Ahmed");
		newUser.setEmail("ahmed@gmail.com");


		Expense user1Expense1 = new Expense("Using the subway", 2.5, "nothing", "Travel", false, LocalDate.now(), newUser);
		Expense user1Expense2 = new Expense("Having lunch", 5.65, "N/A", "Food", false, LocalDate.now(), newUser);

		newUser.addExpense(user1Expense1);
		newUser.addExpense(user1Expense2);

		user1Expense1.setUser(newUser);
		user1Expense2.setUser(newUser);

		User savedUser1 = myUserRepo.save(newUser);
		Expense savedUser1Expense1 = myExpenseRepo.save(user1Expense1);
		Expense savedUser1Expense2 = myExpenseRepo.save(user1Expense2);

		assertThat(savedUser1).isNotNull();
		assertThat(savedUser1.getEmail()).isEqualTo("ahmed@gmail.com");
		assertThat(savedUser1.getExpenses().size()).isEqualTo(2);
		assertThat(savedUser1Expense1.getUser()).isEqualTo(savedUser1);
		assertThat(savedUser1Expense2.getUser()).isEqualTo(savedUser1);
	}
/*
	@Test
	void getSumOfExpensesByUserIdByPaid() {
	}

	@Test
	void addExpense() {
	}

	@Test
	void updateExpense() {
	}

	@Test
	void deleteExpense() {
	}*/

}
