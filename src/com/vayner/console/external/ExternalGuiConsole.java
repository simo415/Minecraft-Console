package com.vayner.console.external;

import com.sijobe.console.ConsoleListener;
import com.sijobe.console.GuiConsole;


public class ExternalGuiConsole implements ConsoleListener{

   private static ExternalConsoleWindow window = null;
   
   public static void openExternalWindow() {
      if(window != null) {
         window.setVisible(true);
         return;
      }
      
      window = new ExternalConsoleWindow("External console");
   }
   
   public static void closeExternalWindow() {
      if(window != null)
         window.setVisible(false);
   }
   
   public static void toggleExternalWIndow() {
      if(window != null)
         window.setVisible(!window.isVisible());
      else
         openExternalWindow();
   }

   
   @Override
   public boolean processInput(String input) {
      passOnMessage(GuiConsole.getInputPrefix() + input);
      
      if(!input.startsWith("@external")) {
         return true;
      }
      
      if(input.equals("@external open")) {
         System.out.println("starting external window");
         openExternalWindow();
      }
      else if(input.equals("@external close")) {
         System.out.println("stop external window");
         
      }
      
      return false;
   }

   @Override
   public void processOutput(String output) {
      passOnMessage(output);
   }
   
   public void passOnMessage(String dirtyMessage) {
      if(window == null)
         return;
      
      String message = dirtyMessage.replaceAll("§\\w", "");
      window.reciveMessage(message);
   }
   
}
