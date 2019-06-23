import javafx.scene.layout.GridPane;

public class AccountInfoController {
    Account account;
    AccountInfoView accountInfoView;

    public AccountInfoController(Account account){
        this.account = account;
        accountInfoView = new AccountInfoView();
        updateView();
        //accountInfoView.saveChangesButton.setOnMouseClicked();
    }

    public void updateView(){
        accountInfoView.update(account);
    }

    public void updateModel(){
        account.setAccountTitle(accountInfoView.titleField.getText());
        account.setEmailAddress(accountInfoView.emailField.getText());
        account.setUsername(accountInfoView.usernameField.getText());
        account.setAdditionalInstructions(accountInfoView.additionalInstructionsField.getText());

        //Only update the password if it's actually different
        if(!account.currentPassword.passcode.equals(accountInfoView.passwordField.getText())) {
            account.updatePassword(new Password(accountInfoView.passwordField.getText()));
        }

        //accountInfoView.passwordTable.getItems();
        //accountInfoView.recoveryTable.getItems();
    }

    public GridPane getViewGridPane(){
        return accountInfoView.grid;
    };
}
