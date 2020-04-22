/*
 *  Projeto ECOAIR
 */
package proj_ecoair;

import java.io.*;
import java.awt.image.RenderedImage;
import javax.swing.*;
import java.awt.image.renderable.ParameterBlock;
import javax.media.jai.*;
import javax.media.jai.operator.*;
import com.sun.media.jai.codec.*;
import org.geotiff.image.jai.*;
import org.libtiff.jai.codecimpl.*;
import org.libtiff.jai.codec.*;
import org.geotiff.image.jai.*;

/**
 * Classe responsável pela parte de processamentos com a imagem,
 * é usado um construtor privado para garantir que sempre haverá um
 * única instancia dela em memória. Dentre outras coisas, efetivamente
 * salva imagens em disco, abre imagens do disco, executa filtros etc..
 *
 * @author Victor Motta da Rocha Lucas
 */
public class ImageProcessing 
{
    
    /**
     * construtor privado da classe ImageProcessing
     */
    private ImageProcessing() 
    {  }
    
    /**
     * método estático que retorna sempre uma mesma instancia da classe
     * ImageProcessing
     * 
     */
    public static ImageProcessing getInstance()
    {
       return imgProcessing;   
    }
    
    /**
     * Carrega uma imagem do disco
     *
     * @return uma nova imagem já carregada do tipo <tt>RenderedImage</tt>
     */
    public RenderedImage loadImage(String imagePath)
    {
       FileSeekableStream stream = null;
       try
       {
          stream = new FileSeekableStream(imagePath);
       }
       catch (IOException e)
       {
          e.printStackTrace();
       }
       ParameterBlock params = new ParameterBlock();
       params.add(stream);
       XTIFFDecodeParam decodeParam = new XTIFFDecodeParam();
       decodeParam.setDecodePaletteAsShorts(true);
       RenderedImage image = JAI.create("tiff", params);
       
       return image;
    }
    
    /**
     * Retorna uma nova imagem dentro de uma escala identificada
     * pelo parâmetro <tt>scale</tt> da imagem <tt>image</tt>
     */
    public RenderedImage scaleImage(RenderedImage image, float scale)
    {
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(image);                   // The source image
        pb.add(scale);                        // The xScale
        pb.add(scale);                        // The yScale
        pb.add(0.0F);                       // The x translation
        pb.add(0.0F);                       // The y translation
        pb.add(new InterpolationNearest()); // The interpolation
        RenderedImage scaleImage = JAI.create("scale", pb, null);
        
        return scaleImage;
    }
    
    /**
     * Retorna uma nova imagem que é um recorte da imagem <tt>img</tt>
     * identificada pelas posições <tt>x0</tt>, <tt>y0</tt>, <tt>x</tt> e <tt>y</tt> 
     *
     */
    public RenderedImage cropImage(RenderedImage img, int x0, int y0, int x, int y)
    {
        float Dimx1 = (float)x0;
        float Dimx2 = (float)x;
        float Dimy1 = (float)y0;
        float Dimy2 = (float)y;
        ParameterBlock parablock = new ParameterBlock();
        parablock.addSource(img);
        parablock.add(Dimx1);
        parablock.add(Dimy1);
        parablock.add(Dimx2-Dimx1);
        parablock.add(Dimy2-Dimy1);
        
        RenderedImage cropImage = JAI.create("Crop",parablock);
     
        return cropImage;     
    }
    
    /**
     *  Salva a imagem <tt>img</tt> em disco como um arquivo <tt>arq_nome</tt>
     */
    public void saveImage(RenderedImage img,String arq_nome)
   {
        XTIFFEncodeParam encodeParam = new XTIFFEncodeParam();
        FileOutputStream out = null;
        try
        {
           out = new FileOutputStream(arq_nome);
        }
        catch(IOException e)
        {
           JOptionPane.showMessageDialog(null,"não foi pssível criar arquivo","Message",
                 JOptionPane.INFORMATION_MESSAGE);
                                   
           e.printStackTrace();
        }
        
        ImageEncoder encoder = ImageCodec.createImageEncoder("TIFF", out,
                                                          encodeParam);
        try
        {
           encoder.encode(img);
           out.close();
        }
        catch (IOException e)
        {
           JOptionPane.showMessageDialog(null,"Não foi possível salvar arquivo","Message",
                         JOptionPane.INFORMATION_MESSAGE);
                                  
           e.printStackTrace();
        }     
    }
    
    /**
     *  Retorna uma nova imagem dentro de uma escala de zoom <tt>scale</tt>
     *  da imagem <tt>img</tt>
     */
    public RenderedImage zoomImage(RenderedImage img, float scale)
    {
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(img);                     // The source image
        pb.add(scale);                          // The xScale
        pb.add(scale);                          // The yScale
        pb.add(0.0F);                         // The x translation
        pb.add(0.0F);                         // The y translation
        pb.add(new InterpolationNearest()); // The interpolation
        
        RenderedImage zoomImage = JAI.create("scale", pb, null);
      
        return zoomImage;
    }
    
    /**
     * Retorna uma nova imagem já processada pelo filtro "sharping"
     * da imagem recebida como parâmetro <tt>img</tt>
     */
    public RenderedImage sharpImage(RenderedImage img)
    {
        float[] sharpKernel = {0.0f, -1.0f, 0.0f,
                            -1.0f, 5.0f, -1.0f,
                             0.0f, -1.0f, 0.0f
                            };
        KernelJAI k = new KernelJAI(3,3,sharpKernel);
        RenderedImage sharpImage = JAI.create("convolve", img, k);
      
        return sharpImage;
    }
    
    /**
     *  Verifica se a image <tt>image</tt> é um mapa temático ou não
     *
     * @return <tt>true</tt> se imagem é um mapa temático ou <tt>false</tt>
     * se não
     */
    public boolean isClassImage( RenderedImage image )
    {
         ParameterBlock pb = new ParameterBlock();
         pb.addSource(image);   // The source image
         pb.add(null);              // The region of the image to scan
         pb.add(1);                // The horizontal sampling rate
         pb.add(1);                // The vertical sampling rate

         // Perform the extrema operation on the source image
         RenderedOp op = JAI.create("extrema", pb);

         // Retrieve both the maximum and minimum pixel value
         double[][] extrema = (double[][]) op.getProperty("extrema");

         int highValue = (int)extrema[1][0];
      
         if(highValue > 10)
         {
             return false;
         }
         else
         {
             return true;
         }
    }
    
    /**
     * gera uma imagem nova apartir de um AND entre uma imagem e um vetor
     * de valores inteiros recebidos como parâmetros. Esses valores representam
     * as cores da imagem para cada banda
     */
    public RenderedImage getANDCONSTImage( RenderedImage image1, int[] array )
    {
        ParameterBlock pb;
        pb = new ParameterBlock();
        pb.addSource(image1);
        pb.add( array );
        RenderedImage newImage = JAI.create("andconst", pb);
        return newImage;           
    }
    
    /**
     * Realiza uma operação de XOR entre duas imagens recebidas como
     * parâmetro.
     */
    public RenderedImage getXORImage( RenderedImage image1, RenderedImage image2 )
    {
        ParameterBlock pb;
        pb = new ParameterBlock();
        pb.addSource( image1 );
        pb.addSource( image2 );
        RenderedImage newImage = JAI.create("Xor", pb);
        return newImage;   
    }
    
    /*private static field imgProcessing */
    private static ImageProcessing imgProcessing = new ImageProcessing();

}
