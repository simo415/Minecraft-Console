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
import de.matthiasmann.twl.ListBox.CallbackReason;
import de.matthiasmann.twl.model.ColorSpaceHSL;
import de.matthiasmann.twl.model.SimpleListModel;


public class ColorWindow extends BaseConsoleSettingsWindow {
   
   public static final String TITTLE = "Adjust color || Select color";
   public static final String BUTTONTITTLE = "Colors";
   
   protected static WidgetSimplewindow colorWindow;
   
   protected static ColorSelector colorSelector;
   protected static ArrayList<Field> validSelection;
   protected static ListBox<String> colorList;
   protected static WidgetClassicTwocolumn colorTwoColumns;
   protected static ListColorManager listColorManager;
   protected static boolean updatingColors = false;
   
   public String getTittle() {
      return BUTTONTITTLE;
   }

   public WidgetSimplewindow getMainWidget() {
      if(colorWindow == null)
         createWindow();
      
      return colorWindow;
   }
   
   //sets up and create window content
   protected static void createWindow() {
      colorTwoColumns = new WidgetClassicTwocolumn();
      
      colorSelector = new ColorSelector(new ColorSpaceHSL());
      colorSelector.setShowAlphaAdjuster(true);
      colorSelector.setShowNativeAdjuster(false);
      colorSelector.setShowRGBAdjuster(true);
      colorSelector.setShowPreview(true);
      
      colorTwoColumns.add(colorSelector);
      colorTwoColumns.heightOverrideExceptions.put(colorSelector, 0);
      
      //retrive all valid field that matters about color
      validSelection = new ArrayList<Field>();
      for (Field field : ConsoleSettings.getFields()) {
         if(field.getType().equals(Integer.TYPE) && field.getName().startsWith("COLOR_")){
            validSelection.add(field);
         }
      }
      
      //adds all valid fields to a list for easy selection
      colorList = new ListBox<String>(new FieldSimpleListModel(validSelection));
      colorList.setSelected(0);
      
      //construct window
      colorTwoColumns.add(colorList);
      colorWindow = new WidgetSimplewindow(colorTwoColumns, TITTLE);
      
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
         colorSelector.setColor(new Color(validSelection.get(ListColorManager.selection).getInt(null)));
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
         String temp = field.getName().substring(6).toLowerCase().replaceAll("_", " ");
         temp = temp.substring(0, 1).toUpperCase() + temp.substring(1);
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
            ColorWindow.validSelection.get(selection).setInt(null, color.toARGB());
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