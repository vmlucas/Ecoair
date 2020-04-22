/*
 * Projeto Ecoair
 */
package proj_ecoair;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.JButton;

/**
 * Classe que gera uma tela com as informações da imagem recebida como
 * parâmetro em seu construtor
 */
public class InfoFrame extends JFrame
{
   
  private JFrame f;
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabel4 = new JLabel();
  private JButton jButton1 = new JButton();
  private Vector names;
  private RenderedImage img;

  public InfoFrame( RenderedImage img, MenuLanguage ml )
  {
    super( (String)ml.getInfoFrameTexts().elementAt(0) );
    f = this;
    names = ml.getInfoFrameTexts();
    this.img = img;
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
    this.setSize(new Dimension(328, 174));
    jLabel1.setText((String)names.elementAt(1));
    jLabel1.setBounds(new Rectangle(20, 25, 140, 25));
    jLabel1.setFont(new Font("Dialog", 1, 12));
    jLabel2.setText((String)names.elementAt(2));
    jLabel2.setBounds(new Rectangle(20, 60, 140, 20));
    jLabel2.setFont(new Font("Dialog", 1, 12));
    jLabel3.setText(String.valueOf(img.getWidth()));
    jLabel3.setBounds(new Rectangle(180, 25, 115, 20));
    jLabel3.setFont(new Font("Dialog", 1, 12));
    jLabel4.setText(String.valueOf(img.getHeight()));
    jLabel4.setBounds(new Rectangle(180, 60, 130, 20));
    jLabel4.setFont(new Font("Dialog", 1, 12));
    jButton1.setText("OK");
    jButton1.setBounds(new Rectangle(120, 105, 73, 27));
    jButton1.addActionListener(
        new ActionListener()
	      {
	         public void actionPerformed(ActionEvent e)
           {
               f.dispose();
           }
	      }
    );
    this.getContentPane().add(jButton1, null);
    this.getContentPane().add(jLabel4, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jLabel1, null);
  }
   
}