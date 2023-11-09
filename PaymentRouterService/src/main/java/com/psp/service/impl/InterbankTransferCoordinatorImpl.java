package com.psp.service.impl;

import com.psp.entity.InterBankTransferWrapper;
import com.psp.service.InterbankTransferCoordinator;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class InterbankTransferCoordinatorImpl implements InterbankTransferCoordinator {

    private RestTemplate restTemplate;


    public InterbankTransferCoordinatorImpl(){
        restTemplate = new RestTemplate();
    }

    @Override
    public void put(InterBankTransferWrapper interBankTransferWrapper) {

    }

    @Override
    public List<InterBankTransferWrapper> pollPendingTransaction() {
        return null;
    }
}
