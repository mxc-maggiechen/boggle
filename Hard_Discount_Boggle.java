import java.lang.Math;
import java.util.Scanner;

import java.util.ArrayList;
public class Hard_Discount_Boggle {
    static final int ARRAY_WIDTH=6;
    static final int ARRAY_LENGTH=6;
    static final int ARRAY_SIZE = ARRAY_WIDTH*ARRAY_LENGTH;
    static final char[] VOWELS = {'A','E','I','O','U'};
    static final char[] CONSONANTS = {'Q','W','R','T','Y', 'P', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X','C','V','B','N','M'};
    static ArrayList<Character> letters = new ArrayList<Character>();
    static char[][] array = new char [ARRAY_WIDTH][ARRAY_LENGTH];
    static double lowestPercentVowel = 1.0/4.0;
    static double highestPercentVowel = 1.0/3.0;
   
   
    static Scanner sc = new Scanner(System.in);
 
    public static void main(String[] args) throws Exception{
        Scanner fileReader = new Scanner( new java.io.File("wordlist.txt"));
        final ArrayList<String> DICTIONARY = new ArrayList<String>();
        while(fileReader.hasNext()){
            DICTIONARY.add(fileReader.next());
        }
        fileReader.close();
 
        createArray();
        while(true){
            System.out.println("Enter a word!");
            String guess = getInput();
            boolean onBoard = checkWord(guess);

            if(onBoard){
            	System.out.println("The word you entered is on the board");
                if(checkDictionary(DICTIONARY,guess)){
                    System.out.println("The word you entered is a valid English word.");
                }else{
                    System.out.println("But the word you entered is not a valid English word.");
                }
            }else{
            	System.out.println("The word you entered is NOT on the board");
            }
            System.out.println();
        }

       
    }
   
    public static String getInput(){
        String input;
        while(true){
            try{
                input = sc.next();
                break;
            }
            catch(Exception e){
                System.out.println("That is not a valid input. Please try again");
            }
        }
        return input;
       
    }
   
    public static void createArray (){
        double percentVowels = Math.random()*(highestPercentVowel-lowestPercentVowel)+lowestPercentVowel;
        int numVowels = (int) Math.round(ARRAY_SIZE*percentVowels);//check
        for(int i=0; i<numVowels;i++){
            int randomIndex = (int)(Math.random()*5);
            letters.add(VOWELS[randomIndex]);
        }
        System.out.println();
        for(int i=numVowels; i<ARRAY_SIZE;i++){
            int randomIndex = (int)(Math.random()*21);
            letters.add(CONSONANTS[randomIndex]);
        }
 
 
        int count=0;
        for(int i=0;i<array.length;i++){
            for(int k=0;k<array[0].length;k++){
                int randomIndex = (int)(Math.random()*(ARRAY_SIZE-count));
                array[i][k] = letters.get(randomIndex);
                letters.remove(randomIndex);
                count++;
            }
        }
 
        for(int i=0;i<array.length;i++){
            for(int k=0;k<array[0].length;k++){
                System.out.print(array[i][k]+" ");
            }
            System.out.println();
        }
    }
   
    
    public static boolean checkWord(String guess){
        char check = guess.charAt(0);
        boolean [][] memoryMap= new boolean[ARRAY_WIDTH][ARRAY_LENGTH];
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array[0].length;j++){

                if(check==array[i][j]){
                    if(guess.length()== 1){
                        return true;
                    }


                    memoryMap[i][j] = true;
                    int [] currentPosition = {i,j};//index 0=i, index 1=j
                    ArrayList<Integer> recordi = new ArrayList<Integer>();
                    ArrayList<Integer> recordj = new ArrayList<Integer>();
                    recordi.add(i);
                    recordj.add(j);
                    for(int k=1;k<guess.length();k++){
                        check = guess.charAt(k);
                        if(k<=0){
                            k=guess.length();
                        }
                        else if(!checkSurroundingChar(currentPosition,check, memoryMap)){
                            k-=2;
                        	currentPosition[0] = recordi.get(recordi.size()-1);
                        	currentPosition[1] = recordj.get(recordj.size()-1);
                            recordi.remove(recordi.size()-1);
                            recordj.remove(recordj.size()-1);
                        }else{
                            if(k==guess.length()-1){
                                return true;
                            }
                            recordi.add(currentPosition[0]);
                            recordj.add(currentPosition[1]);
                    
                        }
                    }
                    memoryMap[i][j] = false;
                }
                
              }
           }
        return false;
    }
 
    public static boolean checkSurroundingChar(int[] currentPos, char check, boolean [][] memoryMap){
        int[] iMove = {0, 0, 1, -1, 1, 1, -1, -1}; // left, right, up, down, left up, right up, left down, right down
        int[] jMove = {-1, 1, 0, 0, -1, 1, -1, 1};
        boolean found = false;
        for(int k =0; k<8;k++){
            if(currentPos[0]+iMove[k]>=0&&currentPos[0]+iMove[k]<=array.length-1&&currentPos[1]+jMove[k]>=0&&currentPos[1]+jMove[k]<=array[0].length-1&&memoryMap[currentPos[0]+iMove[k]][currentPos[1]+jMove[k]]==false){
                if(check==array[currentPos[0]+iMove[k]][currentPos[1]+jMove[k]]){
                    currentPos[0] = currentPos[0]+iMove[k];
                    currentPos[1] = currentPos[1]+jMove[k];
                    found = true;
                }
            }
        }
        return found;
    }
   
    public static boolean checkDictionary(ArrayList<String> dictionary, String guess){
        int low=0;
        int mid;
        int high = dictionary.size()-1;
        boolean found = false;
        while(low<=high){
            mid = (high+low)/2;
            if (guess.compareToIgnoreCase(dictionary.get(mid))==0){
                found = true;
                return found;
            }
            else if(guess.compareToIgnoreCase(dictionary.get(mid))<0){
                high = mid-1;
            }else{
                low = mid+1;
            }  
        }
        return found;
    }
   
}
