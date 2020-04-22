/*
 * Projeto Ecoair
 */
package  proj_ecoair;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JToolBar;
import java.awt.Rectangle;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;

/**
 * Classe que gera o quadro principal com os menus e botoes
 * para a geracao de outros quadros com as imagens
 *
 * @Author Victor Motta
 */
public class QuadroPrinc extends JFrame
{
	
  private String windowMessage;
  private String imageCompletePath;
  private final JDesktopPane desktopPane;
  private JMenu changeMapMenu;
  private QuadroAux frame;
  private int width,height;
  private MenuLanguage ml;
  private Dimension windowDimension;
  
  private JToolBar jToolBar1 = new JToolBar();
  private JButton openButton = new JButton(new ImageIcon(".\\icons\\open.gif"));
  private JButton closeButton = new JButton(new ImageIcon( ".\\icons\\door_exi.gif"));
  private JMenuBar jMenuBar1 = new JMenuBar();
  private JMenu fileMenu = new JMenu();
  private JMenu filterMenu = new JMenu();
  private JMenuItem openMenuItem = new JMenuItem();
  private JMenuItem printMenuItem = new JMenuItem();
  private JMenuItem closeMenuItem = new JMenuItem();
  private JMenu toolMenu = new JMenu();
  private JMenu filterMenuItem = new JMenu();
  private JMenuItem sharpMenuItem = new JMenuItem();
  private JMenuItem colourCompMenuItem = new JMenuItem();
  private JMenu geoMenu = new JMenu();
  private JMenuItem controlMenuItem = new JMenuItem();
  private JMenuItem correctionMenuItem = new JMenuItem();
  private JMenuItem preMenuItem = new JMenuItem();
  private JMenu classMenu = new JMenu();
  private JMenu classificationMenu = new JMenu();
  private JMenuItem supervised = new JMenuItem();
  private JMenuItem unSupervised = new JMenuItem();
  private JMenu lulcMenu = new JMenu();
  private JMenuItem hibridItem = new JMenuItem();
  private JMenuItem newMapMenuItem = new JMenuItem();
  
  /**
  * Constroi um novo quadro principal a cada vez que o 
  * programa e executado.
  */
  public QuadroPrinc(String language)
  {
    super("ECOAIR-PRINCIPAL"); //define o label do quadro
    ml = null;
    if( language.equals( "Pt" ))
    {
       ml = new PtMenuLanguage();  
    }
    else
    {
       ml = new EnMenuLanguage();    
    } 

    //cria um destopPane para adicionar varios quadros com as imagens
    desktopPane = new JDesktopPane();
    
    //define o tamanho do quadro com as definicoes do windows
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    width = screenSize.width;
    height = screenSize.height-30;

    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

    //adicionado evento de fechamento do programa
    this.addWindowListener(
        new WindowAdapter()
        {
           public void windowClosing(WindowEvent e)
           {
              System.exit(0);
                      
           }
        }
    );
    resetMenu();//Define os menus para disabel
    this.setBounds(0,0,width,height);
    windowDimension = this.getSize();
    this.show(); //mostra o quadro na tela
  }

  
  /**
   * Abre um imagem na tela criando uma nova instancia de 
   * <tt>QuadroAux</tt>
   */
  public void abrir()
  {
     QuadroAux quadroAux;
     //Define um novo file dialog 
     ScaleFrame scaleFrame = new ScaleFrame(".", ml);
     ExampleFileFilter tiffFilter = new ExampleFileFilter(new String[] {"tif", "tiff"}, "Tiff Image Files");
     scaleFrame.setFileFilter(tiffFilter);
     int returnval = scaleFrame.showOpenDialog(this);
     if(returnval == JFileChooser.APPROVE_OPTION)
     {
         String imagePath = scaleFrame.getSelectedFile().getAbsolutePath();
         int value = scaleFrame.percentValueReturn();
         //verifica a porcentagem da imagem que devera ser aberta
         //nao pode ser zero
         if(value == 0)
         {
             JOptionPane.showMessageDialog(null, ml.getImagePercentMessage() ,
               "Message", JOptionPane.PLAIN_MESSAGE);
         }
         else
         {
             freeMenu();
             float floatValue = (float)value/100;

             //carregando a imagem com o tamanho total.
             RenderedImage image = ImageProcessing.getInstance().loadImage( imagePath );

             //carregando o quadro auxiliar com a imagem
             quadroAux = new QuadroAux(windowDimension, image, imagePath, floatValue, ml);
             desktopPane.add(quadroAux);
         }
     }
  }

  /**
   * Define os menus como <tt>disable</tt>.
   * E necessario porque quando se abre o prototipo
   * nenuma imagem e aberta, entao os menus para classificacao
   * estao <tt>disable</tt>.
   */
  private void resetMenu()
  {
      geoMenu.setEnabled(false);
      classMenu.setEnabled(false);
  }

  /**
   * Define os menus <tt>geoMenu</tt>, <tt>classMenu</tt> 
   * e <tt>classificationButton</tt> como <tt>Enable</tt>.
   * Isto e necessario porque nao se pode comecar uma classificacao
   * sem se ter uma imagem aberta, ao se abrir uma imagem esse
   * metodo e chamado.
   */
  private void freeMenu()
  {
     geoMenu.setEnabled(true);
     classMenu.setEnabled(true);            
  }
        
        
  /**
   * Gera uma imagem da diferenca entre os mapas tematicos
   * redusidos a cor azul
   */
  private void getBlueChangeMap()
  {
      int[] array = { 0, 0, 255 };
      getChangeMap( array );
  }
        
  /**
   * Metodo que elabora uma imagem da diferenca entre as
   * duas imagens que representam o mapa tematico da imagem
   * antiga e da nova
   */
  private void getChangeMap(int[] array)
  {
     String oldImageMapName = ImageParameter.getOldImageMap();
     String newImageMapName = ImageParameter.getNewImageMap();
     RenderedImage oldImageMap = null;
     RenderedImage newImageMap = null;
            
     //obtendo a mapa temático antigo
     if(oldImageMapName != null)
     {
        oldImageMap = ImageProcessing.getInstance().loadImage( oldImageMapName );
     }
     else
     {
        JOptionPane.showMessageDialog(null, ml.getClassMessage(),
             "Message", JOptionPane.PLAIN_MESSAGE);   
        return;
     }
           
     //obtendo o mapa temático novo
     if(newImageMapName != null)
     {
         newImageMap = ImageProcessing.getInstance().loadImage( newImageMapName );
     }
     else
     {
       JOptionPane.showMessageDialog(null,ml.getClassMessage(),
           "Message", JOptionPane.PLAIN_MESSAGE);   
        return;   
     }
          
     RenderedImage oldMap = ImageProcessing.getInstance().getANDCONSTImage( oldImageMap, array );
     RenderedImage newMap = ImageProcessing.getInstance().getANDCONSTImage( newImageMap, array );
           
     RenderedImage diffImageMap = ImageProcessing.getInstance().getXORImage( oldMap, newMap );
           
    //abrindo a nova imagem
    QuadroAux tempQuadro = new QuadroAux(windowDimension, diffImageMap,"Color Diference Image Map", 1.0f, ml);
    desktopPane.add(tempQuadro);
                    
  }
        
  private void jbInit() throws Exception
  {
    String toolTips[] = ml.getToolTips();
    String [] menuNames = ml.getMenuNames();
    windowMessage = ml.getWindowMessage(); 
    
    this.setJMenuBar(jMenuBar1);

    //##########toolbar creation########################
    //##################################################
    openButton.setToolTipText( toolTips[0] );
    openButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          openButton_actionPerformed(e);
        }
      });
      
    closeButton.setToolTipText( toolTips[3] );  
    closeButton.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          closeButton_actionPerformed(e);
        }
      });
    
    jToolBar1.add(openButton, null);
    jToolBar1.add(closeButton, null);
    //##########Fim da criacao da toolbar##############
    //#################################################
          
    //###########Criacao dos menus####################
    //###############################################  

    //Menu File
    fileMenu.setText(menuNames[0]);

    //ítem que abre um arquivo em disco 
    openMenuItem.setText(menuNames[1]);
    openMenuItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          openMenuItem_actionPerformed(e);
        }
      });
      
    printMenuItem.setText(menuNames[2]);

    //Ítem de menu que fecha o programa   
    closeMenuItem.setText(menuNames[3]);
    closeMenuItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          closeMenuItem_actionPerformed(e);
        }
      });
    
    //********menu de ferramentas****************************************
    toolMenu.setText(menuNames[4]);

    //adicionando um ítem de filtro
    filterMenu.setText( menuNames[5] );
    //adicionando o filtro sharping
    sharpMenuItem.setText( menuNames[6]);
    sharpMenuItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          sharpMenuItem_actionPerformed(e);
        }
      });
                          
    //menu que chama o quadro para a composição de cor
    colourCompMenuItem.setText( menuNames[7] );
    colourCompMenuItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          colourCompMenuItem_actionPerformed(e);
        }
      });

	  //*******menu de correção geométrica*************************
    geoMenu.setText( menuNames[8] );
    //item que chama o quadro para a marcação dos pontos de controle
    controlMenuItem.setText( menuNames[9] );
    controlMenuItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          controlMenuItem_actionPerformed(e);
        }
    });

    //item que chama o quadro para a correção geométrica
    correctionMenuItem.setText( menuNames[10] );
    correctionMenuItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          correctionMenuItem_actionPerformed(e);
        }
    });
              
    //item para a visualização do resultado da correção 
    //geométrica
    preMenuItem.setText( menuNames[11] );
    preMenuItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          preMenuItem_actionPerformed(e);
        }
    });
    
    //***************menu para a classificação***********************
    classMenu.setText( menuNames[12] );
    //menu para a classificação comum
    classificationMenu.setText( menuNames[13] );
    //item class. supervisionada
    supervised.setText( menuNames[21] );
    classificationMenu.add( supervised);
    //item class. não supervisionada
    unSupervised.setText( menuNames[22] );
    classificationMenu.add( unSupervised);
                
    classMenu.add(classificationMenu);

    //menu LULC classification
    lulcMenu.setText( menuNames[14] );
    //item de classificação regressão linear
    hibridItem.setText( menuNames[23] );
    hibridItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          hibridItem_actionPerformed(e);
        }
    });
    lulcMenu.add( hibridItem );

    //item para a classificação do INRIA
    JMenu noaaMenu = new JMenu( menuNames[24] );
    //ítem profiles
    JMenuItem profilesItem = new JMenuItem( menuNames[25] );
    profilesItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          profilesItem_actionPerformed(e);
        }
    });
                                                                    
    //item largeScale
    JMenuItem largeScaleItem = new JMenuItem( menuNames[26] );
    largeScaleItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          largeScaleItem_actionPerformed(e);
        }
    });
                     
    //item classification - parte do Marcelo
    JMenuItem classificationItem = new JMenuItem( menuNames[27] );
    
    //menu para visualizar o mapa de mudanças
    changeMapMenu = new JMenu( menuNames[15] );
    //ítem verde escuro
    JMenuItem darkGreenMenuItem = new JMenuItem( menuNames[16] );
    
    //item verde claro
    JMenuItem lightGreenMenuItem = new JMenuItem( menuNames[17] );
    
    //item laranja
    JMenuItem orangeMenuItem = new JMenuItem( menuNames[18]);
    
    //item amarelo
    JMenuItem yellowMenuItem = new JMenuItem( menuNames[19] );
    
    //item azul
    JMenuItem blueMenuItem = new JMenuItem( menuNames[20] );
    blueMenuItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          blueMenuItem_actionPerformed(e);
        }
    });   
    
    //item que mostra a nova imagem de classificação gerada
    newMapMenuItem.setText( menuNames[28] );
    newMapMenuItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          newMapMenuItem_actionPerformed(e);
        }
      });
    
    jToolBar1.setBounds(new Rectangle(15, 40, 0, 0));
    desktopPane.setLayout(null);    
    
    fileMenu.add(openMenuItem);
    fileMenu.add(printMenuItem);
    fileMenu.add(closeMenuItem);
    jMenuBar1.add(fileMenu);
    toolMenu.add(filterMenu);
    filterMenu.add(sharpMenuItem);
    toolMenu.add(colourCompMenuItem);
    jMenuBar1.add(toolMenu);

    geoMenu.add(controlMenuItem);
    geoMenu.add(correctionMenuItem);
    geoMenu.add(preMenuItem);                  
    jMenuBar1.add(geoMenu);

    noaaMenu.add( profilesItem );   
    noaaMenu.add( largeScaleItem );
    noaaMenu.add( classificationItem );
    lulcMenu.add( noaaMenu );    
    classMenu.add(lulcMenu);
    lightGreenMenuItem.setEnabled( false);
    changeMapMenu.add(lightGreenMenuItem);
    darkGreenMenuItem.setEnabled( false );
    changeMapMenu.add(darkGreenMenuItem);
    orangeMenuItem.setEnabled( false );
    changeMapMenu.add(orangeMenuItem);
    yellowMenuItem.setEnabled( false );
    changeMapMenu.add(yellowMenuItem);
    changeMapMenu.add(blueMenuItem);
    changeMapMenu.add(newMapMenuItem);
    changeMapMenu.setEnabled(true);
    classMenu.add(changeMapMenu); 
    jMenuBar1.add(classMenu);
    this.getContentPane().add(jToolBar1, BorderLayout.NORTH);
    this.getContentPane().add(desktopPane, BorderLayout.CENTER);

  }

  private void openButton_actionPerformed(ActionEvent e)
  {
     abrir();
  }

  private void closeButton_actionPerformed(ActionEvent e)
  {
     System.exit(0);
  }

  private void openMenuItem_actionPerformed(ActionEvent e)
  {
     abrir();
  }

  private void closeMenuItem_actionPerformed(ActionEvent e)
  {
    System.exit(0);
  }

  private void sharpMenuItem_actionPerformed(ActionEvent e)
  {
    frame =(QuadroAux)desktopPane.getSelectedFrame();
    if(frame != null)
    {
       frame.sharpOperation();                              
    }
    else
    {
       JOptionPane.showMessageDialog(null,windowMessage ,"Message",
          JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void colourCompMenuItem_actionPerformed(ActionEvent e)
  {
     new BandMerge(ml);
  }

  private void controlMenuItem_actionPerformed(ActionEvent e)
  {
     frame =(QuadroAux)desktopPane.getSelectedFrame();
     if(frame != null)
     {
         frame.GCP();                                     
     }
     else
     {
         JOptionPane.showMessageDialog(null,windowMessage,"Message",
               JOptionPane.INFORMATION_MESSAGE);
     }
  }

  private void correctionMenuItem_actionPerformed(ActionEvent e)
  {
     frame =(QuadroAux)desktopPane.getSelectedFrame();
     if(frame != null)
     {
         frame.geoCorrection();                                      
     }
     else
     {
         JOptionPane.showMessageDialog(null,windowMessage,"Message",
               JOptionPane.INFORMATION_MESSAGE);
     }
  }

  private void preMenuItem_actionPerformed(ActionEvent e)
  {
      frame =(QuadroAux)desktopPane.getSelectedFrame();
      if(frame != null)
      {
         frame.geoVisualization();                                      
      }
      else
      {
          JOptionPane.showMessageDialog(null,windowMessage,"Message",
               JOptionPane.INFORMATION_MESSAGE);
      }

  }

  private void hibridItem_actionPerformed(ActionEvent e)
  {
        frame =(QuadroAux)desktopPane.getSelectedFrame();
        if(frame != null)
        {
           new Hibrid( frame.getImageName(), changeMapMenu, ml);                                                                    
        }
        else
        {
             JOptionPane.showMessageDialog(null,windowMessage,"Message",
                 JOptionPane.INFORMATION_MESSAGE);
        }
                  
  }

  private void profilesItem_actionPerformed(ActionEvent e)
  {
     new ProfileFrame(ml); 
  }

  private void largeScaleItem_actionPerformed(ActionEvent e)
  {
     new LargeScaleFrame(ml); 
  }

  private void blueMenuItem_actionPerformed(ActionEvent e)
  {
      getBlueChangeMap();    
  }

  private void newMapMenuItem_actionPerformed(ActionEvent e)
  {
     //obtendo a mapa temático antigo
     if(ImageParameter.getOldImageMap() == null)
     {
        JOptionPane.showMessageDialog(null, ml.getClassMessage(),
             "Message", JOptionPane.PLAIN_MESSAGE);   
        return;
     }
     RenderedImage imageMap = ImageProcessing.getInstance().loadImage( "ThematicMap.tif" );
           
     //abrindo a nova imagem
     QuadroAux quadro = new QuadroAux(windowDimension, imageMap,"New Image Map", 1.0f, ml);
     desktopPane.add(quadro);
  }
}

/**
 * Classe que define um novo tipo de file dialog
 * E adicionado uma barra de rolagem horizontal
 * para que se possa escolher a porcentagem da imagem
 * que se deseja abrir
 */
class ScaleFrame extends JFileChooser
{
    
    /**
     * Gera um novo file dialog na tela
     */
    public ScaleFrame(String path, MenuLanguage ml)
    {
      super(path);
      slider = new MySlider(ml);
      this.setAccessory(slider);      
    }

    /**
     * Obtem o valor inteiro da barra de rolagem
     *
     * @return a porcentagem da barra 
     */
    public int percentValueReturn()
    {
        return slider.percentValueReturn();
    }
    
    /* private MySlider field slider */
    private MySlider slider;
}

/**
 * Define um <tt>JPanel</tt> contendo uma barra de rolagem,
 * para ser adicionada no file dialog  
 */
class MySlider extends JPanel
{
   
   /**
    * Constroi um novo JPanel contendo uma barra de rolagem
    * slider
    */
   public MySlider( MenuLanguage ml )
   {
      valueLabel = new JLabel("0");
      percentValue = 0;
      slider = new JSlider(SwingConstants.HORIZONTAL,0,100,0);
      slider.setMajorTickSpacing(10);
      slider.setPaintTicks(true);
      slider.addChangeListener(
             new ChangeListener()
             {
                public void stateChanged(ChangeEvent e)
                {
                  percentValue = slider.getValue();
                  valueLabel.setText(String.valueOf(percentValue)+"%");
                }
             }
      );
      JLabel percentLabel = new JLabel( ml.getSliderLabel() );
      add(percentLabel);
      add(slider);
      add(valueLabel);
   }
   
   /**
    * Obtem o valor atual do slider
    *
    * @return retorna um valor atual do slider
    */
   public int percentValueReturn()
   {
        return percentValue;
   }
   
   /* private JLabel field valueLabel */
   private JLabel valueLabel;
   
   /* private JSlider field slider */
   private JSlider slider;
   
   /* private int field percentValue */
   private int percentValue;
}