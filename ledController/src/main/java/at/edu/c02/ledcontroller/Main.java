package at.edu.c02.ledcontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    /**
     * This is the main program entry point. TODO: add new commands when implementing additional features.
     */
    public static void main(String[] args) throws IOException {
        LedController ledController = new LedControllerImpl(new ApiServiceImpl());
        ledController.turnOffAllLeds();
        String input = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ledController.getGroupLeds();
        while(!input.equalsIgnoreCase("exit"))
        {
            System.out.println("=== LED Controller ===");
            System.out.println("Enter 'demo' to send a demo request");
            System.out.println("Enter 'exit' to exit the program");
            System.out.println("Enter 'groupstatus' to show the status of all lights in group C");
            System.out.println("Enter 'status' to show the status of a specific light");
            input = reader.readLine();
            switch (input.toLowerCase()) {
                case "demo":
                    ledController.demo();
                    break;
                case "exit":
                    break;
                case "groupstatus":
                    ledController.outputGroupStatus();
                    break;
                case "status":
                    System.out.println("Please specify LED ID:");
                    input = reader.readLine();
                    if(!input.matches("\\d+")) {
                        System.out.println("Invalid input");
                        break;
                    }
                    ledController.outputStatus(input);
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
    }
}
