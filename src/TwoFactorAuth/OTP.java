package TwoFactorAuth;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

public class OTP extends Authenticator{
    private final static String username = "yuutubewix1002@gmail.com";
    private final static String password = "yuutube1002";
    private final static String from = "yuutubewix1002@gmail.com";

    private static String otp;

    public OTP (){
        super();
    }
    public PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(username, password);
    }
    public static void generateOTP(){
        Random r = new Random();
        otp = "";
        String s = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        for (int i = 0; i < 10; i++)
            otp += s.charAt( r.nextInt( s.length() ) );
    }
    public static String getOTP(){
        return otp;
    }

    public static void sendEmail( String email) throws Exception{
        String to = email;
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");



        Session session = Session.getInstance(props, new OTP());
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

        message.setSubject("One Time Password To Login Yuu-Tube ");
        generateOTP();
        message.setText("Hi there, your OTP=" + otp );
        Transport.send(message);

        System.out.println("An OTP has been sent to your gmail account.");
    }
}

