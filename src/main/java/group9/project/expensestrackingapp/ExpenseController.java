package group9.project.expensestrackingapp;

import java.util.*;
import java.util.stream.Collectors;

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


    @Autowired private ExpenseService service;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping
    public List<Expense> getExpensesByUserIdByPaid(@PathVariable Long userId, @RequestParam(required = false) Boolean paid) {
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
    public double getSumOfExpensesByUserIdByPaid(@PathVariable Long userId, @RequestParam(required = false) Boolean paid) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));
        double sum = 0;
        if (paid == null) {
            sum = expenseRepository.sumAmountByUser(user);
        } else {
            sum = expenseRepository.sumAmountByUserIdAndPaid(userId, paid);
        }
        return sum;
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

    @GetMapping ("/expenses/new")
    public String showNewForm(Model model){
        model.addAttribute("expense", new Expense());
        model.addAttribute("pagetitle", "Add new User");
                return "expenseform";
    }

    @PostMapping("/expenses/save")
    public String saveExpense(Expense expense, RedirectAttributes ra) {
        service.save(expense);
        return "redirect:/expenses";
    }

    @GetMapping ("/expenses/edit{id")
    public String showEditForm(@PathVariable("id")Integer id, Model model, RedirectAttributes ra){
        try{
            Expense expense = service.get(id);
            model.addAttribute("expense", expense);
            model.addAttribute("pageTitle", "Edit User (ID: " +id +")");
            return "user_form";
        } catch (NotFound e){
            ra.addFlashAttribute("message", "The user has been saved successfuly");
            return "redirect: /expenses";
        }

    }

}

