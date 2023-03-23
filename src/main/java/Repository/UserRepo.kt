package Repository

import User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


interface UserRepo {
    @Repository
    interface UserRepository : JpaRepository<User?, Long?>

}