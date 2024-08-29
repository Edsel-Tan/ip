import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {

    public static final String DATA_FOLDER = "./data";
    public static final String DATA_PATH = "./data/makima.txt";

    public static boolean saveFile(Makima makima) {
        File file = new File(DATA_PATH);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(makima.convertTaskstoFileString());
            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            return false;
        }
        return true;
    }

    public static boolean loadFile(Makima makima) {
        File folder = new File(DATA_FOLDER);
        if (!folder.exists()) {
            if (folder.mkdir()) {
                File file = new File(DATA_PATH);
                try {
                    if (!file.createNewFile()) {
                        System.out.println("An unexpected error occurred.");
                        return false;
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred while creating a new data file!");
                    return false;
                }
            } else {
                System.out.println("An error occurred while creating a new data file!");
                return false;
            };
        }
        File file = new File(DATA_PATH);
        if (file.exists()) {
            ArrayList<String> lines = new ArrayList<>();

            try {
                Scanner reader = new Scanner(file);
                while (reader.hasNextLine()) {
                    lines.add(reader.nextLine());
                }
                reader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred while reading the data file!");
                return false;
            }

            int lineNumber = 0;

            while (lineNumber < lines.size()) {
                String name, startTime, endTime;
                boolean done;

                String line = lines.get(lineNumber);
                switch (line) {
                case "E":
                    if (lineNumber + 4 >= lines.size()) {
                        return false;
                    }

                    name = lines.get(lineNumber + 1);
                    done = Boolean.parseBoolean(lines.get(lineNumber + 2));
                    startTime = lines.get(lineNumber + 3);
                    endTime = lines.get(lineNumber + 4);

                    makima.addEvent(new Event(name, startTime, endTime, done));
                    lineNumber += 5;
                    break;
                case "D":
                    if (lineNumber + 3 >= lines.size()) {
                        return false;
                    }

                    name = lines.get(lineNumber + 1);
                    done = Boolean.parseBoolean(lines.get(lineNumber + 2));
                    endTime = lines.get(lineNumber + 3);

                    makima.addDeadline(new Deadline(name, endTime, done));
                    lineNumber += 4;
                    break;
                case "T":
                    if (lineNumber + 2 >= lines.size()) {
                        return false;
                    }

                    name = lines.get(lineNumber + 1);
                    done = Boolean.parseBoolean(lines.get(lineNumber + 2));

                    makima.addTodo(new ToDo(name, done));
                    lineNumber += 3;
                    break;
                case "":
                    lineNumber++;
                    break;
                default:
                    return false;
                }
            }

        } else {
            try {
                if (!file.createNewFile()) {
                    System.out.println("An unexpected error occurred.");
                    return false;
                }
            } catch (IOException e) {
                System.out.println("An error occurred while creating a new data file!");
                return false;
            }

        }
        return true;
    }

}
