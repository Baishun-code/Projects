package com.psp.service;

import com.psp.entity.TfFinishedTransaction;

import java.util.List;

public interface FinishedTransactionService {

    void createNewFinishedTransac(TfFinishedTransaction record);

    List<TfFinishedTransaction> fetchTransaction();

    void cancelTransaction(String serialNo);
}
