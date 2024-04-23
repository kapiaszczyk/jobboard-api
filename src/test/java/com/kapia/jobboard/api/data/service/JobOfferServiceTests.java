package com.kapia.jobboard.api.data.service;

import com.kapia.jobboard.api.data.constants.DegreeOfKnowledge;
import com.kapia.jobboard.api.data.dto.JobOfferDTO;
import com.kapia.jobboard.api.data.model.*;
import com.kapia.jobboard.api.data.projections.JobOfferBasicView;
import com.kapia.jobboard.api.data.projections.JobOfferDetailedView;
import com.kapia.jobboard.api.data.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobOfferServiceTests {

    @MockBean
    private CacheManager cacheManager;

    @Mock
    private JobOfferRepository jobOfferRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private TechnologyRepository technologyRepository;

    @Mock
    private JobOfferTechnologyRepository jobOfferTechnologyRepository;

    @InjectMocks
    private JobOfferService jobOfferService;

    @Test
    public void givenRetrieveAllJobOffers_whenRetrieveAllJobOffers_thenJobOffersAreRetrieved() {

        List<JobOffer> offers = new ArrayList<JobOffer>();
        JobOffer jobOffer = new JobOffer();
        offers.add(jobOffer);

        when(jobOfferRepository.findAll()).thenReturn(offers);

        List<JobOffer> retrievedJobOffers = jobOfferService.findAllJobOffers();

        verify(jobOfferRepository, times(1)).findAll();

        Assertions.assertEquals(1, retrievedJobOffers.size());

    }

    @Test
    public void givenRetrieveJobOfferById_whenRetrieveJobOfferById_thenJobOfferIsRetrieved() {

        JobOffer jobOffer = new JobOffer();
        jobOffer.setId(1L);

        when(jobOfferRepository.findById(1L)).thenReturn(java.util.Optional.of(jobOffer));

        Optional<JobOffer> retrievedJobOffer = jobOfferService.findJobOfferById(1L);

        verify(jobOfferRepository, times(1)).findById(1L);

        Assertions.assertEquals(1L, retrievedJobOffer.get().getId());

    }

    @Test
    public void givenFindAllBasicProjectedBy_whenFindAllBasicProjectedBy_thenJobOffersAreRetrieved() {

        List<JobOfferBasicView> offers = new ArrayList<JobOfferBasicView>();

        when(jobOfferRepository.findAllBasicProjectedBy()).thenReturn(offers);

        List<JobOfferBasicView> retrievedJobOffers = jobOfferService.findAllBasicProjectedBy();

        verify(jobOfferRepository, times(1)).findAllBasicProjectedBy();

        Assertions.assertEquals(0, retrievedJobOffers.size());

    }

    @Test
    public void givenFindBasicProjectedById_whenFindBasicProjectedById_thenJobOfferIsRetrieved() {

        when(jobOfferRepository.findBasicProjectedById(1L)).thenReturn(null);

        jobOfferService.findBasicProjectedById(1L);

        verify(jobOfferRepository, times(1)).findBasicProjectedById(1L);

        Assertions.assertEquals(null, jobOfferService.findBasicProjectedById(1L));

    }

    @Test
    public void givenFindDetailedProjectedBy_whenFindDetailedProjectedBy_thenJobOffersAreRetrieved() {

        List<JobOfferDetailedView> offers = new ArrayList<JobOfferDetailedView>();

        when(jobOfferRepository.findAllDetailedProjectedBy()).thenReturn(offers);

        List<JobOfferDetailedView> retrievedOffers = jobOfferService.findAllDetailedProjectedBy();

        verify(jobOfferRepository, times(1)).findAllDetailedProjectedBy();

        Assertions.assertEquals(0, retrievedOffers.size());

    }

    @Test
    public void givenFindDetailedProjectedById_whenFindDetailedProjectedById_thenJobOfferIsRetrieved() {

        when(jobOfferRepository.findDetailedProjectedById(1L)).thenReturn(null);

        jobOfferService.findDetailedProjectedById(1L);

        verify(jobOfferRepository, times(1)).findDetailedProjectedById(1L);

        Assertions.assertEquals(null, jobOfferService.findDetailedProjectedById(1L));

    }

    @Test
    public void givenAddJobOffer_whenAddJobOffer_thenJobOfferIsAdded() {

        JobOfferDTO jobOfferDTO = new JobOfferDTO();
        jobOfferDTO.setCompanyId(1L);
        jobOfferDTO.setAddressId(1L);

        Company company = new Company();
        company.setId(1L);
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        Address address = new Address();
        when(addressRepository.findById(any())).thenReturn(Optional.of(address));

        JobOffer savedJobOffer = new JobOffer(); // Mock saved job offer
        when(jobOfferRepository.save(any())).thenReturn(savedJobOffer);

        Technology technology1 = new Technology();
        technology1.setId(1L);
        Technology technology2 = new Technology();
        technology2.setId(2L);

        List<Technology> technologyList = new ArrayList<>();
        technologyList.add(technology1);
        technologyList.add(technology2);

        Map<Long, String> technologies = new HashMap<>();
        technologies.put(1L, "BEGINNER");
        technologies.put(2L, "BEGINNER");

        jobOfferDTO.setTechnologies(technologies);

        when(technologyRepository.findAllById(any())).thenReturn(technologyList);

        Optional<JobOfferBasicView> result = jobOfferService.add(jobOfferDTO);

        verify(jobOfferRepository, times(2)).save(any());

    }

    @Test
    void givenUpdateJobOffer_whenUpdateJobOffer_thenJobOfferIsUpdated() {
        JobOfferDTO jobOfferDTO = new JobOfferDTO();
        jobOfferDTO.setName("Updated Name");
        jobOfferDTO.setShortDescription("Updated Short Description");
        jobOfferDTO.setDescription("Updated Description");

        Long jobId = 1L;

        JobOffer existingJobOffer = new JobOffer();
        existingJobOffer.setId(jobId);

        when(jobOfferRepository.findById(jobId)).thenReturn(Optional.of(existingJobOffer));

        Address address = new Address();
        existingJobOffer.setAddress(address);
        when(addressRepository.findById(any())).thenReturn(Optional.of(address));

        Technology technology1 = new Technology();
        technology1.setId(1L);
        Technology technology2 = new Technology();
        technology2.setId(2L);
        List<Technology> technologies = Arrays.asList(technology1, technology2);
        Map<Long, String> techMap = new HashMap<>();
        techMap.put(1L, "BEGINNER");
        techMap.put(2L, "BEGINNER");
        jobOfferDTO.setTechnologies(techMap);

        Set<JobOfferTechnology> jobOfferTechnologies = new HashSet<>();

        JobOfferTechnology jobOfferTechnology = new JobOfferTechnology();
        jobOfferTechnology.setJobOffer(existingJobOffer);
        jobOfferTechnology.setTechnology(technology1);
        jobOfferTechnology.setDegreeOfKnowledge(DegreeOfKnowledge.BEGINNER);

        jobOfferTechnologies.add(jobOfferTechnology);
        existingJobOffer.setTechnologies(jobOfferTechnologies);
        when(technologyRepository.findAllById(any())).thenReturn(technologies);

        when(jobOfferRepository.save(any())).thenReturn(existingJobOffer);
        JobOffer updatedJobOffer = jobOfferService.update(jobOfferDTO, jobId);

        Assertions.assertNotNull(updatedJobOffer);
        Assertions.assertEquals(jobOfferDTO.getName(), updatedJobOffer.getName());
        Assertions.assertEquals(jobOfferDTO.getShortDescription(), updatedJobOffer.getShortDescription());
        Assertions.assertEquals(jobOfferDTO.getDescription(), updatedJobOffer.getDescription());
    }

    @Test
    void givenDeleteJobOffer_whenDeleteJobOffer_thenJobOfferIsDeleted() {

        long jobId = 1L;

        JobOffer existingJobOffer = new JobOffer();
        existingJobOffer.setId(jobId);

        when(jobOfferRepository.findById(jobId)).thenReturn(java.util.Optional.of(existingJobOffer));

        Assertions.assertDoesNotThrow(() -> jobOfferService.deleteById(jobId));

        verify(jobOfferRepository, times(1)).delete(existingJobOffer);
    }

}
