package Repository

import Expense

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


interface ExpenseRepo {
    @Repository
    interface ExpenseRepository : JpaRepository<Expense?, Long?>

}