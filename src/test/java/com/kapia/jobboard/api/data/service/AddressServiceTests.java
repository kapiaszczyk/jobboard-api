package com.kapia.jobboard.api.data.service;

import com.kapia.jobboard.api.data.model.Address;
import com.kapia.jobboard.api.data.model.Company;
import com.kapia.jobboard.api.data.repository.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTests {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @Test
    public void givenFindAll_whenFindAll_thenAllAddressesAreRetrieved() {
        List<Address> addresses = Arrays.asList(new Address(), new Address());
        when(addressRepository.findAll()).thenReturn(addresses);

        List<Address> result = addressService.findAll();

        Assertions.assertEquals(addresses.size(), result.size());

        verify(addressRepository).findAll();
    }

    @Test
    public void givenFindAllByCompanyName_whenFindAllByCompanyName_thenAddressesAreRetrieved() {
        Company company = new Company();
        String companyName = "Test Company";
        company.setName(companyName);

        Address address = new Address();
        address.setCompany(company);

        List<Address> addresses = List.of(address);

        when(addressRepository.findAllByCompanyName(companyName)).thenReturn(addresses);

        List<Address> result = addressService.findAllByCompanyName(companyName);

        Assertions.assertEquals(addresses.size(), result.size());

        verify(addressRepository).findAllByCompanyName(companyName);
    }

}
