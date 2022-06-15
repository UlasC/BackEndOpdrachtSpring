package ulas1.backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ulas1.backend.domain.dto.CreateHandelingDto;
import ulas1.backend.domain.entity.OverigeHandeling;
import ulas1.backend.repository.OverigeHandelingRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OverigeHandelingServiceTest {

    @Mock
    OverigeHandelingRepository overigeHandelingRepository;

    @InjectMocks
    OverigeHandelingService sut;

    @Test
    void addOverigeHandelingReturnsHandelingWithCorrectDescriptionAndPrice() {
        //Assign
        String handelingsomschrijving = "de tank in elkaar schroeven";
        double prijs = 20.00;
        CreateHandelingDto createHandelingDto = getTestCreateHandelingDTO(handelingsomschrijving, prijs);

        //Act
        OverigeHandeling overigeHandeling = sut.addOverigeHandeling(createHandelingDto);

        //Assert
        assertEquals(overigeHandeling.getHandeling(), handelingsomschrijving);
        assertEquals(overigeHandeling.getPrijs(), prijs);
    }

    private CreateHandelingDto getTestCreateHandelingDTO(String handelingsomschrijving, double prijs){
        CreateHandelingDto createHandelingDto = new CreateHandelingDto();
        createHandelingDto.setHandeling(handelingsomschrijving);
        createHandelingDto.setPrijs(prijs);

        return createHandelingDto;
    }
}