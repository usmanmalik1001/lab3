package Server;

import java.io.*;
import java.util.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {

    public static void main(String arg[]){
        //Creating a SocketClient object
        String user, notes;
        Scanner reader = new Scanner(System.in); 
        
        System.out.println("Enter the username: ");
        user = reader.nextLine();
        
        System.out.println("Enter notes: ");
        notes = reader.nextLine();
        
       
        try 
        {
            Data d = new Data(user, notes);
            FileOutputStream file = new FileOutputStream("C:/Users/shirazi1/Desktop/Sent.txt");
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(d);
            output.close();
            file.close();
        }  
            catch(Exception ex){
             ex.printStackTrace();
        }
        
        try {
            
            Socket sock = new Socket("localhost", 6000);
            File myFile = new File("C:/Users/shirazi1/Desktop/Sent.txt");
        
            byte[] mybytearray = new byte[(int) myFile.length()];
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
            bis.read(mybytearray, 0, mybytearray.length);
            OutputStream os = sock.getOutputStream();
            os.write(mybytearray, 0, mybytearray.length);
            os.flush();
            sock.close();
            System.out.println("File sent");
            
            System.out.println("Enter the username to search: ");
            user = reader.nextLine();
            
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            out.println(user);

        } catch (UnknownHostException e) {
            System.err.println("Host unknown. Cannot establish connection");
        } catch (IOException e) {
            System.err.println("Cannot establish connection. Server may not be up."+e.getMessage());
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
