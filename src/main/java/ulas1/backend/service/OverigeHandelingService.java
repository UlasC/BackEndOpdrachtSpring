package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ulas1.backend.domain.entity.OverigeHandeling;
import ulas1.backend.domain.dto.CreateHandelingDto;
import ulas1.backend.repository.OverigeHandelingRepository;

import java.util.Optional;

@Service
public class OverigeHandelingService {
    private final OverigeHandelingRepository overigeHandelingRepository;

    @Autowired
    public OverigeHandelingService(OverigeHandelingRepository overigeHandelingRepository) {
        this.overigeHandelingRepository = overigeHandelingRepository;
    }

    public OverigeHandeling addOverigeHandeling(CreateHandelingDto createHandelingDto){
        OverigeHandeling overigeHandeling = new OverigeHandeling();
        overigeHandeling.setHandeling(createHandelingDto.getHandeling());
        overigeHandeling.setPrijs(createHandelingDto.getPrijs());

        overigeHandelingRepository.save(overigeHandeling);
        return overigeHandeling;
    }

    public Optional<OverigeHandeling> getOverigeHandelingByHandelingsnummer(Integer handelingsnummer) {
        return overigeHandelingRepository.findById(handelingsnummer);
    }
}
