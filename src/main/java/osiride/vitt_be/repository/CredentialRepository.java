package osiride.vitt_be.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import osiride.vitt_be.domain.Credential;

@Repository
public interface CredentialRepository extends JpaRepository<Credential,Long>{

	Optional<Credential> findByEmail(String email);
	
	@Query(value = "Select count(*)"
			+ "FROM credential c "
			+ "where email LIKE :email ")
	boolean existsByEmail(String email);
}
