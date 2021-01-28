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
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com")); //Included within the equivalence class of valid email; this is a border case as it's the minimum required characters for a valid email (considering only traditional endings such as .com, .gov, .edu, .org)
        assertFalse( BankAccount.isEmailValid("")); //Included within the equivalence classes of missing multiple required characters; this is a border case as it's the minimum characters you could select for a string: 0
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
    }

}