package com.example.multipleClientHandler;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class CapitalizeClient {

	public static void main(String[] args) throws Exception {
		
		try(Socket socket = new Socket("127.0.0.1", 8888)){
			System.out.println("Enter message (close for quitting): ");
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			String clientMsg = "", serverMsg="";
			while(!"close".equalsIgnoreCase(clientMsg)) {
				clientMsg = br.readLine();
				out.writeUTF(clientMsg);
				out.flush();
				serverMsg = in.readUTF();
				System.out.println("Server's Capitalized Response : " + serverMsg);
			}
		}
	}
}
