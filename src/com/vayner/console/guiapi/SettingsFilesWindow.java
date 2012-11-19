package com.vayner.console.guiapi;
import com.sijobe.console.GuiConsole;

import de.matthiasmann.twl.Button;
import net.minecraft.src.GuiApiHelper;
import net.minecraft.src.GuiModScreen;
import net.minecraft.src.ModSettingScreen;
import net.minecraft.src.SettingBoolean;
import net.minecraft.src.WidgetBoolean;
import net.minecraft.src.WidgetClassicTwocolumn;
import net.minecraft.src.WidgetSimplewindow;
import net.minecraft.src.WidgetSinglecolumn;


public class SettingsFilesWindow extends BaseConsoleSettingsWindow{
   
   private static final String TITTLE = "Load, save, reset settings ...";
   private static final String BUTTONTITTLE = "Load/save/reset";
   
   protected static WidgetSimplewindow settingsWindow;
   protected static WidgetSinglecolumn settingsSingleColumn;
   
   private static Button loadSettingsButton;
   private static Button saveSettingsButton;
   private static Button resetSettingsButton;
   
   protected static final String AUTOSAVE_BACKENDNAME = "consoleAutoSave";
   
   public String getTittle() {
      return BUTTONTITTLE;
   }
   
   public WidgetSimplewindow getMainWidget() {
      if(settingsWindow == null)
         createWindow();
      
      return settingsWindow;
   }
   
   protected static void createWindow() {
      
      settingsSingleColumn = new WidgetSinglecolumn();
      settingsWindow = new WidgetSimplewindow(settingsSingleColumn,TITTLE);
      
      loadSettingsButton = GuiApiHelper.makeButton("Load settings from file", "loadSettings", SettingsFilesWindow.class, true);
      saveSettingsButton = GuiApiHelper.makeButton("Save settings to file", "saveSettings", SettingsFilesWindow.class, true);
      resetSettingsButton = GuiApiHelper.makeButton("Reset settings & file", "resetSettings", SettingsFilesWindow.class, true);
      
      settingsSingleColumn.add(loadSettingsButton);
      settingsSingleColumn.add(saveSettingsButton);
      settingsSingleColumn.add(resetSettingsButton);
      
      ConsoleSettings.consoleSettings.addSetting(
               settingsSingleColumn,
               "Autosave",
               AUTOSAVE_BACKENDNAME,
               true,
               "Autosave is on",
               "Autosave is off"
               );
   }
   
   public static void saveSettings() {
      GuiConsole.writeGuiConsoleSettings();
   }
   
   public static void loadSettings() {
      GuiConsole.readGuiConsoleSettings();
   }
   
   public static void resetSettings() {
      GuiConsole.resetGuiConsoleSettings();
   }
}