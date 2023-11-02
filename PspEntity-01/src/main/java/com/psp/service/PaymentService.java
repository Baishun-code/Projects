package com.psp.service;


import com.psp.entity.TfTransactionInfo;

public interface PaymentService {

    void transfer(TfTransactionInfo info);

    void deposit(TfTransactionInfo info);

    void withdraw(TfTransactionInfo info);

}
