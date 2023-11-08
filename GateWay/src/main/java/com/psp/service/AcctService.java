package com.psp.service;

import com.psp.entity.ResponseV0;

public interface AcctService {

    ResponseV0 openAcct();

    ResponseV0 closeAcct();

    ResponseV0 lockAcct();

}
