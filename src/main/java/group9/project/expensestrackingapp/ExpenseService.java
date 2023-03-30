package group9.project.expensestrackingapp;

import group9.project.expensestrackingapp.Expense;
import group9.project.expensestrackingapp.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    @Autowired private ExpenseRepository repo;

    public List<Expense> ListAll() {
        return (List<Expense>) repo.findAll();
    }

    public void save(Expense expense) {
        repo.save(expense);
    }
}
