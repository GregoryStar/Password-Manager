import java.util.Date;

public class Password {
    public String hint;
    public String dateSet;
    public String passcode;

    public Password(String passcode, String dateSet, String hint){
        this.passcode = passcode;
        this.dateSet = dateSet;
        this.hint = hint;
    }

    public Password(String passcode, Date dateSet, String hint){
        this(passcode, dateSet.toString(), hint);
    }

    public Password(String passcode, Date dateSet){
        this(passcode, dateSet, "N/A");
    }

    public Password(String passcode){
        this(passcode, new Date());
    }

    /*
        I wanted to avoid having getters in this class because I wanted it to function as a struct, but having
        getters greatly simplifies populating tables in JavaFX because of the PropertyValueFactory class. I could've
        also used the SimpleStringProperty class, but I actually don't want the auto-updates to values that come
        with that class, and also don't want to rewrite segments of code where I refer to these member variables
        as Strings. If I work with JavaFX again, I'll try to take advantage of SimpleStringProperty.
    */
    public String getPasscode(){
        return passcode;
    }

    public String getDateSet(){
        return dateSet;
    }

    public String getHint(){
        return hint;
    }
}
