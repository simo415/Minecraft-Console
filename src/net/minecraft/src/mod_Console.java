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
import com.sijobe.console.GuiConsole;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;

public class mod_Console extends BaseMod {
   
   public static KeyBinding openKey;
   public static ModSettings settings;
   public static ModSettingScreen settingScreen;
   
   public mod_Console() {
      GuiConsole.getInstance();
      openKey = new KeyBinding("Console", Keyboard.KEY_BACKSLASH);
      ModLoader.RegisterKey(this, openKey, false);
   }
   
   @Override
   public void KeyboardEvent(KeyBinding event) {
      Minecraft game = ModLoader.getMinecraftInstance();
      if (game.currentScreen == null) {
         game.displayGuiScreen(GuiConsole.getInstance());
      }
   }
   
   @Override
   public void load() {
      settings = new ModSettings("Console");
      settingScreen = new ModSettingScreen("Minecraft Console v" + getVersion());
      
   }
   
   public String getVersion() {
      return GuiConsole.VERSION;
   }      
}