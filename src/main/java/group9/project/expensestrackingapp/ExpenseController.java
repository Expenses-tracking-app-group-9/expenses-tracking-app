package group9.project.expensestrackingapp;

import java.util.*;
import org.springframework.beans.factory.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/users/{userId}/expenses")
public class ExpenseController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public List<Expense> getExpensesByUserIdByPaid(@PathVariable int userId, @RequestParam(required = false) Boolean paid) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));
        List<Expense> expenses;
        if (paid == null) {
            expenses = user.getExpenses();
        } else {
            expenses = expenseRepository.findByUserAndPaid(user, paid);
        }
        return expenses;
    }

    @GetMapping("/sum")
    public double getSumOfExpensesByUserIdByPaid(@PathVariable int userId, @RequestParam(required = false) Boolean paid) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));
        double sum;
        if (paid == null) {
            sum = expenseRepository.countAmountByUser(user);
        } else {
            sum = expenseRepository.countAmountByUserIdAndPaid(userId, paid);
        }
        return sum;
    }

    @PostMapping
    public Expense addExpense(@PathVariable int userId, @RequestBody Expense expense) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));
        expense.setUser(user);
        user.addExpense(expense);
        return expenseRepository.save(expense);
    }

    @PutMapping("/{expenseId}")
    public Expense updateExpense(@PathVariable int userId, @PathVariable Long expenseId,
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
    public ResponseEntity<?> deleteExpense(@PathVariable int userId, @PathVariable Long expenseId) {
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

    @GetMapping ("/expenses/new")
    public String showNewForm(Model model){
        model.addAttribute("expense", new Expense());
        model.addAttribute("pagetitle", "Add new User");
    return "expenseform";
    }

    @PostMapping("/expenses/save")
    public String saveExpense(Expense expense, RedirectAttributes ra) {
        expenseService.save(expense);
        return "redirect:/expenses";
    }


}

