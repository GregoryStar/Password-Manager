import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountDB {
    List<Account> accounts;
    File dbFile;

    public AccountDB(File dbFile){
        this.dbFile = dbFile;
        try {
            accounts = readAccountsFromFile();
        } catch (IOException e){
            System.out.println(e.getClass());
            System.out.println("Failed to read file " + dbFile.toString() + "!");
            System.out.println("Initializing db to default data...");
            accounts = new ArrayList<>();
        }
    }

    private List<Account> readAccountsFromFile() throws IOException{

        //Read the file and extract JSON strings to put in accountStrings list
        Scanner scanner = new Scanner(dbFile);
        List<String> accountStrings = new ArrayList<String>();
        while(scanner.hasNextLine()){
            String currentJSON = scanner.nextLine();
            accountStrings.add(currentJSON);
        }
        scanner.close();

        //Now that the JSON strings have been extracted from the file, go through them and make accounts
        List<Account> accountsInFile = new ArrayList<>();
        for(int i = 0; i < accountStrings.size(); i++){
            try {
                JSONObject jsonObject = (JSONObject) new JSONParser().parse(accountStrings.get(i));
                Account account = new Account(jsonObject);
                accountsInFile.add(account);
            } catch (ParseException e){
                System.out.println("Invalid JSON: ");
                System.out.println(accountStrings.get(i));
                System.out.println("Read operation failed!");
            }
        }

        return accountsInFile;
    }

    public void addAccount(Account account){
        accounts.add(account);
    }

    public List<Account> getAccounts(){
        return accounts;
    }

    public List<String> getAccountTitles(){
        List<String> accountNames = new ArrayList<>();
        for(Account account : accounts){
            accountNames.add(account.getAccountTitle());
        }
        return accountNames;
    }

    public List<Account> getAccountsByTitle(String name){
        List<Account> matchingAccounts = new ArrayList<>();
        for(Account account : accounts){
            if(account.getAccountTitle().contains(name)){
                matchingAccounts.add(account);
            }
        }
        return matchingAccounts;
    }

    public List<Account> getAccountsByCategory(Category type){
        List<Account> matchingAccounts = new ArrayList<>();
        for(Account account : accounts){
            if(account.accountType == type){
                matchingAccounts.add(account);
            }
        }
        return matchingAccounts;
    }

    public void saveAccountData(){
        try {
            //Get a JSONObject representation of all accounts
            JSONObject[] jsonAccounts = new JSONObject[accounts.size()];
            for (int i = 0; i < accounts.size(); i++) {
                jsonAccounts[i] = accounts.get(i).toJSON();
            }

            //Iterate through all of those accounts and write their data to the db file
            BufferedWriter writer = new BufferedWriter(new FileWriter(dbFile, false));
            for (int i = 0; i < jsonAccounts.length; i++) {
                String currentJSONString = jsonAccounts[i].toString();
                if (i < jsonAccounts.length - 1) {
                    currentJSONString += "\n";
                }
                writer.write(currentJSONString);
            }
            writer.close();
        } catch (IOException e){
            System.out.println("Failed to write data to file!");
        }
    }

}
