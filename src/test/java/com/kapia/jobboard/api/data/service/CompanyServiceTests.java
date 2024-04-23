package com.kapia.jobboard.api.data.service;

import com.kapia.jobboard.api.data.dto.CompanyAddressDTO;
import com.kapia.jobboard.api.data.exception.IncompleteDataException;
import com.kapia.jobboard.api.data.exception.ResourceNotFoundException;
import com.kapia.jobboard.api.data.mapper.CompanyMapper;
import com.kapia.jobboard.api.data.model.Address;
import com.kapia.jobboard.api.data.model.Company;
import com.kapia.jobboard.api.data.repository.CompanyRepository;
import com.kapia.jobboard.api.data.repository.JobOfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class CompanyServiceTests {

    @Mock
    private CompanyMapper companyMapper;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private JobOfferRepository jobOfferRepository;

    @InjectMocks
    private CompanyService companyService;

    @Test
    public void givenFindAll_whenFindAll_thenAllCompaniesAreRetrieved() {
        when(companyRepository.findAll()).thenReturn(Collections.singletonList(new Company()));

        List<Company> result = companyService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    public void givenFindByName_whenFindByName_thenCompanyIsRetrieved() {
        String name = "Test Company";
        when(companyRepository.findByName(name)).thenReturn(Optional.of(new Company()));

        Optional<Company> result = companyService.findByName(name);

        assertTrue(result.isPresent());
    }

    @Test
    public void givenAddWithValidData_whenAdd_thenCompanyIsAdded() {
        CompanyAddressDTO dto = new CompanyAddressDTO();
        dto.setCompany(new Company());
        dto.setAddresses(Set.of(new Address()));
        when(companyRepository.save(any(Company.class))).thenReturn(new Company());

        Company result = companyService.add(dto);

        assertNotNull(result);
    }

    @Test
    public void givenAddWithInvalidData_whenAdd_thenThrowsException() {
        CompanyAddressDTO dto = new CompanyAddressDTO();

        assertThrows(IncompleteDataException.class, () -> companyService.add(dto));
    }

    @Test
    public void givenUpdateWithValidData_whenUpdate_thenCompanyIsUpdated() {
        CompanyAddressDTO dto = new CompanyAddressDTO();
        dto.setCompany(new Company());
        dto.setAddresses(Set.of(new Address()));
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(new Company()));
        when(companyRepository.save(any(Company.class))).thenReturn(new Company());
        when(companyMapper.updateCompanyFromDto(any(CompanyAddressDTO.class), any(Company.class))).thenReturn(new Company());

        Company result = companyService.update(dto, 1L);

        assertNotNull(result);
    }

    @Test
    public void givenUpdateWithInvalidData_whenUpdate_thenThrowsException() {
        CompanyAddressDTO dto = new CompanyAddressDTO();
        dto.setCompany(new Company());
        dto.setAddresses(Set.of(new Address()));
        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> companyService.update(dto, 1L));
    }

    @Test
    public void givenDeleteCompany_withValidId_thenCompanyIsDeleted() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(new Company()));

        assertDoesNotThrow(() -> companyService.deleteCompany(1L));
    }

    @Test
    public void givenDeleteCompany_withInvalidId_thenThrowsException() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> companyService.deleteCompany(1L));
    }
}