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
   private static final String singleplayerFileName = "singleplayer.txt";
   private static final String multiplayerFileName = "multiplayer.txt";
   
   private static final File COMMANDFILE_SINGLEPLAYER = new File(GuiConsole.MOD_DIR,singleplayerFileName);
   private static final File COMMANDFILE_MULTIPLAYER = new File(GuiConsole.MOD_DIR,multiplayerFileName);

   private static List commands_Singleplayer;
   private static List commands_Multiplayer;
   
   
   static
   {
      ConsoleDefaultCommands.init();
      loadAllCommands();
   }
   
   public static List<String> getChatCommands()
   {
      if(GuiConsole.getInstance().isMultiplayerMode())
      {	//multiplayer
         return commands_Multiplayer;
      }
      else
      {	//singleplayer
         return commands_Singleplayer;
      }
   }



   private static List<String> loadChatCommands(File commandFile)
   {
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

   private static void saveChatCommands(List<String> commands,File commandFile)
   {
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

   private static void loadAllCommands()
   {
      if(!COMMANDFILE_SINGLEPLAYER.exists())
      {
         System.out.println("Generating " + singleplayerFileName);
         saveChatCommands(ConsoleDefaultCommands.getDefaultSingleplayerCommands(),COMMANDFILE_SINGLEPLAYER);
      }

      commands_Singleplayer = loadChatCommands(COMMANDFILE_SINGLEPLAYER);

      if(!COMMANDFILE_MULTIPLAYER.exists())
      {
         System.out.println("Generating " + multiplayerFileName);
         saveChatCommands(ConsoleDefaultCommands.getDefaultMultiplayerCommands(),COMMANDFILE_MULTIPLAYER);
      }

      commands_Multiplayer = loadChatCommands(COMMANDFILE_MULTIPLAYER);

      System.out.println(singleplayerFileName + " contains:" + commands_Singleplayer.size() + " entries");
      System.out.println(multiplayerFileName + " contains:" + commands_Multiplayer.size() + " entries");
   }

   @Override
   public boolean processInput(String input) {

      return true;
   }
   
   @Override
   public void processOutput(String output) {
      // empty
   }
}
