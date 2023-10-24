package psp.task;

public class MessageWrapper {

    public String key;
    public Object messObj;
    public String topic;

    public MessageWrapper(String k, String topic, Object messObj){
        this.key = k;
        this.topic = topic;
        this.messObj = messObj;
    }
}
