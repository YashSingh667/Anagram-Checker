import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class Anagram {
    public static void main(String[] args) throws FileNotFoundException {
        File fileVocab = new File("./" + args[0]);
        Scanner sVocab = new Scanner(fileVocab);
        //Vector chekr = new Vector();


        myHashMap hashTab = new myHashMap(2*sVocab.nextInt() + 1, 31);
        sVocab.nextLine();

        while (sVocab.hasNextLine()){
            String st = sVocab.nextLine();
            hashTab.insert(st, hashTab.seedVar);
            //chekr.add(st);
        }

        File fileInp = new File("./" + args[1]);
        Scanner sInput =  new Scanner(fileInp);
        int numLoop = sInput.nextInt();
        sInput.nextLine();
        for (int i = 0; i < numLoop; i++) {
            String myWord = sInput.nextLine();
            Vector finalOut = new Vector();

            Vector noSpace = new Vector();
            allCombs(myWord, "", noSpace);
            for (int j = 0; j < noSpace.size(); j++) {
                if (hashTab.search((String) noSpace.get(j), hashTab.seedVar) && !finalOut.contains(noSpace.get(j)) && !myWord.equals(noSpace.get(j))) {
                    finalOut.add(noSpace.get(j));
                }
            }

            //noSpace = null;

            //Vector oneSpace =  new Vector();

            for (int j = 0; j < noSpace.size(); j++) {
                String thisStr = (String) noSpace.get(j);
                for (int k = 3; k < thisStr.length() - 3; k++) {
                    String modString = thisStr.substring(0, k) + " " + thisStr.substring(k);
                    int checkCountOne = 0;
                    String[] words = modString.split(" ");
                    if (words.length == 2) {

                        for (int l = 0; l < words.length; l++) {
                            if (hashTab.search(words[l], hashTab.seedVar)) {
                                checkCountOne += 1;
                            }
                        }
                    }
                    if (checkCountOne == 2 && !finalOut.contains(modString)) {
                        finalOut.add(modString);

                        //oneSpace.add(modString);

                    }
                }
            }




            //Vector twoSpace =  new Vector();
            for (int j = 0; j < noSpace.size(); j++) {
                String currStr = (String) noSpace.get(j);
                for (int k = 3; k < currStr.length() -3; k++) {
                    for (int l = k + 3; l <= currStr.length() -3; l++) {
                        String modStringTwo =  currStr.substring(0,k) + " " + currStr.substring(k,l) + " " + currStr.substring(l);
                        int checkCountTwo = 0 ;
                        String[] wordSet = modStringTwo.split(" ");
                        if (wordSet.length == 3){
                            for (int z = 0; z < wordSet.length; z++) {
                                if (hashTab.search(wordSet[z], hashTab.seedVar)){
                                    checkCountTwo += 1;
                                }
                            }
                        }
                        if (checkCountTwo == 3 && !finalOut.contains(modStringTwo)){
                            finalOut.add(modStringTwo);
                        }
                        //twoSpace.add(modStringTwo);
                    }
                }
            }

//            for (int j = 0; j < twoSpace.size(); j++) {
//                System.out.println(twoSpace.get(j));
//            }

//            for (int j = 0; j < twoSpace.size(); j++) {
//                String thisStr = (String) twoSpace.get(j);
//
//                int checkCountTwo = 0 ;
//                String[] wordSet = thisStr.split(" ");
//                if (wordSet.length == 3){
//                    for (int k = 0; k < wordSet.length; k++) {
//                        if (hashTab.search(wordSet[k], hashTab.seedVar)){
//                            checkCountTwo += 1;
//                        }
//                    }
//                }
//                if (checkCountTwo == 3 && !finalOut.contains(thisStr)){
//                    finalOut.add(thisStr);
//                }
//
//            }
            //twoSpace = null;
            //noSpace = null;


            Collections.sort(finalOut);

            for (int j = 0; j < finalOut.size(); j++) {
                System.out.println(finalOut.get(j));
            }
            //finalOut = null;
            System.out.println(-1);
        }



    }

    public static void allCombs(String str, String out , Vector myOut){
        int n = str.length();

        if( n == 0){
            myOut.add(out);
        }

        for (int i = 0; i < n; i++) {
            char thisChar = str.charAt(i);
            String remainingStr =  str.substring(0,i) + str.substring(i+1);
            allCombs(remainingStr,out + thisChar, myOut);
        }
    }


}
 class myHashMap{
    int size;
    public String[] array;
    int collisions = 0;
    int seedVar;

    public myHashMap(int i, int sd){
        array = new String[i];
        size = i;
        seedVar =sd;
    }

    private int myHashKeyMaker(String str, int base){
        int answer = 0;
        for (int i = 0; i < str.length(); i++) {
            answer += ((int)str.charAt(i))*Math.pow((base+i),2);
        }
        answer = answer % size;

        return answer;

    }

    public void insert(String str, int seed){
        int key  = myHashKeyMaker(str, seed);
        //System.out.println(" this string " + str + " insertKey = " + key);
        if (array[key] == null){
            array[key] = str;

        }
        else if (array[key] != null){
            collisions += 1;
            insert(str, seed + 1);
        }


    }

    public boolean search(String strp, int seed){
        int thisKey = myHashKeyMaker(strp, seed);
        //System.out.println(" this string " + strp + " searchKey = " + thisKey);
        //System.out.println(" this key stores " + array[thisKey]);
        if (strp.equals(array[thisKey])){
            return true;
        }
        else if (array[thisKey] != null && array[thisKey] !=  strp){
            return search(strp, seed + 1);
        }
        else {
            return false;
        }
    }
}
