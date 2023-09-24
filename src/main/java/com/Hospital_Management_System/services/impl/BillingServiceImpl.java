package com.Hospital_Management_System.services.impl;

import com.Hospital_Management_System.entities.Billing;
import com.Hospital_Management_System.exceptions.BillingException;
import com.Hospital_Management_System.payload.BillingDto;
import com.Hospital_Management_System.repositories.BillingRepository;
import com.Hospital_Management_System.services.BillingService;
import com.Hospital_Management_System.utilities.BillingResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillingServiceImpl implements BillingService {
    @Autowired
    private BillingRepository billingRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BillingDto createBilling(BillingDto billingDto) {
        Billing billing = mapToEntity(billingDto);
        Billing save = billingRepository.save(billing);
        return mapToDto(save);
    }

    @Override
    public BillingDto getBillingById(Long id) {
        Billing billing = billingRepository.findById(id).orElseThrow(
                () -> new BillingException("Bill not found for ID:" + id)
        );
        return mapToDto(billing);
    }

    @Override
    public BillingDto updateBilling(Long id, BillingDto billingDto) {
        Billing billing = billingRepository.findById(id).orElseThrow(
                () -> new BillingException("Bill not found for ID:" + id)
        );
        billing.setBillingDateTime(billingDto.getBillingDateTime());
        billing.setCost(billingDto.getCost());
       return  mapToDto(billing);
    }

    @Override
    public void deleteBill(Long id) {
        Billing billing = billingRepository.findById(id).orElseThrow(
                () -> new BillingException("Bill not found for ID:" + id)
        );
        billingRepository.deleteById(id);
    }

    @Override
    public BillingResponse billingList(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Billing> billingPage = billingRepository.findAll(pageable);
        List<Billing> content = billingPage.getContent();
        List<BillingDto> billList = content.stream().map(this::mapToDto).collect(Collectors.toList());

        BillingResponse billingResponse= new BillingResponse();
        billingResponse.setBills(billList);
        billingResponse.setLast(billingPage.isLast());
        billingResponse.setPageNo(billingPage.getNumber());
        billingResponse.setTotalPages(billingPage.getTotalPages());
        billingResponse.setPageSize(billingPage.getSize());
        billingResponse.setTotalElements(billingPage.getTotalElements());

        return billingResponse;

    }

    //--------------------//--Conversion from entity to dto and vice versa--//-------------------//
    public Billing mapToEntity(BillingDto billingDto){
        return modelMapper.map(billingDto,Billing.class);
    }

    public BillingDto mapToDto(Billing billing){
        return modelMapper.map(billing,BillingDto.class);
    }
}
