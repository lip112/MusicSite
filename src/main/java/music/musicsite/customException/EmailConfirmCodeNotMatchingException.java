package music.musicsite.customException;

public class EmailConfirmCodeNotMatchingException extends Exception{
    public EmailConfirmCodeNotMatchingException(String msg) {
        super(msg);
    }
}
