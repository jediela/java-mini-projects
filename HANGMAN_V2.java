import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HANGMAN_V2 {

	public static void main(String[] args) {
		//Import Objects
		Scanner input = new Scanner (System.in);
		Random rand = new Random();
		
		//Variables
		String decision, word, topic;
		int category, wrong = 0, index, turn = 1;
		char userGuess;
		
		//2D array for all the words
		String words [][] = {{"basketball", "hockey", "baseball", "lacrosse"}, //Sports
							 {"lizard", "dog", "parrot", "fish"},			   //Pets
							 {"math", "science", "english", "history"},		   //Subjects
							 {"apple", "samsung", "microsoft", "intel"},	   //Tech companies
							 {"honda", "toyota", "mazda", "audi"}};			   //Car brands
		
		//Array for the subjects
		String subject[] = {"Sports", "Pets", "School Subjects", "Tech Companies", "Car Brands"};
		
		//ArrayList for user guesses and letters of the selected word
		ArrayList<Character> lettersGuessed = new ArrayList<Character>();
		ArrayList<Character> wordLetters = new ArrayList<Character>();
		ArrayList<Character> blank = new ArrayList<Character>();
		
		//Do while to allow user to play multiple times
		do {
			
			//2D array for the hangman
			char [][] hangman =  {  { ' ', ' ', ' ',},
									{ ' ', ' ', ' ',},
									{ ' ', ' ', ' ',}};
			
			//Intro to game
			category = gameIntro();
			
			//Choose a random word from selected category
			word = words[category][rand.nextInt(words[category].length)];
			topic = subject[category];
			
			//Tell user how long the selected word is
			System.out.println("\n------HINTS------");
			System.out.println("We have selected a word from the **" + topic + "** category");
			System.out.println("Your word has *" + word.length() + "* letters");

			//Add letters of selected word to arraylist and create the right amount of blank spaces
			for(int i = 0; i < word.length(); i++) {
				wordLetters.add(word.charAt(i));
				blank.add('_');
			}
			
			//MAIN GAME
			//Loop until user guesses wrong 6 times
			do {
				
				//Display turn number
				System.out.println("\n====================================");
				System.out.println("\t\tTURN " + turn);
				System.out.println("====================================");
				turn++;
				
				//Get user to guess a letter
				//Loop to ensure user doesn't guess same letter twice
				do {
					System.out.print("\nGuess a letter: ");
					userGuess = Character.toLowerCase(input.next().charAt(0));
					if(lettersGuessed.contains(userGuess)) {
						System.out.println("You already guessed that letter!");
					}
				}while(lettersGuessed.contains(userGuess));
				
				//Add user guess to letters guessed arraylist
				lettersGuessed.add(userGuess);
				
				//Correct guess
				if(wordLetters.contains(userGuess)) {
					System.out.println("Correct guess!");
					
					//
					do {
						index = wordLetters.indexOf(userGuess);
						wordLetters.set(index, ' ');
						blank.set(index, userGuess);
					}while(wordLetters.contains(userGuess));
				}
				
				//Incorrect guess
				else {
					System.out.println("Incorrect guess");
					wrong++;
					//Draw hangman based on how much user got wrong
					hangman(wrong, hangman);
				}
				
				//Print hangman, user guess, and word progress
				System.out.println("\n-------HANGMAN-------");
				printHangman(hangman);
				System.out.println("---------------------");
				System.out.println("\n---Word---");
				printArraylist(blank);
				System.out.println("\n---Letters Guessed---");
				printArraylist(lettersGuessed);
				
				//Break from loop if user guesses the word correctly
				if(!blank.contains('_')) {
					System.out.println("\nYou Win!");
					System.out.println("The word was: " + word.toUpperCase());
					break;
				}
				
				//Print lose message if user did not guess the word
				if(wrong == 6) {
					System.out.println("\nGame Over");
					System.out.println("The word was: " + word.toUpperCase());
				}
					
			}while(wrong < 6);
			
			//Ask user if they want to play again
			decision = decision();
			
			//Reset values if user wants to play again
			if(decision.equalsIgnoreCase("Y")) {
				lettersGuessed.clear();
				wordLetters.clear();
				blank.clear();
				wrong = 0;
				turn = 1;
			}
			
		}while(decision.equalsIgnoreCase("Y"));
		
		//Farewell
		System.out.println("Thank you for playing!");
		
		input.close();
		
	}//end of main method

	//Method to display game instructions
	public static int gameIntro() {
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner (System.in);
		int category;
		//Print instructions and categories
		System.out.println("\n====================================================");
		System.out.println("\t\t--------HANGMAN--------");
		System.out.println("====================================================");
		System.out.println("\n---------INSTRUCTIONS---------");
		System.out.println("To play simply choose one of the 5 categories");
		System.out.println("After choosing a category we will choose a word related to the category you have selected");
		System.out.println("You have an unlimited amount of tries, but if you guess wrong 6 times the game is over");
		System.out.println("\n---------CATEGORIES---------");
		System.out.println("1 - Sports");
		System.out.println("2 - House Pets");
		System.out.println("3 - School Subjects");
		System.out.println("4 - Tech Companies");
		System.out.println("5 - Car Brands");
		
		//Ask user what category they want
		do {
			System.out.print("\nPlease choose the number of your desired category: ");
			category = input.nextInt();
			if(category > 5 || category < 1) {
				System.out.println("Please select a valid category (1-5)");
			}
		}while(category > 5 || category < 1);
		return category-1;
	}//end of gameIntro method
	
	//Method to print an arrayList
	public static void printArraylist(ArrayList<Character> a) {
		for(int i = 0; i < a.size(); i++) {
			System.out.print(a.get(i)+ " ");
		}
		System.out.println();
	}//end of print Arraylist method 
	
	//Method to get y/n from user
	public static String decision() {
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner (System.in);
		String decision;
		
		do {
			System.out.print("\nWould you like to play again? (Y/N): ");
			decision = input.next();
			if(!decision.equalsIgnoreCase("y") && !decision.equalsIgnoreCase("n")) {
				System.out.println("Invalid. Please enter Y or N");
			}
		}while(!decision.equalsIgnoreCase("y") && !decision.equalsIgnoreCase("n"));
		return decision;
	}//end of decision method
	
	//Method to print hangman
	public static void printHangman(char [][] hangMan) {
		for(int i = 0; i < hangMan.length; i++) {
			for (int j = 0; j < hangMan[i].length; j++) {
				System.out.print(hangMan[i][j]);
			}
			System.out.println();
		}
	}//end of printHangman method
	
	// Method to print hangman status
	public static void hangman(int wrong, char [][] hangMan) {
		
		switch(wrong) {
			case 1:
				hangMan [0][1] = 'O';
				break;						
			case 2:
				hangMan [1][1] =  '|';
				break;						
			case 3:
				hangMan [1][2] = '-';
				break;						
			case 4: 
				hangMan [1][0] = '-'; 
				break;							
			case 5:
				hangMan [2][2] =  '\\';
				break;						
			case 6:
				hangMan [2][0] =  '/';
				break;	
		}//end of switch case
	}//end of hangman method
	
}//end of class