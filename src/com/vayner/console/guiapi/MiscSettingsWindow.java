package com.vayner.console.guiapi;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;

import de.matthiasmann.twl.Widget;
import net.minecraft.src.WidgetClassicTwocolumn;
import net.minecraft.src.WidgetSimplewindow;


public class MiscSettingsWindow extends BaseConsoleSettingsWindow {
   
   private static final String BUTTONTITTLE = "Adjust misc options";
   private static final String TITTLE = "Misc settings window";
   
   private static WidgetSimplewindow miscWindowWidget;
   private static WidgetClassicTwocolumn windowLayout; 
   private static ArrayList<Widget> parameterControls;
   
   private static ArrayList<Field> validSelection;
   
   @Override
   public String getTittle() {
      return BUTTONTITTLE;
   }
   
   @Override
   public WidgetSimplewindow getMainWidget() {
      if(miscWindowWidget == null)
         createWindow();
      return miscWindowWidget;
   }

   private void createWindow() {
      
      parameterControls = new ArrayList<Widget>();
      
      validSelection = new ArrayList<Field>();
      for (Field field : ConsoleSettings.getFields()) {
         if(field.getType().equals(Integer.TYPE) && field.getName().startsWith("MISC_")){
            validSelection.add(field);
         }
      }
      
      
	   
	   windowLayout = new WidgetClassicTwocolumn(parameterControls.toArray(new Widget[0]));
	   miscWindowWidget = new WidgetSimplewindow(windowLayout, TITTLE);
	   
   }
   
}