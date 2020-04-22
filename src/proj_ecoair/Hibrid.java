package proj_ecoair;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

/**
 *  Classe que representa a classificação linear regretion. 
 *  Contêm um area de texto para mostrar a saída da execução dos programas
 *  chamados externamente. possui dois botões para a execução de duas partes:
 *  training set e class maker
 * 
 */
public class Hibrid extends JFrame
{
  private JFrame f;
  private String Img;
  private JTextArea area;
  private String oldImageMap;
  private String newImageMap;
  private JMenu changeMenu;
  private MenuLanguage menuLanguage;

  /**
   *  Constroi um quadro na tela.
   */
  public Hibrid(String Image, JMenu menu, MenuLanguage ml)
  {
       super("Linear Regretion");
       f=this;
       Img = Image;
       menuLanguage = ml;
       changeMenu = menu;
       oldImageMap = null;
       newImageMap = null;
       Container c = this.getContentPane();
       area = new JTextArea(8,30);
       JButton b1 = new JButton("Training Set Maker");
       b1.addActionListener(
         new ActionListener()
	       {
	         public void actionPerformed(ActionEvent e)
           {
               new TrainingSetMaker(menuLanguage, Img, area);
           }
	       }
       );
       JButton b2 = new JButton("Class Maker");
       b2.addActionListener(
        new ActionListener()
	      {
	         public void actionPerformed(ActionEvent e)
           {
                new ClassMaker(menuLanguage, Img, area, changeMenu);
           }
	      }
       );
       JPanel p1 = new JPanel();
       JPanel p2 = new JPanel();
       p1.setLayout(new FlowLayout());
       p2.setLayout(new FlowLayout());
       c.setLayout(new BorderLayout());
       c.add("North",p1);
       c.add("Center",p2);
       p1.add(b1);p1.add(b2);
       p2.add(new JScrollPane(area));
       this.pack();
       this.setBounds(0,0,350,250);
       this.show();
  }
}

