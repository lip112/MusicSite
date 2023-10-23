package music.musicsite.customexception;

public class DuplicateUserException extends Exception{
    public DuplicateUserException(String msg) {
        super(msg);
    }
}
