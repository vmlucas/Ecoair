package proj_ecoair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.media.jai.*;
import java.awt.image.renderable.ParameterBlock;
import com.sun.media.jai.codec.FileSeekableStream;
import org.libtiff.jai.codec.XTIFFDecodeParam;

public class GeoCorrectionFrame extends JFrame 
{

  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JButton jButton1 = new JButton();
  private JButton jButton2 = new JButton();
  private JTextField textField1 = new JTextField();
  private JTextField textField2 = new JTextField();
  private JLabel jLabel3 = new JLabel();
  private JLabel wrongImageLabel = new JLabel();
  private JButton beginButton = new JButton();
  private float[] sourceCoords, destCoords;
  private int number;
  private RenderedImage correctImage, wrongImage;
  private MenuLanguage ml;
  private Vector name;
  private JFrame frame;
  private String wrongImageName;

  public String sourcePointsFile, destinyPointsFile;
  private JLabel jLabel4 = new JLabel();
  private JTextField degreeTextField = new JTextField();
  
  public GeoCorrectionFrame( String wrongImageName, RenderedImage wrong, MenuLanguage menuLanguage )
  {
    super((String)menuLanguage.getGeocorrectionTexts().elementAt(0) );
    frame=this;
    ml = menuLanguage;
    name = ml.getGeocorrectionTexts();
    wrongImage = wrong;
    this.wrongImageName = wrongImageName;
    
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
    this.setSize(new Dimension(481, 300));
    jLabel1.setText( (String)name.elementAt(2) );
    jLabel1.setBounds(new Rectangle(5, 45, 205, 20));
    jLabel1.setFont(new Font("Dialog", 1, 13));
    jLabel2.setText( (String)name.elementAt(5) );
    jLabel2.setBounds(new Rectangle(5, 95, 200, 20));
    jLabel2.setFont(new Font("Dialog", 1, 13));
    jButton1.setText((String)name.elementAt(3));
    jButton1.setBounds(new Rectangle(345, 40, 105, 25));
    jButton1.addActionListener(
        new ActionListener()
	      {
	         public void actionPerformed(ActionEvent e)
           {
               JFileChooser fc = new JFileChooser(".");
               ExampleFileFilter tiffFilter = new ExampleFileFilter("txt", (String)name.elementAt(4));
               fc.setFileFilter(tiffFilter);
               int returnval = fc.showOpenDialog(frame);
               if(returnval == JFileChooser.APPROVE_OPTION)
               {
                  String way = fc.getSelectedFile().getParent();
                  String fs = fc.getSelectedFile().getName();
                  sourcePointsFile = way+"\\"+fs;
                  textField1.setText(fs);
               }
           }
	      }
    );
    
    jButton2.setText((String)name.elementAt(3));
    jButton2.setBounds(new Rectangle(345, 85, 105, 25));
    jButton2.addActionListener(
        new ActionListener()
	      { 
	         public void actionPerformed(ActionEvent e)
           {
               JFileChooser fc = new JFileChooser(".");
               ExampleFileFilter tiffFilter = new ExampleFileFilter("txt", (String)name.elementAt(4) );
               fc.setFileFilter(tiffFilter);
               int returnval = fc.showOpenDialog(frame);
               if(returnval == JFileChooser.APPROVE_OPTION)
               {
                  String way = fc.getSelectedFile().getParent();
                  String fs = fc.getSelectedFile().getName();
                  destinyPointsFile = way+"\\"+fs;
                  textField2.setText(fs);
               }
           }
	      }
    );
    
    textField1.setBounds(new Rectangle(220, 45, 110, 20));
    textField1.setFont(new Font("SansSerif", 0, 13));
    textField2.setBounds(new Rectangle(220, 95, 110, 20));
    jLabel3.setText((String)name.elementAt(6));
    jLabel3.setBounds(new Rectangle(5, 135, 130, 15));
    jLabel3.setFont(new Font("Dialog", 1, 13));
    wrongImageLabel.setText(wrongImageName);
    wrongImageLabel.setBounds(new Rectangle(180, 135, 235, 15));
    wrongImageLabel.setFont(new Font("Dialog", 1, 13));
    beginButton.setText((String)name.elementAt(1));
    beginButton.setBounds(new Rectangle(175, 220, 115, 25));
    beginButton.addActionListener(
        new ActionListener()
	      {
	         public void actionPerformed(ActionEvent e)
           {
               carregarVetores();
               corrigir();
           }
        }
    );
    jLabel4.setText((String)name.elementAt(7));
    jLabel4.setBounds(new Rectangle(5, 170, 125, 15));
    jLabel4.setFont(new Font("Dialog", 1, 13));
    degreeTextField.setBounds(new Rectangle(175, 170, 25, 20));
    this.getContentPane().add(degreeTextField, null);
    this.getContentPane().add(jLabel4, null);
    this.getContentPane().add(beginButton, null);
    this.getContentPane().add(wrongImageLabel, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(textField2, null);
    this.getContentPane().add(textField1, null);
    this.getContentPane().add(jButton2, null);
    this.getContentPane().add(jButton1, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jLabel1, null);
    
  }

  /*
   * Obtem a imagem já corrigida 
   */
  public RenderedImage getCorrectImage()
  {
      return correctImage;
  }
  
  /*
   * carrega os valores de cada arquivo em vetores com as coordenadas
   * dos pontos de controle. Gera um vetor de coordenadas de origem e 
   * destino
   */
  private void carregarVetores()
  {
     //gerando o vetor com as coordenadas de origem
     String sourceFile = this.sourcePointsFile;
     int numero=0;
     if(sourceFile != null)
     {
       try
       {
           File file =  new File( sourceFile );
           BufferedReader buff = new BufferedReader(new FileReader(file));
           String linha = buff.readLine();
           StringTokenizer tok = new StringTokenizer(linha,";");
           numero =Integer.parseInt(tok.nextToken());
           number=numero;
           int cont=0;
           sourceCoords = new float[numero*2];
           boolean eof= false;
           while((!eof))
           {
               linha = buff.readLine();
               if(linha == null)
                   eof=true;
               else
               {
                   tok = new StringTokenizer(linha,";");
                   String valor = tok.nextToken();
                   //as coordenadas são colocadas no vetor na forma:
                   // [0]x1, [1]y1, [2]x2, [3]y2.....
                   sourceCoords[cont]=Float.parseFloat(tok.nextToken());
                   cont++;
                   sourceCoords[cont]=Float.parseFloat(tok.nextToken());
                }
               cont++;
            }
       }
       catch(IOException exception)
       {
            System.out.println(exception);
       }
   }
   else
   {
        JOptionPane.showMessageDialog(null, (String)name.elementAt(8), "Warning",
             JOptionPane.INFORMATION_MESSAGE);
   }

   //obtenção das coordenadas de destino
   String destinyFile = this.destinyPointsFile;
   int numero2=0;
   if( destinyFile != null)
   {
       try
       {
           File file =  new File(destinyFile);
           BufferedReader buff = new BufferedReader(new FileReader(file));
           String linha = buff.readLine();
           StringTokenizer tok = new StringTokenizer(linha,";");
           numero2 =Integer.parseInt(tok.nextToken());
           int cont=0;
           destCoords = new float[numero2*2];
           boolean eof= false;
           while((!eof))
           {
               linha = buff.readLine();
               if(linha == null)
                   eof=true;
               else
               {
                   tok = new StringTokenizer(linha,";");
                   String valor = tok.nextToken();
                   //as coordenadas são colocadas no vetor na forma:
                   // [0]x1, [1]y1, [2]x2, [3]y2.....
                   destCoords[cont]=Float.parseFloat(tok.nextToken());
                   cont++;
                   destCoords[cont]=Float.parseFloat(tok.nextToken());
                }
               cont++;
            }
       }
       catch(IOException exception)
       {
            System.out.println(exception);
       }
   }
   else
   {
        JOptionPane.showMessageDialog(null, (String)name.elementAt(9), "Warning",
             JOptionPane.INFORMATION_MESSAGE);
   }
  }

  /*
   * Executa a correção geométrica, obtendo o grau da correção, a imagem 
   * geometricamente errada e os pontos relativo as imagens certa e errada.  
   */
  private void corrigir()
  {
        String stringDegree = degreeTextField.getText();
        if((stringDegree == null)||(stringDegree.equals("")))
        {
           JOptionPane.showMessageDialog(null, (String)name.elementAt(10),"Warning",
             JOptionPane.INFORMATION_MESSAGE);
            return;   
        }
        int degree = Integer.parseInt(stringDegree);
        if((degree > 3)||(degree <=0))
        {
           JOptionPane.showMessageDialog(null,(String)name.elementAt(11),"Warning",
             JOptionPane.INFORMATION_MESSAGE);
           return;
        }

        //criação do objeto warpPolinomial e obtenção de parâmetros
        //para a criação de uma nova imagem já corrigida
        int width = wrongImage.getWidth();
        int height = wrongImage.getHeight();
        
        try
        {
          WarpPolynomial warp = 
                 WarpPolynomial.createWarp(sourceCoords,0,
                                           destCoords,0,
                                           2*number,
                                           1.0F / width,
                                           1.0F / height,
                                           (float)width,
                                           (float)height,
                                           degree);
          ParameterBlock pb = new ParameterBlock();
          pb.addSource(wrongImage);
          pb.add(warp);
          pb.add(new InterpolationNearest());
          
          correctImage = JAI.create("warp",pb);
          
          JOptionPane.showMessageDialog(null,(String)name.elementAt(12),"Warning",
             JOptionPane.INFORMATION_MESSAGE);
        }
        
        catch(Exception Warp_ex)
        {
           JOptionPane.showMessageDialog(null,Warp_ex,"Warning",
             JOptionPane.INFORMATION_MESSAGE);
        }
  }

}