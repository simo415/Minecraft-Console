package com.vayner.console.external;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.RenderingHints.Key;

import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import net.minecraft.src.ModLoader;

import org.lwjgl.input.Keyboard;

import com.sijobe.console.GuiConsole;

import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ExternalConsoleWindow extends JFrame {
   
   private static int COLOR_BACKGROUND = Color.DARK_GRAY.getRGB();
   private JTextField textField;
   private JTextArea textArea;
   private JScrollPane scrollPane;
   
   
   public ExternalConsoleWindow(String Tittle) {
      super(Tittle);
      setFont(new Font("Consolas", Font.PLAIN, 12));
      setBackground(Color.DARK_GRAY);
      setVisible(true);
      GridBagLayout gridBagLayout = new GridBagLayout();
      gridBagLayout.columnWidths = new int[]{0, 0};
      gridBagLayout.rowHeights = new int[]{0, 0, 0};
      gridBagLayout.columnWeights = new double[]{1.0};
      gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
      getContentPane().setLayout(gridBagLayout);
      
      scrollPane = new JScrollPane();
      scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      GridBagConstraints gbc_scrollPane = new GridBagConstraints();
      gbc_scrollPane.fill = GridBagConstraints.BOTH;
      gbc_scrollPane.gridwidth = 2;
      gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
      gbc_scrollPane.gridx = 0;
      gbc_scrollPane.gridy = 0;
      getContentPane().add(scrollPane, gbc_scrollPane);
      
      textArea = new JTextArea();
      scrollPane.setViewportView(textArea);
      textArea.setLineWrap(true);
      textArea.setWrapStyleWord(true);
      textArea.setFont(new Font("Tahoma", Font.PLAIN, 12));
      textArea.setEditable(false);
      
      textField = new JTextField();
      textField.addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER)
               sendMessage();
         }
      });
      GridBagConstraints gbc_textField = new GridBagConstraints();
      gbc_textField.insets = new Insets(0, 0, 0, 5);
      gbc_textField.anchor = GridBagConstraints.SOUTH;
      gbc_textField.fill = GridBagConstraints.HORIZONTAL;
      gbc_textField.gridx = 0;
      gbc_textField.gridy = 1;
      getContentPane().add(textField, gbc_textField);
      textField.setColumns(10);
      
      JButton btnSend = new JButton("Enter");
      btnSend.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent arg0) {
            sendMessage();
         }
      });
      GridBagConstraints gbc_btnSend = new GridBagConstraints();
      gbc_btnSend.gridx = 1;
      gbc_btnSend.gridy = 1;
      getContentPane().add(btnSend, gbc_btnSend);
      
      this.setSize(300, 500);
   }
   
   public void reciveMessage(String message) {
      Document doc = textArea.getDocument();
      try {
         if(message.startsWith("\u00a7"))
            doc.insertString(doc.getLength(), message.substring(2) + "\n", null);
         else
            doc.insertString(doc.getLength(), message + "\n", null);
      } catch (BadLocationException e) {
         System.out.println("[MCC] Something has gone horrobly wrong with the external console, more spesificly 'reciveMessage'");
         ModLoader.throwException("Something has gone horrobly wrong with the external console, more spesificly 'reciveMessage'", e);
      }
      
      scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
   }
   
   public void clearMessages() {
      textArea.setText("");
   }
   
   public void sendMessage() {
      String message = textField.getText();
      textField.setText("");
      GuiConsole.getInstance().sendUncleanMessage(message);
   }
}
