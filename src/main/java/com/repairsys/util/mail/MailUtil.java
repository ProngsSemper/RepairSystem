package com.repairsys.util.mail;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author Prongs
 */
public class MailUtil {
    /**
     * 我的邮箱账号
     */
    final private static String MY_EMAIL_ACCOUNT = "798237844@qq.com";
    /**
     * 我的邮箱授权码
     */
    final private static String MY_EMAIL_PASSWORD = "olppzdurhcgabcjb";

    /**
     * qq邮箱的SMTP服务器地址
     */
    final private static String MY_EMAILSMTP_HOST = "smtp.qq.com";

    /**
     * 收件人邮箱
     */

    private static Session session;

    public static boolean sendPrepareMail(String receiveMailAccount, String date) throws Exception {
        init();
        // 创建准备维修邮件
        MimeMessage message = createPrepareMessage(session, MY_EMAIL_ACCOUNT, receiveMailAccount, date);
        //根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        transport.connect(MY_EMAIL_ACCOUNT, MY_EMAIL_PASSWORD);
        // 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人
        transport.sendMessage(message, message.getAllRecipients());
        //关闭连接
        transport.close();
        return true;
    }

    public static boolean sendFinishMail(String receiveMailAccount) throws Exception {
        init();
        //确认报修完成时的时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //格式化为日期/时间字符串
        String simpleDate = sdf.format(date);
        // 创建维修完成邮件
        MimeMessage message = createFinishMessage(session, MY_EMAIL_ACCOUNT, receiveMailAccount, simpleDate);
        //根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        transport.connect(MY_EMAIL_ACCOUNT, MY_EMAIL_PASSWORD);
        // 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人
        transport.sendMessage(message, message.getAllRecipients());
        //关闭连接
        transport.close();
        return true;
    }

    public static boolean sendErrorMail(String receiveMailAccount, String tel) throws Exception {
        init();
        // 创建维修有问题邮件
        MimeMessage message = createErrorMessage(session, MY_EMAIL_ACCOUNT, receiveMailAccount, tel);
        //根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        transport.connect(MY_EMAIL_ACCOUNT, MY_EMAIL_PASSWORD);
        // 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人
        transport.sendMessage(message, message.getAllRecipients());
        //关闭连接
        transport.close();
        return true;
    }

    /**
     * 创建一封通知维修时间的邮件
     *
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱
     * @param date        师傅上门的时间
     * @return
     * @throws Exception
     */
    private static MimeMessage createPrepareMessage(Session session, String sendMail, String receiveMail, String date) throws Exception {
        //创建一封邮件
        MimeMessage message = new MimeMessage(session);
        //From: 发件人
        message.setFrom(new InternetAddress(sendMail, "广金维修部", "UTF-8"));
        //收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "XX用户", "UTF-8"));
        //Subject: 邮件主题
        message.setSubject("维修进度", "UTF-8");
        //Content: 邮件正文（可以使用html标签）
        message.setContent("维修师傅将于" + date + "上门进行维修，请您做好准备。", "text/html;charset=UTF-8");
        //设置发件时间
        message.setSentDate(new Date());
        //保存设置
        message.saveChanges();
        return message;
    }

    /**
     * 创建报修完成时的邮件
     *
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱
     * @param date        保修单完成时的日期
     * @return
     * @throws Exception
     */
    private static MimeMessage createFinishMessage(Session session, String sendMail, String receiveMail, String date) throws Exception {
        //创建一封邮件
        MimeMessage message = new MimeMessage(session);
        //From: 发件人
        message.setFrom(new InternetAddress(sendMail, "广金维修部", "UTF-8"));
        //收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "XX用户", "UTF-8"));
        //Subject: 邮件主题
        message.setSubject("维修进度", "UTF-8");
        //Content: 邮件正文（可以使用html标签）
        message.setContent("您的报修已于" + date + "完成，请回宿舍检查是否修好了。", "text/html;charset=UTF-8");
        //设置发件时间
        message.setSentDate(new Date());
        //保存设置
        message.saveChanges();
        return message;
    }

    /**
     * 创建维修有问题时的邮件
     *
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱
     * @param tel         工人电话
     * @return
     * @throws Exception
     */
    private static MimeMessage createErrorMessage(Session session, String sendMail, String receiveMail, String tel) throws Exception {
        //创建一封邮件
        MimeMessage message = new MimeMessage(session);
        //From: 发件人
        message.setFrom(new InternetAddress(sendMail, "广金维修部", "UTF-8"));
        //收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "XX用户", "UTF-8"));
        //Subject: 邮件主题
        message.setSubject("维修进度", "UTF-8");
        //Content: 邮件正文（可以使用html标签）
        message.setContent("本次维修因缺少材料或其他问题请同学耐心等待维修师傅下次上门维修，" +
                "或直接与维修师傅联系，师傅电话：" + tel, "text/html;charset=UTF-8");
        //设置发件时间
        message.setSentDate(new Date());
        //保存设置
        message.saveChanges();
        return message;
    }

    /**
     * 初始化设置
     */
    private static void init() {
        // 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置使用的协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.host", MY_EMAILSMTP_HOST);
        //设置请求认证
        props.setProperty("mail.smtp.auth", "true");
        // 根据配置创建会话对象, 用于和邮件服务器交互
        session = Session.getInstance(props);
        //设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);
    }
}
