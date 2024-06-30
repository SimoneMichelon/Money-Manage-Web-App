package osiride.vitt_be.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import osiride.vitt_be.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	Optional<Category> findById(Long id);
}
