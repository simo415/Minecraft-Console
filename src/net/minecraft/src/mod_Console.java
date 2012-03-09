package net.minecraft.src;

/**
 * 
 * @author simo_415
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *  
 */
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

import com.sijobe.console.GuiConsole;

public class mod_Console extends BaseMod {
   
   public static KeyBinding openKey;
   
   public mod_Console() {
      GuiConsole.getInstance();
      openKey = new KeyBinding("Console", Keyboard.KEY_BACKSLASH);
      ModLoader.registerKey(this, openKey, false);
   }
   
   @Override
   public void keyboardEvent(KeyBinding event) {
      Minecraft game = ModLoader.getMinecraftInstance();
      if (game.currentScreen == null) {
         game.displayGuiScreen(GuiConsole.getInstance());
      }
   }
   
   @Override
   public void load() {
      try{
         Class.forName("net.minecraft.src.GuiApiHelper", false, null); //Checks if GUIAPI exists
         
         net.minecraft.src.ModSettings settings = new net.minecraft.src.ModSettings("Console");
         net.minecraft.src.ModSettingScreen settingScreen = new net.minecraft.src.ModSettingScreen("Minecraft Console");
         settingScreen.setSingleColumn(true);
         try {
            Field[] declaredFields = GuiConsole.class.getDeclaredFields();
            
            for (Field field : declaredFields) {
               if (Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                  try {
                     if (!field.isAccessible()) {
                        field.setAccessible(true);
                     }
                     if (field.getType().equals(Integer.TYPE)) {
                        if(field.getName().startsWith("COLOR")){
                           net.minecraft.src.WidgetSinglecolumn widget = new net.minecraft.src.WidgetSinglecolumn();
                           widget.childDefaultWidth = 300;
                           String niceName = field.getName().toLowerCase().replaceAll("_", " ").substring(6) + " color";
                           widget.add(new de.matthiasmann.twl.Label(niceName + ":"));
                           de.matthiasmann.twl.ColorSelector colorSelector = new de.matthiasmann.twl.ColorSelector(new de.matthiasmann.twl.model.ColorSpaceHSL());
                           colorSelector.setShowHexEditField(true);
                           colorSelector.setShowNativeAdjuster(false);
                           colorSelector.setShowAlphaAdjuster(true);
                           colorSelector.setShowRGBAdjuster(true);
                           colorSelector.setShowPreview(true);
                           colorSelector.setColor(new de.matthiasmann.twl.Color(field.getInt(null)));
                           widget.add(colorSelector);
                           widget.heightOverrideExceptions.put(colorSelector, 0);
                           settingScreen.append(widget);
                           settingScreen.widgetColumn.heightOverrideExceptions.put(widget, 0);
                        }else{
                           settings.addSetting(settingScreen, field.getName().toLowerCase().replaceAll("_", " "), field.getName(), field.getInt(null), 0, 0xFFFFFFFF);
                        }                        
                     }else if (field.getType().equals(Boolean.TYPE)) {
                        settings.addSetting(settingScreen, field.getName().toLowerCase().replaceAll("_", " "), field.getName(), field.getBoolean(null));
                     } else if (field.getType().equals(Long.TYPE)) {
                        settings.addSetting(settingScreen, field.getName().toLowerCase().replaceAll("_", " "), field.getName(), field.getLong(null));
                     } else if (field.getType().equals(String.class)) {
                        settings.addSetting(settingScreen, field.getName().toLowerCase().replaceAll("_", " "), field.getName(), (String) field.get(null));
                     } else if (field.getType().equals(Float.TYPE)) {
                        settings.addSetting(settingScreen, field.getName().toLowerCase().replaceAll("_", " "), field.getName(), field.getFloat(null));
                     }
                  } catch (Exception e) {
                     e.printStackTrace();
                  }
               }
            }
            
         } catch (Exception e) {
            e.printStackTrace();
         }
         
         settings.load();
      }catch(ClassNotFoundException exception){
         System.out.println("DNE");
      }
   }
   
   public String getVersion() {
      return GuiConsole.VERSION;
   }      
}