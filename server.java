package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
 
    public static void main(String[] args) throws IOException{
        // Setting a default port number.
        int portNumber = 6000;
        Socket socket = null;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ServerSocket servsock = new ServerSocket(6000);
        try {

            socket = servsock.accept();
            byte[] mybytearray = new byte[1024];
            InputStream is = socket.getInputStream();
            fos = new FileOutputStream("C:/Users/shirazi1/Desktop/Received.txt");
            bos = new BufferedOutputStream(fos);
            int bytesRead = is.read(mybytearray, 0, mybytearray.length);
            current = bytesRead;

      do {
         bytesRead =
            is.read(mybytearray, current, (mybytearray.length-current));
         if(bytesRead >= 0) current += bytesRead;
      } while(bytesRead > -1);

      bos.write(mybytearray, 0 , current);
      bos.flush();
      System.out.println("File " + "Data.txt"
          + " downloaded (" + current + " bytes read)");
       deserializer();
       
    }
    finally {
      if (fos!=null) fos.close();
      if (bos!=null) bos.close();
      if (socket!=null) socket.close();
    }
       socket = servsock.accept();
       BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
       String message = reader.readLine();
       System.out.println("Message Received: " + message);  
       
  }
    
    public static void deserializer()
    {
       Data d;
        try
        { 
	   FileInputStream fin = new FileInputStream("C:/Users/shirazi1/Desktop/Received.txt");
	   ObjectInputStream ois = new ObjectInputStream(fin);
	   d = (Data) ois.readObject();
	   ois.close();	  
           System.out.println("Username is: " + d.username);
           System.out.println("Note: " + d.note);
	}
        catch(Exception ex)
        {
	   ex.printStackTrace();
	} 
    }
}

class Data implements Serializable
{
    String username = "", note = "";
    Data(String user, String note)
    {
            this.username =user;
            this.note = note;
    }
}
