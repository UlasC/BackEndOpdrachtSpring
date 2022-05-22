package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ulas1.backend.domain.Handeling;
import ulas1.backend.domain.Mankement;
import ulas1.backend.domain.Auto;
import ulas1.backend.domain.Onderdeel;
import ulas1.backend.domain.dto.CreateMankementDto;
import ulas1.backend.exception.AutoNotFoundException;
import ulas1.backend.exception.HandelingNotFoundException;
import ulas1.backend.exception.OnderdeelNotFoundException;
import ulas1.backend.repository.MankementRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MankementService {

        private MankementRepository mankementRepository;
        private AutoService autoService;
        private HandelingService handelingService;
        private OnderdeelService onderdeelService;

        @Autowired
        public MankementService(MankementRepository mankementRepository, AutoService autoService, HandelingService handelingService, OnderdeelService onderdeelService) {
            this.mankementRepository = mankementRepository;
            this.autoService = autoService;
            this.handelingService = handelingService;
            this.onderdeelService = onderdeelService;
            
        }

        public Mankement createMankement(CreateMankementDto createMankementDto){
            Mankement mankement = new Mankement();
            mankement.setMankementId(createMankementDto.getMankementId());
            mankement.setBetalingstatus(createMankementDto.getBetalingstatus());
            mankement.setReparatiestatus(createMankementDto.getReparatiestatus());
            

            Optional<Auto> auto = autoService.getAutoByKenteken(createMankementDto.getKenteken());
            if(auto.isEmpty()){
                throw new AutoNotFoundException(createMankementDto.getKenteken());
            }
            mankement.setAuto(auto.get());

            List<Onderdeel> onderdelen = new ArrayList<>();
            for(Integer onderdeelnummer : createMankementDto.getOnderdeelnummers()) {
                Optional<Onderdeel> onderdeel =  onderdeelService.getOnderdeelByArtikelnummer(onderdeelnummer);
                if(onderdeel.isEmpty()) {
                    throw new OnderdeelNotFoundException(onderdeelnummer);
                }
                onderdelen.add(onderdeel.get());
            }
            mankement.setOnderdelen(onderdelen);

            List<Handeling> handelingen = new ArrayList<>();
            for(Integer handelingsnummer : createMankementDto.getHandelingsnummers()) {
                Optional<Handeling> handeling =  handelingService.getHandelingByHandelingsnummer(handelingsnummer);
                if(handeling.isEmpty()) {
                    throw new HandelingNotFoundException(handelingsnummer);
                }
                handelingen.add(handeling.get());
            }
            mankement.setHandelingen(handelingen);

            mankementRepository.save(mankement);
            return mankement;
        }
        public Optional <Mankement> getMankementByMankementId(int mankementId) {
            Optional<Mankement> mankement = mankementRepository.findById(mankementId);
            return mankement;
        }

        public Optional <List <Mankement>> getMankementenByAuto(String kenteken){
            Optional <Auto> auto= autoService.getAutoByKenteken(kenteken);
            if(auto.isEmpty()){
                throw new AutoNotFoundException(kenteken);
            }
            Optional<List < Mankement>> mankement  = mankementRepository.findMankementenByAuto(auto.get());
            return mankement;
        }

    }


