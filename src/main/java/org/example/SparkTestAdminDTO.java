package org.example;


public class SparkTestAdminDTO {

    private String timeStampAdmin;
    private String keyAdmin;
    private String messagesAdmin;


    public SparkTestAdminDTO(String timeStampAdmin, String keyAdmin, String messagesAdmin) {
        this.timeStampAdmin = timeStampAdmin;
        this.keyAdmin = keyAdmin;
        this.messagesAdmin = messagesAdmin;
    }


    public String getTimeStampAdmin() {
        return timeStampAdmin;
    }

    public void setTimeStampAdmin(String timeStampAdmin) {
        this.timeStampAdmin = timeStampAdmin;
    }

    public String getKeyAdmin() {
        return keyAdmin;
    }

    public void setKeyAdmin(String keyAdmin) {
        this.keyAdmin = keyAdmin;
    }

    public String getMessagesAdmin() {
        return messagesAdmin;
    }

    public void setMessagesAdmin(String messagesAdmin) {
        this.messagesAdmin = messagesAdmin;
    }

}
