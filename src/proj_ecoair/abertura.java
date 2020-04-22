/*
 * Projeto Ecoair
 */
package proj_ecoair;

import java.awt.*;
import java.awt.event.*;

/**
 * Classe responsável pela abertura do programa, carrega a imagem de
 * abertura e chama a classe <tt>QuadroPrinc</tt>
 */
public class abertura extends Frame
{
        /**
         * Constroi um quadro de abertura
         */
	public abertura()
	{
            panel1 = new ImagePanel();
            panel2 = new java.awt.Panel();
            choice1 = new java.awt.Choice();
            frame = this;
            
            add(panel1, java.awt.BorderLayout.CENTER);
            
            choice1.add( "Pt" );
            choice1.add( "En" );
            choice1.addItemListener(
               new java.awt.event.ItemListener() 
               {
                  public void itemStateChanged(ItemEvent evt) 
                  {
                      frame.dispose(); //fecha a tela
                      new QuadroPrinc( choice1.getSelectedItem() );//chama o quadro principal
                  }
               }
            );
            panel2.add(choice1);
            add(panel2, java.awt.BorderLayout.SOUTH);
            
            this.setBounds(20,20,700,550);
            this.setResizable(false);
	    this.setVisible(true);
	}
        
    /* private Choice field */    
    private Choice choice1;
    
    /*private Panel field */
    private Panel panel2;
    
    /*private ImagePanel field */
    private ImagePanel panel1;
    
    /* private Frame field */
    private Frame frame;
}

/**
 *  Classe que gera um painel com a imagem de abertura
 */
class ImagePanel extends Panel
{
   /**
    * Construtor da classe, é nele que é carregado a imagem
    */ 
   public ImagePanel()
   {
       Toolkit toolkit = this.getToolkit();
       backGroundImage = toolkit.getImage(".\\images\\abertura.jpg");
       
   }
   
   /**
    * desenha a imagem de abertura na tela
    */
    public void paint(Graphics g)
    {
      g.drawImage(backGroundImage,0,0,this);
    }
        
    /* private Image field backGroundImage */
    private Image backGroundImage;
    
}
