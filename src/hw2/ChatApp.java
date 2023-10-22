package hw2;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatApp {
    private static Logger logger = Logger.getLogger("MyLogger");

    static {
        try {
            LogManager.getLogManager().readConfiguration(
                    ChatApp.class.getClassLoader().getResourceAsStream("logging.properties")
            );

        } catch (IOException e) {
            throw new RuntimeException("We cant read your .properties file");
        }
    }
    static Scanner scanner = new Scanner(System.in);
    static {
        System.out.println("\u001B[34m=================================");
        System.out.println("-----Welcome to our Chat APP-----");
        System.out.println("================================="  + "\u001B[0m");
    }
    public static void main(String[] args) throws IOException {

        printMenu();


    }

    private static void printMenu() throws IOException {
        while (true) {
            System.out.println("  ");
            System.out.println("-------------Menu---------------");
            System.out.println("1.Register");
            System.out.println("2.Login");
            System.out.println("0.Exit");

            System.out.print("Enter your answer => ");
            int answer = -1;
            try {
                answer = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31mYou entered wrong input, try again !\u001B[0m");
                printMenu();
            }

            switch (answer) {
                case 1 -> register();
                case 2 -> login();
                case 0 -> System.exit(0);
                default -> {
                    System.out.println("\u001B[31mYou entered wrong input, try again !\u001B[0m");
                    printMenu();
                }
            }
        }
    }

    private static void register() throws IOException {
        boolean result = true;
        String username = "";
        String email = "";
        while (result) {
            System.out.print("Enter your username: ");
            username = scanner.nextLine();
            Pattern usernamePattern = Pattern.compile("\\w{3,}");
            Matcher nameMatcher = usernamePattern.matcher(username);

            if (!nameMatcher.matches()) {
                System.out.println("\u001B[31mYou entered wrong username :( , try again..\u001B[0m");
            } else {
                result = false;
            }
        }

        result = true;
        while (result) {
            System.out.print("Enter your email: ");
            email = scanner.nextLine();
            Pattern emailPattern = Pattern.compile("^(\\w{3,})(@gmail.com)$");
            Matcher emailMatcher = emailPattern.matcher(email);

            if (!emailMatcher.matches()) {
                System.out.println("\u001B[31mYou entered wrong email :( , try again..\u001B[0m");
            } else {
                result = false;
            }
        }

        User user = new User(username, email);
        Database.userList.add(user);
        System.out.println("You are registered successfully!");

        saveToFile(user);
        startAction(user);

    }

    private static void saveToFile(User user) throws IOException {
        File file1 = new File("src/hw2/user_objects.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file1, true);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(user);

        File file2 = new File("src/hw2/users_history.txt");
        try(FileWriter fileWriter = new FileWriter(file2, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.append(user.getUsername() + " | " + user.getEmail() + " | " + user.getRegisterDateTime() + "\n");
        }
    }

    private static void login() throws IOException {
        System.out.print("Enter your email to login: ");
        String email = scanner.nextLine();

        boolean isRegistered = false;

        for (int i = 0; i < Database.userList.size(); i++) {
            if (Database.userList.get(i).getEmail().equals(email)) {
                isRegistered = true;
                System.out.println("You logged in successfully");
                System.out.println("Your registered time: " + Database.userList.get(i).getRegisterDateTime());

                User user = Database.userList.get(i);
                startAction(user);
                break;
            }
        }

        if (!isRegistered){
            System.out.println("\u001B[31mYou have not registered !\u001B[0m");
            register();
        }


    }

    private static void startAction(User user) throws IOException {
        System.out.println("  ");
        if (user.getReceivedMessage().size() > user.getNumOfReceivedMessages()) {

            CustomLogFormatter customLogFormatter = new CustomLogFormatter();
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(customLogFormatter);
            logger.addHandler(consoleHandler);

            logger.setUseParentHandlers(false);

            String messageToLog = user.getReceivedMessage().get(user.getReceivedMessage().size() - 1);

            if (!messageToLog.equals(user.getLastLoggedMessage())){
                logger.log(Level.INFO, messageToLog);
                user.setNumOfReceivedMessages(user.getNumOfReceivedMessages() + 1);
                user.setLastLoggedMessage(messageToLog);
            }

        }
        while (true) {
            System.out.println();
            System.out.println("------------Operations---------------");
            System.out.println("1.Send messages");
            System.out.println("2.See your message email-box");
            System.out.println("3.See your chat history");
            System.out.println("0.Exit to main menu");

            System.out.print("Enter your answer => ");


            int answer = -1;
            try {
                answer = Integer.parseInt(scanner.nextLine());
                System.out.println(" ");
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31mYou entered wrong input, try again !\u001B[0m");
                startAction(user);
            }

            switch (answer) {
                case 1 -> sendMessage(user);
                case 2 -> seeEmailBox(user);
                case 3 -> seeChatHostory(user);
                case 0 -> printMenu();
                default -> {
                    System.out.println("\u001B[31mYou entered wrong input, try again !\u001B[0m");
                    startAction(user);
                }
            }


        }

    }

    private static void sendMessage(User user) {
        System.out.println("Sending message");
        showEmailAccounts();
        System.out.print("Enter email to send message to: ");
        String receiverEmail = scanner.nextLine();
        System.out.print("Enter text to send: ");
        String text = scanner.nextLine();

        LocalDateTime localDateTime = LocalDateTime.now();
        String pattern = "dd-MMM-yyyy '|' HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        String emailSentDate = localDateTime.format(dateTimeFormatter);

        String senderName = user.getUsername();

        String sentEmailInfo = senderName + " ==> " + text + " in " + emailSentDate;

        boolean isEmailExist = false;

        for (int i = 0; i < Database.userList.size(); i++) {

            if (Database.userList.get(i).getEmail().equals(receiverEmail)){
                User receiver = Database.userList.get(i);
                receiver.setReceivedMessage(sentEmailInfo);
                receiver.setChatHistory(sentEmailInfo);
                isEmailExist = true;
                String myMessage = "You ==> " + text + " in " + emailSentDate;
                user.setChatHistory(myMessage);
                break;

            }
        }

        if (!isEmailExist) {
            System.out.println("This email does not exist!");
        }


    }

    private static void seeEmailBox(User user) {
        System.out.println("Checking email box");

        List<String> receivedMessage = user.getReceivedMessage();
        System.out.println("-----------Received Messages--------------");
        for (int i = 0; i < receivedMessage.size(); i++) {
            System.out.println(i+1 + ". " + receivedMessage.get(i));
        }



    }

    private static void seeChatHostory(User user) {
        System.out.println("  ");
        System.out.println("------------Seeing chat history-------------");
        for (int i = 0; i < user.getChatHistory().size(); i++) {
            System.out.println(i + 1 + ". " + user.getChatHistory().get(i));
        }



    }

    public static void showEmailAccounts() {
        for (int i = 0; i < Database.userList.size(); i++) {
            System.out.println(i+1 + ". " + Database.userList.get(i));
        }
    }

}
