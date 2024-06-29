package osiride.vitt_be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import osiride.vitt_be.domain.User;
import osiride.vitt_be.domain.Vault;

@Repository
public interface VaultRepository extends JpaRepository<Vault, Long> {

	Optional<Vault> findById(Long id);
	
	@Query(value = "SELECT v "
			+ "FROM vault v "
			+ "WHERE v.id=:userId.id;")
	List<Vault> getAllByUser(@Param("userId") User userId);

}
