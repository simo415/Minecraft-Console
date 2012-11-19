package com.vayner.console.guiapi;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.sijobe.console.GuiConsole;

import de.matthiasmann.twl.Widget;

import net.minecraft.src.GuiApiHelper;
import net.minecraft.src.GuiModScreen;
import net.minecraft.src.ModSettingScreen;
import net.minecraft.src.ModSettings;
import net.minecraft.src.WidgetInt;
import net.minecraft.src.WidgetSimplewindow;

public class ConsoleSettings {
   
   protected static ModSettings consoleSettings;
   protected static ModSettingScreen consoleSettingsScreen = null;
   protected static SaveCallBack saveCallBack;
   
   private static ArrayList<Field> consoleFields; 
   
   //easier than using an Enum
   private static final BaseConsoleSettingsWindow [] subWindows = {
       new ColorWindow()
      ,new ScreenSettingsWindow()
      ,new SettingsFilesWindow()
      //,new MiscSettingsWindow()
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
      consoleSettings = new ModSettings("Console");
      consoleSettingsScreen = new ModSettingScreen("Minecraft Console settings");
      consoleSettingsScreen.setSingleColumn(true);
      saveCallBack = new SaveCallBack();
      
      //initialize every section/sub window, each in their own class (because i can)
      for (BaseConsoleSettingsWindow widgetRef : subWindows) {
         if(widgetRef.getMainWidget() == null)
            continue;
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
         //add callback to the back button for "automatic" saving
         widgetRef.getMainWidget().backButton.addCallback(saveCallBack);
      }
      
      //same as above comment
      ((WidgetSimplewindow) (consoleSettingsScreen.theWidget)).backButton.addCallback(saveCallBack);
      
      if(!consoleSettings.settingsLoaded)
         consoleSettings.load();
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

//class which only purpose is to save changes
class SaveCallBack implements Runnable {

   @Override
   public void run() {
      if(ConsoleSettings.consoleSettings.getBooleanSettingValue(SettingsFilesWindow.AUTOSAVE_BACKENDNAME)){
         SettingsFilesWindow.saveSettings();
      }
   }
}