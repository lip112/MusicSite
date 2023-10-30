package music.musicsite.service.mail;

import lombok.RequiredArgsConstructor;
import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lombok.extern.log4j.Log4j2;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender emailSender;

    public static String ePw;

    private MimeMessage createMessage(String to)throws Exception{
        System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+ePw);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);//받는 대상
        message.setSubject("회원가입 이메일 인증 코드");//제목

        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> 안녕하세요 동서울대학교 노래 사이트 인증 번호입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 복사해 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다.<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        //        message.setFrom(new InternetAddress("properties에 입력한 이메일","보내는사람이름"));//보내는 사람
        message.setFrom(new InternetAddress("gkswk52060@gmail.com","믹꾸"));//보내는 사람

        return message;
    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rand = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rand.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rand.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rand.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rand.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }
    @Override
    public String sendSimpleMessage(String to)throws Exception {
        log.info("sendSimpleMessage" + to);
        // TODO Auto-generated method stub
        ePw = createKey();
        MimeMessage message = createMessage(to); // 보낼 메세지, 받는사람, 보내는사람 작성
        try{//예외처리
            emailSender.send(message); // 전송하기
        }catch(MailException es){
            es.printStackTrace();//어디서 예외가 발생했는지 알려줌
            throw new IllegalArgumentException();
        }
        return ePw;
    }
}
