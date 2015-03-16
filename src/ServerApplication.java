//7

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.*;

public class ServerApplication {
	public static void main(String args[]) {
		ServerSocket server = null;
		MessageBoard mb = new MessageBoard();
		try {
			//System.out.print("Enter port: ");
			//Scanner in = new Scanner(System.in);
			//int num = in.nextInt();
			//oSystem.out.println("port request is: "+num);
			server = new ServerSocket(1900, 5); /*1900*/
		} catch (IOException e) {
		}
		Socket socket = null;
		ClientDescriptor client = null;
		ConnectionProxy connection = null;
		while (true) {
			try {
				socket = server.accept();
				connection = new ConnectionProxy(socket);
				client = new ClientDescriptor();
				connection.setClientD(client);
				
				//connection.setMb(mb);						//
				client.setMb(mb);							//for not loos reference
				mb.saveUserInArr(connection);				//save new user
				client.setConnectionP(connection);
				//connection.addConsumer(client);
				//client.addConsumer(mb);
				//mb.addConsumer(connection);
				connection.start();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				client.wellcomM();
				
				//client.getMessageBord		
			} catch (IOException e) {
			}
		}
	}
}
