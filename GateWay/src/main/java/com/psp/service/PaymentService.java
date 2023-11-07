package com.psp.service;

import com.psp.entity.ResponseV0;

public interface PaymentService {

    ResponseV0 transferTo();

    ResponseV0 withdrawFromAcct();

    ResponseV0 DepositToAcct();
}
