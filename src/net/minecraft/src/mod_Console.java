package net.minecraft.src;

/**
 * 
 * @author simo_415
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *  
 */
import java.util.Collections;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

import com.sijobe.console.GuiConsole;
import com.vayner.console.guiapi.ConsoleSettings;

public class mod_Console extends BaseMod {
   
   public static KeyBinding openKey;
   private static boolean guiApiInstalled = false;
   private static boolean SPCInstalled = false;
   
   public mod_Console() {
      GuiConsole.getInstance();
      openKey = new KeyBinding("Console", Keyboard.KEY_BACKSLASH);
      ModLoader.registerKey(this, openKey, false);
   }
   
   @Override
   public void keyboardEvent(KeyBinding event) {
      Minecraft game = ModLoader.getMinecraftInstance();
      if (game.currentScreen == null) {
         game.displayGuiScreen(GuiConsole.getInstance());
      }
   }
   
   @Override
   public void load() {
      try {
         Class helper = Class.forName("PlayerHelper", false, null);
         SPCInstalled = true;
      } catch (ClassNotFoundException e) {
         System.out.println("Single Player Commands 'PlayerHelper.class' not found, unable to retrive commands for SPC");
         SPCInstalled = false;
      }
      
      try {
         Class test = Class.forName("ModSettings", false, null);
         guiApiInstalled = true;
      } catch (ClassNotFoundException e) {
         System.out.println("GuiApi not installed, settings adjustment ingame will not be avaiable");
         guiApiInstalled = false;
      }      
      
      if(ModLoader.getMinecraftInstance().session.username.equals("MCPTEST")) {
         System.out.println("Username is MCPTEST, assuming running via eclipse with all prerequisits installed");
         SPCInstalled = true;
         guiApiInstalled = true;  
      }
   }
   
   public void modsLoaded() {
      if(guiApiInstalled)
         ConsoleSettings.init();
   }
   
   public static boolean GuiApiInstalled() {
      return guiApiInstalled;
   }
   
   public static boolean SPCInstalled() {
      return SPCInstalled;
   }
   
   public String getVersion() {
      return GuiConsole.VERSION;
   }      
}