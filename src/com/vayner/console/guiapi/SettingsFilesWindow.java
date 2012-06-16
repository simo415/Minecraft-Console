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


public class SettingsFilesWindow {

   protected static WidgetSimplewindow settingsWindow;
   protected static WidgetSinglecolumn settingsSingleColumn;
   
   private static Button loadSettingsButton;
   private static Button saveSettingsButton;
   private static Button resetSettingsButton;
   
   protected static void createWindow(ModSettingScreen SettingsScreen) {
      
      settingsSingleColumn = new WidgetSinglecolumn();
      settingsWindow = new WidgetSimplewindow(settingsSingleColumn,"Settings file option");
      
      
      loadSettingsButton = GuiApiHelper.makeButton("Load settings from file", "loadSettings", SettingsFilesWindow.class, true);
      saveSettingsButton = GuiApiHelper.makeButton("Save settings from file", "saveSettings", SettingsFilesWindow.class, true);
      resetSettingsButton = GuiApiHelper.makeButton("Reset settings & file", "resetSettings", SettingsFilesWindow.class, true);
      
      settingsSingleColumn.add(loadSettingsButton);
      settingsSingleColumn.add(saveSettingsButton);
      settingsSingleColumn.add(resetSettingsButton);
      
      
      //add button from main config page to the new window
      SettingsScreen.append(
               GuiApiHelper.makeButton(
                  "Load/save/reset settings",
                  "show",
                  GuiModScreen.class,
                  true,
                  new Class[] { Widget.class },
                  settingsWindow
                  )
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
