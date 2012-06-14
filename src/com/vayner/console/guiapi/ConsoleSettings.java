package com.vayner.console.guiapi;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.sijobe.console.GuiConsole;

import net.minecraft.src.ModSettingScreen;
import net.minecraft.src.ModSettings;

public class ConsoleSettings {
   
   public static ModSettings consoleSettings;
   public static ModSettingScreen consoleSettingsScreen;
   private static boolean init = false;
   
   protected static ArrayList<Field> consoleFields; 
   
   public static void init() {
      //only to run once, can't use static{} because of wrong load order
      if(init)
         return;
      init = true;
      
      //get all configurable fields
      consoleFields = GuiConsole.returnSettingsFields(GuiConsole.getInstance().getClass());
      
      //create base framework
      consoleSettings = new ModSettings("ConsoleSettings");
      consoleSettingsScreen = new ModSettingScreen("Minecraft Console settings");
      
      //initialize every sub window, each in their own class because it is cleaner
      ColorWindow.createColorWindow(consoleSettingsScreen);
   }
}