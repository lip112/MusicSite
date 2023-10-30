package music.musicsite.customException;

public class DuplicateUserException extends Exception{
    public DuplicateUserException(String msg) {
        super(msg);
    }
}
