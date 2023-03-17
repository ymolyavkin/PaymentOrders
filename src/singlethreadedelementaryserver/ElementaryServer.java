package singlethreadedelementaryserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ElementaryServer {
    static void eServer() throws InterruptedException {
        System.out.println("Hello");
        //Start the server on port 3345
        try (ServerSocket server = new ServerSocket(3345)) {
            // waiting for a connection to a socket named "client" on the server side
            Socket client = server.accept();
            // After the handshake, the server associates the connecting client with this socket connection
            System.out.println("Connectin accepted.");
            // initiate channels for communication in the socket for the server
            // socket writing channel
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            System.out.println("DataOutputStream created");
            // socket read channel
            DataInputStream in = new DataInputStream(client.getInputStream());
            System.out.println("DataInputStream created");
            // start a dialog with the connected client in a loop until the socket is closed (пока сокет не закрыт)
            while (!client.isClosed()) {
                System.out.println("Server reading from channel");
                // the server waits in the read channel (inputstream) to receive client data
                String entry = in.readUTF();
                // after receiving the data, reads it and outputs it to the console
                System.out.println("Read from client message - " + entry);
                System.out.println("Server try writing to channel");
                // Initialize the checking condition to continue working with the client on this socket by the codeword
                if (entry.equalsIgnoreCase("quit")) {
                    System.out.println("Client initialize coonection suicide...");
                    out.writeUTF("Server reply - " + entry + " - OK");
                    out.flush();
                    Thread.sleep(3000);
                    break;
                }
                // if the termination condition is not correct - continue work - send an echo - reply back to the client
                out.writeUTF("Server reply - " + entry + " - OK");
                System.out.println("Server wrote message to client.");
                /**
                 * Clear the buffer of network messages (by default the message is not immediately sent to the network,
                 * but first accumulated in a special message buffer, whose size is determined by specific settings
                 * in the system, and the flush() method sends a message without waiting for
                 * the buffer to fill according to the system settings
                 */
                out.flush();
            }
            // if the exit condition is correct - switch off the connection
            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");
            // first close the socket channels
            in.close();
            out.close();
            // then close the server side communication socket itself
            client.close();
            /**
             * Then close the server socket that creates the communication sockets.
             * Although in a multithreaded application it is not necessary to close it - to be able to put
             * this server socket back in waiting for a new connection
             */
            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
