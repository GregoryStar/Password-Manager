import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main extends Application {
    AccountDB accountDB;
    Scene accountSelection, accountInfoScene;
    Stage primaryStage;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        File accountDataFile = new File("accountData.txt");

        System.out.println("Using file to get data to DB");
        accountDB = new AccountDB(accountDataFile);
        System.out.println("Writing that data back to the file");
        accountDB.saveAccountData();

        //The main scene will consist of a few controls on top followed by a list of accounts below
        List<Account> allAccounts = accountDB.getAccounts();
        ObservableList<Account> accountTitlesList = FXCollections.observableArrayList(allAccounts);
        final ListView accountSelectLayout = new ListView();
        accountSelectLayout.setItems(accountTitlesList);
        accountSelectLayout.setCellFactory(param -> new ListCell<Account>() {
            @Override
            protected void updateItem(Account item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getAccountTitle() == null) {
                    setText(null);
                } else {
                    setText(item.getAccountTitle());
                }
            }
        });

        //Set list view behavior
        accountSelectLayout.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //This complicated chain of methods gets the title of the account you clicked on
                accountSelected((Account)accountSelectLayout.getSelectionModel().getSelectedItem());
            }
        });

        //Create the root view for the scene
        StackPane root = new StackPane();
        root.getChildren().add(accountSelectLayout);

        primaryStage.setTitle("Password Manager");

        //Button deleteAccount = new Button("Delete an Existing Account");


        accountSelection = new Scene(root, 300, 250);
        primaryStage.setScene(accountSelection);
        primaryStage.show();
    }



    public void accountSelected(Account selectedAccount){
        //Create a new Scene with the given GridPane
        AccountInfoController accountInfoController = new AccountInfoController(selectedAccount, accountDB);
        GridPane accountInfoGrid = accountInfoController.getViewGridPane();
        accountInfoScene = new Scene(accountInfoGrid, 500, 700);
        primaryStage.setScene(accountInfoScene);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
