package com.vayner.console.guiapi;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.src.GuiApiHelper;
import net.minecraft.src.GuiModScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ModSettingScreen;
import net.minecraft.src.WidgetClassicTwocolumn;
import net.minecraft.src.WidgetSimplewindow;
import de.matthiasmann.twl.CallbackWithReason;
import de.matthiasmann.twl.Color;
import de.matthiasmann.twl.ColorSelector;
import de.matthiasmann.twl.ListBox;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.ListBox.CallbackReason;
import de.matthiasmann.twl.model.ColorSpaceHSL;
import de.matthiasmann.twl.model.SimpleListModel;


public class ColorWindow {
   
   
   protected static ColorSelector colorSelector;
   protected static ArrayList<Field> ValidSelection;
   protected static ListBox<String> colorList;
   protected static WidgetClassicTwocolumn colorTwoColumns;
   protected static WidgetSimplewindow colorWindow;
   protected static ListColorManager listColorManager;
   protected static boolean updatingColors = false;
   
   //sets up and create window content
   protected static void createColorWindow(ModSettingScreen SettingsScreen) {
      colorTwoColumns = new WidgetClassicTwocolumn();
      
      colorSelector = new ColorSelector(new ColorSpaceHSL());
      colorSelector.setShowAlphaAdjuster(true);
      colorSelector.setShowNativeAdjuster(false);
      colorSelector.setShowRGBAdjuster(true);
      colorSelector.setShowPreview(true);
      
      colorTwoColumns.add(colorSelector);
      colorTwoColumns.heightOverrideExceptions.put(colorSelector, 0);
      
      //retrive all valid field that matters about color
      ValidSelection = new ArrayList<Field>();
      for (Field field : ConsoleSettings.consoleFields) {
         if(field.getType().equals(Integer.TYPE) && field.getName().startsWith("COLOR_")){
            ValidSelection.add(field);
         }
      }
      
      //adds all valid fields to a list for easy selection
      colorList = new ListBox<String>(new FieldSimpleListModel(ValidSelection));
      colorList.setSelected(0);
      
      //construct window
      colorTwoColumns.add(colorList);
      colorWindow = new WidgetSimplewindow(colorTwoColumns, "Color selector");
      
      //add button from main config page to the new window
      SettingsScreen.append(
               GuiApiHelper.makeButton(
                  "Open Color selectors",
                  "show",
                  GuiModScreen.class,
                  true,
                  new Class[] { Widget.class },
                  colorWindow
                  )
               );
      
      //add callback
      listColorManager = new ListColorManager();
      colorList.addCallback(listColorManager);
      colorSelector.addCallback(listColorManager.hook);
      
      updateColorView();
   }
   
   protected static void updateColorView()
   {
      updatingColors = true;
      try {
         colorSelector.setColor(new Color(ValidSelection.get(ListColorManager.selection).getInt(null)));
      } catch (IllegalArgumentException e) {
         ModLoader.throwException("Exception in ColorWindow, field don't exist", e);
      } catch (IllegalAccessException e) {
         ModLoader.throwException("Exception in ColorWindow, field is not accsessable", e);
      }
      updatingColors = false;
   }
}

//wraper class for SimpleListModel<T> for less claddy code
class FieldSimpleListModel extends SimpleListModel<String> {
   
   private ArrayList<String> list;
   
   public FieldSimpleListModel(ArrayList<Field> arrayList) {
      list = new ArrayList<String>();
      for (Field field : arrayList) {
         String temp = field.getName().substring(6);
         String words [] = temp.toLowerCase().split("_");
         temp = words[0].substring(0, 1).toUpperCase() + words[0].substring(1);
         for (int i = 1; i < words.length; i++) {
            temp += " " + words[i];
         }
         list.add(temp);
      }
   }
   
   @Override
   public int getNumEntries() {
      return list.size();
   }

   @Override
   public String getEntry(int index) {
      return list.get(index);
   }
   
}

//callback classes for easier and a bit cleaner code
//manages changes in both the list and the color selector
class ListColorManager implements CallbackWithReason<ListBox.CallbackReason> {
   
   static Color color = new Color(0);
   static int selection = 0;
   static CallBackHookHelper hook = new CallBackHookHelper();
   
   @Override
   public void callback(CallbackReason reason) {
      selection = ColorWindow.colorList.getSelected();
      ColorWindow.updateColorView();
   }
   
   public static void updateColor(Color newColor)
   {
      if(!color.equals(newColor) && !ColorWindow.updatingColors){
         color = newColor;
         try {
            ColorWindow.ValidSelection.get(selection).setInt(null, color.toARGB());
         } catch (IllegalArgumentException e) {
            e.printStackTrace();
         } catch (IllegalAccessException e) {
            e.printStackTrace();
         }
      }
   }
   
}

//hook for colorSelector so that ListColorManager receives the changes
class CallBackHookHelper implements Runnable {
   
   @Override
   public void run() {
      ListColorManager.updateColor(ColorWindow.colorSelector.getColor());
   }
   
}
