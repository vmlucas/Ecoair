/*
 * Projeto Ecoair
 */
package proj_ecoair;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;

/**
 * Classe responsável pela criação do quadro que inicia a marcação dos pontos
 * de controle para a correção geométrica. Classe que verifica ainda se uma
 * marcação já foi feita ou não.
 */
public class gcp extends JFrame
{
  private JFrame f;
  private GeoPoints geoPoints = null;
  private MenuLanguage ml;
  private Vector names;
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JTextField jTextField1 = new JTextField();
  private JTextField jTextField2 = new JTextField();
  private JButton jButton1 = new JButton();
  private JButton jButton2 = new JButton();
  private JButton jButton3 = new JButton();
  private JButton jButton4 = new JButton();
  private boolean flag;
  
  /**
   * Constrói um quadro para a seleção dos pontos de controle e os salva em arquivo
   *
   */
  public gcp( MenuLanguage menuLanguage )
  {
    super((String)menuLanguage.getGCPTexts().elementAt(0));
    f=this;
    ml = menuLanguage;
    names = ml.getGCPTexts();
    flag = false;
    
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

    this.show();
  }

  private void jbInit() throws Exception
  {
    this.getContentPane().setLayout(null);
    this.setSize(new Dimension(425, 205));
    jLabel1.setText((String)names.elementAt(3));
    jLabel1.setBounds(new Rectangle(10, 35, 180, 15));
    jLabel1.setFont(new Font("Dialog", 1, 12));
    jLabel2.setText((String)names.elementAt(2));
    jLabel2.setBounds(new Rectangle(10, 70, 180, 15));
    jLabel2.setFont(new Font("Dialog", 1, 12));
    jTextField1.setBounds(new Rectangle(200, 30, 100, 20));
    jTextField2.setBounds(new Rectangle(200, 65, 100, 20));
    jButton1.setText((String)names.elementAt(5));
    jButton1.setBounds(new Rectangle(310, 25, 95, 25));
    jButton1.addActionListener(
        new ActionListener()
	      {
	         public void actionPerformed(ActionEvent e)
           {
               JFileChooser fc = new JFileChooser(".");
               ExampleFileFilter tiffFilter = new ExampleFileFilter("txt", (String)names.elementAt(6) );
               fc.setFileFilter(tiffFilter);
               int returnval = fc.showOpenDialog(f);
               if(returnval == JFileChooser.APPROVE_OPTION)
               {
                  String way = fc.getSelectedFile().getParent();
                  String f = fc.getSelectedFile().getName();
                  String arq = way+"\\"+f;
                  jTextField1.setText(f);
               }
           }
	      }
    );
    
    jButton2.setText((String)names.elementAt(1));
    jButton2.setBounds(new Rectangle(45, 135, 95, 25));
    jButton2.addActionListener(
        new ActionListener()
	      {
	         public void actionPerformed(ActionEvent e)
           {
              jButton2_actionPerformed(e);
           }
	       }
    );

    //salva os pontos marcados em um arquivo texto
    jButton3.setText((String)names.elementAt(8));
    jButton3.setBounds(new Rectangle(160, 135, 80, 25));
    jButton3.addActionListener(
       new ActionListener()
	     {
	         public void actionPerformed(ActionEvent e)
           {
              if(geoPoints != null)
                 geoPoints.writeFile();                 
           }
	     }
    );

    /******************************************************/
    //apaga o último ponto que foi marcado
    jButton4.setText((String)names.elementAt(9));
    jButton4.setBounds(new Rectangle(260, 135, 90, 25));
    jButton4.addActionListener(
       new ActionListener()
	     {
	         public void actionPerformed(ActionEvent e)
           {
              if(geoPoints != null)
                geoPoints.subtractCount();
           }
       }
    );
    /*************************************************************/
    this.getContentPane().add(jButton4, null);
    this.getContentPane().add(jButton3, null);
    this.getContentPane().add(jButton2, null);
    this.getContentPane().add(jButton1, null);
    this.getContentPane().add(jTextField2, null);
    this.getContentPane().add(jTextField1, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jLabel1, null);
  }

  private void jButton2_actionPerformed(ActionEvent e)
  {
    String s = jTextField1.getText();
    if(s != null)
    {
     
       if(!jTextField2.getText().equals(""))
       {
          geoPoints = new GeoPoints(s, 
                         Integer.parseInt(jTextField2.getText())); 
          flag = geoPoints.isReady();               
       }
       else
       {
          JOptionPane.showMessageDialog(null,(String)names.elementAt(2),"Warning",
              JOptionPane.INFORMATION_MESSAGE);
       }
    }
    else
    {
        JOptionPane.showMessageDialog(null,"Warning",(String)names.elementAt(3),
          JOptionPane.INFORMATION_MESSAGE);
    }  
  }

  public int getCount()
  {
    return geoPoints.getCount();
  }

  public int getPointx()
  {
    return geoPoints.getPointx();
  }

  public int getPointy()
  {
     return geoPoints.getPointy();
  }

  public void setMousePoints( int x, int y )
  {
    geoPoints.setMousePoints(x, y);
  }

  public boolean isReady()
  {
     return flag;
  }
  
}