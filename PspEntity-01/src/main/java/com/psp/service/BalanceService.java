package com.psp.service;

/**
 * balance service to manipulate the balance of an account
 * (1) deduct from an account
 * (2) top up to an account
 */
public interface BalanceService {

    void deductFromAcct(String acctId, String currcd, double amt);

    void topUpToAcct(String acctId, String currcd, double amt);

}
