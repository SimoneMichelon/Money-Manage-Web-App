package osiride.vitt_be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import osiride.vitt_be.repository.FirmRepository;

@Service
public class FirmService {

    @Autowired
    private FirmRepository firmRepository;

}
