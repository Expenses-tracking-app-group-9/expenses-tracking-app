package group9.project.expensestrackingapp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserAndPaid(User user, boolean paid);

    Double sumAmountByUser(User user);

    Double sumAmountByUserIdAndPaid(Long userId, boolean paid);
}