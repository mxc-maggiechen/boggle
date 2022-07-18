//===========================================================================================================================================================================================================
//2D Arrays, GUI, Files & Problem-Solving Programming Assignment
//Maggie Chen
//10th, March, 2022
//Java 17
//===========================================================================================================================================================================================================
//Problem Definition - Create a 6x6 2D array filled with random English letters with a specified percentage of vowels; check if a user entered word exists on the board as connected letters horizontally,
//                     vertically, or diagonally; and search a word list to determine if the user input is a valid English word
//Input - User inputs strings in a textfield and press available buttons to performed the functions defined above
//Output - The program will generate and display the above described 2D arrays, whether the entered word exists on the board or not, and if so, if the input is a valid English word
//Process - Randomization of letters: The program will randomly select a percentage of vowels from the lowest percentage range to the highest percent range and randomly pick the chosen percentage of vowels
//          to put on the board. The program then randomly select the rest of the required letters from a String array containing all the letters of the English alphabet. Once an array of all the letters
//          that is required for the board is generated, the program will place all the letters onto the gameboard in a random manner.
//        - Checking for a word on the board:
//        - Checking for if the word is a valid English word:
//===========================================================================================================================================================================================================
//List of Identifiers - let ARRAY_WIDTH represent the width of the 2D array of random English letters (type int)
//                    - let ARRAY_LENGTH represent the length of the 2D array of random English letters (type int)
//                    - let ARRAY_SIZE represent the number of elements present in the 2D array of random English letters (type int)
//                    - let VOWELS represent the selection of vowels that the program may choose from to put on the D array of random English letters (type int array)
//                    - let CONSONANTS represent the selection of consonants that the program may choose from to put on the D array of random English letters (type int[])
//                    - let LOWEST_PERCENT_VOWEL represent the lowest percent of vowels that could be on the board (type int)
//                    - let HIGHEST_PERCENT_VOWEL represent the highest percent of vowels that could be on the board (type int)
//                    - let dictionary represent the list of valid English words that the program can check if the user input is a valid English word (type ArrayList<String>)
//                    - let array represent the 2D array that will store the randomized assortion of English letters from the arrayList letters (type int[][])
//                    - let letters represent a randomized assortion of English letters that the program will put on the 2D arry of random English letters (type ArrayList<Integer>)
//===========================================================================================================================================================================================================
 
//import packages required for program's backend
import java.lang.Math;
import java.util.Scanner;
import java.util.ArrayList;
 
//import packages required for GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
 
public class Discount_Boggle extends JFrame implements ActionListener{
    //instantiate variables required for program's backend
    //global final variables
    static final int ARRAY_WIDTH=6;
    static final int ARRAY_LENGTH=6;
    static final int ARRAY_SIZE = ARRAY_WIDTH*ARRAY_LENGTH;
    static final String[] VOWELS = {"A", "E", "I","O","U"};
    static final String[] ALPHABET = {"Q","W","R","T","Y","P","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M","A","E","I","O","U"};
    static final double LOWEST_PERCENT_VOWEL = 1.0/4.0;
    static final double HIGHEST_PERCENT_VOWEL = 1.0/3.0;

    static final int MAX_DISPLAY_WORDS = 10;
 
    //global variables
    static ArrayList<String> dictionary = new ArrayList<String>();
    static ArrayList<String> letters = new ArrayList<String>();
    static String[][] array = new String [ARRAY_WIDTH][ARRAY_LENGTH];
    ArrayList<String> validBoardWords = new ArrayList<String>();
   
    //instantiate GUI component variables
    JLabel titleLabel = new JLabel("DISCOUNT BOGGLE");
    JLabel instructionLabel = new JLabel("Enter a word below to play.");
    JTextField inputField = new JTextField(15);
    JButton confirmButton = new JButton("Enter");
    JLabel foundWordLabel = new JLabel();
    JLabel foundDictionaryLabel = new JLabel();
    JButton resetButton = new JButton ("Reset Board");
    //declare 2D array of labels for displaying the board
    JLabel[][] boardLabels = new JLabel[ARRAY_WIDTH][ARRAY_LENGTH];
    JLabel[] validBoardLabels = new JLabel [MAX_DISPLAY_WORDS];
    JLabel wordBankLabel = new JLabel("The valid English words that you found on this board are:");
 
    //declare variables to store user inputs and interactions
    String buttonPress;
    String input;
   
    //instantiate GUI panel variables
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JPanel p3 = new JPanel();
    JPanel p4 = new JPanel();
    JPanel p5 = new JPanel();
    JPanel p6 = new JPanel();
    JPanel p7 = new JPanel();
    JPanel p8 = new JPanel();
    JPanel p2Left = new JPanel();
    JPanel p2Centre = new JPanel();
    JPanel p2Right = new JPanel();
 
 
    /*
     * This constructor method initializes and adds in GUI components and panels
     *
     * List of Local Variables:
     * layout1 - layout for the entire GUI frame (type GridLayout)
     * layout2 - layout for the gameboard (type GridLayout)
     * centerLayout - layout for p2, the panel in which the gameboard is stored (type GridLayout)
     * @param none
     * @throws Exception
     * @return void
    */
    public Discount_Boggle() throws Exception{
        //Set the GUI frame
        setTitle("Discount Boggle");
        setSize(600,800);
        setResizable(true);
       
        //Instantiate the JLabels in the 2D array of labels
        for(int i=0;i<array.length;i++){
            for(int k=0;k<array[0].length;k++){
                boardLabels[i][k] = new JLabel(array[i][k]);
 
            }
        }
 
        //Call the createArray program to create a randomized array of letters for the game board
        createArray(boardLabels);
       
        //Declare the layout for the frame
        GridLayout layout1 = new GridLayout(9,1);
        setLayout(layout1);
 
        //Declare the layouts for p2, the panel where the gameboard is displayed
        GridLayout layout2 = new GridLayout(ARRAY_WIDTH, ARRAY_LENGTH);//gameboard layout
        GridLayout centerLayout = new GridLayout(1,3);//layout to centre the gameboard on p2 panel
        GridLayout layout3 = new GridLayout(1,MAX_DISPLAY_WORDS);
        p2.setLayout(centerLayout);
        p2Centre.setLayout(layout2);
        p7.setLayout(layout3);
 
        //add actionaListener to buttons and textfields
        resetButton.addActionListener(this);
        confirmButton.addActionListener(this);
        inputField.addActionListener(this);
 
        //add components to their respective panels
        p1.add(titleLabel);
        for(int i=0;i<ARRAY_WIDTH;i++){//add gameboard letters to p2Centre panel
            for(int j=0;j<ARRAY_LENGTH;j++){
                p2Centre.add(boardLabels[i][j]);
            }
        }
        p2.add(p2Left);
        p2.add(p2Centre);
        p2.add(p2Right);
        p3.add(instructionLabel);
        p4.add(inputField);
        p4.add(confirmButton);
        p4.add(resetButton);
        p5.add(foundWordLabel);
        p6.add(foundDictionaryLabel);
        for(int i=0;i<MAX_DISPLAY_WORDS;i++){
            validBoardLabels[i] = new JLabel();
            p7.add(validBoardLabels[i]);
        }
        p8.add(wordBankLabel);
        
       
        //add panels to frame
        add(p1);
        add(p3);
        add(p2);
        add(p4);
        add(p5);
        add(p6);
        add(p8);
        add(p7);
 
        setVisible(true);
 
    }    
 
    /*
     * This procedure method is called automatically and is used to organize the calling of the other methods defined in the class and to create a instance of the class for the GUI
     *
     * List of Local Variables:
     * FileReader - Scanner instance that reads in the file "wordlist.txt" for the program to access the provided dictionary (type Scanner)
     * @param none
     * @throws Exception
     * @return void
    */
    public static void main(String[] args) throws Exception{
        Scanner fileReader = new Scanner( new java.io.File("wordlist.txt"));
        while(fileReader.hasNext()){
            dictionary.add(fileReader.next());
        }        fileReader.close();
        new Discount_Boggle();
    }
 
    /*
     * This procedure method is called automatically and is used to organize the calling of the other methods defined in the class
     *
     * @param ActionEvent event
     * @return void
    */
    @Override
    public void actionPerformed(ActionEvent event) {
 
        //receive and store information from user input into GUI
        buttonPress = event.getActionCommand();
        input = inputField.getText();
 
        //actions for if user presses confirmButton
        if(buttonPress.equals("Enter")){
        	
        	resetBoardColours(boardLabels);
            //when user didn't provide anything in inputField
            if(input.length()==0){
                foundWordLabel.setText("Please enter a word to play! Words can be formed in straight lines horizontally, vertically, or diagonally!");
                foundDictionaryLabel.setText("");
            }
 
            //check if word is on board
            else if(checkWord(input, boardLabels)){
                foundWordLabel.setText("The word you entered is ON the board!");
 
                //check if word is a valid English word
                if(checkDictionary(dictionary, input)){
                    foundDictionaryLabel.setText("The word you entered is a valid English word!");
                    if(!validBoardWords.contains(input.toUpperCase())){
                        if(validBoardWords.size()==MAX_DISPLAY_WORDS){
                            validBoardWords.remove(0);
                        }
                        validBoardWords.add(input.toUpperCase());
                        for(int i=0;i<validBoardWords.size();i++){
                            validBoardLabels[i].setText(validBoardWords.get(i));
                        }
                    }
                }else{
                    foundDictionaryLabel.setText("The word you entered is NOT a valid English word!");
                }
            }else{
                foundWordLabel.setText("The word you entered is NOT on the board.");
                foundDictionaryLabel.setText("");
            }
 
        //actions for if user presses resetButton
        }else if(buttonPress.equals("Reset Board")){
            validBoardWords.clear();
            for(int i=0; i<validBoardLabels.length;i++){
                validBoardLabels[i].setText("");
            }
            createArray(boardLabels);
        }
    }
   
    /*
     * This procedure method is used to create a randomized 6x6 board of English letters with a random percentage (ranging from 25-33% of vowels present
     *
     * List of Local Variables:
     * percentVowels - stores the percentage of vowels that will appear on the board (type double)
     * numVowels - stores the number of vowels that will appear on the board based on percentage indicated by percentVowels (type int)
     * randomIndex - stores a randomly generated number that indicates an index in the pre-set list of cosonants and vowels for the puposes of randomly choosing English letters (type int)
     * count - tracks the number of empty cells in a 2D array when filling it with the list of randomly generated letters (type int)
     * @param JLabel[][] board
     * @return void
    */
    public static void createArray (JLabel[][] board){
 
        int randomIndex;
 
        //Generates a random lowest percentage of vowels between the range of HIGHEST_PERCENT_VOWEL and LOWEST_PERCENT_VOWEL to be added to the 2D array of random English letters
        double percentVowels = Math.random()*(HIGHEST_PERCENT_VOWEL-LOWEST_PERCENT_VOWEL)+LOWEST_PERCENT_VOWEL;
        //Generate the number of vowels that will be present in the 2D array of random English letters based on the random percentage generated above
        int numVowels = (int) Math.round(ARRAY_SIZE*percentVowels);
 
        //Randomly select "numVowels" of vowels from the pre-set list of vowels and add them to the ArrayList<String> letters
        for(int i=0; i<numVowels;i++){
            randomIndex = (int)(Math.random()*5);
            letters.add(VOWELS[randomIndex]);
        }
 
        //Randomly select the rest of the letters from the pre-set list of the alphabet and add them to the ArrayList<String> letters
        for(int i=numVowels; i<ARRAY_SIZE;i++){
            randomIndex = (int)(Math.random()*21);
            letters.add(ALPHABET[randomIndex]);
        }
 
        //Randomly set the list of randomized letters to the 2D array and the GUI
        int count=0;
        for(int i=0;i<array.length;i++){
            for(int k=0;k<array[0].length;k++){
                randomIndex = (int)(Math.random()*(ARRAY_SIZE-count));
                array[i][k] = letters.get(randomIndex);
                board[i][k].setText(array[i][k]);
                board[i][k].setForeground(Color.BLACK);
                letters.remove(randomIndex);
                count++;
            }
        }
    }
   
    /*
     * This boolean return type method checks if a word exists on the 2D array
     *
     * List of Local Variables:
     * iMove - stores values that manipulates the row index of the 2D array for letter searching purposes (type int[])
     * jMove - stores values that manipulates the column index of the 2D array for letter searching purposes (type int[])
     * direction - stores the "direction" (or index corresponding to the iMove and jMove) in which the program will search in upon finding the first 2 letters of the word (type int)
     * tracki - stores the i value of a letter in the user's input if the word is found on the board, used for GUI colour purposes (type ArrayList<Integer>)
     * trackj - stores the j value of a letter in the user's input if the word is found on the board, used for GUI colour purposes (type ArrayList<Integer)
     * @param String guess, JLabel[][] boardLabel
     * @return boolean
    */
    public static boolean checkWord(String guess, JLabel[][] boardLabel){
        //An array that stores the possible directions the program may search for the second letter, the index of these arrays will then represent a direction:
        //left, right, up, down, left down, right down, left up, right up
        int[] iMove = {0, 0, -1, 1, 1, 1, -1, -1};
        int[] jMove = {-1, 1, 0, 0, -1, 1, -1, 1};
        ArrayList<Integer> tracki = new ArrayList<Integer>();
        ArrayList<Integer> trackj = new ArrayList<Integer>();
        int direction;
 
        //iterate through board to find the first letter
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array[0].length;j++){
                direction=-1;
 
                //check if the current array position is the first letter of guess
                if(guess.substring(0,1).compareToIgnoreCase(array[i][j])==0){
                   
                	tracki.add(i);
                	trackj.add(j);
                    //check if guess is only 1 letter
                    if(guess.length()==1){
                    	boardLabel[tracki.get(0)][trackj.get(0)].setForeground(Color.BLUE);
                        return true;
                    }
 
                    //check around the first letter for the second letter
                    for(int k =0; k<8;k++){
                        //check if the coordinate is inside the grid AND if the letter at the coordinate match the second letter of the word
                        if(i+iMove[k]>=0&&i+iMove[k]<=array.length-1&&j+jMove[k]>=0&&j+jMove[k]<=array[0].length-1&&guess.substring(1,2).compareToIgnoreCase(array[i+iMove[k]][j+jMove[k]])==0){
                           
                            //stores direction of the second letter so that the program knows which direction to continue in when searching for the rest of the guess
                            direction=k;
                            tracki.add(i+iMove[k]);
                        	trackj.add(j+jMove[k]);
                            
                            //check if the guess is only 2 letters
                            if(guess.length()==2){
                            	boardLabel[tracki.get(0)][trackj.get(0)].setForeground(Color.BLUE);
                            	boardLabel[tracki.get(1)][trackj.get(1)].setForeground(Color.BLUE);
                                return true;
                            }
 
                            //search in the previously stored direction for the rest of the word
                            for(int m=2;m<guess.length();m++){
                                tracki.add(i+iMove[direction]*m);
                                trackj.add(j+jMove[direction]*m);
                                if(i+iMove[direction]*m>=0&&i+iMove[direction]*m<=array.length-1&&j+jMove[direction]*m>=0&&j+jMove[direction]*m<=array[0].length-1&&m==guess.length()-1&&guess.substring(m,m+1).compareToIgnoreCase(array[i+iMove[direction]*m][j+jMove[direction]*m])==0){
                                    if(m==guess.length()-1){
                                    	for(int s=0;s<tracki.size();s++){
                                            boardLabel[tracki.get(s)][trackj.get(s)].setForeground(Color.BLUE);
                                        }
                                        return true;
                                    }
                                }
                            }
                            while(tracki.size()>1){
                                tracki.remove(tracki.size()-1);
                                trackj.remove(trackj.size()-1);
                            }
                        }
                    }
                }
                tracki.clear();
                trackj.clear();
              }
           }
        
        //return false if the word is not found
        return false;
    }
 
    /*
     * This boolean return type method checks if a word exists on the 2D array
     *
     * List of Local Variables:
     * low - stores the low range of the sorted list, used for binary search
     * mid - stores the middle index between the low and high index, guess will be compared to the value stored at this index, used for binary search
     * high - stores the high range of the sorted list, used for binary search
     * @param ArrayList<String> dictionary, String guess
     * @return boolean
    */
    public static boolean checkDictionary(ArrayList<String> dictionary, String guess){
       
        //declare the local variables required
        int low=0;
        int mid;
        int high = dictionary.size()-1;
 
        //binary search for guess in the dictionary
        while(low<=high){
            mid = (high+low)/2;
 
            //check if the target is equal to, less than, or greater than the current word
            if (guess.compareToIgnoreCase(dictionary.get(mid))==0){
                return true;
            }
            else if(guess.compareToIgnoreCase(dictionary.get(mid))<0){
                high = mid-1;
            }else{
                low = mid+1;
            }  
        }
        return false;
    }
    
    /*
     * This procedural method resets the colours of all the letters on the board to black, for GUI purposes
     *
     * List of Local Variables:
     * @param JLabel[][] boardLabel
     * @return void
    */
    public static void resetBoardColours (JLabel[][] boardLabel){
    	for(int i=0;i<array.length;i++){
        	for(int j=0;j<array[0].length;j++){
        		boardLabel[i][j].setForeground(Color.BLACK);
        	}
        }
        
    }
   
}
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 

