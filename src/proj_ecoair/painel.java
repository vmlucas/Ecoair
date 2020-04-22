/*
 *  Projeto Ecoair
 */
package proj_ecoair;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * Classe que herda de <tt>javax.swing.JPanel</tt> e é responsável 
 * pelo desenho de uma imagem na tela. Cada instancia desta classe 
 * desenha uma nova imagem na tela. É responsável pelo seleção da área
 * a ser recortada e munus pop up. Utiliza a classe ImageProcessing 
 * para realizar operações com a imagem, são requisições vindas da classe
 * QuadroAux e passadas por ela para a classe ImageProcessing 
 *
 * @author Victor Motta da Rocha Lucas
 */
public class painel extends JPanel implements MouseListener, MouseMotionListener
{

  /**
   * Constrói um novo objeto do tipo painel que será adicionado
   * em um JFrame QuadroAux. Utiliza os parâmetros <tt>imageCompletePath</tt>
   * e <tt>scale</tt>
   *
   * @Param imageCompletePath o caminho completo da imagem a ser aberta
   * @Param scale a porcentagem da imagem a ser aberta
   * 
   */
  public painel(RenderedImage realImage, float scale, MenuLanguage ml)
  {
     //inicialização da variáveis 
     this.ml = ml; 
     x1=x2=y1=y2=0;
     gcp = null;
     
     //inicialização do labels da tela que marcam a pos. da imagem
     widthLabel = new JLabel();
     heightLabel = new JLabel();
     
     //Carregando as imagens
     setFullImage( realImage );
     setCurrentImage(ImageProcessing.getInstance().scaleImage( getFullImage(), scale ));
     setZoomLessImage(getCurrentImage());
     setUndoImage(getCurrentImage());

     //Definindo a largura e altura do JPanel
     setPanelWidth(getCurrentImageWidth());
     setPanelHeight(getCurrentImageHeight());
     
     //definindo a orientação da imagem
     trans = new AffineTransform();
     trans.setToTranslation(0,0);

     //##########adicionando um menu pop up###########
     String[] popupText = ml.getPopupTexts();
     //adicionado a menu "copy"
     popup = new JPopupMenu();
       JMenuItem mi = new JMenuItem( popupText[0] );
       mi.addActionListener( new ActionListener()
       {
          public void actionPerformed(ActionEvent e)
          {
            copyPopup_actionPerformed(e);
          }
      });
        
     popup.add(mi);
       
       //se currentImage for um mapa temático adiciona ao pop up
       //uma legenda
       if( ImageProcessing.getInstance().isClassImage( getCurrentImage() ) )
       {
          JMenu legend = new JMenu( popupText[1] );
            JMenuItem greenMenuItem = new JMenuItem( popupText[2] );
            greenMenuItem.setIcon(new ImageIcon( ".\\icons\\darkgreen.gif"));
            JMenuItem lightgreenMenuItem = new JMenuItem( popupText[3] );
            lightgreenMenuItem.setIcon(new ImageIcon( ".\\icons\\green.gif"));
            JMenuItem yellowMenuItem = new JMenuItem( popupText[4] );
            yellowMenuItem.setIcon(new ImageIcon( ".\\icons\\yellow.gif"));
            JMenuItem orangeMenuItem = new JMenuItem( popupText[5] );
            orangeMenuItem.setIcon(new ImageIcon( ".\\icons\\orange.gif"));
            JMenuItem blueMenuItem = new JMenuItem( popupText[6] );
            blueMenuItem.setIcon(new ImageIcon( ".\\icons\\blue.gif"));
            
          legend.add(lightgreenMenuItem);
          legend.add(yellowMenuItem);
          legend.add(orangeMenuItem);
          legend.add(blueMenuItem);
          legend.add(greenMenuItem);
          
          popup.add(legend); 
       }
       this.add(popup);
       //#################Fim cosntrução menu pop up############
    
       //#########Eventos do mouse##############################
       //adionando eventos com o mouse ao JPanel
       this.addMouseListener( this );
	     
       //adiociona eventos de mouse como o arrasto pelo pela e o movimento
       this.addMouseMotionListener( this );	    
  }

  private void copyPopup_actionPerformed(ActionEvent e)
  {
     copiar(); 
  }
  
  /**
   *  Mostra o menu pop up na tela
   */
  private void mostrar(MouseEvent e)
  {
     if(e.isPopupTrigger())
     {
        popup.show(this,e.getX(),e.getY());
     }
  }
  
  /**
   * Define a largura do <tt>JPanel</tt>
   */
  private void setPanelWidth(int x)
  {
      width = x;
  }
  
  /**
   * Obtém a largura do <tt>JPanel</tt>
   *
   * @return a largura do <tt>JPanel</tt>
   */
  private int getPanelWidth()
  {
     return width;   
  }
  
  /**
   * Define a altura do <tt>JPanel</tt>
   */
  private void setPanelHeight(int y)
  {
      height = y;
  }
  
  /**
   * Obtém a altura do <tt>JPanel</tt>
   *
   * @return a altura do <tt>JPanel</tt>
   */
  private int getPanelHeight()
  {
     return height;   
  }
  
  /**
   * Obtém o label que marca a largura
   *
   * @return um label que marca a largura
   */
  public JLabel getWidthLabel()
  {
      return widthLabel;
  }
  
  /**
   * Define a posição x do label de largura
   */
  private void setWidthLabel(int x)
  {
      widthLabel.setText(String.valueOf(x));
  }
  
  /**
   * Obtém o label que marca a altura
   *
   * @return um label que marca a altura
   */
  public JLabel getHeightLabel()
  {
      return heightLabel;
  }
  
  /**
   * Define a posição y para o label que marca a altura 
   *  
   */
  private void setHeightLabel(int y)
  {
      heightLabel.setText(String.valueOf(y));    
  }
  
  /**
   * Método que sobreescreve o método Paint da classe <tt>JPanel</tt>
   * Responsável por qualquer desenho na tela dentro de cada quadro
   * com as imagens
   */
  public void paintComponent(Graphics g)
  {
     super.paintComponent(g);//evita que a tela pisque e desenha 
                             //as modificções da imagem sem atraso
     paintBackground(g);    //desenha o fundo da imagem
     paintForeground(g);    //desenha o foreground da imagem
  }

  /**
   *  método para mostrar o popup menu
   */
  public void mousePressed(MouseEvent e)
	{
	   mostrar(e);
	}

  /**
   *  Método para a criação do menu popup, e para a marcaçao 
   *  dos pontos de controle na tela. Desenha os números na tela.
   */
  public void mouseClicked(MouseEvent e)
	{
     mostrar(e);

     //Zera os pontos do retângulo
     x1=x2=y1=y2=0;
                  
     //marca os pontos para o desenho na tela dos números
     //dos pontos de marcação da correção geométrica
     if((gcp != null)&&(gcp.isReady()))
     {
        gcp.setMousePoints( e.getX(), e.getY() );
     }
     repaint(); //apaga o retângulo ou desenha um número referente ao ponto
                //de marcação para a correção geométrica
	}

  /**
   * Método para mostrar o menu popup na tela
   */ 
  public void mouseReleased(MouseEvent e)
  {
     mostrar(e);    
  }

  public void mouseExited( MouseEvent e)
  { }

  public void mouseEntered( MouseEvent e)
  { }
  
  /**
   *  Método para a criaçao de um retângulo na tela como marcação
   *  da área para o recorte.
   */
  public void mouseDragged(MouseEvent e)
	{
	   //define as posições finais do retângulo se as iniciais
     //existirem  
     if((x1 != 0)&&(y1 != 0))
	   {
	        x2 = e.getX();
	        y2 = e.getY();
	   }//se as posições iniciais não existirem, as define
	   else
	   {
	        x1 = e.getX();
	        y1 = e.getY();
	   }
     setWidthLabel(e.getX()); //define a largura atual no label
     setHeightLabel(e.getY()); //define a altura atual no label
     repaint(); //desenha o retângulo na tela
	}

  /**
   * marca a posição para mostrar nos labels
   */ 
  public void mouseMoved(MouseEvent e)
  {
      setWidthLabel(e.getX());
      setHeightLabel(e.getY());
  }
     
  /**
   * Recorta a imagem corrente e gera uma nova na tela
   */
  public void copiar()
  {
     setCurrentImage( ImageProcessing.getInstance().cropImage( getCurrentImage(), dimx1, dimy1, dimx2, dimy2 )); 
     setZoomLessImage( getCurrentImage() );
     repaint(); //repinta a tela, chama o método paint 
  }
  
  /**
   * Salva a imagem corrente em disco, chama um método de ImageProcessing
   */
  public void saveImage(String fileName)
  {
     ImageProcessing.getInstance().saveImage( getCurrentImage(), fileName );
     
     JOptionPane.showMessageDialog(null,fileName+ml.getSavedImageMessage() ,"Message",
                JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Passa um filtro passa alta do tipo "sharping" na imagem corrente
   */
  public void sharpImage()
  {
      setCurrentImage( ImageProcessing.getInstance().sharpImage( getCurrentImage() ));
      setZoomLessImage( getCurrentImage() );
      repaint(); //redesenha a imagem nova na tela, chama o método paint
  }

  /**
   *  Obtém a imagem corrente da tela
   *
   * @return a imagem corrente da tela
   */
  private RenderedImage getCurrentImage()
  {
     return currentImage;    
  }
  
  /**
   * Define a imagem corrente da tela
   */
  public void setCurrentImage(RenderedImage img)
  {
     currentImage = img;   
  }
  
  /**
   * Obtém a imagem corrente em seu tamanho e formato original 
   */
  public RenderedImage getFullImage()
  {
     return fullImage;    
  }
  
  /**
   * Define a imagem a ser aberta em seu tamanho origianl
   */
  private void setFullImage(RenderedImage img)
  {
     fullImage = img;   
  }
  
  /**
   * Obtém a imagem original que foi aberta antes de terem sido feita
   * mudanças 
   */
  public RenderedImage getUndoImage()
  {
     return undoImage;    
  }
  
  /**
   * Define uma imagem para ser recuperada sem mudanças
   */
  private void setUndoImage(RenderedImage img)
  {
     undoImage = img;   
  }
  
  /**
   * Obtém a imagem aberta sem zoom
   *
   * @return a imagem corrente sem zoom 
   */
  public RenderedImage getZoomLessImage()
  {
     return zoomLessImage;    
  }
  
  /**
   * Define a imagem corrente sem zoom 
   */
  private void setZoomLessImage(RenderedImage img)
  {
     zoomLessImage = img;   
  }
  
  /**
   * Obtém a largura da imagem corrente
   *
   * @return a largura da imagem corrente
   */
  private int getCurrentImageWidth()
  {
     return getCurrentImage().getWidth();   
  }
  
  /**
   * Obtém a altura da imagem corrente
   *
   * @return a altura da imagem corrente
   */
  private int getCurrentImageHeight()
  {
    return getCurrentImage().getHeight();    
  }
  
  /**
   * Define a imagem corrente em seu tamnho original
   */
  public void getFullResolution()
  {
      setCurrentImage(fullImage);
      setPanelWidth(getCurrentImageWidth());
      setPanelHeight(getCurrentImageHeight());
      setZoomLessImage( getCurrentImage() );
      repaint(); //desenha a imagem em seu tamnho original na tela
  }

  /**
   * Define a imagem corrente aumentada de 10%
   */
  public void zoomin()
  {
      setCurrentImage(ImageProcessing.getInstance().zoomImage( getCurrentImage(), 1.1f ));
      setPanelWidth(getCurrentImageWidth());
      setPanelHeight(getCurrentImageHeight());
      repaint(); //redesenha a imagem na tela
  }

  /**
   * Define a imagem deiminuida de 10%
   */
  public void zoomout()
  {
      setCurrentImage(ImageProcessing.getInstance().zoomImage( getCurrentImage(), 0.9f ));
      setPanelWidth(getCurrentImageWidth());
      setPanelHeight(getCurrentImageHeight());
      repaint(); //redesenha a imagem na tela
  }
  
  /**
   * Define a imagem corrente sem zoom, mas com suas mudanças
   */
  public void undoZoom()
  {
      setCurrentImage( getZoomLessImage() );
      setPanelWidth( getCurrentImageWidth() );
      setPanelHeight( getCurrentImageHeight() );
      repaint(); //redesenha a imagem na tela
  }

  /**
   * Define a imagem corrente com zoom ou não, mas sem mudanças 
   */
  public void undoChanges()
  {
      setCurrentImage( getUndoImage() );
      setZoomLessImage( getUndoImage() );
      setPanelWidth( getCurrentImageWidth() );
      setPanelHeight( getCurrentImageHeight() );
      repaint(); //redesenha a imagem na tela
  }

  /**
   * Abre o quadro com informações da imagem original (fullImage)
   */
  public void getImageInfo()
  {
      new InfoFrame( getFullImage() , ml);
  }
  
  /**
   * Método chamado após correção geomética para visualizar as mudanças
   * da imagem corrente
   */
  public void geoVisualization()
  {
     setCurrentImage( geoFrame.getCorrectImage() );
     repaint(); //redesenha a imagem na tela
  }
       
  /**
   * Método que abre o quadro de coleta dos pontos para a correção
   * geométrica
   */
  public void GCP()
  {
      gcp = new gcp(ml);
  }
        
  /**
   * Abre o quadro para efetuar a correção geométrica
   */
  public void geoCorrection( String imageName )
  {
     geoFrame = new GeoCorrectionFrame( imageName, getCurrentImage(), ml);
  }
  
  /**
   * Obtém as dimansões deste <tt>JPanel</tt> para definir o tamanho
   * do <tt>ScrollPane</tt> da classe <tt>QuadroAux</tt> 
   */
  public Dimension getPreferredSize()
  {
      return new Dimension(getPanelWidth(),getPanelHeight());
  }
  
  /**
   * pinta na tela a imagem corrente
   */
  private void paintBackground(Graphics g)
  {
     Graphics2D g2 = (Graphics2D)g;
     g2.drawRenderedImage( currentImage, trans);
  }

  /**
   * pinta por cima da imagem um retângulo e ou os poantos da 
   * correção geométrica
   */
  private void paintForeground(Graphics g)
  {
     g.setColor(Color.white);
     if((x1 !=0)&&(x2 !=0)&&(y1 !=0)&&(y2 != 0))
     {
         g.drawRect(x1,y1,x2-x1,y2-y1);
         dimx1=x1;dimx2=x2;dimy1=y1;dimy2=y2;
         x2=y2=0;
     }
     if((gcp != null)&&(gcp.isReady()))
     {
        g.setColor(Color.yellow);
        g.drawString(String.valueOf(gcp.getCount()),gcp.getPointx(), gcp.getPointy());
     }
  }

   private JLabel widthLabel, heightLabel;
   private int x1,x2,y1,y2;
   private int dimx1,dimx2,dimy1,dimy2;
   private int width,height;
   private AffineTransform trans;
   private JPopupMenu popup;
   private RenderedImage currentImage;
   private RenderedImage fullImage;
   private RenderedImage undoImage;
   private RenderedImage zoomLessImage;
   private MenuLanguage ml;
   private GeoCorrectionFrame geoFrame;
   private gcp gcp;
}
