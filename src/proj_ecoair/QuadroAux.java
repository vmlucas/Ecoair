/*
 * Projeto ECOAIR
 * 
 */
package proj_ecoair;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.*;
import javax.swing.event.*;

/** Classe que representa o quadro interno
 * do quadro principal. Respons�vel pela
 * visualiza��o da imagem por conter um objeto
 * do tipo <tt>painel</tt>
 *
 * @Author Victor Motta da Rocha Lucas
 */
public class QuadroAux extends JInternalFrame
{
	/** Constroi um novo quadro com a imagem a ser aberta
         * representada pelo seu nome <tt>name</tt>,
         * pelo seu caminho completo <tt>imageCompletePath</tt>,
         * pela por��o da imagem a ser aberta<tt>scale</tt>
         * e pelas dimens�es do quadro principal
         * <tt>dimension</tt>
         *
         */        
	public QuadroAux(Dimension dimension, RenderedImage realImage, String name, float scale, MenuLanguage ml)
	{
	  super(name,true,true,true,true);
          
          this.ml = ml;
          
          //obtendo um container para contru��o do quadro 
          container = this.getContentPane();
          
          //Constriundo um objeto myPanel do tipo painel
          //objeto esse que realiza as opera�oes com as imagens. 
          //� respons�vel tamb�m pelo desenho das imagens na tela, dos menus
          //pop up e dos ret�ngulos desenhados pelo mouse
          myPanel= new painel(realImage, scale, ml);
          
          //obten��o dos labels do objeto myPanel
          widthLabel = myPanel.getWidthLabel();
          heightLabel = myPanel.getHeightLabel();

          scrollPane = new JScrollPane(myPanel);
          
          //########in�cio da cria��o da toolbar###############
          //constructing and adding a toolbar
          String[] toolTips2 = ml.getAuxToolTips();
          
          JToolBar toolbar = new JToolBar();
          
          //cria��o do bot�o save
          JButton saveButton = new JButton(new ImageIcon( ".\\icons\\save.gif" ));
             saveButton.setToolTipText( toolTips2[0] );
             saveButton.addActionListener(
               new ActionListener()
	       {
		  public void actionPerformed(ActionEvent e)
                  {
                     saveImage();                   
	          }
               }
             );
          //adi��o do bot�o save   
          toolbar.add(saveButton,0);
          
          //cria�ao do botao zoom in
          JButton zoonInButton = new JButton("Zoom in");
              zoonInButton.setToolTipText("Zoom in");
             zoonInButton.addActionListener(
                     new ActionListener()
	             {
		        public void actionPerformed(ActionEvent e)
                        {
                            myPanel.zoomin();
                            scrollPane.setSize(myPanel.getSize());
                        }
	             }
             );
          //adi��o do bot�o zoom in   
          toolbar.add(zoonInButton,1);
          
          //cria��o do bot�o undo zoom
          JButton undoZoomButton = new JButton( "Undo zoom" );
              undoZoomButton.setToolTipText( toolTips2[1] );
             undoZoomButton.addActionListener(
                     new ActionListener()
	             {
		        public void actionPerformed(ActionEvent e)
                        {
                            myPanel.undoZoom();  
                            scrollPane.setSize(myPanel.getSize());
                        }
	             }
             );
          //adi��o do bot�o undo zoom   
          toolbar.add(undoZoomButton,2);
          
          //cria��o do bot�o zoomout
          JButton zoonOutButton = new JButton( "Zoom out" );
              zoonOutButton.setToolTipText("Zoom out");
             zoonOutButton.addActionListener(
                     new ActionListener()
	             {
		        public void actionPerformed(ActionEvent e)
                        {
                            myPanel.zoomout();
                            scrollPane.setSize(myPanel.getSize());
                        }
	             }
             );
          //adi��o do bot�o zoomout   
          toolbar.add(zoonOutButton,3);
          
          //cria��o do bot�o undo changes
          JButton undoChangesButton = new JButton(new ImageIcon( ".\\icons\\undo.gif"));
             undoChangesButton.setToolTipText( toolTips2[2] );
             undoChangesButton.addActionListener(
                     new ActionListener()
	             {
		        public void actionPerformed(ActionEvent e)
                        {
                            myPanel.undoChanges(); 
                            scrollPane.setSize(myPanel.getSize());
                        }
	             }
             );
          //adi��o do botao undo changes   
          toolbar.add(undoChangesButton,4);
          
          //cria��o do bot�o "full"
          JButton fullButton = new JButton("Full");
             fullButton.setToolTipText( toolTips2[3] );
             fullButton.addActionListener(
                     new ActionListener()
	             {
		        public void actionPerformed(ActionEvent e)
                        {
                            myPanel.getFullResolution(); 
                            scrollPane.setSize(myPanel.getSize());
                        }
	             }
             );
          //adi��o do bot�o full   
          toolbar.add(fullButton,5);
          
          //cria��o do bot�o image info
          JButton infoButton = new JButton(new ImageIcon( ".\\icons\\info.gif"));
             infoButton.setToolTipText( toolTips2[4] );
             infoButton.addActionListener(
                     new ActionListener()
	             {
		        public void actionPerformed(ActionEvent e)
                        {
                            myPanel.getImageInfo();                                                    
                        }
	             }
             );
          //adi��o do bot�o image info   
          toolbar.add(infoButton,6); 
          //adi��o da label de largura
          toolbar.add(new JLabel( toolTips2[5] ),7);
          toolbar.add(widthLabel,8);
          //adi��o da label de altura
          toolbar.add(new JLabel( toolTips2[6] ),9);
          toolbar.add(heightLabel,10);
          //############fim da cria��o da toolbar################

          //adicionando a toolbar no container na posi��o norte
          container.add(toolbar,BorderLayout.NORTH);
          
          imagePath = name;
          
          //adiciona o objeto myPanel no JScrollPane
          //do obejto Box b
          b = Box.createHorizontalBox();
          b.add(scrollPane);
          container.add(b,BorderLayout.CENTER);
          
          //setting the window size
          int width = (int)dimension.getWidth()*2/3;
          int height = (int)dimension.getHeight()-100;
          setSize(width,height);
          show();
        }
        
        /*
         *#######images operations############################
         */
        /**
         * M�todo que chama o m�todo da classe <tt>painel</tt>
         * <tt>sharpImage()</tt>, m�todo esse que passa um filtro
         * do tipo sharping na imagem corrente
         */
        public void sharpOperation()
        {
           myPanel.sharpImage();   
        }
        
        /**
         * Ap�s a corre��o geom�trica, � chamado esse m�todo para
         * visualizar a imagem j� corrigida.
         * � chamado uma m�todo da classe <tt>painel</tt>
         */
        public void geoVisualization()
        {
            myPanel.geoVisualization();
        }
        
        /**
         * Chama o m�todo GCP para coletar os pontos de controle para 
         * a corre��o geom�trica. � chamado um m�todo da classe <tt>painel</tt>
         */
        public void GCP()
        {
           myPanel.GCP();
        }
        
        /**
         * Chama o m�todo para iniciar a corre�ao geom�trica da
         * imagem. � chamado um m�todo da classe <tt>painel</tt>
         */
        public void geoCorrection()
        {
           myPanel.geoCorrection( imagePath );
        }
        
        /*
         *###########Fim de opera�oes com imagem################
         */
        
        /**
         * Retorna o nome da imagem que est� contida neste quadro auxiliar
         * (inner Frame).
         */        
        public String getImageName()
        {
           return imagePath;   
        }
        
        /**
         * Salva a imagem atual da tela
         */
        public void saveImage()
        {
            JFileChooser fc = new JFileChooser(".");
            ExampleFileFilter tiffFilter = new ExampleFileFilter(new String[] {"tif", "tiff"}, "Tiff Image Files");
            fc.setFileFilter(tiffFilter);
            int returnval = fc.showSaveDialog(this);
            if(returnval == JFileChooser.APPROVE_OPTION)
            {
                String way = fc.getSelectedFile().getParent();
                String f = fc.getSelectedFile().getName();
                String arq = way+"\\"+f;
                //chama o m�todo saveImage da classe <tt>painel</tt>
                //para salvar a imagem corrente
                myPanel.saveImage(arq);                
            }
        }
        
        /* private painel field myPanel */
        private painel myPanel;
        
        /* private Container field container */
        private Container container;
        
        /* private Box field b */
        private Box b;
        
        /* private JLabel fields widthLabel, heightLabel */
        private JLabel widthLabel, heightLabel;
        
        /* private String field imagePath */
        private String imagePath;
        
        /* private MenuLanguage field ml */
        private MenuLanguage ml;
        private JScrollPane scrollPane;
}