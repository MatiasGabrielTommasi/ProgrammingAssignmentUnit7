package programmingassignment;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
 private static final String SERVER_ADDRESS = "localhost";
 private static final int PORT = 12345;

 public static void main(String[] args) {
     System.out.println("Welcome to Averybodychat.");
     System.out.println("What's your name?");

     Scanner scanner = new Scanner(System.in);
    		 
     String nickname = scanner.nextLine();
     
     try (Socket socket = new Socket(SERVER_ADDRESS, PORT)) {
    	 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

         writer.println(nickname);
         System.out.println("Welcome to the room " + nickname + "!");

         Thread receivingThread = new Thread(() -> {
             String message;
             try {
                 while ((message = reader.readLine()) != null) {
                     System.out.println(message);
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
         });
         receivingThread.start();

         while (true) {
             String userMessage = scanner.nextLine();
             writer.println(nickname + "####" + userMessage);
         }
     } catch (IOException e) {
         e.printStackTrace();
     }
     scanner.close();
 }
}