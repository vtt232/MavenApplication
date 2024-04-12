package org.example;

public class ResultAggregation {
    private String messagesAdmin;
    private Long count;

    public ResultAggregation(String messagesAdmin, Long count) {
        this.messagesAdmin = messagesAdmin;
        this.count = count;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getMessagesAdmin() {
        return messagesAdmin;
    }

    public void setMessagesAdmin(String messageAdmin) {
        this.messagesAdmin = messageAdmin;
    }

}
