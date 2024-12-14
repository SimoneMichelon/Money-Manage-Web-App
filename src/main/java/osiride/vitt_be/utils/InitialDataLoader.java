package osiride.vitt_be.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.constant.Role;
import osiride.vitt_be.domain.Credential;
import osiride.vitt_be.domain.Expense;
import osiride.vitt_be.domain.Revenue;
import osiride.vitt_be.domain.User;
import osiride.vitt_be.domain.Vault;
import osiride.vitt_be.repository.CredentialRepository;
import osiride.vitt_be.repository.ExpenseRepository;
import osiride.vitt_be.repository.RevenueRepository;
import osiride.vitt_be.repository.UserRepository;
import osiride.vitt_be.repository.VaultRepository;

@Slf4j
@Component
public class InitialDataLoader implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder; 

	@Autowired
	private CredentialRepository credentialRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired 
	private RevenueRepository revenueRepository;

	@Autowired 
	private ExpenseRepository expenseRepository;

	@Autowired
	private VaultRepository vaultRepository;


	@Override
	public void run(String... args) throws Exception {
		loadDefaultUserAdmin();
	}

	public void loadDefaultUserAdmin() {
		if (!userRepository.existsByRole(Role.ADMIN)) {
			Credential credential = new Credential();
			String password = "admin1234";

			credential.setEmail("admin@admin.com");
			credential.setPassword(passwordEncoder.encode(password));

			Optional<User> maybeUser = userRepository.findById(1L);
			if(maybeUser.isPresent()) {
				credential.setUser(maybeUser.get());
				credential = credentialRepository.save(credential);
				
				loadDefaultVaultsData(credential.getId());

				log.info("INITIAL DATA LOADER - Admin Data load complete");
			}
			else {
				log.error("INITIAL DATA LOADER - Admin already exists");
			}
		}
	}

	private void loadDefaultVaultsData(Long userId) {
		List<Vault> vaults = vaultRepository.getAllByUser(userId);
		
		for (Vault vault : vaults) {
			int vaultAmount = 0;
			
			List<Revenue> revList = revenueRepository.findByVault(vaultRepository.getReferenceById(vault.getId()));
			for (Revenue revenue : revList) {
				vaultAmount += revenue.getAmount().intValue();
			}

			List<Expense> expList = expenseRepository.findByVault(vaultRepository.getReferenceById(vault.getId()));
			for (Expense expense : expList) {
				vaultAmount -= expense.getAmount().intValue();
			}
			
			vault.setCapital(new BigDecimal(vaultAmount));
			vaultRepository.save(vault);
		}
		
	}
}
