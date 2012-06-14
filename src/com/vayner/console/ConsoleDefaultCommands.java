package com.vayner.console;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import net.minecraft.src.PlayerHelper;
import net.minecraft.src.SPCPlugin;
import net.minecraft.src.SPCPluginManager;
import net.minecraft.src.mod_Console;
import net.minecraft.src.spc_AprilFools2012;

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

public class ConsoleDefaultCommands {
   private static ArrayList<String> vanillaServerCommands;
   private static ArrayList<String> worldEditCommands;
   private static ArrayList<String> commandBookCommands;
   
   public static List<String> getDefaultSingleplayerCommands()
   {
      List<String> commands = new ArrayList();
      
      if(mod_Console.SPCInstalled())
      {
         commands.addAll(getSPCcommands());
      }
      
      Collections.sort(commands);

      return commands;
   }
   
   private static ArrayList<String> getSPCcommands()
   {
      ArrayList<String> commands = new ArrayList<String>();
      
      Set SPC_cmd = PlayerHelper.CMDS.keySet();
      List<SPCPlugin> SPC_pluginCmd = SPCPluginManager.getPluginManager().getPlugins();
      
      for (Object entry : SPC_cmd) {
         if(entry instanceof String)
         {
            commands.add((String)entry);
         }
      }
      
      for (SPCPlugin spcPlugin : SPC_pluginCmd) {
         if(spcPlugin.getCommands() == null)
            continue;
         for (String string : spcPlugin.getCommands()) {
            commands.add(string);
         }
      }
      
      commands.addAll(worldEditCommands);
      
      return commands;
   }
   
   public static List<String> getDefaultMultiplayerCommands()
   {
      List<String> commands = new ArrayList();

      commands.addAll(vanillaServerCommands);
      commands.addAll(worldEditCommands);
      commands.addAll(commandBookCommands);
      
      Collections.sort(commands);

      return commands;
   }

   static void init()
   {
      vanillaServerCommands = new ArrayList<String>();

      //non-OP commands
      vanillaServerCommands.add("/tell");
      vanillaServerCommands.add("/me");
      vanillaServerCommands.add("/kill");

      //OP commands
      vanillaServerCommands.add("/ban");
      vanillaServerCommands.add("/ban-ip");
      vanillaServerCommands.add("/banlist");
      vanillaServerCommands.add("/banlist ips");
      vanillaServerCommands.add("/gamemode");
      vanillaServerCommands.add("/give");
      vanillaServerCommands.add("/help");
      vanillaServerCommands.add("/kick");
      vanillaServerCommands.add("/list");
      vanillaServerCommands.add("/op");
      vanillaServerCommands.add("/pardon");
      vanillaServerCommands.add("/pardon-ip");
      vanillaServerCommands.add("/save-all");
      vanillaServerCommands.add("/save-off");
      vanillaServerCommands.add("/save-on");
      vanillaServerCommands.add("/say");
      vanillaServerCommands.add("/stop");
      vanillaServerCommands.add("/time set");
      vanillaServerCommands.add("/time add");
      vanillaServerCommands.add("/toggledownfall");
      vanillaServerCommands.add("/tp");
      vanillaServerCommands.add("/whitelist add");
      vanillaServerCommands.add("/whitelist remove");
      vanillaServerCommands.add("/whitelist list");
      vanillaServerCommands.add("/whitelist reload");
      vanillaServerCommands.add("/xp");



      worldEditCommands = new ArrayList<String>();

      worldEditCommands.add("//limit");
      worldEditCommands.add("//undo");
      worldEditCommands.add("//redo");
      worldEditCommands.add("/clearhistory");
      worldEditCommands.add("//wand");
      worldEditCommands.add("/toggleeditwand");
      worldEditCommands.add("//sel");
      worldEditCommands.add("//sel cuboid");
      worldEditCommands.add("//sel extend");
      worldEditCommands.add("//sel poly");
      worldEditCommands.add("//sel ellipsoid");
      worldEditCommands.add("//sel sphere");
      worldEditCommands.add("//sel cyl");
      worldEditCommands.add("//pos1");
      worldEditCommands.add("//pos2");
      worldEditCommands.add("//hpos1");
      worldEditCommands.add("//hpos2");
      worldEditCommands.add("//chunk");
      worldEditCommands.add("//expand");
      worldEditCommands.add("//contract");
      worldEditCommands.add("//outset");
      worldEditCommands.add("//inset");
      worldEditCommands.add("//shift");
      worldEditCommands.add("//size");
      worldEditCommands.add("//count");
      worldEditCommands.add("//distr");
      worldEditCommands.add("//set");
      worldEditCommands.add("//replace");
      worldEditCommands.add("//overlay");
      worldEditCommands.add("//walls");
      worldEditCommands.add("//outline");
      worldEditCommands.add("//smooth");
      worldEditCommands.add("//regen");
      worldEditCommands.add("//move");
      worldEditCommands.add("//stack");
      worldEditCommands.add("//copy");
      worldEditCommands.add("//cut");
      worldEditCommands.add("//paste");
      worldEditCommands.add("//rotate");
      worldEditCommands.add("//flip");
      worldEditCommands.add("//schem save");
      worldEditCommands.add("//schem load");
      worldEditCommands.add("//schem list");
      worldEditCommands.add("//schem formats");
      worldEditCommands.add("//schematic save");
      worldEditCommands.add("//schematic load");
      worldEditCommands.add("//schematic list");
      worldEditCommands.add("//schematic formats");
      worldEditCommands.add("/clearclipboard");
      worldEditCommands.add("//hcyl");
      worldEditCommands.add("//cyl");
      worldEditCommands.add("//sphere");
      worldEditCommands.add("//hsphere");
      worldEditCommands.add("//pyramid");
      worldEditCommands.add("//hpyramid");
      worldEditCommands.add("/forestgen");
      worldEditCommands.add("/pumpkins");
      worldEditCommands.add("/toggleplace");
      worldEditCommands.add("//fill");
      worldEditCommands.add("//fillr");
      worldEditCommands.add("//drain");
      worldEditCommands.add("/fixwater");
      worldEditCommands.add("/fixlava");
      worldEditCommands.add("/removeabove");
      worldEditCommands.add("/removebelow");
      worldEditCommands.add("/replacenear");
      worldEditCommands.add("/removenear");
      worldEditCommands.add("/snow");
      worldEditCommands.add("/thaw");
      worldEditCommands.add("//ex");
      worldEditCommands.add("/butcher");
      worldEditCommands.add("/remove");
      worldEditCommands.add("/chunkinfo");
      worldEditCommands.add("/listchunks");
      worldEditCommands.add("/delchunks");
      worldEditCommands.add("//");
      worldEditCommands.add("/sp single");
      worldEditCommands.add("/sp area");
      worldEditCommands.add("/sp recur");
      worldEditCommands.add("/none");
      worldEditCommands.add("/info");
      worldEditCommands.add("/tree");
      worldEditCommands.add("//repl");
      worldEditCommands.add("//cycler");
      worldEditCommands.add("/brush sphere");
      worldEditCommands.add("/brush cylinder");
      worldEditCommands.add("/brush clipboard");
      worldEditCommands.add("/brush smooth");
      worldEditCommands.add("/size");
      worldEditCommands.add("//mat");
      worldEditCommands.add("//mask");
      worldEditCommands.add("/unstuck");
      worldEditCommands.add("/ascend");
      worldEditCommands.add("/descend");
      worldEditCommands.add("/ceil");
      worldEditCommands.add("/thru");
      worldEditCommands.add("/jumpto");
      worldEditCommands.add("/up");
      worldEditCommands.add("//restore");
      worldEditCommands.add("//snap use");
      worldEditCommands.add("//snap list");
      worldEditCommands.add("//snap before");
      worldEditCommands.add("//snap after");
      worldEditCommands.add("/cs");
      worldEditCommands.add("/.s");
      worldEditCommands.add("/search");
      worldEditCommands.add("//worldedit reload");
      worldEditCommands.add("//worldedit version");
      worldEditCommands.add("//worldedit tz");
      worldEditCommands.add("/biome");
      worldEditCommands.add("/biomelist");
      worldEditCommands.add("//setbiome");
      
      
      commandBookCommands = new ArrayList<String>();
      
      commandBookCommands.add("/item");
      commandBookCommands.add("/i");
      //commandBookCommands.add("/give");
      commandBookCommands.add("/more");
      commandBookCommands.add("/who");
      //commandBookCommands.add("/list");
      commandBookCommands.add("/playerlist");
      commandBookCommands.add("/online");
      commandBookCommands.add("/players");
      commandBookCommands.add("/motd");
      commandBookCommands.add("/intro");
      commandBookCommands.add("/midi");
      commandBookCommands.add("/rules");
      commandBookCommands.add("/kit");
      commandBookCommands.add("/kit");
      commandBookCommands.add("/setspawn");
      //commandBookCommands.add("/time");
      commandBookCommands.add("/time now");
      commandBookCommands.add("/time -l now");
      commandBookCommands.add("/time");
      commandBookCommands.add("/playertime");
      commandBookCommands.add("/spawnmob");
      commandBookCommands.add("/weather stormy");
      commandBookCommands.add("/weather sunny");
      commandBookCommands.add("/thunder on");
      commandBookCommands.add("/thunder off");
      //commandBookCommands.add("/biome");
      commandBookCommands.add("/spawn");
      commandBookCommands.add("/teleport");
      commandBookCommands.add("/tp");
      commandBookCommands.add("/bring");
      commandBookCommands.add("/tphere");
      commandBookCommands.add("/summon");
      commandBookCommands.add("/s");
      commandBookCommands.add("/place");
      commandBookCommands.add("/put");
      commandBookCommands.add("/return");
      commandBookCommands.add("/ret");
      commandBookCommands.add("/call");
      commandBookCommands.add("/home");
      commandBookCommands.add("/sethome");
      commandBookCommands.add("/warp");
      commandBookCommands.add("/setwarp");
      commandBookCommands.add("/warps del");
      commandBookCommands.add("/warps delete");
      commandBookCommands.add("/warps remove");
      commandBookCommands.add("/warps rem");
      commandBookCommands.add("/warps list");
      commandBookCommands.add("/warps show");
      commandBookCommands.add("/broadcast");
      //commandBookCommands.add("/say");
      //commandBookCommands.add("/me");
      commandBookCommands.add("/msg");
      commandBookCommands.add("/message");
      commandBookCommands.add("/whisper");
      commandBookCommands.add("/pm");
      //commandBookCommands.add("/tell");
      commandBookCommands.add("/reply");
      commandBookCommands.add("/r");
      commandBookCommands.add("/mute");
      commandBookCommands.add("/unmute");
      commandBookCommands.add("/afk");
      commandBookCommands.add("/whereami");
      commandBookCommands.add("/getpos");
      commandBookCommands.add("/pos");
      commandBookCommands.add("/where");
      commandBookCommands.add("/compass");
      commandBookCommands.add("/clear");
      commandBookCommands.add("/slap");
      commandBookCommands.add("/rocket");
      commandBookCommands.add("/barrage");
      commandBookCommands.add("/firebarrage");
      commandBookCommands.add("/shock");
      commandBookCommands.add("/thor");
      commandBookCommands.add("/freeze");
      commandBookCommands.add("/unthor");
      //commandBookCommands.add("/gamemode");
      commandBookCommands.add("/heal");
      commandBookCommands.add("/ping");
      commandBookCommands.add("/whois");
      commandBookCommands.add("/debug info");
      commandBookCommands.add("/debug clock");
      commandBookCommands.add("/cmdbook version");
      commandBookCommands.add("/cmdbook reload");
      //commandBookCommands.add("/kick");
      //commandBookCommands.add("/ban");
      commandBookCommands.add("/unban");
      commandBookCommands.add("/isbanned");
      commandBookCommands.add("/baninfo");
      commandBookCommands.add("/bans load");
      commandBookCommands.add("/bans save");
      commandBookCommands.add("/god");
      commandBookCommands.add("/ungod");
      
   }
}