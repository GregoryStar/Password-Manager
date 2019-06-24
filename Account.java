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
        accountTitle = (String)jsonAccount.get("accountTitle");
        additionalInstructions = (String)jsonAccount.get("additionalInstructions");
        emailAddress = (String)jsonAccount.get("emailAddress");
        username = (String)jsonAccount.get("username");
        accountID = (String)jsonAccount.get("accountID");
        accountType = Category.FINANCE; //Temporary

        //Initialize objects
        previousPasswords = new ArrayList<>();
        JSONArray jsonPreviousPasswords = (JSONArray)jsonAccount.get("previousPasswords");
        for(Object jsonPassword : jsonPreviousPasswords){
            //Cast the jsonPassword object to a map so that details can be extracted
            Map<String, String> mapJsonPassword = (Map)jsonPassword;
            String tempCode = mapJsonPassword.get("passcode");
            String tempHint = mapJsonPassword.get("hint");
            String tempDate = mapJsonPassword.get("dateSet");
            Password tempPass = new Password(tempCode, tempDate, tempHint);
            previousPasswords.add(tempPass);
        }

        //Temporary
        recoveryQuestions = new ArrayList<>(); //Temporary

        //Extract password data
        Map jsonPassword = (Map)jsonAccount.get("currentPassword");
        String passcode = (String)jsonPassword.get("passcode");
        String dateSet = (String)jsonPassword.get("dateSet");
        String hint = (String)jsonPassword.get("hint");
        Password password = new Password(passcode, dateSet, hint);
        currentPassword = password;
        previousPasswords.add(password);
    }

    public Account(Password password, String accountName, Category accountType){
        previousPasswords = new ArrayList<>();

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

    public void setPreviousPasswords(List<Password> previousPasswords){
        this.previousPasswords = previousPasswords;
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

    public void setRecoveryQuestions(List<RecoveryQuestion> recoveryQuestions){
        this.recoveryQuestions = recoveryQuestions;
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
            current.put("dateSet", password.dateSet);
            jsonPasswordArray.add(current);
        }
        jsonRep.put("previousPasswords", jsonPasswordArray);

        //Add list of recovery questions
        JSONArray jsonRecoveryQuestionArray = new JSONArray();
        for(RecoveryQuestion recoveryQuestion : recoveryQuestions){
            Map current = new LinkedHashMap(3);
            current.put("question", recoveryQuestion.question);
            current.put("answer", recoveryQuestion.answer);
            current.put("dateSet", recoveryQuestion.dateCreated);
            jsonRecoveryQuestionArray.add(current);
        }
        jsonRep.put("recoveryQuestions", jsonRecoveryQuestionArray);

        Category accountType;
        return jsonRep;
    }
}
