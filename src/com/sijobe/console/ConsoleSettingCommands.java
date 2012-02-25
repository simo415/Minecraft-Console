package com.sijobe.console;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

import net.minecraft.src.mod_Console;

/**
 * 
 * @author simo_415
 * 
 *         This program is free software: you can redistribute it and/or modify
 *         it under the terms of the GNU Lesser General Public License as published by
 *         the Free Software Foundation, either version 3 of the License, or
 *         (at your option) any later version.
 * 
 *         This program is distributed in the hope that it will be useful,
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *         GNU Lesser General Public License for more details.
 * 
 *         You should have received a copy of the GNU General Public License
 *         along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
public class ConsoleSettingCommands implements ConsoleListener {

   @Override
   public boolean processInput(String input) {
      // Check that the input is a command
      if (input == null || input.length() == 0 || !input.startsWith("@")) {
         return true;
      }
      try {
         if (input.startsWith("@set ")) {
            String cmd = input.substring(5).trim();
            String name = cmd.substring(0, cmd.indexOf(" ")).trim();
            String value = cmd.substring(cmd.indexOf(" ")).trim();
            if (!set(name, value)) {
               GuiConsole.getInstance().addOutputMessage("Unable to set the specified setting \"" + name + "\"");
            } else {
               GuiConsole.getInstance().addOutputMessage("\"" + name + "\" = " + value);
            }
         } else if (input.startsWith("@get ")) {
            String name = input.substring(5).trim();
            GuiConsole.getInstance().addOutputMessage(get(name));
         } else if (input.startsWith("@list")) {
            String list = list();

            while (list.length() > 0) {
               GuiConsole.getInstance().addOutputMessage(list.substring(0, list.indexOf("\n")));
               list = list.substring(list.indexOf("\n") + 1);
            }
         } else if (input.startsWith("@save ")) {
            GuiConsole.getInstance().writeSettings(GuiConsole.class, new File(GuiConsole.MOD_DIR, "gui.properties"));
         } else if (input.startsWith("@help")) {
            GuiConsole.getInstance().addOutputMessage("@set [property] [new value]  -  Sets the propery to a new value");
            GuiConsole.getInstance().addOutputMessage("@get [property]  -  Gets the value of the specified propery");
            GuiConsole.getInstance().addOutputMessage("@list  -  Gets a list of all properties");
            GuiConsole.getInstance().addOutputMessage("@save  -  Saves the properties to the properties file");
         } else {
            return true;
         }
      } catch (Exception e) {
         ;
      }
      return false;
   }

   /**
    * Sets the specified setting with the specified value
    * 
    * @param settingName - The name of the setting, non case-sensitive
    * @param value - The value to set the setting
    * @return True is returned when the specified setting was successfully set, false otherwise
    */
   private boolean set(String settingName, String property) {
      try {
         Field[] declaredFields = GuiConsole.class.getDeclaredFields();
         for (Field field : declaredFields) {
            if (Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
               try {
                  if (!field.isAccessible()) {
                     field.setAccessible(true);
                  }
                  if (field.getName().equals(settingName)) {
                     if (field.getType().equals(String.class)) {
                        if (property != null) {
                           field.set(null, property);
                        }
                     } else if (field.getType().equals(Integer.TYPE)) {
                        if (property != null) {
                           field.set(null, Integer.decode(property).intValue());
                        }
                     } else if (field.getType().equals(Double.TYPE)) {
                        if (property != null) {
                           field.set(null, Double.parseDouble(property));
                        }
                     } else if (field.getType().equals(Boolean.TYPE)) {
                        if (property != null) {
                           field.set(null, Boolean.parseBoolean(property));
                        }
                     } else if (field.getType().equals(Long.TYPE)) {
                        if (property != null) {
                           field.set(null, Long.decode(property).longValue());
                        }
                     } else if (field.getType().equals(Byte.TYPE)) { // new
                        if (property != null) {
                           field.set(null, Byte.parseByte(property));
                        }
                     } else if (field.getType().equals(Float.TYPE)) {
                        if (property != null) {
                           field.set(null, Float.parseFloat(property));
                        }
                     } else if (field.getType().equals(Short.TYPE)) {
                        if (property != null) {
                           field.set(null, Short.decode(property));
                        }
                     } else if (field.getType().equals(Character.TYPE)) {
                        if (property != null) {
                           field.set(null, property.charAt(0));
                        }
                     }
                     return true;
                  }
               } catch (Exception e) {
               }
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

      return false;
   }

   /**
    * Gets the value of the specified setting
    * 
    * @param settingName - The name of the setting, non case-sensitive
    * @return The value of the setting
    */
   private String get(String settingName) {
      Properties p = new Properties();
      try {
         Field[] declaredFields = GuiConsole.class.getDeclaredFields();
         for (Field field : declaredFields) {
            if (Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
               try {
                  if (!field.isAccessible()) {
                     field.setAccessible(true);
                  }
                  if (field.getType().equals(Integer.TYPE)) {
                     p.setProperty(field.getName(), field.getInt(null) + "");
                  } else if (field.getType().equals(Double.TYPE)) {
                     p.setProperty(field.getName(), field.getDouble(null) + "");
                  } else if (field.getType().equals(Boolean.TYPE)) {
                     p.setProperty(field.getName(), field.getBoolean(null) + "");
                  } else if (field.getType().equals(Long.TYPE)) {
                     p.setProperty(field.getName(), field.getLong(null) + "");
                  } else if (field.getType().equals(String.class)) {
                     p.setProperty(field.getName(), (String) field.get(null));
                  } else if (field.getType().equals(Byte.TYPE)) {
                     p.setProperty(field.getName(), field.getByte(null) + "");
                  } else if (field.getType().equals(Short.TYPE)) {
                     p.setProperty(field.getName(), field.getShort(null) + "");
                  } else if (field.getType().equals(Float.TYPE)) {
                     p.setProperty(field.getName(), field.getFloat(null) + "");
                  } else if (field.getType().equals(Character.TYPE)) {
                     p.setProperty(field.getName(), field.getChar(null) + "");
                  }
               } catch (Exception e) {
               }
            }
         }

         Object setting = p.get(settingName);

         if (setting != null) {
            return settingName + " = " + setting.toString();
         } else {
            return "\"" + settingName + "\"" + " does not exist";
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
      return "Error getting settings";
   }

   /**
    * @return List of the names of all of the properties
    */
   public String list() {
      String result = "";
      Field[] declaredFields = GuiConsole.class.getDeclaredFields();

      for (Field field : declaredFields) {
         result += field.getName() + "\n";
      }

      result.trim();
      return result;
   }

   @Override
   public void processOutput(String output) {
   }
}
