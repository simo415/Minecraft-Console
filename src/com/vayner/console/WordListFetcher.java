package com.vayner.console;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.GuiPlayerInfo;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetClientHandler;


public class WordListFetcher {
   
   static Pattern playerNamePattern = Pattern.compile("[\\[[\\{[\\(]]]+?.*?[\\][\\}[\\)]]]"); //Get rid of everything between the brackets (), [], or {}
   static Minecraft mcRef = ModLoader.getMinecraftInstance();
   
   /**
    * Fetches the current word list, can include playernames 
    *
    * @return The current word list
    */
   static public List<String> getCurrentWordList(boolean playerNames) {
      
      List<String> autowords = new ArrayList<String>();
      List<String> players = (playerNames)? getPlayerNames() : null;
      
      if(players != null && players.size() > 0)
      {
         autowords.addAll(players);
      }
   
      autowords.addAll(ConsoleChatCommands.getChatCommands());
   
      return autowords;
   }
   
   /**
    * Gets all the usernames on the current server you're on
    *
    * @return A list in alphabetical order of players logged onto the server
    */
   static public List<String> getPlayerNames() {
      List<String> names = new ArrayList<String>();
      if (!mcRef.isSingleplayer() && mcRef.thePlayer instanceof EntityClientPlayerMP) {
         NetClientHandler netclienthandler = ((EntityClientPlayerMP) mcRef.thePlayer).sendQueue;
         List<GuiPlayerInfo> tempList = netclienthandler.playerInfoList;
         for (GuiPlayerInfo info : (List<GuiPlayerInfo>) tempList) {
            String name = info.name; 
            
            //There were some problems with bukkit plugins adding prefixes or suffixes to the names list. This cleans the strings.
            Matcher matcher = playerNamePattern.matcher(name);
            name = matcher.replaceAll("");
            String cleanName = "";
            for (int i = 0; i < name.length(); i++) { //Get rid of every invalid character for minecraft usernames
               if (name.charAt(i) == '\u00a7') { //Gets rid of color codes
                  i++;
                  continue;
               }

               if (Character.isLetterOrDigit(name.charAt(i)) || name.charAt(i) == '_') {
                  cleanName += name.charAt(i);
               }
            }
            if (!cleanName.equals("")) {
               names.add(cleanName);
            }
         }
      } else {
         names.add(mcRef.session.username);
      }
      return names;
   }

}
