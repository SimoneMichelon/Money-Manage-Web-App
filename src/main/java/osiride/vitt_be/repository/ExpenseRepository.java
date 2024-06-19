package osiride.vitt_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import osiride.vitt_be.domain.Expense;

public interface ExpenseRepository  extends JpaRepository<Expense, Long>{

}
