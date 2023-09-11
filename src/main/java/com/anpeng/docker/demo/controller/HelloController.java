package com.anpeng.docker.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

/**
 * @author anpeng
 */
@Slf4j
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello, Docker!";
    }


    @GetMapping("/anpeng")
    public String anpeng() {
        return "Hello, anpeng!";
    }


    @Autowired
    private JavaMailSender javaMailSender;


    @GetMapping("send")
    public String send() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("2582020694@qq.com");
        message.setSubject("test");
        message.setText("apcs");
        long l = System.currentTimeMillis();
        log.info("=====发送邮件开始");
        javaMailSender.send(message);
        log.info("=====发送邮件结束");
        return "send success, useTime:" + (System.currentTimeMillis() - l);
    }


    @GetMapping("send1")
    public String send1() throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Session session = message.getSession();
        // 设置 日志打印控制器
        session.setDebug(true);
        session.getProperties().setProperty("mail.smtp.localhost", "211.150.65.66");
        System.getProperties().setProperty("mail.mime.address.usecanonicalhostname", "false");
        //  解决本地DNS未配置 ip->域名场景下，邮件发送太慢的问题
//        session.getProperties().setProperty("mail.smtp.localhost", "myComputer");
        helper.setTo("2582020694@qq.com");
        helper.setSubject("test");
        helper.setText("apcs", true);
        long l = System.currentTimeMillis();
        log.info("=====发送邮件开始");
        javaMailSender.send(message);
        log.info("=====发送邮件结束");
        return "send success, useTime:" + (System.currentTimeMillis() - l);
    }


    @GetMapping("send2")
    public String send2() throws MessagingException {

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("211.150.65.66");
        sender.setUsername("communicate@zzfund.com");
        sender.setPassword("Zzfund@2023");
        sender.setPort(25);
        sender.setProtocol("smtp");
        sender.setDefaultEncoding("UTF-8");
//            sender.setDefaultFileTypeMap();
//            sender.setJavaMailProperties();
//            sender.setSession();

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo("2582020694@qq.com");
        helper.setSubject("test");
        helper.setText("apcs", true);
        long l = System.currentTimeMillis();
        log.info("=====发送邮件开始");
        javaMailSender.send(message);
        log.info("=====发送邮件结束");
        return "send success, useTime:" + (System.currentTimeMillis() - l);
    }
}