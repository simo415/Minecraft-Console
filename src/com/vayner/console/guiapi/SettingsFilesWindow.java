package com.vayner.console.guiapi;

import com.sijobe.console.GuiConsole;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Widget;
import net.minecraft.src.GuiApiHelper;
import net.minecraft.src.GuiModScreen;
import net.minecraft.src.ModSettingScreen;
import net.minecraft.src.WidgetClassicTwocolumn;
import net.minecraft.src.WidgetSimplewindow;
import net.minecraft.src.WidgetSinglecolumn;


public class SettingsFilesWindow extends BaseConsoleSettingsWindow{
   
   private static final String TITTLE = "Load, save & reset setting files";
   private static final String BUTTONTITTLE = "Load/save/reset";
   
   protected static WidgetSimplewindow settingsWindow;
   protected static WidgetSinglecolumn settingsSingleColumn;
   
   private static Button loadSettingsButton;
   private static Button saveSettingsButton;
   private static Button resetSettingsButton;
   
   public String getTittle() {
      return BUTTONTITTLE;
   }
   
   public Widget getMainWidget() {
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
