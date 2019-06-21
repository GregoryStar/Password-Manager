import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.*;

public class Account {
    //Simple data
    String accountTitle;
    String additionalInstructions;
    String emailAddress;
    String username;
    String accountID;
    Category accountType;

    //Object data
    Password currentPassword;
    List<Password> previousPasswords;
    List<RecoveryQuestion> recoveryQuestions;

    public Account(JSONObject jsonAccount){
        //Simple data
        this.accountTitle = (String)jsonAccount.get("accountTitle");
        this.additionalInstructions = (String)jsonAccount.get("additionalInstructions");
        this.emailAddress = (String)jsonAccount.get("emailAddress");
        this.username = (String)jsonAccount.get("username");
        this.accountID = (String)jsonAccount.get("accountID");
        this.accountType = Category.FINANCE; //Temporary

        //Initialize objects
        this.previousPasswords = new ArrayList<Password>(); //Temporary
        this.recoveryQuestions = new ArrayList<RecoveryQuestion>(); //Temporary

        //Extract password data
        Map jsonPassword = (Map)jsonAccount.get("currentPassword");
        String passcode = (String)jsonPassword.get("passcode");
        String dateSet = (String)jsonPassword.get("dateSet");
        String hint = (String)jsonPassword.get("hint");
        Password password = new Password(passcode, dateSet, hint);
        this.currentPassword = password;
        this.previousPasswords.add(password);
    }

    public Account(Password password, String accountName, Category accountType){
        previousPasswords = new ArrayList<Password>();

        this.currentPassword = password;
        previousPasswords.add(password);
        this.accountTitle = accountName;
        this.accountType = accountType;
        accountID = UUID.randomUUID().toString();
    }

    public void updatePassword(Password password){
        previousPasswords.add(password);
        currentPassword = password;
    }

    public Password getCurrentPassword(){
        return currentPassword;
    }

    public void setAccountTitle(String accountName){
        this.accountTitle = accountName;
    }

    public String getAccountTitle(){
        return accountTitle;
    }

    public void setAccountType(Category accountType){
        this.accountType = accountType;
    }

    public Category getAccountType(){
        return accountType;
    }

    public List<Password> getPreviousPasswords(){
        return previousPasswords;
    }

    public void setAdditionalInstructions(String additionalInstructions){
        this.additionalInstructions = additionalInstructions;
    }

    public String getAdditionalInstructions(){
        return additionalInstructions;
    }

    public void addRecoveryQuestion(RecoveryQuestion recoveryQuestion){
        recoveryQuestions.add(recoveryQuestion);
    }

    public List<RecoveryQuestion> getRecoveryQuestions(){
        return recoveryQuestions;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getEmailAddress(){
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }

    public JSONObject toJSON(){
        JSONObject jsonRep = new JSONObject();

        //Add all simple types
        jsonRep.put("accountTitle", accountTitle);
        jsonRep.put("additionalInstructions", additionalInstructions);
        jsonRep.put("emailAddress", emailAddress);
        jsonRep.put("username", username);
        jsonRep.put("accountID", accountID);

        //Add category
        Map category = new LinkedHashMap(3);

        //Add current password
        Map currPassword = new LinkedHashMap(3);
        currPassword.put("passcode", currentPassword.passcode);
        currPassword.put("hint", currentPassword.hint);
        currPassword.put("dateSet", currentPassword.dateSet.toString());
        jsonRep.put("currentPassword", currPassword);

        //Iterate through previous passwords and add them
        JSONArray jsonPasswordArray = new JSONArray();
        for(Password password : previousPasswords){
            Map current = new LinkedHashMap(3);
            current.put("passcode", password.passcode);
            current.put("hint", password.hint);
            current.put("dateSet", password.dateSet.toString());
            jsonPasswordArray.add(current);
        }
        jsonRep.put("previousPasswords", jsonPasswordArray);

        //Add list of recovery questions
        JSONArray jsonRecoveryQuestionArray = new JSONArray();
        for(RecoveryQuestion recoveryQuestion : recoveryQuestions){
            Map current = new LinkedHashMap(3);
            current.put("question", recoveryQuestion.question);
            current.put("answer", recoveryQuestion.answer);
            current.put("dateSet", recoveryQuestion.dateCreated.toString());
            jsonRecoveryQuestionArray.add(current);
        }
        jsonRep.put("recoveryQuestions", jsonRecoveryQuestionArray);

        Category accountType;
        return jsonRep;
    }
}
