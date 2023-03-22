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

        }
    }