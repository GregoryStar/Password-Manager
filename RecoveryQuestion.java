import java.util.Date;

public class RecoveryQuestion {
    String question;
    String answer;
    String dateCreated;

    public RecoveryQuestion(String question, String answer, Date dateCreated){
        this.question = question;
        this.answer = answer;
        this.dateCreated = dateCreated.toString();
    }

    public RecoveryQuestion(String question, String answer){
        this(question, answer, new Date());
    }
}
