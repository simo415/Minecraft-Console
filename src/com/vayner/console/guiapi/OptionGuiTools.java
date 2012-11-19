package com.vayner.console.guiapi;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import net.minecraft.src.WidgetBoolean;
import net.minecraft.src.WidgetFloat;
import net.minecraft.src.WidgetInt;
import net.minecraft.src.WidgetSetting;
import net.minecraft.src.WidgetText;
import de.matthiasmann.twl.Widget;


public class OptionGuiTools {
   
   static public ArrayList<Widget> genericFieldToWidget(ArrayList<Field> fields) {
      
      ArrayList<Widget> widgets = new ArrayList<Widget>(10);
      
      for (Field field : fields) {
         Type fieldType = field.getGenericType();
         
         
         if(fieldType.equals(Boolean.TYPE)){
            //TODO
            continue;
         }
         
         
         if(fieldType.equals(Byte.TYPE)){
            //TODO
            continue;
         }
         
         if(fieldType.equals(Character.TYPE)){
            //TODO
            continue;
         }
         
         if(fieldType.equals(Short.TYPE)){
            //TODO
            continue;
         }
         
         if(fieldType.equals(Integer.TYPE)){
            //TODO
            continue;
         }
         
         if(fieldType.equals(Long.TYPE)){
            //TODO
            continue;
         }
         
         
         if(fieldType.equals(Double.TYPE)){
            //TODO
            continue;
         }
         
         if(fieldType.equals(Float.TYPE)){
            //TODO
            continue;
         }
         
         
         if(fieldType.equals(String.class)){
            //TODO
            continue;
         }
      }
      
      return widgets;
   }
   
   //TODO make a generic version that works for all the above, or just 1 for each...
   static public String cleanFieldName (Field field) {
      StringBuilder string = new StringBuilder(field.getName());
      
      String temp = field.getName().substring(7).toLowerCase().replaceAll("_", " ");
      temp = temp.substring(0, 1).toUpperCase() + temp.substring(1);
      
      return temp;
   }
   
}


class BooleanFieldCallback implements Runnable {
   
   private Field target;
   private WidgetBoolean provider;
   
   protected BooleanFieldCallback (Field TargetField, WidgetBoolean newValueProvider) {
      target = TargetField;
      provider = newValueProvider;
   }
   
   @Override
   public void run () {
      try {
         target.setBoolean(null, provider.settingReference.get());
      } catch (IllegalArgumentException e) {
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      }
   }
}


class ByteFieldCallback implements Runnable {
   
   private Field target;
   private WidgetInt provider;
   
   protected ByteFieldCallback (Field TargetField, WidgetInt newValueProvider) {
      target = TargetField;
      provider = newValueProvider;
   }
   
   @Override
   public void run () {
      byte[] bytes = ByteBuffer.allocate(4).putInt(provider.settingReference.get()).array();
      try {
         target.setByte(null, bytes[3]);
      } catch (IllegalArgumentException e) {
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      }
   }
}

class CharFieldCallback implements Runnable {
   
   private Field target;
   private WidgetInt provider;
   
   protected CharFieldCallback (Field TargetField, WidgetInt newValueProvider) {
      target = TargetField;
      provider = newValueProvider;
   }
   
   @Override
   public void run () {
      char[] chars = ByteBuffer.allocate(4).putInt(provider.settingReference.get()).asCharBuffer().array();
      try {
         target.setChar(null, chars[3]);
      } catch (IllegalArgumentException e) {
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      }
   }
}

class ShortFieldCallback implements Runnable {
   
   private Field target;
   private WidgetInt provider;
   
   protected ShortFieldCallback (Field TargetField, WidgetInt newValueProvider) {
      target = TargetField;
      provider = newValueProvider;
   }
   
   @Override
   public void run () {
      short[] shorts = ByteBuffer.allocate(4).putInt(provider.settingReference.get()).asShortBuffer().array();
      try {
         target.setShort(null, shorts[1]);
      } catch (IllegalArgumentException e) {
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      }
   }
}

class IntFieldCallback implements Runnable {
   
   private Field target;
   private WidgetInt provider;
   
   protected IntFieldCallback (Field TargetField, WidgetInt newValueProvider) {
      target = TargetField;
      provider = newValueProvider;
   }
   
   @Override
   public void run () {
      try {
         target.setInt(null, provider.settingReference.get());
      } catch (IllegalArgumentException e) {
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      }
   }
}

class LongFieldCallback implements Runnable {
   
   private Field target;
   private WidgetInt provider;
   
   protected LongFieldCallback (Field TargetField, WidgetInt newValueProvider) {
      target = TargetField;
      provider = newValueProvider;
   }
   
   @Override
   public void run () {
      try {
         target.setLong(null, provider.settingReference.get());
      } catch (IllegalArgumentException e) {
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      }
   }
}


class FloatFieldCallback implements Runnable {
   
   private Field target;
   private WidgetFloat provider;
   
   protected FloatFieldCallback (Field TargetField, WidgetFloat newValueProvider) {
      target = TargetField;
      provider = newValueProvider;
   }
   
   @Override
   public void run () {
      try {
         target.setFloat(null, provider.settingReference.get());
      } catch (IllegalArgumentException e) {
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      }
   }
}

class DoubleFieldCallback implements Runnable {
   
   private Field target;
   private WidgetFloat provider;
   
   protected DoubleFieldCallback (Field TargetField, WidgetFloat newValueProvider) {
      target = TargetField;
      provider = newValueProvider;
   }
   
   @Override
   public void run () {
      try {
         target.setDouble(null, provider.settingReference.get());
      } catch (IllegalArgumentException e) {
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      }
   }
}


class StringFieldCallback implements Runnable {
   
   private Field target;
   private WidgetText provider;
   
   protected StringFieldCallback (Field TargetField, WidgetText newValueProvider) {
      target = TargetField;
      provider = newValueProvider;
   }
   
   @Override
   public void run () {
      try {
         target.set(null, provider.settingReference.get());
      } catch (IllegalArgumentException e) {
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      }
   }   
}


