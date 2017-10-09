/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.app.Activity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class AnagramDictionary extends Activity {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static int DEFAULT_WORD_LENGTH = 4;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    private ArrayList<String> wordList = new ArrayList<>();

    HashMap<String,HashSet<String>> lettersToWord=new HashMap<String, HashSet<String>>();

    HashMap<Integer,HashSet<String>> sizeToWords=new HashMap<Integer, HashSet<String>>();


    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();

            wordList.add(word);
        }


        for(int i=0;i<wordList.size();i++) {

            //hashmap creation by word length start

            int wordLength = wordList.get(i).length();

            if(sizeToWords.containsKey(wordLength)){

                sizeToWords.get(wordLength).add(wordList.get(i));

            }else {

                HashSet<String> temp1 = new HashSet<>();

                sizeToWords.put(wordLength,temp1);

                sizeToWords.get(wordLength).add(wordList.get(i));

            }


            // word length hashmap ends

            String alphaOrder = sortLetters(wordList.get(i));

            if(lettersToWord.containsKey(alphaOrder)){

                if(lettersToWord.get(alphaOrder).contains(wordList.get(i))){

                }else {

                    lettersToWord.get(alphaOrder).add(wordList.get(i));
                }

            }else {

                HashSet<String> temp = new HashSet<>();

                lettersToWord.put(alphaOrder,temp);

                lettersToWord.get(alphaOrder).add(wordList.get(i));
            }
        }


    }

    public boolean isGoodWord(String word, String base) {
        return true;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();

        String inputAlphaOrder = sortLetters(targetWord);

        if (lettersToWord.containsKey(inputAlphaOrder)) {

            HashSet wordSet = lettersToWord.get(inputAlphaOrder);

            for (Object word:wordSet) {

                result.add(word.toString());

            }

        }

        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {

        String newWord="";

        ArrayList<String> result = new ArrayList<String>();

        for (char c='a';c<='z';c++){

            newWord = word+c;

            String inputAlphaOrder = sortLetters(newWord);

            if (lettersToWord.containsKey(inputAlphaOrder)) {

                HashSet wordSet = lettersToWord.get(inputAlphaOrder);

                for (Object word1:wordSet) {

                    result.add(word1.toString());

                }

            }
        }




        return result;
    }

    public String pickGoodStarterWord() {

        String returnString ="";

        int flag = 0;

        while (flag!=1) {

            int keySetSize = lettersToWord.size();

            List keys = new ArrayList(lettersToWord.keySet());

           // List Lengthkeys = new ArrayList(sizeToWords.keySet());

           // ArrayList<Integer> intKeys = new ArrayList<Integer>(sizeToWords.keySet());

            int randomNum = 0 + (int)(Math.random() * (keySetSize-1));

            for (int i = randomNum;i<keySetSize;i++) {

                HashSet hs = lettersToWord.get(keys.get(i));

                int sizeToWordsKeyLength = keys.get(i).toString().length();

                Log.i("size word length :", String.valueOf(sizeToWordsKeyLength));
                if ((hs.size()>=MIN_NUM_ANAGRAMS)&&(sizeToWordsKeyLength==DEFAULT_WORD_LENGTH)){

                    Log.i("hello :", String.valueOf(DEFAULT_WORD_LENGTH));
                  //  Log.i("Length keys  :", String.valueOf(intKeys.get(i)));

                         Iterator iterator = hs.iterator();
                         returnString = String.valueOf(iterator.next());


                        if (DEFAULT_WORD_LENGTH == MAX_WORD_LENGTH) {
                            DEFAULT_WORD_LENGTH = 4;
                        } else {

                            DEFAULT_WORD_LENGTH = DEFAULT_WORD_LENGTH + 1;

                        }

                        flag = 1;
                        break;
                    //Iterator iterator = hs.iterator();
                     //returnString = String.valueOf(iterator.next());
                }

            }
        }

        return returnString;
    }



    // function to sort the input

    private String sortLetters(String input){

        char[] charArray = input.toCharArray();
        Arrays.sort(charArray);
        String sortedString = new String(charArray);

        return  sortedString;

    }

}
