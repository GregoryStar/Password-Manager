import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class AccountInfoController {
    Account account;
    AccountDB db;
    AccountInfoView accountInfoView;

    public AccountInfoController(Account account, AccountDB db){
        this.db = db;
        this.account = account;
        accountInfoView = new AccountInfoView();
        updateView();

        //Set behavior for save changes button and return to account list button
        accountInfoView.saveChangesButton.setOnMouseClicked((MouseEvent event) -> updateModel());
        accountInfoView.returnToAccountListButton.setOnMouseClicked(
                (MouseEvent event) -> System.out.println("return...")
        );
    }

    public void updateView(){
        accountInfoView.update(account);
    }

    public void updateModel(){
        account.setAccountTitle(accountInfoView.titleField.getText());
        account.setEmailAddress(accountInfoView.emailField.getText());
        account.setUsername(accountInfoView.usernameField.getText());
        account.setAdditionalInstructions(accountInfoView.additionalInstructionsField.getText());

        //Update previous password list
        ObservableList<Password> passwordList = accountInfoView.passwordTable.getItems();
        List<Password> newPasswords = new ArrayList<Password>(passwordList);
        account.setPreviousPasswords(newPasswords);

        //Only update the password if it's actually different
        String oldPassword = account.currentPassword.passcode;
        String newPassword = accountInfoView.passwordField.getText();
        if(!oldPassword.equals(newPassword)){
            account.updatePassword(new Password(accountInfoView.passwordField.getText()));
        }

        //Update recovery questions list
        ObservableList<RecoveryQuestion> recoveryList = accountInfoView.recoveryTable.getItems();
        List<RecoveryQuestion> newRecoveries = new ArrayList<>(recoveryList);
        account.setRecoveryQuestions(newRecoveries);
        db.saveAccountData();
    }

    public GridPane getViewGridPane(){
        return accountInfoView.grid;
    }
}
