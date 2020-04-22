package proj_ecoair;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.Rectangle;
import java.awt.Dimension;

public class TrainingSetMaker extends JFrame 
{
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabel4 = new JLabel();
  private JTextField oldImageTF = new JTextField();
  private JTextField oldImageMapTF = new JTextField();
  private JButton oldImageButton = new JButton();
  private JButton OldImageMapButton = new JButton();
  private JLabel jLabel5 = new JLabel();
  private JTextField trainingSetTF = new JTextField();
  private JLabel jLabel6 = new JLabel();
  private JComboBox jComboBox1 = new JComboBox();
  private JLabel jLabel7 = new JLabel();
  private JTextField trustLevelTF = new JTextField();
  private JLabel jLabel8 = new JLabel();
  private JTextField classPercentTF = new JTextField();
  private JLabel jLabel9 = new JLabel();
  private JTextField imagePercentTF = new JTextField();
  private JButton okButton = new JButton();
  private String oldImageName;
  private String oldImageMapName;
  private String recentImageName;
  private JTextArea area;
  private JFrame f;
  private MenuLanguage ml;
  private String[] labels;

  public TrainingSetMaker( MenuLanguage ml, String imageName, JTextArea textArea )
  {
    recentImageName = imageName;
    area = textArea;
    f = this;
    this.ml = ml;
    labels = ml.getTrainingSetMakerLabels();
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
    this.setSize(new Dimension(443, 393));
    this.setTitle( labels[0] );
    jLabel1.setText( labels[1] );
    jLabel1.setBounds(new Rectangle(5, 30, 190, 20));
    jLabel1.setFont(new Font("Dialog", 1, 12));
    jLabel2.setText( labels[2] );
    jLabel2.setBounds(new Rectangle(5, 65, 190, 20));
    jLabel2.setFont(new Font("Dialog", 1, 12));
    jLabel3.setText( labels[3] );
    jLabel3.setBounds(new Rectangle(5, 95, 135, 20));
    jLabel3.setFont(new Font("Dialog", 1, 12));
    jLabel4.setText( recentImageName );
    jLabel4.setBounds(new Rectangle(200, 95, 220, 20));
    jLabel4.setFont(new Font("Dialog", 1, 12));
    oldImageTF.setBounds(new Rectangle(200, 30, 110, 20));
    oldImageMapTF.setBounds(new Rectangle(200, 65, 110, 20));
    oldImageButton.setText( labels[4] );
    oldImageButton.setBounds(new Rectangle(320, 25, 100, 25));
    oldImageButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          oldImageButton_actionPerformed(e);
        }
      });
    OldImageMapButton.setText( labels[4] );
    OldImageMapButton.setBounds(new Rectangle(320, 60, 100, 25));
    OldImageMapButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          OldImageMapButton_actionPerformed(e);
        }
      });
    jLabel5.setText( labels[5] );
    jLabel5.setBounds(new Rectangle(5, 125, 280, 20));
    jLabel5.setFont(new Font("Dialog", 1, 12));
    trainingSetTF.setBounds(new Rectangle(295, 125, 110, 20));
    jLabel6.setText( labels[6] );
    jLabel6.setBounds(new Rectangle(5, 165, 190, 20));
    jLabel6.setFont(new Font("Dialog", 1, 12));
    jComboBox1.setBounds(new Rectangle(200, 165, 124, 20));
    String statistical = new String("statistical");
    String regress = new String("regress");
    jComboBox1.addItem(statistical);
    jComboBox1.addItem(regress);
       
    jLabel7.setText( labels[7] );
    jLabel7.setBounds(new Rectangle(5, 200, 190, 20));
    jLabel7.setFont(new Font("Dialog", 1, 12));
    trustLevelTF.setText("0.01");
    trustLevelTF.setBounds(new Rectangle(200, 200, 125, 20));
    jLabel8.setText( labels[8] );
    jLabel8.setBounds(new Rectangle(5, 235, 190, 20));
    jLabel8.setFont(new Font("Dialog", 1, 12));
    classPercentTF.setText("0.1");
    classPercentTF.setBounds(new Rectangle(200, 235, 125, 20));
    jLabel9.setText( labels[9] );
    jLabel9.setBounds(new Rectangle(5, 270, 190, 20));
    jLabel9.setFont(new Font("Dialog", 1, 12));
    imagePercentTF.setText("1");
    imagePercentTF.setBounds(new Rectangle(200, 270, 125, 20));
    okButton.setText("Ok");
    okButton.setBounds(new Rectangle(160, 320, 73, 27));
    okButton.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
          okButton_actionPerformed(e);
        }
      });
    this.getContentPane().add(okButton, null);
    this.getContentPane().add(imagePercentTF, null);
    this.getContentPane().add(jLabel9, null);
    this.getContentPane().add(classPercentTF, null);
    this.getContentPane().add(jLabel8, null);
    this.getContentPane().add(trustLevelTF, null);
    this.getContentPane().add(jLabel7, null);
    this.getContentPane().add(jComboBox1, null);
    this.getContentPane().add(jLabel6, null);
    this.getContentPane().add(trainingSetTF, null);
    this.getContentPane().add(jLabel5, null);
    this.getContentPane().add(OldImageMapButton, null);
    this.getContentPane().add(oldImageButton, null);
    this.getContentPane().add(oldImageMapTF, null);
    this.getContentPane().add(oldImageTF, null);
    this.getContentPane().add(jLabel4, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jLabel1, null);
  }

  private void oldImageButton_actionPerformed(ActionEvent e)
  {
     JFileChooser fc = new JFileChooser(".");
     ExampleFileFilter tiffFilter = new ExampleFileFilter(new String[]{"tiff","tif"}, labels[10]);
     fc.setFileFilter(tiffFilter);
     int returnval = fc.showOpenDialog(f);
     if(returnval == JFileChooser.APPROVE_OPTION)
     {
         String way = fc.getSelectedFile().getParent();
         String f = fc.getSelectedFile().getName();
         String arq = way+"\\"+f;
         oldImageTF.setText(f);
         oldImageName = arq;
     }
  }

  private void OldImageMapButton_actionPerformed(ActionEvent e)
  {
     JFileChooser fc = new JFileChooser(".");
     ExampleFileFilter tiffFilter = new ExampleFileFilter(new String[]{"tiff","tif"}, labels[10]);
     fc.setFileFilter(tiffFilter);
     int returnval = fc.showOpenDialog(f);
     if(returnval == JFileChooser.APPROVE_OPTION)
     {
          String way = fc.getSelectedFile().getParent();
          String f = fc.getSelectedFile().getName();
          String arq = way+"\\"+f;
          oldImageMapTF.setText(f);
          oldImageMapName = arq;
     }
  }

  private void okButton_actionPerformed(ActionEvent e)
  {
      String alpha = trustLevelTF.getText();
      String nom   = trainingSetTF.getText();
      String tipo  = jComboBox1.getSelectedItem().toString();
      String n_pat = classPercentTF.getText();
      String s_sct = imagePercentTF.getText();
      if(nom == null)
      {
          JOptionPane.showMessageDialog(null,labels[11] ,"Message",
             JOptionPane.INFORMATION_MESSAGE);
          return;
      }
      if(oldImageName == null)
      {
         JOptionPane.showMessageDialog(null, labels[12] ,"Message",
            JOptionPane.INFORMATION_MESSAGE);
         return;
      }
      if(oldImageMapName == null)
      {
        JOptionPane.showMessageDialog(null, labels[13] ,"Message",
            JOptionPane.INFORMATION_MESSAGE);
         return;
      }
      try
      {
        Runtime runTime = Runtime.getRuntime();
        Process process = runTime.exec("trainingsetmaker.exe "+oldImageName+" "+oldImageMapName+" "+recentImageName+" "+nom+" "+tipo+" "+alpha+" "+n_pat+" "+s_sct);
        InputStream stdin = process.getInputStream();
        InputStreamReader stdOut = new InputStreamReader(stdin);
        BufferedReader buffer = new BufferedReader(stdOut);
        String line = null;
        while ( (line = buffer.readLine()) != null)
            area.append(line+"\n");
        int exitVal = process.waitFor();
        area.append("Process exitValue: " + exitVal+"\n");
        area.append("------------------------------------ \n");
                 
        if(exitVal == 0)
        {
           ImageParameter.setOldImageMap( oldImageMapName );   
        }
        f.dispose();
        JOptionPane.showMessageDialog(null,labels[14] ,"Message",
          JOptionPane.INFORMATION_MESSAGE);
      }
      catch(Exception IOEx)
      {
         area.append(IOEx.toString());
      }
             
  }
  
}