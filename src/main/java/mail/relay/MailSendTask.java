package mail.relay;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 邮件发送任务类
class MailSendTask implements Runnable {
    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String from;
    private final String to;
    private final String subject;
    private final String content;

    public MailSendTask(String host, int port, String username, String password, String from, String to, String subject, String content) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    @Override
    public void run() {
        try {
            // 配置JavaMail属性
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);

            // 创建Session对象
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // 创建MimeMessage对象
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(content);

            // 发送邮件
            Transport.send(message);

            // 记录发送状态
            System.out.println("Mail sent successfully: " + subject);
            // 更新数据库等操作

        } catch (MessagingException e) {
            System.out.println("Mail send failed: " + subject);
            // 错误处理逻辑，例如重试、记录错误日志等
        }
    }
}