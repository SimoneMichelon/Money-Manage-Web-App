package osiride.vitt_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import osiride.vitt_be.domain.Vault;

@Repository
public interface VaultRepository extends JpaRepository<Vault, Long> {

}
