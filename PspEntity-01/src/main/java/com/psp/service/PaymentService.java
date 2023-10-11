package com.psp.service;


import com.psp.entity.TransactionInfo;

public interface PaymentService {

    void transfer(TransactionInfo info);

}
