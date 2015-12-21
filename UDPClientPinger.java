import java.io.*;
import java.net.*;
import java.util.*;
public class UDPClientPinger
{
	public static void main(String args[])
	{
		try
		{
			DatagramSocket client=new DatagramSocket();
			InetAddress address=InetAddress.getByName("127.0.0.1");

			String strcontinue=null;
			byte[] sendbyte=new byte[1024];
			byte[] receivebyte=new byte[1024];
			BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
			do{
				System.out.println("Enter the url of the website or IP adress:");
				String str=in.readLine();
				//InetAddress address=InetAddress.getByName(str);
				sendbyte=str.getBytes();
				DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,address,1309);
				client.send(sender);
				long millisStart = Calendar.getInstance().getTimeInMillis();
				//System.out.println(millisStart);
				DatagramPacket receiver=new DatagramPacket(receivebyte,receivebyte.length);
				client.receive(receiver);
				long millisEnd = Calendar.getInstance().getTimeInMillis();
				//System.out.println(millisEnd);
				String s=new String(receiver.getData());
				//System.out.println("IP address or URL of the website: "+s.trim());  
				System.out.println("Time difference in milli seconds "+(millisEnd-millisStart));
				System.out.println("Do you want to continue? (Y/N)");
				strcontinue=in.readLine();
			}while(strcontinue.equals("Y"));
			client.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
