
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;


public class MailHandler {


    public MailHandler(String user, String pass) throws MessagingException, IOException {
        System.out.println("This is the mailhandler object");
        BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt"));


        String host = "imap.gmail.com";
        String username = user;
        String password = pass;
        String terminalText = "";
        Properties props = new Properties();
        props.setProperty("mail.imap.ssl.enable", "true");
        // set any other needed mail.imap.* properties here
        Session session = Session.getInstance(props);
        Store store = session.getStore("imaps");
        store.connect(host, username, password);

        Folder inbox = store.getFolder( "INBOX" );
        inbox.open( Folder.READ_WRITE );


        // Fetch unseen messages from inbox folder
        Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));



        System.out.println("Printing messages!");
        for (Message message : messages) {
//            String s = message.getSubject();
            if(message.getSubject().contains("alert for all symbols")){
                message.setFlag(Flags.Flag.SEEN,true);
                String html = (String) message.getContent();
                Document doc = Jsoup.parse(html);
                //the information we actually want is always inside an element p that is inside an element td
                Elements select = doc.select("td > p");
                //remove beginning part from string
                String s = select.text().split("for all symbols",2)[1];
                //remove the ending string
                String alert = s.split("To view and manage your alerts:", 2)[0];
//                System.out.println(alert);
                terminalText += alert + "\n";
                Main.gui.updateText(alert);
                writer.write(alert + "\n");

            }

        }
        writer.close();
//        inbox.close(false);


    }

    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }


    public void printEmails() throws MessagingException {
//        System.out.println("Printing messages!");
//        for (Message message : messages ) {
//            System.out.println(
//                    "sendDate: " + message.getSentDate()
//                            + " subject:" + message.getSubject() );
//        }
    }


}