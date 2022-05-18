package com.example.pets_backend.service;

import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Map;

import static com.example.pets_backend.ConstantValues.TEAM_EMAIL;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendMailService {

    private final JavaMailSenderImpl mailSender;
    private final FreeMarkerConfigurer freemarkerConfigurer;


    private void sendHtmlEmail(String to, String htmlBody) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(TEAM_EMAIL);
        helper.setTo(to);
        helper.setSubject("Pet Pocket Reminder");
        helper.setText(htmlBody, true);
        mailSender.send(message);
        log.info("Sent email to '{}' at {}", to, LocalDateTime.now());
    }

    public void sendEmailForEvent(String to, Map<String, String> templateModel) throws Exception {
        Template freemarkerTemplate = freemarkerConfigurer.getConfiguration().getTemplate("template-event.ftlh");
        String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
        sendHtmlEmail(to, htmlBody);
    }

    public void sendEmailForTasks (String to, Map<String, String> templateModel) throws Exception {
        Template freemarkerTemplate = freemarkerConfigurer.getConfiguration().getTemplate("template-tasks.ftlh");
        String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
        sendHtmlEmail(to, htmlBody);
    }

//    public void sendMailAttach(String filePath) throws Exception {
//        Properties mailProperties = mailSender.getJavaMailProperties();
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper( message, true );
//        helper.setFrom(mailSender.getUsername());
//        helper.setTo( receiver.split("," ));
//        helper.setText("Have a nice day !", true);
//        helper.setSubject("Test Send Mail with File");
//
//        FileSystemResource file = new FileSystemResource(new File(filePath));
//        helper.addAttachment("required_data_20210824.csv", file);
//
//        mailSender.send(message);
//    }
}
