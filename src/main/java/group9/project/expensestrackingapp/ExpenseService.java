package group9.project.expensestrackingapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository repo;

    public List<Expense> ListAll() {
        return (List<Expense>) repo.findAll();
    }

    public void save(Expense expense) {
        repo.save(expense);
    }


    public Expense get(Integer id) throws NotFound {
        Optional<Expense> result = repo.findById(Long.valueOf(id));
        if (result.isPresent()) {
            return result.get();
        }
        throw new NotFound("couldn't find users with ID" + id);
    }
}
