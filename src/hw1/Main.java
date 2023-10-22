package hw1;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    static {
        try {
            // connecting to my custom configs
            LogManager.getLogManager().readConfiguration(
                    Main.class.getClassLoader().getResourceAsStream("logging.properties")
            );

        } catch (IOException e) {
            throw new RuntimeException("We cant read your .properties file");
        }
    }

    // getting logger object
    private static Logger logger = Logger.getLogger("MyLogger");
    public static void main(String[] args) {

        List<ToDo> tasks = new ArrayList<>(); // todolar shu yerda saqlanadi
        tasks.add(new ToDo("Starting day...", LocalTime.now()));
        tasks.add(new ToDo("I learn Java", LocalTime.of(22, 01)));
        tasks.add(new ToDo("I have breakfast at 13:00", LocalTime.of(22, 02)));
        tasks.add(new ToDo("I read book at 14:00", LocalTime.of(22, 02)));
        tasks.add(new ToDo("I want online tutorials at 17:00", LocalTime.of(22, 02)));

        while (tasks.size() > 0) {
            for (int i = 0; i < tasks.size(); i++) {
                if ((LocalTime.now().getHour() == tasks.get(i).getTime().getHour()) && // todoni vaqti tizim vaqti bilan ten bo'lsa ekranga chiqadi
                        (LocalTime.now().getMinute() == tasks.get(i).getTime().getMinute())) {

                    logger.log(Level.WARNING, tasks.get(i).getTask()); // todoni ekranga log qilib chiqarish
                    tasks.remove(tasks.get(i)); // bajarilgan todoni o'chirish
                }
            }
        }
        System.out.println("Todays' tasks ended");


    }
}
