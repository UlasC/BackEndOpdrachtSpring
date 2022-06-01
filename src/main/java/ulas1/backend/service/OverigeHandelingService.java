package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.CannotReadScriptException;
import org.springframework.stereotype.Service;
import ulas1.backend.domain.OverigeHandeling;
import ulas1.backend.domain.dto.CreateHandelingDto;
import ulas1.backend.repository.OverigeHandelingRepository;

import java.util.Optional;

@Service
public class OverigeHandelingService {
    private OverigeHandelingRepository overigeHandelingRepository;

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
        Optional<OverigeHandeling> handeling = overigeHandelingRepository.findById(handelingsnummer);
        return handeling;
    }
}
