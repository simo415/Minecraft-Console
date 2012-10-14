package com.vayner.console.guiapi;

import net.minecraft.src.WidgetSimplewindow;


public class MiscSettingsWindow extends BaseConsoleSettingsWindow {
   
   private static final String Tittle = "Misc settings window";
   
   private static WidgetSimplewindow miscWindowWidget;
   
   @Override
   public String getTittle() {
      return Tittle;
   }
   
   @Override
   public WidgetSimplewindow getMainWidget() {
      if(miscWindowWidget == null)
         createWindow();
      return miscWindowWidget;
   }

   private void createWindow() {
      
   }
   
}
