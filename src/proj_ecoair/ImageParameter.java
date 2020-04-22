package proj_ecoair;

/**
 * Classe que armazena os nomes das imagens que representam os mapas de 
 * classificação após a classificação land use land cover.
 */
public class ImageParameter 
{
    private static String oldImageMap = null;
    private static String newImageMap = null;
    
    public static void setOldImageMap( String name )
    {
       oldImageMap = name;     
    }
    
    public static void setNewImageMap( String name )
    {
       newImageMap = name;    
    }
    
    public static String getOldImageMap()
    {
       return oldImageMap;   
    }
    
    public static String getNewImageMap()
    {
       return newImageMap;   
    }
}
