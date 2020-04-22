package proj_ecoair;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.awt.Rectangle;

public class ProfileFrame extends JFrame
{

  //private atributes
  private JFrame profileFrame;
  private String rootDir;
  private StringBuffer rootDataBuffer;
  private JLabel channel1Label = new JLabel();
  private JLabel channel2Label = new JLabel();
  private JLabel classImageLabel = new JLabel();
  private JLabel maskImageLabel = new JLabel();
  private JLabel numberLabel = new JLabel();
  private JLabel ndviOutLabel = new JLabel();
  private JTextField channel1TF = new JTextField();
  private JButton channel1Button = new JButton();
  private JTextField channel2TF = new JTextField();
  private JTextField classTF = new JTextField();
  private JTextField maskTF = new JTextField();
  private JTextField numberTF = new JTextField();
  private JTextField ndviTF = new JTextField();
  private JButton channel2Button = new JButton();
  private JButton classButton = new JButton();
  private JButton maskIButton = new JButton();
  private JButton okButton = new JButton();
  private MenuLanguage ml;
  private String noaaCanal1ImageName;
  private String noaaCanal2ImageName;
  private String classificationImageName;
  private String maskName;
  private String classNumber;
  private String ndviFile;
  private Param param;   
  private String[] labels;
   
   public ProfileFrame(MenuLanguage ml)
   {
       super("Profiles");
       this.ml = ml;
       profileFrame=this;

       labels = ml.getProfilesLabels();
       
       String root = ".";
       rootDir = root+"\\INRIA-softs";
       rootDataBuffer = new StringBuffer(rootDir);
       rootDataBuffer.append("\\DATA");

       this.addWindowListener(
            new WindowAdapter()
            {
               public void windowClosing( WindowEvent WindowEvent)
               {
                  profileFrame.dispose();
               }
            }
       );
       try
       {
         jbInit();
       }
       catch(Exception e)
       {
         e.printStackTrace();
       }
       this.setBounds(0,0,457,300);
       this.setResizable(false);
       this.show();      

  }

  private void runProfiles()
  {
      //calling cloud detection
      int valor;
      valor = cloudDetection();
      if(valor != 1)
      {
           return;
      }
      //calling cloud correction
      valor = cloudCorrection();
      if(valor != 1)
      {
           return;
      }
      //calling proportions
      valor = proportions();
      if(valor != 1)
      {
           return;
      }
      //calling reflectance
      valor = reflectance();
      if(valor != 1)
      {
           return;
      }
      //calling ndi profiles
      valor = ndviProfiles();
      if(valor != 1)
      {
           return;
      }
  }

  private int cloudDetection()
  {
     int retorno = 0;

     try
     {
        //begining of cloud detection
        Runtime runTime = Runtime.getRuntime();
        JOptionPane.showMessageDialog(null,"CLOUD DETECTION ...","Message",
            JOptionPane.INFORMATION_MESSAGE);

        StringBuffer buffer = new StringBuffer(rootDir);
        buffer.append("\\PROGRAMS\\ecoair_mask.exe -c1 ");
        buffer.append(noaaCanal1ImageName);
        buffer.append(" -c2 ");
        buffer.append(noaaCanal2ImageName);
        buffer.append(" -mb ");
        buffer.append(maskName);
        buffer.append(" -o ");
        buffer.append(rootDir);
        buffer.append("\\TMP\\cloud_mask.inr");

        //executing the cloud detection
        Process process = runTime.exec(buffer.toString());

        int exitVal = process.waitFor();
        retorno = exitVal;
        if(exitVal == 1)
        {
           String message = new String( labels[13] );
           
           JOptionPane.showMessageDialog(null,message,"Message",
              JOptionPane.PLAIN_MESSAGE);

        }
        else
        {
           JOptionPane.showMessageDialog(null, labels[15] ,"Message",
              JOptionPane.PLAIN_MESSAGE);

         }
     }
     catch(Exception IOEx)
     {
        JOptionPane.showMessageDialog(null,IOEx.toString(),"Message",
          JOptionPane.INFORMATION_MESSAGE);
     }

     return retorno;
  }

  private int cloudCorrection()
  {
     int retorno = 0;

     try
     {
        //begining of cloud correction
        Runtime runTime = Runtime.getRuntime();
        JOptionPane.showMessageDialog(null,"CLOUD CORRECTION...","Message",
            JOptionPane.INFORMATION_MESSAGE);

        StringBuffer buffer = new StringBuffer(rootDir);
        buffer.append("\\PROGRAMS\\ecoair_interpolation.exe -c1 ");
        buffer.append(noaaCanal1ImageName);
        buffer.append(" -c2 ");
        buffer.append(noaaCanal2ImageName);
        buffer.append(" -m ");
        buffer.append(rootDir);
        buffer.append("\\TMP\\cloud_mask.inr -o1 ");
        buffer.append(rootDir);
        buffer.append("\\TMP\\cloud_corrected1.inr -o2 ");
        buffer.append(rootDir);
        buffer.append("\\TMP\\cloud_corrected2.inr -mb ");
        buffer.append(maskName);

        //executing the cloud correction
        Process process = runTime.exec(buffer.toString());
        //-smooth -alpha 1 (optional parameters)

        int exitVal = process.waitFor();
        retorno = exitVal;
        if(exitVal == 1)
        {
          String message = new String( labels[16] );
          
          JOptionPane.showMessageDialog(null,message,"Message",
             JOptionPane.PLAIN_MESSAGE);

        }
        else
        {
          JOptionPane.showMessageDialog(null, labels[15] ,"Message",
                JOptionPane.PLAIN_MESSAGE);

        }
        //end of cloud correction
     }
     catch(Exception IOEx)
     {
        JOptionPane.showMessageDialog(null,IOEx.toString(),"Message",
           JOptionPane.INFORMATION_MESSAGE);
     }

     return retorno;
  }

  private int proportions()
  {
     int retorno = 0;

     try
     {
        //begining of cloud correction
        Runtime runTime = Runtime.getRuntime();
        JOptionPane.showMessageDialog(null,"PROPORTIONS COMPUTATION ...","Message",
            JOptionPane.INFORMATION_MESSAGE);

        StringBuffer buffer = new StringBuffer(rootDir);
        buffer.append("\\PROGRAMS\\ecoair_noaa_proportions.exe -mb ");
        buffer.append(maskName);
        buffer.append(" -c ");
        buffer.append(classificationImageName);
        buffer.append(" -o ");
        buffer.append(rootDir);
        buffer.append("\\TMP\\proportions98.inr ");
        buffer.append("-nbc ");
        buffer.append(classNumber);
        System.out.println(buffer);
        //executing the propotions
        Process process = runTime.exec(buffer.toString());

        int exitVal = process.waitFor();
        retorno = exitVal;
        if(exitVal == 1)
        {
          String message = new String( labels[19] );
          
          JOptionPane.showMessageDialog(null,message,"Message",
             JOptionPane.PLAIN_MESSAGE);
         }
         else
         {
            JOptionPane.showMessageDialog(null, labels[15] ,"Message",
              JOptionPane.PLAIN_MESSAGE);

         }
         //end of propotions
     }
     catch(Exception IOEx)
     {
       JOptionPane.showMessageDialog(null,IOEx.toString(),"Message",
          JOptionPane.INFORMATION_MESSAGE);
     }

     return retorno;
  }

  private int reflectance()
  {
     int retorno = 0;

     try
     {
        //begining of reflectance for channel 1
        Runtime runTime = Runtime.getRuntime();
        JOptionPane.showMessageDialog(null, labels[20] ,"Message",
            JOptionPane.INFORMATION_MESSAGE);

        StringBuffer buffer = new StringBuffer(rootDir);
        buffer.append("\\PROGRAMS\\ecoair_profiles.exe -i ");
        buffer.append(rootDir);
        buffer.append("\\TMP\\cloud_corrected1.inr -p ");
        buffer.append(rootDir);
        buffer.append("\\TMP\\proportions98.inr -mb ");
        buffer.append(maskName);
        buffer.append(" -o ");
        buffer.append(rootDir);
        buffer.append("\\TMP\\seq_reflectance1.inr");

        System.out.println(buffer);

        //executing the reflectance for channel 1
        Process process = runTime.exec(buffer.toString());

        int exitVal = process.waitFor();
        retorno = exitVal;
        if(exitVal == 1)
        {
          String message = new String( labels[21] );
          
          JOptionPane.showMessageDialog(null,message,"Message",
             JOptionPane.PLAIN_MESSAGE);
         }
         else
         {
            JOptionPane.showMessageDialog(null, labels[15] ,"Message",
              JOptionPane.PLAIN_MESSAGE);

         }
         //end of reflectance for channel 1
     }
     catch(Exception IOEx)
     {
       JOptionPane.showMessageDialog(null,IOEx.toString(),"Message",
          JOptionPane.INFORMATION_MESSAGE);
     }

     try
     {
        //begining of reflectance for channel 2
        Runtime runTime = Runtime.getRuntime();
        JOptionPane.showMessageDialog(null, labels[22] ,"Message",
            JOptionPane.INFORMATION_MESSAGE);

        StringBuffer buffer = new StringBuffer(rootDir);
        buffer.append("\\PROGRAMS\\ecoair_profiles.exe -i ");
        buffer.append(rootDir);
        buffer.append("\\TMP\\cloud_corrected2.inr -p ");
        buffer.append(rootDir);
        buffer.append("\\TMP\\proportions98.inr -mb ");
        buffer.append(maskName);
        buffer.append(" -o ");
        buffer.append(rootDir);
        buffer.append("\\TMP\\seq_reflectance2.inr ");

        System.out.println(buffer);

        //executing the propotions
        Process process = runTime.exec(buffer.toString());

        int exitVal = process.waitFor();
        retorno = exitVal;
        if(exitVal == 1)
        {
          String message = new String( labels[23] );
          
          JOptionPane.showMessageDialog(null,message,"Message",
             JOptionPane.PLAIN_MESSAGE);
         }
         else
         {
            JOptionPane.showMessageDialog(null, labels[15] ,"Message",
              JOptionPane.PLAIN_MESSAGE);

         }
         //end of propotions
     }
     catch(Exception IOEx)
     {
       JOptionPane.showMessageDialog(null,IOEx.toString(),"Message",
          JOptionPane.INFORMATION_MESSAGE);
     }

     return retorno;
  }

  private int ndviProfiles()
  {
     int retorno = 0;

     try
     {
        //begining of ndvi profiles
        Runtime runTime = Runtime.getRuntime();

        JOptionPane.showMessageDialog(null,"NDVI PROFILES...","Message",
            JOptionPane.INFORMATION_MESSAGE);

        StringBuffer buffer = new StringBuffer(rootDir);
        buffer.append("\\PROGRAMS\\ecoair_indice_ind.exe -c1 ");
        buffer.append(rootDir);
        buffer.append("\\TMP\\seq_reflectance1.inr -c2 ");
        buffer.append(rootDir);
        buffer.append("\\TMP\\seq_reflectance2.inr -o ");
        buffer.append(rootDir);
        buffer.append("\\TMP\\seq_ndvi.inr -a ");
        buffer.append(rootDir);
        buffer.append("\\OUTPUTS\\"+ndviFile);

        System.out.println(buffer);

        //executing the ndi profiles
        Process process = runTime.exec(buffer.toString());

        int exitVal = process.waitFor();
        retorno = exitVal;
        if(exitVal == 1)
        {
          String message = new String( labels[24] );
          

          JOptionPane.showMessageDialog(null,message,"Message",
             JOptionPane.PLAIN_MESSAGE);
         }
         else
         {
            JOptionPane.showMessageDialog(null, labels[15] ,"Message",
              JOptionPane.PLAIN_MESSAGE);

         }
         //end of reflectance for channel 1
     }
     catch(Exception IOEx)
     {
       JOptionPane.showMessageDialog(null,IOEx.toString(),"Message",
          JOptionPane.INFORMATION_MESSAGE);
     }

     return retorno;
  }

  private void jbInit() throws Exception
  {
    this.getContentPane().setLayout(null);
    this.setSize(new Dimension(457, 300));
    channel1Label.setText( labels[0] );
    channel1Label.setBounds(new Rectangle(5, 20, 155, 20));
    channel2Label.setText( labels[1] );
    channel2Label.setBounds(new Rectangle(5, 50, 155, 20));
    classImageLabel.setText( labels[2] );
    classImageLabel.setBounds(new Rectangle(5, 80, 155, 20));
    maskImageLabel.setText( labels[3] );
    maskImageLabel.setBounds(new Rectangle(5, 110, 155, 20));
    numberLabel.setText( labels[4] );
    numberLabel.setBounds(new Rectangle(5, 140, 155, 20));
    ndviOutLabel.setText( labels[5] );
    ndviOutLabel.setBounds(new Rectangle(5, 170, 155, 25));
    channel1TF.setBounds(new Rectangle(205, 20, 125, 20));
    channel1Button.setText( labels[6] );
    channel1Button.setBounds(new Rectangle(350, 20, 90, 25));
    channel1Button.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          channel1Button_actionPerformed(e);
        }
      });
    channel2TF.setBounds(new Rectangle(205, 50, 125, 20));
    classTF.setBounds(new Rectangle(205, 80, 125, 20));
    maskTF.setBounds(new Rectangle(205, 110, 125, 20));
    numberTF.setBounds(new Rectangle(205, 140, 125, 20));
    ndviTF.setBounds(new Rectangle(205, 170, 125, 20));
    channel2Button.setText( labels[6] );
    channel2Button.setBounds(new Rectangle(350, 50, 90, 25));
    channel2Button.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          channel2Button_actionPerformed(e);
        }
      });
    classButton.setText( labels[6] );
    classButton.setBounds(new Rectangle(350, 80, 90, 25));
    classButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          classButton_actionPerformed(e);
        }
      });
    maskIButton.setText( labels[6] );
    maskIButton.setBounds(new Rectangle(350, 110, 90, 25));
    maskIButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          maskIButton_actionPerformed(e);
        }
      });
    okButton.setText( labels[7] );
    okButton.setBounds(new Rectangle(180, 220, 73, 27));
    okButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          okButton_actionPerformed(e);
        }
      });
    this.getContentPane().add(okButton, null);
    this.getContentPane().add(maskIButton, null);
    this.getContentPane().add(classButton, null);
    this.getContentPane().add(channel2Button, null);
    this.getContentPane().add(ndviTF, null);
    this.getContentPane().add(numberTF, null);
    this.getContentPane().add(maskTF, null);
    this.getContentPane().add(classTF, null);
    this.getContentPane().add(channel2TF, null);
    this.getContentPane().add(channel1Button, null);
    this.getContentPane().add(channel1TF, null);
    this.getContentPane().add(ndviOutLabel, null);
    this.getContentPane().add(numberLabel, null);
    this.getContentPane().add(maskImageLabel, null);
    this.getContentPane().add(classImageLabel, null);
    this.getContentPane().add(channel2Label, null);
    this.getContentPane().add(channel1Label, null);
  }

  private void channel1Button_actionPerformed(ActionEvent e)
  {
     openFile( this.channel1TF );
  }

  private void channel2Button_actionPerformed(ActionEvent e)
  {
     openFile( channel2TF );
  }

  private void classButton_actionPerformed(ActionEvent e)
  {
     openFile( classTF );
  }

  private void maskIButton_actionPerformed(ActionEvent e)
  {
     openFile( maskTF );     
  }

  private void okButton_actionPerformed(ActionEvent e)
  {
     classNumber = numberTF.getText();
     ndviFile = ndviTF.getText();     
     noaaCanal1ImageName = channel1TF.getText();
     noaaCanal2ImageName = channel2TF.getText();
     classificationImageName = classTF.getText();
     maskName = maskTF.getText();
     Param.maskName = maskName;
     
     if((noaaCanal1ImageName == null)||(noaaCanal1ImageName.equals(" ")) )
     {
         JOptionPane.showMessageDialog(null, labels[8] ,"Message",
              JOptionPane.INFORMATION_MESSAGE);
         return;
     }
     if((noaaCanal2ImageName == null)||(noaaCanal2ImageName.equals(" ")))
     {
         JOptionPane.showMessageDialog(null, labels[9] ,"Message",
             JOptionPane.INFORMATION_MESSAGE);
         return;
     }
     if((maskName == null)||(maskName.equals(" ")))
     {
         JOptionPane.showMessageDialog(null, labels[10] ,"Message",
             JOptionPane.INFORMATION_MESSAGE);
         return;
     }
     if((classNumber == null)||(classNumber.equals(" ")))
     {
         JOptionPane.showMessageDialog(null, labels[11] ,"Message",
             JOptionPane.INFORMATION_MESSAGE);
        return;
     }
     runProfiles();
           
  }

  private void openFile( JTextField tf )
  {
     JFileChooser fileChooser = new JFileChooser(rootDataBuffer.toString());
     ExampleFileFilter inrFilter = new ExampleFileFilter("inr", labels[12] );
     fileChooser.setFileFilter(inrFilter);
     int returnval = fileChooser.showOpenDialog(profileFrame);
     if(returnval == JFileChooser.APPROVE_OPTION)
     {
         String fileChosenPath = fileChooser.getSelectedFile().getParent();
         String fileChosen = fileChooser.getSelectedFile().getName();
         String imageCompletePath = fileChosenPath+"\\"+fileChosen;
         tf.setText( imageCompletePath );
     }

  }
}