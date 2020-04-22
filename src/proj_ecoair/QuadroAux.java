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
 * do quadro principal. Responsável pela
 * visualização da imagem por conter um objeto
 * do tipo <tt>painel</tt>
 *
 * @Author Victor Motta da Rocha Lucas
 */
public class QuadroAux extends JInternalFrame
{
	/** Constroi um novo quadro com a imagem a ser aberta
         * representada pelo seu nome <tt>name</tt>,
         * pelo seu caminho completo <tt>imageCompletePath</tt>,
         * pela porção da imagem a ser aberta<tt>scale</tt>
         * e pelas dimensões do quadro principal
         * <tt>dimension</tt>
         *
         */        
	public QuadroAux(Dimension dimension, RenderedImage realImage, String name, float scale, MenuLanguage ml)
	{
	  super(name,true,true,true,true);
          
          this.ml = ml;
          
          //obtendo um container para contrução do quadro 
          container = this.getContentPane();
          
          //Constriundo um objeto myPanel do tipo painel
          //objeto esse que realiza as operaçoes com as imagens. 
          //É responsável também pelo desenho das imagens na tela, dos menus
          //pop up e dos retângulos desenhados pelo mouse
          myPanel= new painel(realImage, scale, ml);
          
          //obtenção dos labels do objeto myPanel
          widthLabel = myPanel.getWidthLabel();
          heightLabel = myPanel.getHeightLabel();

          scrollPane = new JScrollPane(myPanel);
          
          //########início da criação da toolbar###############
          //constructing and adding a toolbar
          String[] toolTips2 = ml.getAuxToolTips();
          
          JToolBar toolbar = new JToolBar();
          
          //criação do botão save
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
          //adição do botão save   
          toolbar.add(saveButton,0);
          
          //criaçao do botao zoom in
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
          //adição do botão zoom in   
          toolbar.add(zoonInButton,1);
          
          //criação do botão undo zoom
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
          //adição do botão undo zoom   
          toolbar.add(undoZoomButton,2);
          
          //criação do botão zoomout
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
          //adição do botão zoomout   
          toolbar.add(zoonOutButton,3);
          
          //criação do botão undo changes
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
          //adição do botao undo changes   
          toolbar.add(undoChangesButton,4);
          
          //criação do botão "full"
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
          //adição do botão full   
          toolbar.add(fullButton,5);
          
          //criação do botão image info
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
          //adição do botão image info   
          toolbar.add(infoButton,6); 
          //adição da label de largura
          toolbar.add(new JLabel( toolTips2[5] ),7);
          toolbar.add(widthLabel,8);
          //adição da label de altura
          toolbar.add(new JLabel( toolTips2[6] ),9);
          toolbar.add(heightLabel,10);
          //############fim da criação da toolbar################

          //adicionando a toolbar no container na posição norte
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
         * Método que chama o método da classe <tt>painel</tt>
         * <tt>sharpImage()</tt>, método esse que passa um filtro
         * do tipo sharping na imagem corrente
         */
        public void sharpOperation()
        {
           myPanel.sharpImage();   
        }
        
        /**
         * Após a correção geométrica, é chamado esse método para
         * visualizar a imagem já corrigida.
         * É chamado uma método da classe <tt>painel</tt>
         */
        public void geoVisualization()
        {
            myPanel.geoVisualization();
        }
        
        /**
         * Chama o método GCP para coletar os pontos de controle para 
         * a correção geométrica. É chamado um método da classe <tt>painel</tt>
         */
        public void GCP()
        {
           myPanel.GCP();
        }
        
        /**
         * Chama o método para iniciar a correçao geométrica da
         * imagem. É chamado um método da classe <tt>painel</tt>
         */
        public void geoCorrection()
        {
           myPanel.geoCorrection( imagePath );
        }
        
        /*
         *###########Fim de operaçoes com imagem################
         */
        
        /**
         * Retorna o nome da imagem que está contida neste quadro auxiliar
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
                //chama o método saveImage da classe <tt>painel</tt>
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