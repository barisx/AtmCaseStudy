/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package atmcasestudy;

/**
 *
 * @author Barış Şenyerli
 */
public class Deposit extends Transaction
{
    private double amount;
    private Keypad keypad;
    private DepositSlot  depositSlot;
    private final static int CANCELED = 0;
    
    public Deposit( int userAccountNumber, Screen atmScreen,
            BankDatabase atmBankDatabase, Keypad atmKeypad,
            DepositSlot atmDepositSlot )
    {
        super( userAccountNumber, atmScreen, atmBankDatabase );
        
        keypad = atmKeypad;
        depositSlot = atmDepositSlot;
    }
    
    @Override
    public void execute()
    {
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();
        
        amount = promptForDepositAmount();
        
        if ( amount != CANCELED )
        {
            screen.displayMessage(
            "\nPlease insert a deposit envelope containing " );
            screen.displayDollarAmount( amount );
            screen.displayMessageLine( "." );
            
            boolean envelopeRecieved = depositSlot.isEnvelopeRecieved();
            
            if ( envelopeRecieved )
            {
                screen.displayMessageLine( "\nYour envelope has been " +
                        "recieved.\nNOTE: The money just deposited will not " +
                        "be available until we verify the amount of any "+ 
                        "enclosed cash and your checks clear." );
                bankDatabase.credit( getAccountNumber(), amount );
            } // end if
            else
            {
                screen.displayMessageLine( " \nYou did not insert an "+
                        "envelope, so the ATM has canceled your transaction." );
            } // end else
        } // end if
        else
        {
            screen.displayMessageLine( "\nCanceling transaction..." );
        } // end else
    } // end method execute
    
    private double promptForDepositAmount()
    {
       Screen screen = getScreen();
       
       screen.displayMessage( "\nPlease enter a deposit amount in "+
               "CENTS (or 0 to cancel): ");
       int input = keypad.getInput();
       
       if ( input == CANCELED )
           return CANCELED;
       else 
       {
           return ( double ) input / 100; // return dollar amount
       } // end else
    } // end method promptForDepositAmount
} // end class Deposit
