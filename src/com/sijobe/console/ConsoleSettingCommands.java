package com.sijobe.console;


public class ConsoleSettingCommands implements ConsoleListener {

   @Override
   public void processInput(String input) {
      // Check that the input is a command
      if (input == null || input.length() == 0 || !input.startsWith("@")) {
         return;
      }
      try {
         if (input.startsWith("@set ")) {
            String cmd = input.substring(5).trim();
            String name = input.substring(0, cmd.indexOf(" ")).trim();
            String value = input.substring(cmd.indexOf(" ")).trim();
            if (!set(name,value)) {
               System.out.println("Unable to set the specified setting " + name + " to " + value);
            }
         }
      } catch (Exception e) {
         ;
      }
   }

   /**
    * Sets the specified setting with the specified value
    * 
    * @param settingName - The name of the setting, non case-sensitive
    * @param value - The value to set the setting
    * @return True is returned when the specified setting was successfully set, false otherwise
    */
   private boolean set(String settingName, String value) {
      return false;
   }

   /**
    * Gets the value of the specified setting 
    * 
    * @param settingName - The name of the setting, non case-sensitive
    * @return The value of the setting
    */
   private String get(String settingName) {
      return null;
   }

   @Override
   public void processOutput(String output) {}
}
