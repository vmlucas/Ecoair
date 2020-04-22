package proj_ecoair;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.Rectangle;
import java.awt.Dimension;

/**
 *  Classe que gera o mapa de classificação baseado na regressão linear
 *  da imagem recente. 
 */
public class ClassMaker extends JFrame 
{
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JTextField trainingSetFileTF = new JTextField();
  private JButton browseButton = new JButton();
  private JLabel jLabel4 = new JLabel();
  private JTextField valueTF = new JTextField();
  private JButton okButton = new JButton();
  private String img2, arq;
  private String trainingFile;
  private JTextArea area;
  private JMenu changeMenu;
  private MenuLanguage ml;
  private String[] labels;
  private JFrame f;

  /**
   *  Constroi um quadro novo.
   */
  public ClassMaker(MenuLanguage ml, String Image, JTextArea textArea, JMenu menu)
  {
    f=this;
    img2 = Image;
    area = textArea;
    changeMenu = menu; 
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
    this.setSize(new Dimension(480, 255));
    this.setTitle("ClassMaker");
    jLabel1.setText( labels[3] );
    jLabel1.setBounds(new Rectangle(10, 25, 145, 20));
    jLabel1.setFont(new Font("Dialog", 1, 12));
    jLabel2.setText( img2 );
    jLabel2.setBounds(new Rectangle(185, 25, 240, 20));
    jLabel2.setFont(new Font("Dialog", 1, 12));
    jLabel3.setText( labels[5] );
    jLabel3.setBounds(new Rectangle(10, 70, 215, 20));
    jLabel3.setFont(new Font("Dialog", 1, 12));
    trainingSetFileTF.setBounds(new Rectangle(235, 70, 110, 20));
    browseButton.setText( labels[4] );
    browseButton.setBounds(new Rectangle(365, 65, 95, 25));
    browseButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          browseButton_actionPerformed(e);
        }
      });
    jLabel4.setText( labels[9] );
    jLabel4.setBounds(new Rectangle(10, 115, 195, 20));
    jLabel4.setFont(new Font("Dialog", 1, 12));
    valueTF.setBounds(new Rectangle(235, 115, 25, 20));
    okButton.setText("ok");
    okButton.setBounds(new Rectangle(185, 170, 73, 27));
    okButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          okButton_actionPerformed(e);
        }
      });
    this.getContentPane().add(okButton, null);
    this.getContentPane().add(valueTF, null);
    this.getContentPane().add(jLabel4, null);
    this.getContentPane().add(browseButton, null);
    this.getContentPane().add(trainingSetFileTF, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jLabel1, null);
  }

 /**
  * Evento do botão ok. 
  */
  private void okButton_actionPerformed(ActionEvent e)
  {
     String nom = trainingFile;
     String s_sct = valueTF.getText();
     if(nom == null)
     {
        JOptionPane.showMessageDialog(null, labels[11] ,"Message",
            JOptionPane.INFORMATION_MESSAGE);
        return;
     }
     if(s_sct == null)
     {
        JOptionPane.showMessageDialog(null, labels[15] ,"Message",
            JOptionPane.INFORMATION_MESSAGE);
        return;
     }
     try
     {
        Runtime runTime = Runtime.getRuntime();
        Process process = runTime.exec("classmaker.exe "+img2+" "+nom+" "+s_sct);
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
            ImageParameter.setNewImageMap( "ThematicMap.tif" );  
            changeMenu.setEnabled(true);
        }
               
        f.dispose();
        JOptionPane.showMessageDialog(null, labels[16] ,"Message",
           JOptionPane.INFORMATION_MESSAGE);
     }
     catch(Exception IOEx)
     {
        JOptionPane.showMessageDialog(null,IOEx.toString(),"Message",
           JOptionPane.INFORMATION_MESSAGE);
     }
           
  }

  private void browseButton_actionPerformed(ActionEvent e)
  {
     JFileChooser fc = new JFileChooser(".");
     ExampleFileFilter matFilter = new ExampleFileFilter("mat", labels[17] );
     fc.setFileFilter(matFilter);
     int returnval = fc.showOpenDialog(f);
     if(returnval == JFileChooser.APPROVE_OPTION)
     {
        String way = fc.getSelectedFile().getParent();
        String f = fc.getSelectedFile().getName();
        String arq = way+"\\"+f;
        trainingSetFileTF.setText(f);
        trainingFile = arq;
     }
  }
}