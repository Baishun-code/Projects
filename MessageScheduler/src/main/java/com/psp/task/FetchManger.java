package com.psp.task;

import java.util.List;

public interface FetchManger {

    List<String> getMessagesInHandling();

    void pollFromService();

}
