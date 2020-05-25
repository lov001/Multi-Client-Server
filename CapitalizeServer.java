package com.example.multipleClientHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CapitalizeServer {

	public static void main(String[] args) throws Exception{
		try(ServerSocket server = new ServerSocket(8888)){
			System.out.println("Server is running now......");
			ExecutorService pool = Executors.newFixedThreadPool(20);
			while(true) {
				pool.execute(new Capitalizer(server.accept()));
			}
		}
	}
	
	
	private static class Capitalizer implements Runnable{
		private Socket socket;
		
		public Capitalizer(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			
			System.out.println("Connected to client : " + socket);
			try {
				
				DataInputStream din = new DataInputStream(socket.getInputStream());
				DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
				
				String strClient = "";
				
				while(true) {
					strClient = din.readUTF();
					System.out.println("Message from Client with PORT No.: " + this.socket.getPort() + " : " + strClient);
					dout.writeUTF(strClient.toUpperCase());
					dout.flush();
				}
			}
			catch(Exception e) {
				System.out.println("Error : " + e.getMessage());
			}
			finally {
				try {
					socket.close();
				}
				catch (IOException e) {
					System.out.println("Closed : " + socket);
				}
			}
		}
		
	}
}
