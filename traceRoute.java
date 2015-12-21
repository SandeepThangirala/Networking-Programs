import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Scanner;

public class traceRoute {

	public static void main(String args[]) throws Exception {
		String finderrorinstring;
		@SuppressWarnings("resource")
		Scanner user_input = new Scanner( System.in );
		String user_url;
		int count=0;
		System.out.print("Enter you the URL that you want to TraceRoute: ");
		user_url = user_input.next( );
		System.out.println("Enter the number of time you want the TraceRoute to run ");
		count = user_input.nextInt();
		int i =0;
		for(i=0;i<count;i++)
		{
			try {
				Process proc = Runtime.getRuntime().exec("tracert "+user_url);
				BufferedInputStream stream = new BufferedInputStream(proc.getInputStream());
				int data = 0;
				StringWriter writer = new StringWriter();
				System.out.println("\n\nLoop "+(i+1));
				while ((data = stream.read()) > 0) {
					System.out.print((char) data);
					writer.write(data);
					writer.flush();
					finderrorinstring = writer.toString();
					if (
							(finderrorinstring.contains("Host unreachable")) == true
							|| (finderrorinstring.contains("Administratively prohibited")) == true
							|| (finderrorinstring.contains("Source quench (destination too busy)") == true
							|| (finderrorinstring.contains("User interrupted test")) == true
							|| (finderrorinstring.contains("Port unreachable")) == true
							|| (finderrorinstring.contains("Host unreachable")) == true
							|| (finderrorinstring.contains("Network unreachable")) == true
							|| (finderrorinstring.contains("Protocol Unreachable")) == true
							|| (finderrorinstring.contains("Timeout")) == true
							|| (finderrorinstring.contains("Unknown packet type")) == true
									)
							) {
						//System.out.println("Please tell the Newtork Manager about the situation!");
						sendemail("Something went wrong in Loop: "+(i+1));
					}
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
			}
		}
	}
	public static void sendemail(String messagefromloop)
	{
		String to = "to_email_addr"; // You need to change this
		String from = "from_email_addr";	 // You need to change this     
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //We are using GMAIL here
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("username","password"); // write your own username and password
			}
		});
		try{
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));
			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// Set Subject: header field
			message.setSubject(messagefromloop);
			// Now set the actual message
			message.setText("Please tell the Newtork Manager about the situation!");
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully !");
		}catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}

