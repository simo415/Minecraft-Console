package com.vayner.console;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sijobe.console.ConsoleListener;
import com.sijobe.console.GuiConsole;

import net.minecraft.src.ModLoader;

/**
 *
 * @author Vayner
 *
 *         This program is free software: you can redistribute it and/or modify
 *         it under the terms of the GNU Lesser General Public License as published by
 *         the Free Software Foundation, either version 3 of the License, or
 *         (at your option) any later version.
 *
 *         This program is distributed in the hope that it will be useful,
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *         GNU Lesser General Public License for more details.
 *
 *         You should have received a copy of the GNU General Public License
 *         along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

public class ConsoleChatCommands implements ConsoleListener{
   
   private static final File WORDLIST_DIR = new File(GuiConsole.getModDir(),"/wordlist");
   private static final String DEFAULT_COMMAND_LIST = "defaults";
   private static final String DEFAULT_FILE_ENDING = ".txt";
   
   private static List<String> currentCommandSet = null;
   
   private static String lastServerName = "";
   
   static
   {
      ConsoleDefaultCommands.init();
      currentCommandSet = loadNewCommandSet();
   }
   
   public static List<String> getChatCommands()
   {
      String currentServerName = GuiConsole.getInstance().serverName();
      if(!currentServerName.equals(lastServerName) || currentCommandSet == null) {
         lastServerName = currentServerName;
         currentCommandSet = loadNewCommandSet();
      }
         
      
      return currentCommandSet;
   }


   private static List<String> loadNewCommandSet() {
      if(lastServerName.equals(""))
         return loadCommands(DEFAULT_COMMAND_LIST);
      return loadCommands(lastServerName);
   }
   
   private static List<String> loadCommands(String listName) {
      File listFile = new File(WORDLIST_DIR,listName + DEFAULT_FILE_ENDING);
      
      if(!listFile.exists()) {
         saveCommandFile(ConsoleDefaultCommands.getDefaultMultiplayerCommands(), listFile);
      }
      
      return loadCommandFile(listFile);
   }
   
   private static void saveCommands(String listName) {
      File listFile = new File(WORDLIST_DIR,listName + DEFAULT_FILE_ENDING);
      
      saveCommandFile(ConsoleDefaultCommands.getDefaultMultiplayerCommands(), listFile);
   }

   private static List<String> loadCommandFile(File commandFile) {
      List<String> commandList = new ArrayList<String>(250);

      try {
         BufferedReader reader;
         reader = new BufferedReader(new FileReader(commandFile));

         String in = "";

         do {
            in = reader.readLine();
            if(in == null || in.equals(""))break;
            commandList.add(in);
         } while (true);
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }

      Collections.sort(commandList);

      return commandList;
   }

   private static void saveCommandFile(List<String> commands,File commandFile) {
      
      try {
         BufferedWriter writer = new BufferedWriter(new FileWriter(commandFile));

         for (String string : commands) {
            writer.write(string);
            writer.newLine();
         }

         writer.close();

      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   @Override
   public boolean processInput(String input) {
      if(input.equals("servername")){
         System.out.println("Server name:" + GuiConsole.getInstance().serverName());
         System.out.println("Server IP:" + GuiConsole.getInstance().serverIp());
         GuiConsole.getInstance().addOutputMessage("Server name:" + GuiConsole.getInstance().serverName());
         GuiConsole.getInstance().addOutputMessage("Server IP:" + GuiConsole.getInstance().serverIp());
         return false;
      }
      return true;
   }
   
   @Override
   public void processOutput(String output) {
      // empty
   }
}
