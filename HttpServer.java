package example1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class HttpServer {

	public static void main(String[] args) {
		try {			
			ServerSocket server = new ServerSocket(80);
			System.out.println("Server started.");
			Set<String> ipSet = new TreeSet<>();
			while (true) {
				Socket client = server.accept();			
				System.out.println(client.getInetAddress().getHostAddress());
				ipSet.add(client.getInetAddress().getHostAddress());
				
				BufferedWriter clientWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				BufferedReader clientReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				
				// It can be better
	//			clientWriter.write("<!DOCTYPE html><html><body><h2>My First Heading</h2><p>My first paragraph.</p></body></html>");
	//			clientWriter.newLine();
	//			clientWriter.close();
				
				BufferedReader fileReader = null;
				String htmlDoc = "";
				boolean printIps = true;
				try {
					String fromClient = clientReader.readLine();
					String[] wantedAddress = fromClient.split(" ");
				
					String address = "src/example1/example.html";
					
					if (wantedAddress[1].equals("/about.html")) {
						address = "src/example1/about.html";
						printIps = false;
					}
					
					fileReader = new BufferedReader(new FileReader(new File(address)));
					
				} catch (NullPointerException ex) {
					System.out.println("Neko zeza...");
					continue;
				}
				
				while (fileReader.ready()) {
					htmlDoc += fileReader.readLine();
				}
				
				if (printIps) {
					Iterator<String> it = ipSet.iterator();
					while (it.hasNext()) {
						htmlDoc += it.next() + "<br>\n";
					}
				}
				
				htmlDoc += "</p></body></html>";
				clientWriter.write(htmlDoc);				
				clientWriter.close();
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
