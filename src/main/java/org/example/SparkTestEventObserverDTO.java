package org.example;


public class SparkTestEventObserverDTO {

    private String timeStampEventObserver;
    private String keyEventObserver;
    private String messagesEventObserver;


    public SparkTestEventObserverDTO(String timeStampEventObserver, String keyEventObserver, String messagesEventObserver) {
        this.timeStampEventObserver = timeStampEventObserver;
        this.keyEventObserver = keyEventObserver;
        this.messagesEventObserver = messagesEventObserver;
    }

    public String getTimeStampEventObserver() {
        return timeStampEventObserver;
    }

    public void setTimeStampEventObserver(String timeStampEventObserver) {
        this.timeStampEventObserver = timeStampEventObserver;
    }

    public String getKeyEventObserver() {
        return keyEventObserver;
    }

    public void setKeyEventObserver(String keyEventObserver) {
        this.keyEventObserver = keyEventObserver;
    }

    public String getMessagesEventObserver() {
        return messagesEventObserver;
    }

    public void setMessagesEventObserver(String messagesEventObserver) {
        this.messagesEventObserver = messagesEventObserver;
    }

}
