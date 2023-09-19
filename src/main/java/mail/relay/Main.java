package mail.relay;

// 示例使用
public class Main {
    public static void main(String[] args) {
        // 设置邮件发送默认参数
        MailSendPool mailSendPool = new MailSendPool(10, 10, 60L, 100);
        // 假设从接收到的邮件中获取到以下信息
        String host = "smtp.example.com";
        int port = 25;
        String username = "username";
        String password = "password";
        String from = "sender@example.com";
        String to = "recipient@example.com";
        String subject1 = "Hello";
        String content1 = "This is a test email.";

        String subject2 = "Important Announcement";
        String content2 = "Please check the attachment for important information.";

        // 将邮件加入发送池
        mailSendPool.addMail(host, port, username, password, from, to, subject1, content1);
        mailSendPool.addMail(host, port, username, password, from, to, subject2, content2);

        // 关闭邮件发送池
        mailSendPool.shutdown();

        System.out.println("All mails sent");
    }
}