package hw2;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
    private String username;
    private String email;
    private String registerDateTime;
    private List<String> receivedMessage;
    private List<String> chatHistory;
    private int numOfReceivedMessages;
    private String lastLoggedMessage;

    public User() {
    }

    public User(String username, String email) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String pattern = "dd-MM-yyyy '|' HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        this.registerDateTime = localDateTime.format(formatter);
        this.username = username;
        this.email = email;
        this.receivedMessage = new ArrayList<>();
        this.numOfReceivedMessages = 0;
        this.chatHistory = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegisterDateTime() {
        return registerDateTime;
    }

    public void setReceivedMessage(String receivedMessage) {
        this.receivedMessage.add(receivedMessage);
    }

    public int getNumOfReceivedMessages() {
        return numOfReceivedMessages;
    }

    public void setNumOfReceivedMessages(int numOfReceivedMessages) {
        this.numOfReceivedMessages = numOfReceivedMessages;
    }

    public List<String> getReceivedMessage() {
        return receivedMessage;
    }

    public void setRegisterDateTime(String registerDateTime) {
        this.registerDateTime = registerDateTime;
    }

    public List<String> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(String chatHistory) {
        this.chatHistory.add(chatHistory);
    }

    public String getLastLoggedMessage() {
        return lastLoggedMessage;
    }

    public void setLastLoggedMessage(String lastLoggedMessage) {
        this.lastLoggedMessage = lastLoggedMessage;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", registerDateTime='" + registerDateTime + '\'' +
                '}';
    }
}
