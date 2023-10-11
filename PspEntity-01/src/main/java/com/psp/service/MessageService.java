package com.psp.service;

import java.util.List;

public interface MessageService {

    List getTxData();

    int cancelTxData(List data);

}
