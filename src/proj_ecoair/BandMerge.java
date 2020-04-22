package proj_ecoair;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.awt.image.renderable.ParameterBlock;
import java.awt.image.*;
import javax.media.jai.*;
import javax.media.jai.operator.*;
import com.sun.media.jai.codec.*;
import org.libtiff.jai.codecimpl.*;
import org.libtiff.jai.codec.*;
import org.geotiff.image.jai.*;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

public class BandMerge extends JFrame
{
  private JFrame f;
  private String arq;
  private MenuLanguage ml;
  private String[] labels;
  private JLabel image1Label = new JLabel();
  private JLabel image2Label = new JLabel();
  private JLabel image3Label = new JLabel();
  private JTextField image1TextField = new JTextField();
  private JTextField image2TextField = new JTextField();
  private JTextField image3TextField = new JTextField();
  private JButton image1Button = new JButton();
  private JButton image2Button = new JButton();
  private JButton image3Button = new JButton();
  private JButton okButton = new JButton();
  private JLabel jLabel1 = new JLabel();
  private JTextField outputTextField = new JTextField();

  public BandMerge(MenuLanguage menuLanguage)
  {
    super("Band Merging");
    f=this;
    ml = menuLanguage;
       
    labels = ml.getBandMergeLabels();

    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception
  {
    this.getContentPane().setLayout(null);
    this.setSize(new Dimension(454, 300));
    image1Label.setText(labels[0]);
    image1Label.setBounds(new Rectangle(5, 30, 130, 20));
    image1Label.setFont(new Font("Dialog", 0, 12));
    image2Label.setText(labels[1]);
    image2Label.setBounds(new Rectangle(5, 65, 130, 20));
    image2Label.setFont(new Font("Dialog", 0, 12));
    image3Label.setText(labels[2]);
    image3Label.setBounds(new Rectangle(5, 100, 130, 20));
    image3Label.setFont(new Font("Dialog", 0, 12));
    image1TextField.setBounds(new Rectangle(160, 30, 150, 20));
    image2TextField.setBounds(new Rectangle(160, 65, 150, 20));
    image3TextField.setBounds(new Rectangle(160, 100, 150, 20));
    image1Button.setText(labels[4]);
    image1Button.setBounds(new Rectangle(330, 30, 105, 25));
    image1Button.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          image1Button_actionPerformed(e);
        }
      });
    image2Button.setText(labels[4]);
    image2Button.setBounds(new Rectangle(330, 65, 105, 25));
    image2Button.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          image2Button_actionPerformed(e);
        }
      });
    image3Button.setText(labels[4]);
    image3Button.setBounds(new Rectangle(330, 100, 105, 25));
    image3Button.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          image3Button_actionPerformed(e);
        }
      });
    okButton.setText("Ok");
    okButton.setBounds(new Rectangle(145, 210, 73, 27));
    okButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          okButton_actionPerformed(e);
        }
      });
    jLabel1.setText(labels[3]);
    jLabel1.setBounds(new Rectangle(5, 150, 130, 20));
    outputTextField.setBounds(new Rectangle(160, 150, 105, 25));
    this.getContentPane().add(image1TextField, null);
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(okButton, null);
    this.getContentPane().add(image3Button, null);
    this.getContentPane().add(image2Button, null);
    this.getContentPane().add(image1Button, null);
    this.getContentPane().add(image3TextField, null);
    this.getContentPane().add(image2TextField, null);
    this.getContentPane().add(outputTextField, null);
    this.getContentPane().add(image3Label, null);
    this.getContentPane().add(image2Label, null);
    this.getContentPane().add(image1Label, null);
    this.setBounds(50,50,454,300);     
    this.show();
  }

  private void okButton_actionPerformed(ActionEvent e)
  {
     FileSeekableStream stream1 = null;
     FileSeekableStream stream2 = null;
     FileSeekableStream stream3 = null;
     try
     {
         stream1 = new FileSeekableStream(image1TextField.getText());
         stream2 = new FileSeekableStream(image2TextField.getText());
         stream3 = new FileSeekableStream(image3TextField.getText());
     }
     catch (IOException ex)
     {
         ex.printStackTrace();
     }

     XTIFFDecodeParam decodeParam = new XTIFFDecodeParam();
     decodeParam.setDecodePaletteAsShorts(true);
     BandMergeDescriptor bm = new BandMergeDescriptor();

     ParameterBlock p1 = new ParameterBlock();
     p1.add(stream1);
     RenderedImage image1 = JAI.create("tiff",p1);

     ParameterBlock p2 = new ParameterBlock();
     p2.add(stream2);
     RenderedImage image2 = JAI.create("tiff",p2);

     ParameterBlock p3 = new ParameterBlock();
     p3.add(stream3);
     RenderedImage image3 = JAI.create("tiff",p3);

     ParameterBlock params = new ParameterBlock();
     params.addSource(image1);
     params.addSource(image2);
     params.addSource(image3);
     params.add(bm);

     RenderedImage image = JAI.create("BandMerge",params);

     XTIFFEncodeParam encodeParam = new XTIFFEncodeParam();
     FileOutputStream out = null;
     String outarq = outputTextField.getText();
     try
     {
          out = new FileOutputStream(outarq);
     }
     catch(IOException exc)
     {
          System.out.println("IOException.");
     }

     ImageEncoder encoder = ImageCodec.createImageEncoder("TIFF", out,
                                                             encodeParam);
     try
     {
          encoder.encode(image);
          out.close();
     }
     catch (IOException excp)
     {
          System.out.println("IOException at encoding..");
     }
     JOptionPane.showMessageDialog(null,outarq + labels[6] ,"Message",
            JOptionPane.INFORMATION_MESSAGE);
     
  }

  private void openFile( JTextField tf )
  {
     JFileChooser fc = new JFileChooser(".");
     ExampleFileFilter tiffFilter = new ExampleFileFilter(new String[]{"tiff","tif"}, labels[5] );
     fc.setFileFilter(tiffFilter);
     int returnval = fc.showOpenDialog(f);
     if(returnval == JFileChooser.APPROVE_OPTION)
     {
          String way = fc.getSelectedFile().getParent();
          String f = fc.getSelectedFile().getName();
          arq = way+"\\"+f;
          tf.setText(arq);
     }
  }

  private void image1Button_actionPerformed(ActionEvent e)
  {
     openFile( image1TextField );
  }

  private void image2Button_actionPerformed(ActionEvent e)
  {
     openFile( image2TextField );
  }

  private void image3Button_actionPerformed(ActionEvent e)
  {
     openFile( image3TextField );
  }

  
}