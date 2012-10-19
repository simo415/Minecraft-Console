package com.vayner.console;

import java.util.ArrayList;
import java.util.List;


public class WordCompleter {
   
   private int wordListPosition;                                     // Current position in the word list
   private boolean isMatchingWords = false;                          // Is searching
   private boolean playerNamesOnly = false;                          // Is matching for player names only
   private int matchingWordStartPosition;                            // start place of matching space
   private String tabMatchingWord;                                   // The current word searching for matches with
   private String messageBeforeWord = "";
   private String messageAfterCursor = "";
   private ArrayList<String> tabCurrentList;                         // The current List matching words
   
   private String originalMessage;
   
   public void resetTabbing() {
      isMatchingWords = false;
      playerNamesOnly = false;
      wordListPosition = 0;
      matchingWordStartPosition = 0;
      tabMatchingWord = "";
      messageBeforeWord = "";
      messageAfterCursor = "";
   }
   
   public void setMatchingMessage(String message, int cursorPos) {
      resetTabbing();
      
      //find the last word, defined via the cursor
      String messageBeforeCursor = message.substring(0, cursorPos);
      messageAfterCursor = (cursorPos < message.length()) ? message.substring(cursorPos) : "" ;
      
      if (messageBeforeCursor == null || messageBeforeCursor.length() == 0) {
         tabMatchingWord = "";
         matchingWordStartPosition = 0;
         return;
      } else if (messageBeforeCursor.startsWith("@")){
         tabMatchingWord = messageBeforeCursor.substring(1);
         matchingWordStartPosition = 1;
         playerNamesOnly = true;
      } else if (messageBeforeCursor.endsWith(" ")) {
         tabMatchingWord = "";
         matchingWordStartPosition = messageBeforeCursor.length();
      } else if (messageBeforeCursor.contains(" ")) {
         String [] splitMessage = messageBeforeCursor.split(" ");
         tabMatchingWord = splitMessage[splitMessage.length - 1];
         matchingWordStartPosition = messageBeforeCursor.length() - tabMatchingWord.length();
      } else {
         tabMatchingWord = messageBeforeCursor;
         matchingWordStartPosition = 0;
      }
      
      messageBeforeWord = originalMessage.substring(0, matchingWordStartPosition);
   }
   
   private boolean updateTabPos(int Diff)
   {
      if(isMatchingWords == false)
      {
         return false;
      }
      
      List<String> autoWords;
      
      if(playerNamesOnly)
         autoWords = WordListFetcher.getPlayerNames();       //list of player names only
      else
         autoWords = WordListFetcher.getCurrentWordList(true);   //list of all possible words
      
      if(autoWords == null)
         return false;
      
      tabCurrentList = new ArrayList<String>(); 
      if(tabMatchingWord == null){
         tabCurrentList.addAll(autoWords);
      } else {
         for (int i = 0; i < autoWords.size(); i++) {
            String currentWord = autoWords.get(i);
            // Tests if a autoword starts with the matching word
            if (currentWord.toLowerCase().startsWith(tabMatchingWord.toLowerCase())) {
               tabCurrentList.add(currentWord);
            }
         }
      }

      if (tabCurrentList.size() > 0) {
         
         if(isMatchingWords)
            wordListPosition += Diff;
         else
            isMatchingWords = true;
         
         //check for see if out of bound
         wordListPosition = (wordListPosition >= tabCurrentList.size())? 0 : wordListPosition;
         wordListPosition = (wordListPosition < 0)? tabCurrentList.size() - 1 : wordListPosition;
         
         return true;
      }
      
      return false;
   }
   
   public String get () {
      return tabCurrentList.get(wordListPosition);
   }
   
   public String get (int index) {
      return tabCurrentList.get(index);
   }
   
   public int size () {
      return tabCurrentList.size();
   }
   
   public String getEntireMessage () {
      return messageBeforeWord + get() + messageAfterCursor;
   }
   
}
