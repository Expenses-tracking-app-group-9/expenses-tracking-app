package group9.project.expensestrackingapp;

import java.util.*;

import org.springframework.beans.factory.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users/{userId}/expenses")
public class ExpenseController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping
    public List<Expense> getExpensesByUserId(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));
        return user.getExpenses();
    }

    @PostMapping
    public Expense addExpense(@PathVariable Long userId, @RequestBody Expense expense) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));
        expense.setUser(user);
        return expenseRepository.save(expense);
    }

    @PutMapping("/{expenseId}")
    public Expense updateExpense(@PathVariable Long userId, @PathVariable Long expenseId,
                                 @RequestBody Expense updatedExpense) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new NoSuchElementException("Expense not found with id " + expenseId));
        try {
            if (!expense.getUser().equals(user)) {
                throw new MissingRequestValueException("Expense with id " + expenseId + " does not belong to user with id " + userId);
            }
            expense.setAmount(updatedExpense.getAmount());
            expense.setDescription(updatedExpense.getDescription());
            expense.setDate(updatedExpense.getDate());
            return expenseRepository.save(expense);
        } catch (MissingRequestValueException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long userId, @PathVariable Long expenseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new NoSuchElementException("Expense not found with id " + expenseId));
        try {
            if (!expense.getUser().equals(user)) {
                throw new MissingRequestValueException("Expense with id " + expenseId + " does not belong to user with id " + userId);
            }
            expenseRepository.delete(expense);
            return ResponseEntity.ok().build();
        } catch (MissingRequestValueException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
