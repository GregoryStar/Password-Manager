public class AccountInfoController {
    Account account;
    AccountInfoView accountInfoView;

    public AccountInfoController(Account account){
        this.account = account;
        accountInfoView = new AccountInfoView(account);
    }

    public void updateAccountData(){
        //Update title
        String newTitle = accountInfoView.titleField.getText();
        account.setAccountTitle(newTitle);

        //Update emailAddress
        String newEmail = accountInfoView.emailField.getText();
        account.setEmailAddress(newEmail);

        //Update username
        String newUsername = accountInfoView.usernameField.getText();
        account.setUsername(newUsername);

        //Update password only if password is different
        String newPasscode = accountInfoView.passwordField.getText();
        if(!newPasscode.equals(account.currentPassword.passcode)) {
            account.updatePassword(new Password(newPasscode));
        }

        //Update title TODO: Need to add this field
        //String newAdditionalInstructions = accountInfoView.additionalInstructionsField.getText();
        //account.setAccountTitle(newAdditionalInstructions);
    }
}
