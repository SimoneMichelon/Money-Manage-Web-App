package osiride.vitt_be.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import osiride.vitt_be.constant.Role;
import osiride.vitt_be.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  
	Optional<User> findById(Long id);
	
	
	boolean existsByRole(Role role);
}
