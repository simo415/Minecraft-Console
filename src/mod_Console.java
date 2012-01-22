package net.minecraft.src;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;

public class mod_Console extends BaseMod {
   public mod_Console() {
      GuiConsole.getInstance();
      ModLoader.RegisterKey(this, new KeyBinding("Console", Keyboard.KEY_BACKSLASH), false);
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
   }
   
   public String getVersion() {
      return GuiConsole.VERSION;
   }      
}