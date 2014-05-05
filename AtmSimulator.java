/**
 * Author: Bhavana Dalgo Thomas
 * Date: May 05, 2014
 * Time: 11:30:18 PM
 */
import java.util.InputMismatchException;
import java.util.Scanner;

public class AtmMachine {

	// Assumption of the balance amount and how many each of $20 and $50
	private double availableBal = 1500;
	private double totalBal = 1000;
	private int noofTwenty = 25;
	private int noofFifty = 10;
	private int FIFTY_NOTE = 50;
	private int TWENTY_NOTE = 20;
	 

	Scanner input = new Scanner(System.in);

	// Input the pin
	public int userPin() {
		System.out.print("Enter your pin number: ");
		int pin;
		pin = input.nextInt();

		return pin;
	}

	// Calls the method for entering account number , pin & main menu
	public void startAtm() {
		
		userPin();
		drawMainMenu();
	}

	// Main menu
	public void drawMainMenu() {
		int selection;

		System.out.println("\nATM main menu:");
		System.out.println("1 - View account balance");
		System.out.println("2 - Withdraw funds");
		System.out.println("3 - Add funds");
		System.out.println("4 - Terminate transaction");
		System.out.print("Choice: ");
		selection = input.nextInt();

		switch (selection) {
		case 1:
			viewAccountInfo();
			break;
		case 2:
			withdraw();
			break;
		case 3:
			addFunds();
			break;
		case 4:
			System.out.println("Thank you for using this ATM!!! goodbye");
		default:
			System.out.println("Invalid choice");
			drawMainMenu();
		}
	}

	// Displays account balance
	public void viewAccountInfo() {
		System.out.println("Account Information:");
		System.out.println("\t--Total balance: $" + totalBal);
		System.out.println("\t--Available balance: $" + availableBal);
		drawMainMenu();
	}

	// Adding new funds to the account
	public void deposit(int depAmount) {
		System.out.println("\n***Please insert your money now...***");
		totalBal = totalBal + depAmount;
		availableBal = availableBal + depAmount;
	}
	
	
	// checks if the amount can be dispensed as 20's and 50's 
	public boolean checkLegalCombination(int amount){
		
		boolean legal_combination = false;
		int temp  = 0;
		
		// checks if the amount can be dispensed as 50's or 20's
		if((amount % FIFTY_NOTE) == 0 || (amount % TWENTY_NOTE) == 0 )
			legal_combination = true;

		temp = amount%FIFTY_NOTE;
		
		if(temp % TWENTY_NOTE ==0)
			legal_combination = true;
		
		return legal_combination;
	}

	public int[] findNotesCombination(int amount) {

		int fiftyNotesRequired = 0;
		int twentyNotesRequired = 0;
		int[] noOfNotes = { 0, 0 };
		int temp = 0;

		fiftyNotesRequired = amount / FIFTY_NOTE;

		//checks if the amount can be withdrawn as 50's
		if (fiftyNotesRequired <= noofFifty) {
			if ((amount % FIFTY_NOTE) == 0) {
				noOfNotes[0] = fiftyNotesRequired;
				noOfNotes[1] = 0;
			} else
				temp = amount - fiftyNotesRequired * FIFTY_NOTE;
		} else {
			fiftyNotesRequired = noofFifty;
			temp = amount - noofFifty * FIFTY_NOTE;
		}

		// checks if the money can be withdrawns as combination of 50's and 20's
		twentyNotesRequired = temp / TWENTY_NOTE;
		if (temp % TWENTY_NOTE == 0) {
			if (twentyNotesRequired <= noofTwenty) {
				noOfNotes[0] = fiftyNotesRequired;
				noOfNotes[1] = twentyNotesRequired;
			} else
				System.out
						.println("Sorry ....There are not enough  notes in this ATM Machine to make this combination");

		} else {

			// checks if the money can be withdrawn as 20's
			twentyNotesRequired = amount / TWENTY_NOTE;
			if (amount % TWENTY_NOTE == 0) {
				if (twentyNotesRequired <= noofTwenty) {
					noOfNotes[0] = 0;
					noOfNotes[1] = twentyNotesRequired;
				}
				else{
					System.out.println("The machine does not have enough 20's");
				}
					
			}

			if (noofFifty == 0 ) {
				System.out.println("The machine does not have enough 50's");
			}
			if (noofTwenty == 0 ) {
				System.out.println("The machine does not have enough 20's");
			}
			
		}

		return noOfNotes;
	}

	
	// calls the method to check of money can be withdrawn as a combination of
	// 50's and 20's
	// updates the balance and the number of 50's and 20's
	public void checkNsf(int withdrawAmount) {
		if (totalBal - withdrawAmount < 0)
			System.out
					.println("\n***ERROR!!! Insufficient funds in the ATM***");
		else {

			int[] combinationNotes = { 0, 0 };
			
			
			if (checkLegalCombination(withdrawAmount)) {
				
				combinationNotes = findNotesCombination(withdrawAmount);

				if (combinationNotes[0] > 0 || combinationNotes[1] > 0) {
					System.out.println("$50 Notes: " + combinationNotes[0]
							+ "\n$20 Notes: " + combinationNotes[1]);
					noofFifty = noofFifty - combinationNotes[0];
					noofTwenty = noofTwenty - combinationNotes[1];
					totalBal = totalBal - withdrawAmount;
					availableBal = availableBal - withdrawAmount;
					System.out.println("\n***Please take your money now...***");
				} else {

				}
			}
			else{
				System.out.println("Please enter a legal combination of $50 & $20");
			}
		}
		

	}

	// Adding money to the account
	public void addFunds() {
		int addSelection;

		System.out.println("Deposit funds:");
		System.out.println("1 - $20");
		System.out.println("2 - $40");
		System.out.println("3 - $60");
		System.out.println("4 - $100");
		System.out.println("5 - Back to main menu");
		System.out.print("Choice: ");
		addSelection = input.nextInt();

		switch (addSelection) {
		case 1:
			deposit(20);
			drawMainMenu();
			break;
		case 2:
			deposit(40);
			drawMainMenu();
			break;
		case 3:
			deposit(60);
			drawMainMenu();
			break;
		case 4:
			deposit(100);
			drawMainMenu();
			break;
		case 5:
			drawMainMenu();
			break;
		}
	}

	// withdrawing money from the account
	public void withdraw() {
		int withdrawSelection;

		System.out.println("1 - Enter the amount");
		withdrawSelection = input.nextInt();
		checkNsf(withdrawSelection);
		drawMainMenu();

	}

	public static void main(String args[]) {
		AtmMachine myAtm = new AtmMachine();
		try {
			myAtm.startAtm();
		} catch (InputMismatchException e) {
			System.out
					.println("Invalid Input.... Try running the program again");
		}
	}
}
