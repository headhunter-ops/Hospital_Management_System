package com.Hospital_Management_System.services;

import com.Hospital_Management_System.payload.BillingDto;
import com.Hospital_Management_System.utilities.BillingResponse;

public interface BillingService {

    public BillingDto createBilling(BillingDto billingDto);
    public BillingDto getBillingById(Long id);
    public BillingDto updateBilling(Long id, BillingDto billingDto);
    public void deleteBill(Long id);
    public BillingResponse billingList(int pageNo, int pageSize, String sortBy, String sortDir);
}
