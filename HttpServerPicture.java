package example1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServerPicture {
	
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(80);
			Socket client = server.accept();
			
			OutputStream clientWriter = client.getOutputStream();
			FileInputStream clientReader = new FileInputStream(new File("src/test1/example.jpg"));
			
			byte[] buffer = new byte[1024];
			int readBytes;
			
			while ( (readBytes = clientReader.read(buffer, 0, buffer.length)) > 0) {
				clientWriter.write(buffer, 0, readBytes);
			}
			clientWriter.close();
			client.close();
			
			server.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
