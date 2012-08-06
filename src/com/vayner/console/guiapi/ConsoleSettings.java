package com.vayner.console.guiapi;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.sijobe.console.GuiConsole;

import de.matthiasmann.twl.Widget;

import net.minecraft.src.GuiApiHelper;
import net.minecraft.src.GuiModScreen;
import net.minecraft.src.ModSettingScreen;
import net.minecraft.src.ModSettings;

public class ConsoleSettings {
   
   private static ModSettings consoleSettings;
   private static ModSettingScreen consoleSettingsScreen = null;
   private static ArrayList<Field> consoleFields; 
   
   private static final BaseConsoleSettingsWindow [] subWindows = {
      new ColorWindow(),
      new SettingsFilesWindow(),
      new ScreenSettingsWindow()
      };
   
   private static boolean init = false;
   
   public static void init() {
      //only to run once
      if(init)
         return;
      init = true;
      
      //get all configurable fields
      consoleFields = GuiConsole.returnSettingsFields(GuiConsole.class);
      
      //create base framework
      consoleSettings = new ModSettings("ConsoleSettings");
      consoleSettingsScreen = new ModSettingScreen("Minecraft Console settings");
      
      //initialize every section/sub window, each in their own class (because i can)
      for (BaseConsoleSettingsWindow widgetRef : subWindows) {
         consoleSettingsScreen.append(
                  GuiApiHelper.makeButton(
                     widgetRef.getTittle(),
                     "show",
                     GuiModScreen.class,
                     true,
                     new Class[] { Widget.class },
                     widgetRef.getMainWidget()
                     )
                  );
      }
   }
   
   public static ArrayList<Field> getFields() {
      return consoleFields;
   }
   
   public static Widget getMainWindow() {
      if(consoleSettingsScreen == null)
         init();
      return consoleSettingsScreen.theWidget;
   }
}