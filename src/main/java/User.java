import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class User {
    @Entity
    @Table(name = "users")
    public class user {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String email;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Expense> expenses = new ArrayList<>();

        public user() {}

        public user(String name, String email, List<Expense> expenses) {

            this.name = name;
            this.email = email;
            this.expenses = expenses;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public List<Expense> getExpenses() {
            return expenses;
        }

        public void setExpenses(List<Expense> expenses) {
            this.expenses = expenses;
        }

        @Override
        public String toString() {
            return "User [name=" + name + ", email=" + email + ", expenses=" + expenses + "]";
        }
    }}