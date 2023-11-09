package com.psp.service;

import com.psp.entity.InterBankTransferWrapper;

import java.util.List;

public interface InterbankTransferCoordinator {

    void put(InterBankTransferWrapper interBankTransferWrapper);

    List<InterBankTransferWrapper> pollPendingTransaction();

}
