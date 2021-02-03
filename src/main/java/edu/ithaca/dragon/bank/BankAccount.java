package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
        if (isAmountValid(startingBalance)){
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Starting balance: " + startingBalance + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (BankAccount.isAmountValid(amount) == false){
            throw new IllegalArgumentException("Cannot withdraw an invalid amount");
        }
        else if (amount <= balance){
            balance -= amount;
        }
        else if (amount > balance){
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1){
            return false;
        }
        else if (email.length() == 0){
            return false;
        }
        else if (email.indexOf('@') == 0){
            return false;
        }
        else if (email.indexOf('.') == -1){
            return false;
        }
        else {
            return true;
        }
    }
    /**
    IsAmountValid -> takes a double and returns true if the amount is positive and has two decimal points or less, and false otherwise.
     */
    public static boolean isAmountValid(double amount) {
        if (amount < 0) {
            return false;
        } //Checking for negative numbers
        else if ((amount * 1000 % 10) != 0) { return false; } //Checking to make sure that there aren't more than 2 decimal places
        return true;
    }
    /**
     * @param amount deposited
     *         if amount is invalid, an error needs to be thrown
     */
    public void deposit(double amount) {
        if (BankAccount.isAmountValid(amount)==false){
            throw new IllegalArgumentException("Deposit amount is invalid");
        }
        else{ balance += amount; }
    }
    /**
     * @param amount to transfer
     * @param BankAccount to transfer from
     * @param BankAccount to transfer to
     */
    public void transfer(double amount, BankAccount transferFrom, BankAccount transferTo){
        if (transferFrom == transferTo) {
            throw new IllegalArgumentException("Cannot transfer money to the same account");
        }
        else if (amount > transferFrom.balance){
            throw new IllegalArgumentException("Cannot transfer money you don't have");
        }
        else if (isAmountValid(amount) == false){
            throw new IllegalArgumentException("Cannot transfer an invalid amount of money");
        }
        else {
            transferFrom.balance-=amount;
            transferTo.balance+=amount;
        }
    }
}
