package net.scrooby.util;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class Mail {
    private final Session session;

    public Mail(String host, String port, String account, String password) {
        
        Properties props = new Properties();
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        
        session = Session.getInstance(props, new Authenticator() {
            @Override protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(account, password);
            }
        });
    }
    
    public MailResult send(String from, String to, String subject, String message) {
        return send(from, to, subject, message, null);
    }
    
    public MailResult send(String from, String to, String subject, String message, String pathedFilenameForAttchment) {
        MailResult mailResult = new MailResult();
        try {
            MimeMessage m = new MimeMessage(session);
            Multipart mp = new MimeMultipart("alternative");
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setContent(message, "text/html");
            mp.addBodyPart(mbp1);
            
            if (pathedFilenameForAttchment != null) {
                String unpathedFilename = pathedFilenameForAttchment.substring(pathedFilenameForAttchment.lastIndexOf("\\"));
                MimeBodyPart mbp2 = new MimeBodyPart();      
                DataSource source = new FileDataSource(pathedFilenameForAttchment);    
                mbp2.setDataHandler(new DataHandler(source));    
                mbp2.setFileName(unpathedFilename);
                mp.addBodyPart(mbp2);
            }

            m.setContent(mp);
            m.setFrom(new InternetAddress(from));
            m.addRecipient(
                    Message.RecipientType.TO, new InternetAddress(to));
            m.setSubject(subject);
            Transport.send(m);

            mailResult.setSent(true);
            mailResult.setResult("Sent OK");

        } catch (MessagingException ex) {
            mailResult.setSent(false);
            mailResult.setResult(ex.getMessage());
        }
        return mailResult;
    }
    
    public class MailResult {
        private boolean sent = false;
        private String result = "";
        
        public boolean getSent() { return sent; }
        public String getResult() { return result; }
        
        protected void setSent(boolean sent) { this.sent = sent; }
        protected void setResult(String result) { this.result = result; }
    }
}
