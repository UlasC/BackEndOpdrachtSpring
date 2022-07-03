package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ulas1.backend.domain.entity.OverigeHandeling;
import ulas1.backend.domain.dto.CreateHandelingDto;
import ulas1.backend.repository.OverigeHandelingRepository;

import java.util.List;

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

    //Deze methode wordt aangeroepen wanneer een mankement wordt verwijderd vanuit een andere service
    public void deleteOverigeHandelingenList(List<OverigeHandeling> overigeHandelingen){
        for(OverigeHandeling overigeHandeling: overigeHandelingen){
            overigeHandelingRepository.delete(overigeHandeling);
        }
    }
}
