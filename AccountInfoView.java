import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AccountInfoView {
    GridPane grid;
    TextField titleField;
    TextField emailField;
    TextField usernameField;
    TextField passwordField;
    TextArea additionalInstructionsField;
    Button saveChangesButton;
    Button returnToAccountListButton;
    TableView<Password> passwordTable;
    TableView<RecoveryQuestion> recoveryTable;


    public AccountInfoView(){
        //Initialize input components
        titleField = new TextField();
        emailField = new TextField();
        usernameField = new TextField();
        passwordField = new TextField();
        additionalInstructionsField = new TextArea();
        saveChangesButton = new Button();
        returnToAccountListButton = new Button();
        passwordTable = new TableView<Password>();
        recoveryTable = new TableView<RecoveryQuestion>();
        generateView();
    }

    private GridPane createAccountInfoGridPane(){
        //Initialize the new scene
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 25, 25, 50));
        //grid.setAlignment(Pos.TOP_CENTER);

        //Set column constraints
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.LEFT);
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        return grid;
    }

    private void generateView() {
        grid = createAccountInfoGridPane();

        //Add the input details
        int rowIndex = 0;
        Text scenetitle = new Text("Account Information");
        scenetitle.setFont(Font.font("Ariel", FontWeight.NORMAL, 30));
        grid.add(scenetitle, 0, rowIndex, 2, 1);
        rowIndex++;

        //Account title
        Label accountTitleLabel = new Label("Account Title:");
        grid.add(accountTitleLabel, 0, rowIndex);
        grid.add(titleField, 1, rowIndex);
        rowIndex++;

        //Email address
        Label emailLabel = new Label("Email Address:");
        grid.add(emailLabel, 0, rowIndex);
        grid.add(emailField, 1, rowIndex);
        rowIndex++;

        //Username
        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, rowIndex);
        grid.add(usernameField, 1, rowIndex);
        rowIndex++;

        //Password
        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, rowIndex);
        grid.add(passwordField, 1, rowIndex);
        rowIndex++;

        //Previous passwords label
        Label previousPasswordsLabel = new Label("Previous Passwords:");
        grid.add(previousPasswordsLabel, 0, rowIndex, 2, 1);
        rowIndex++;

        //Previous passwords table
        TableColumn passcodeColumn = new TableColumn("Password");
        passcodeColumn.setMinWidth(100);
        TableColumn hintColumn = new TableColumn("Hint");
        hintColumn.setMinWidth(100);
        TableColumn passwordDateColumn = new TableColumn("Date Set");
        passwordDateColumn.setMinWidth(100);
        passwordDateColumn.setPrefWidth(200);

        passcodeColumn.setCellValueFactory(
                new PropertyValueFactory<Password, String>("passcode")
        );
        hintColumn.setCellValueFactory(
                new PropertyValueFactory<Password, String>("hint")
        );
        passwordDateColumn.setCellValueFactory(
                new PropertyValueFactory<Password, String>("dateSet")
        );
        //passwordTable = new TableView<Password>(previousPasswordData);
        passwordTable = new TableView<Password>();
        passwordTable.setEditable(false);
        passwordTable.getColumns().addAll(passcodeColumn, hintColumn, passwordDateColumn);
        passwordTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        passwordTable.setMaxHeight(200);
        grid.add(passwordTable, 0, rowIndex, 2, 1);
        rowIndex++;

        //Recovery Questions label
        Label recoveryQuestionsLabel = new Label("Recovery Questions:");
        grid.add(recoveryQuestionsLabel, 0, rowIndex, 2, 1);
        rowIndex++;

        //Recovery Questions Table
        TableColumn questionColumn = new TableColumn("Question");
        passcodeColumn.setMinWidth(100);
        TableColumn answerColumn = new TableColumn("Answer");
        hintColumn.setMinWidth(100);
        TableColumn recoveryDateColumn = new TableColumn("Date Set");
        recoveryDateColumn.setMinWidth(100);
        recoveryDateColumn.setPrefWidth(200);
        questionColumn.setCellValueFactory(
                new PropertyValueFactory<RecoveryQuestion, String>("question")
        );
        answerColumn.setCellValueFactory(
                new PropertyValueFactory<RecoveryQuestion, String>("answer")
        );
        recoveryDateColumn.setCellValueFactory(
                new PropertyValueFactory<RecoveryQuestion, String>("dateSet")
        );
        //recoveryTable = new TableView<RecoveryQuestion>(recoveryQuestionsData);
        recoveryTable = new TableView<RecoveryQuestion>();
        recoveryTable.setEditable(false);
        recoveryTable.getColumns().addAll(questionColumn, answerColumn, recoveryDateColumn);
        recoveryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        recoveryTable.setMaxHeight(200);
        grid.add(recoveryTable, 0, rowIndex, 2, 1);
        rowIndex++;

        //Additional Account Instructions
        Label additionalInstructionsLabel = new Label("Additional Instructions for Account:");
        grid.add(additionalInstructionsLabel, 0, rowIndex, 2, 1);
        rowIndex++;
        additionalInstructionsField = new TextArea();
        additionalInstructionsField.setWrapText(true);
        grid.add(additionalInstructionsField, 0, rowIndex, 2, 1);
        rowIndex++;

        saveChangesButton = new Button("Save Changes");
        grid.add(saveChangesButton, 0, rowIndex);
        returnToAccountListButton = new Button("Return to Account List");
        grid.add(returnToAccountListButton, 1, rowIndex);
    }

    public void update(Account account){
        titleField.setText(account.getAccountTitle());
        emailField.setText(account.getEmailAddress());
        usernameField.setText(account.getUsername());
        passwordField.setText(account.getCurrentPassword().passcode);
        additionalInstructionsField.setText(account.getAdditionalInstructions());

        ObservableList<Password> previousPasswordData = FXCollections.observableArrayList();
        for (int i = 0; i < account.previousPasswords.size(); i++) {
            previousPasswordData.add(account.previousPasswords.get(i));
        }
        passwordTable.setItems(previousPasswordData);

        ObservableList<RecoveryQuestion> recoveryQuestionsData = FXCollections.observableArrayList();
        for (int i = 0; i < account.recoveryQuestions.size(); i++) {
            recoveryQuestionsData.add(account.recoveryQuestions.get(i));
        }
        recoveryTable.setItems(recoveryQuestionsData);
    }

    public GridPane getGridPane(){
        return grid;
    }
}
