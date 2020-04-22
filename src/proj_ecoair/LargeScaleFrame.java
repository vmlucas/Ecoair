package proj_ecoair;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LargeScaleFrame extends JFrame
{
  private JFrame f;
  private String rootDir;
  private JButton oKButton = new JButton();
  private JLabel proportionsLabel = new JLabel();
  private JTextField proportionsTextField = new JTextField();
  private String[] names;
  
  public LargeScaleFrame( MenuLanguage ml)
  {
    super("Large Scale Classification");
    f=this;
    names = ml.getLargeScaleNames();
    String root = ".";
    rootDir = root+"\\INRIA-softs";
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    this.setResizable(false);
    this.show();
    
  }

  private void runLargeScale()
  {
     try
     {
        //begining of cloud detection
        Runtime runTime = Runtime.getRuntime();
        JOptionPane.showMessageDialog(null,"LargeScale Classification...","Message",
            JOptionPane.INFORMATION_MESSAGE);

        StringBuffer buffer = new StringBuffer(rootDir);
        buffer.append("\\PROGRAMS\\ecoair_noaa_proportions_estimation.exe -c1 ");
        buffer.append(rootDir+"\\TMP\\cloud_corrected1.inr");
        buffer.append(" -c2 ");
        buffer.append(rootDir+"\\TMP\\cloud_corrected2.inr");
        buffer.append(" -r1 ");
        buffer.append(rootDir+"\\TMP\\seq_reflectance1.inr");
        buffer.append(" -r2 ");
        buffer.append(rootDir+"\\TMP\\seq_reflectance2.inr");
        buffer.append(" -can1 -can2 -o ");
        buffer.append(rootDir+"\\OUTPUT\\"+proportionsTextField.getText()+" -mb ");
        buffer.append(Param.maskName);

        //executing the cloud detection
        Process process = runTime.exec(buffer.toString());

        int exitVal = process.waitFor();
        if(exitVal == 1)
        {
           String message = new String( names[0] );
           
           JOptionPane.showMessageDialog(null,message,"Message",
              JOptionPane.PLAIN_MESSAGE);

        }
        else
        {
           JOptionPane.showMessageDialog(null, names[1] ,"Message",
              JOptionPane.PLAIN_MESSAGE);

         }
     }
     catch(Exception IOEx)
     {
        JOptionPane.showMessageDialog(null,IOEx.toString(),"Message",
          JOptionPane.INFORMATION_MESSAGE);
     }

  }

  private void jbInit() throws Exception
  {
    this.getContentPane().setLayout(null);
    this.setSize(new Dimension(330, 194));
    oKButton.setText("Ok");
    oKButton.setBounds(new Rectangle(120, 115, 65, 30));
    oKButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          oKButton_actionPerformed(e);
        }
      });
    proportionsLabel.setText( names[2] );
    proportionsLabel.setBounds(new Rectangle(5, 35, 165, 20));
    proportionsLabel.setFont(new Font("Dialog", 0, 13));
    proportionsTextField.setBounds(new Rectangle(195, 35, 120, 25));
    proportionsTextField.setFont(new Font("SansSerif", 0, 13));
    this.getContentPane().add(proportionsTextField, null);
    this.getContentPane().add(proportionsLabel, null);
    this.getContentPane().add(oKButton, null);
  }

  private void oKButton_actionPerformed(ActionEvent e)
  {
     if(Param.maskName != null)
     {
        runLargeScale();
     }
     else
     {
        JOptionPane.showMessageDialog(null, names[3] ,"Message",
                    JOptionPane.INFORMATION_MESSAGE);

     }  
  }

}