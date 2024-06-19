package osiride.vitt_be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osiride.vitt_be.repository.RevenueRepository;

@Service
public class RevenueService {

    @Autowired
    private RevenueRepository revenueRepository;

}
