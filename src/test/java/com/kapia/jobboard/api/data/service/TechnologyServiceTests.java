package com.kapia.jobboard.api.data.service;

import com.kapia.jobboard.api.data.dto.TechnologyDTO;
import com.kapia.jobboard.api.data.exception.ResourceNotFoundException;
import com.kapia.jobboard.api.data.mapper.TechnologyMapper;
import com.kapia.jobboard.api.data.model.Technology;
import com.kapia.jobboard.api.data.repository.TechnologyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TechnologyServiceTests {

    @Mock
    private TechnologyRepository technologyRepository;

    @Mock
    private TechnologyMapper technologyMapper;

    @InjectMocks
    private TechnologyService technologyService;

    @Test
    public void givenFindAll_whenFindAll_thenAllTechnologiesAreRetrieved() {
        List<Technology> technologies = Arrays.asList(new Technology(), new Technology());
        when(technologyRepository.findAll()).thenReturn(technologies);

        List<Technology> result = technologyService.findAll();

        Assertions.assertEquals(technologies.size(), result.size());
        verify(technologyRepository, times(1)).findAll();
    }

    @Test
    public void givenFindById_whenFindById_thenTechnologyIsRetrieved() {
        List<Technology> technologies = Arrays.asList(new Technology(), new Technology());
        when(technologyRepository.saveAll(technologies)).thenReturn(technologies);

        List<Technology> result = technologyService.create(technologies);

        Assertions.assertEquals(technologies.size(), result.size());
        verify(technologyRepository, times(1)).saveAll(technologies);
    }

    @Test
    public void givenUpdate_whenUpdate_thenTechnologyIsUpdated() {
        TechnologyDTO technologyDTO = new TechnologyDTO();
        long id = 1L;
        Technology technology = new Technology();
        when(technologyRepository.findById(id)).thenReturn(Optional.of(technology));
        when(technologyRepository.save(any(Technology.class))).thenReturn(technology);
        when(technologyMapper.updateTechnologyWithDtoDetails(technologyDTO, technology)).thenReturn(technology);

        Technology result = technologyService.update(technologyDTO, id);

        Assertions.assertNotNull(result);
        verify(technologyRepository, times(1)).findById(id);
        verify(technologyRepository, times(1)).save(any(Technology.class));
    }

    @Test
    public void givenUpdate_whenUpdate_givenInvalidId_thenResourceNotFoundExceptionIsThrown() {
        TechnologyDTO technologyDTO = new TechnologyDTO();
        long id = 1L;
        when(technologyRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> technologyService.update(technologyDTO, id));
        verify(technologyRepository, times(1)).findById(id);
    }

}
