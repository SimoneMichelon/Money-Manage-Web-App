package osiride.vitt_be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import osiride.vitt_be.domain.User;
import osiride.vitt_be.domain.Vault;

@Repository
public interface VaultRepository extends JpaRepository<Vault, Long> {

	Optional<Vault> findById(Long id);
	
	
	//Esempio di query
	//@Query(value = "SELECT * FROM Vault Where User.id == user.id")
	//List<Vault> getAllByUser(User user);
}
