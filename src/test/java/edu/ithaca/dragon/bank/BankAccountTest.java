package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));

        //Added Tests
        BankAccount bankAccount1 = new BankAccount("a@b.com", 100);
        bankAccount1.withdraw(0); //Border case 0 test
        assertEquals(100, bankAccount1.getBalance());

        assertThrows(IllegalArgumentException.class, () -> bankAccount1.withdraw(-1));; //negative equivalence case, shouldn't allow for withdrawal

        assertThrows(IllegalArgumentException.class, () -> bankAccount1.withdraw(-1.1)); //negative equivalence case to one decimal place, shouldn't allow for withdrawal

        assertThrows(IllegalArgumentException.class, () -> bankAccount1.withdraw(-1.11)); //negative equivalence case to two decimal places, shouldn't allow for withdrawal

        assertThrows(IllegalArgumentException.class, () -> bankAccount1.withdraw(1.111)); //equivalence case with too many decimals for valid input, shouldn't allow withdrawal

        bankAccount1.withdraw(49.50); //Convert balance to float (50.50) for float testing

        assertThrows(InsufficientFundsException.class, () -> bankAccount1.withdraw(100.11)); //equivalence case where amount is a double that is greater than float balance
        assertEquals(50.50, bankAccount1.getBalance());

        assertThrows(InsufficientFundsException.class, () -> bankAccount1.withdraw(1000)); //equivalence case where amount is an int that is greater than float balance
        assertEquals(50.50, bankAccount1.getBalance());

    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com")); //Included within the equivalence class of valid email; this is a border case as it's the minimum required characters for a valid email (considering only traditional endings such as .com, .gov, .edu, .org)
        assertFalse(BankAccount.isEmailValid("")); //Included within the equivalence classes of missing multiple required characters; this is a border case as it's the minimum characters you could select for a string: 0
        assertFalse(BankAccount.isEmailValid("@.com"));//Included in the equivalence classes of missing multiple required characters; This is not a border case
        assertFalse(BankAccount.isEmailValid("a"));//Included within the equivalence classes of missing multiple required characters; this is not a border case
        assertFalse(BankAccount.isEmailValid("a@b"));//Included within the equivalence class of missing multiple required characters; this is not a border case
        assertFalse(BankAccount.isEmailValid("ab.com")); //Included in equivalence class of missing multiple required characters; this is not a border case
        assertFalse(BankAccount.isEmailValid("b.com"));//Included in equivalence class of missing multiple required characters, this is not a border case

        //A border case I noticed that is not present is too many characters for a valid email
        //Missing border cases for all of the following equivalence classes that were not tested as the only problem with an email being invalid
        //An equivalence class I noticed that was missing is an invalid email missing letters after the @ before the . such as a@.com
        //An equivalence class I noticed that was missing is those without proper endings (such as com, edu, org, etc...)
        //An equivalence class I noticed that was missing is those without letters before the @
        //An equivalence class I noticed that was missing is those without a period
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));

        //Negative equivalence cases
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -1));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -1.1));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -1.11));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -1.111));

        //Positive border case
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 1.111));
    }
    @Test
    void isAmountValid() {
        assertTrue(BankAccount.isAmountValid(0)); //Border case, separates positive and negatives and their equivalence cases

        assertTrue(BankAccount.isAmountValid(11)); //Equivalence case, valid integer
        assertTrue(BankAccount.isAmountValid(1.1)); //Equivalence case, valid float with 1 decimal
        assertTrue(BankAccount.isAmountValid(1.11)); //Equivalence case, valid float with 1 decimal

        assertFalse(BankAccount.isAmountValid(-1)); //Equivalence case, invalid negative integer
        assertFalse(BankAccount.isAmountValid(-1.1)); //Equivalence case, invalid negative float with 1 decimal
        assertFalse(BankAccount.isAmountValid(-1.11)); //Equivalence case, invalid negative float with 2 decimals

        assertFalse(BankAccount.isAmountValid(1.111)); //Border case, too many decimals (positive)
        assertFalse(BankAccount.isAmountValid(-1.111)); //Border case, too many decimals (negative)
    }
    @Test
    void depositTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 100);
        bankAccount.deposit(100);
        assertEquals(200, bankAccount.getBalance()); //Equivalence case of depositing a positive integer

        bankAccount.deposit(100.1);
        assertEquals(300.1, bankAccount.getBalance()); //Equivalence case for depositing a positive float with one decimal

        bankAccount.deposit(100.01);
        assertEquals(400.11, bankAccount.getBalance()); //Equivalence case for depositing a positive float with two decimals

        bankAccount.deposit(0);
        assertEquals(400.11, bankAccount.getBalance()); //Border case for depositing 0

        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-1)); //Equivalence case for negative integers
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-1.1)); //Equivalence case for negative floats to one decimal place
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-1.11)); //Equivalence case for negative floats to two decimal places
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-1.111)); //Equivalence case for negative floats to three decimal places

        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(1.111)); //Border case for inputs with too many decimal places

    }
    @Test
    void transferTest() {
        BankAccount bankAccount1 = new BankAccount("a@b.com", 100);
        BankAccount bankAccount2 = new BankAccount("a@b.com", 100);

        bankAccount1.transfer(100, bankAccount1, bankAccount2); //Equivalence case of transferring a positive integer with correct funding
        assertEquals(0, bankAccount1.getBalance()); // Border case of 0 for funds
        bankAccount2.transfer(100, bankAccount2, bankAccount1); // Border case of transferring money into an account that has 0 funds
        assertEquals(bankAccount1.getBalance(), bankAccount1.getBalance());

        //Illegal Cases
        assertThrows(IllegalArgumentException.class, () -> bankAccount1.transfer(10000, bankAccount1, bankAccount2)); //Border case, transferring funds that we don't have
        assertThrows(IllegalArgumentException.class, () -> bankAccount1.transfer(-10, bankAccount1, bankAccount2)); //Equivalence case, negative transfer amount\
        assertThrows(IllegalArgumentException.class, () -> bankAccount1.transfer(10.123, bankAccount1, bankAccount2)); //Border case, too many decimals
        assertThrows(IllegalArgumentException.class, () -> bankAccount1.transfer(-1.1, bankAccount1, bankAccount2)); //Equivalence case, negative float with one decimal
        assertThrows(IllegalArgumentException.class, () -> bankAccount1.transfer(-1.11, bankAccount1, bankAccount2)); //Equivalence case, negative float with one decimal
        assertThrows(IllegalArgumentException.class, () -> bankAccount1.transfer(100, bankAccount1, bankAccount1)); //Equivalence case, bank accounts are the same


    }

}