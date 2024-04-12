package org.example;

public class Result {

    private String keyAdmin;

    private String keyEventObserver;

    private String timeStampAdmin;

    private String timeStampEventObserver;

    private String messagesAdmin;


    public Result(String keyAdmin, String keyEventObserver, String timeStampAdmin, String timeStampEventObserver, String messagesAdmin) {
        this.keyAdmin = keyAdmin;
        this.keyEventObserver = keyEventObserver;
        this.timeStampAdmin = timeStampAdmin;
        this.timeStampEventObserver = timeStampEventObserver;
        this.messagesAdmin = messagesAdmin;
    }

    public String getKeyAdmin() {
        return keyAdmin;
    }

    public void setKeyAdmin(String keyAdmin) {
        this.keyAdmin = keyAdmin;
    }

    public String getKeyEventObserver() {
        return keyEventObserver;
    }

    public void setKeyEventObserver(String keyEventObserver) {
        this.keyEventObserver = keyEventObserver;
    }

    public String getTimeStampAdmin() {
        return timeStampAdmin;
    }

    public void setTimeStampAdmin(String timeStampAdmin) {
        this.timeStampAdmin = timeStampAdmin;
    }

    public String getTimeStampEventObserver() {
        return timeStampEventObserver;
    }

    public void setTimeStampEventObserver(String timeStampEventObserver) {
        this.timeStampEventObserver = timeStampEventObserver;
    }

    public String getMessagesAdmin() {
        return messagesAdmin;
    }

    public void setMessagesAdmin(String messagesAdmin) {
        this.messagesAdmin = messagesAdmin;
    }
}
