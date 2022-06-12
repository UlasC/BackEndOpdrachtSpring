package ulas1.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ulas1.backend.domain.dto.CreateHandelingDto;
import ulas1.backend.domain.dto.CreateMankementDto;
import ulas1.backend.domain.entity.*;
import ulas1.backend.exception.AutoNotFoundException;
import ulas1.backend.exception.MankementNotFoundException;
import ulas1.backend.repository.MankementRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MankementService {

        private MankementRepository mankementRepository;
        private AutoService autoService;
        private HandelingService handelingService;
        private OverigeHandelingService overigeHandelingService;
        private OnderdeelService onderdeelService;
        private double btwPercentage = 21.0;

        @Autowired
        public MankementService(MankementRepository mankementRepository, AutoService autoService, HandelingService handelingService, OverigeHandelingService overigeHandelingService, OnderdeelService onderdeelService) {
            this.mankementRepository = mankementRepository;
            this.autoService = autoService;
            this.handelingService = handelingService;
            this.overigeHandelingService = overigeHandelingService;
            this.onderdeelService = onderdeelService;
            
        }

        public Mankement createMankement(CreateMankementDto createMankementDto){
            Mankement mankement = new Mankement();
            mankement.setMankementId(createMankementDto.getMankementId());
            mankement.setBetalingstatus(createMankementDto.getBetalingstatus());
            mankement.setReparatieFase(createMankementDto.getReparatieFase());

            Auto auto = autoService.getAutoByKenteken(createMankementDto.getKenteken());
            mankement.setAuto(auto);

            mankement.setOnderdelen(new ArrayList<>());
            mankement.setBestaandeHandelingen(new ArrayList<>());
            mankement.setOverigeHandelingen(new ArrayList<>());

            mankementRepository.save(mankement);
            return mankement;
        }

        public Mankement addOnderdeelToMankement(int mankementId, int onderdeelId){
            Mankement mankement = getMankementByMankementId(mankementId);
            Onderdeel onderdeel = onderdeelService.getOnderdeelByArtikelnummer(onderdeelId);

            List<Onderdeel> onderdelen = mankement.getOnderdelen();
            onderdelen.add(onderdeel);
            mankement.setOnderdelen(onderdelen);

            mankementRepository.save(mankement);
            return mankement;
        }

        public Mankement addBestaandeHandelingToMankement(int mankementId, int handelingId){
            Mankement mankement = getMankementByMankementId(mankementId);
            BestaandeHandeling handeling = handelingService.getHandelingByHandelingsnummer(handelingId);

            List<BestaandeHandeling> handelingen = mankement.getBestaandeHandelingen();
            handelingen.add(handeling);
            mankement.setBestaandeHandelingen(handelingen);

            mankementRepository.save(mankement);
            return mankement;
        }

        public Mankement addOverigeHandelingToMankement(int mankementId, CreateHandelingDto createHandelingDto){
            Mankement mankement = getMankementByMankementId(mankementId);

            OverigeHandeling overigeHandeling = overigeHandelingService.addOverigeHandeling(createHandelingDto);

            List<OverigeHandeling> handelingen = mankement.getOverigeHandelingen();
            handelingen.add(overigeHandeling);
            mankement.setOverigeHandelingen(handelingen);

            mankementRepository.save(mankement);
            return mankement;
        }

        public Mankement deleteOverigeHandelingen(int mankementId){
            Mankement mankement = getMankementByMankementId(mankementId);
            mankement.setOverigeHandelingen(new ArrayList<>());
            mankementRepository.save(mankement);
            return mankement;
        }

        public void deleteMankement(int mankementId){
            Mankement mankement = getMankementByMankementId(mankementId);
            mankementRepository.delete(mankement);
        }

        public Mankement getMankementByMankementId(int mankementId) {
            Optional<Mankement> mankement = mankementRepository.findById(mankementId);
            if(mankement.isEmpty()){
                throw new MankementNotFoundException(mankementId);
            }
            return mankement.get();
        }

        public Optional <List <Mankement>> getMankementenByAuto(String kenteken){
            Auto auto= autoService.getAutoByKenteken(kenteken);
            Optional<List < Mankement>> mankement  = mankementRepository.findMankementenByAuto(auto);
            return mankement;
        }

        public String getBon(int mankementId){
            Mankement mankement = getMankementByMankementId(mankementId);
            StringBuilder bon = new StringBuilder();
            bon.append("Bon\n");
            addKlantNameToBon(bon, mankement);
            bon.append("\n");
            double totaalprijsMetBTW = addHandelingenAndPrijsToBon(bon, mankement);
            bon.append("\n");
            totaalprijsMetBTW += addOnderdelenAndPrijsToBon(bon, mankement);
            bon.append("\n");
            addBTWandTotaalprijsToBon(bon, totaalprijsMetBTW);
            return bon.toString();
        }

    public void addKlantNameToBon(StringBuilder bon, Mankement mankement) {
        Klant klant = mankement.getAuto().getKlant();
        bon.append(klant.getFirstName()).append(" ").append(klant.getLastName()).append("\n");
    }

    public double addHandelingenAndPrijsToBon(StringBuilder bon, Mankement mankement){
            List<BestaandeHandeling> bestaandehandelingen = mankement.getBestaandeHandelingen();
            List<OverigeHandeling> overigehandelingen = mankement.getOverigeHandelingen();
            double kosten = 0.0;

            bon.append("Handelingen:\n");
            for(BestaandeHandeling handeling : bestaandehandelingen){
                kosten += handeling.getPrijs();
                bon.append(handeling.getHandeling()).append(" €").append(formatPrijs(handeling.getPrijs())).append("\n");
            }
            for(OverigeHandeling handeling : overigehandelingen){
                kosten += handeling.getPrijs();
                bon.append(handeling.getHandeling()).append(" €").append(formatPrijs(handeling.getPrijs())).append("\n");
            }
            return kosten;
        }

        public double addOnderdelenAndPrijsToBon(StringBuilder bon, Mankement mankement){
            List<Onderdeel> onderdelen = mankement.getOnderdelen();
            double kosten = 0.0;

            bon.append("Onderdelen:\n");
            for(Onderdeel onderdeel : onderdelen){
                kosten += onderdeel.getPrijs();
                bon.append(onderdeel.getName()).append(" €").append(formatPrijs(onderdeel.getPrijs())).append("\n");
            }
            return kosten;
        }

        public void addBTWandTotaalprijsToBon(StringBuilder bon, double totaalprijsMetBTW){
            double totaalprijsZonderBTW = totaalprijsMetBTW / (100 + btwPercentage) * 100;
            double BTW = totaalprijsMetBTW / (100 + btwPercentage) * btwPercentage;
            bon.append("Totaal (exclusief btw): €").append(formatPrijs(totaalprijsZonderBTW)).append("\n");
            bon.append("BTW: €").append(formatPrijs(BTW)).append("\n\n");
            bon.append("Totaal (inclusief btw): €").append(formatPrijs(totaalprijsMetBTW));
        }

        public String formatPrijs(double prijs){
            prijs = Math.round(prijs * 100.0) / 100.0;
            String prijsFormatted = String.valueOf(prijs);
            prijsFormatted = prijsFormatted.replace(".",",");
            int kommapositie = prijsFormatted.indexOf(",");
            if(kommapositie == prijsFormatted.length() - 2){ //Als er één getal achter de komma staat, moet er een extra 0 geschreven worden
                prijsFormatted += "0";
            }
            return prijsFormatted;
        }

    }


