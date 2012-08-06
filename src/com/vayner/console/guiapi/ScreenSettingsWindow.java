package com.vayner.console.guiapi;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.src.SettingInt;
import net.minecraft.src.WidgetClassicTwocolumn;
import net.minecraft.src.WidgetInt;
import net.minecraft.src.WidgetSimplewindow;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.FloatModel;


public class ScreenSettingsWindow extends BaseConsoleSettingsWindow{
   
   private static WidgetSimplewindow screenSettingsWindow;
   private static WidgetClassicTwocolumn windowContent;
   private static WidgetInt [] screenSizes;
   
   private static final String BUTTONTITTLE = "Adjust layout";
   private static final String TITTLE = "Screen size, padding & layout";

   public String getTittle() {
      return BUTTONTITTLE;
   }
   
   public Widget getMainWidget() {
      if(screenSettingsWindow == null)
         createWindow();
      
      return screenSettingsWindow;
   }

   private static void createWindow() {
      
      int amount = 0;
      ArrayList<Field> toUse = new ArrayList<Field>();
      
      for (Field field : ConsoleSettings.getFields()) {
         if(field.getType().equals(int.class) && field.getName().startsWith("SCREEN_")) {
            amount++;
            toUse.add(field);
         }
      }
      
      screenSizes = new WidgetInt [amount];
      
      for (int i = 0; i < toUse.size(); i++) {
         Field field = toUse.get(i);
         String temp = field.getName().substring(7).toLowerCase().replaceAll("_", " ");
         temp = temp.substring(0, 1).toUpperCase() + temp.substring(1);
         
         int value = 0;
         
         try {
            value = field.getInt(null);
         } catch (IllegalArgumentException e) {
            e.printStackTrace();
         } catch (IllegalAccessException e) {
            e.printStackTrace();
         }
         
         screenSizes[i] = new WidgetInt(new SettingInt(field.getName(), value, 0, 100), temp);
         screenSizes[i].addCallback(new screenParamCallBack(field, screenSizes[i]));
         
      }
      
      windowContent = new WidgetClassicTwocolumn(screenSizes);
      screenSettingsWindow = new WidgetSimplewindow(windowContent, TITTLE);
      
   }
}

//callback class to update field to match
class screenParamCallBack implements Runnable {
   
   private Field target;
   private WidgetInt provider;
   
   protected screenParamCallBack (Field TargetField, WidgetInt newValueProvider) {
      target = TargetField;
      provider = newValueProvider;
   }
   
   @Override
   public void run() {
      try {
         target.setInt(null, provider.settingReference.get());
      } catch (IllegalArgumentException e) {
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      }
   }
   
}
