package psp.service;

import java.util.concurrent.ExecutionException;

public interface MessageService {

    boolean sendTo(String message, String topic) throws ExecutionException, InterruptedException;

}
