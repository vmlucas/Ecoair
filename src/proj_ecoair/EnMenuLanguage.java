/*
 * Projeto Ecoair
 */

package proj_ecoair;

import java.util.*;

/**
 *
 * @author  Victor Motta
 */
public class EnMenuLanguage implements MenuLanguage
{
    
    /** Creates a new instance of EnMenuLanguage */
    public EnMenuLanguage() 
    {
    }
    
    public String[] getMenuNames() 
    {
        String [] names = new String[29];
        names[0] = "File";
        names[1] = "Open";
        names[2] = "Print";
        names[3] = "Close";
        names[4] = "Tools";
        names[5] = "Filtering";
        names[6] = "Sharping";
        names[7] = "Colour Composition";
        names[8] = "Georeference";
        names[9] = "GCP - Ground Control Point";
        names[10] = "Geometric Correction";
        names[11] = "Visualization";
        names[12] = "Classification";
        names[13] = "Open classification";
        names[14] = "Land use/ land cover detection";
        names[15] = "Change Map";
        names[16] = "Dark Green";
        names[17] = "Light Green";
        names[18] = "Orange";
        names[19] = "Yellow";
        names[20] = "Blue";
        names[21] = "Supervised";
        names[22] = "Unsupervised";
        names[23] = "Linear Regretion";
        names[24] = "NOAA/LandSat Fusion";
        names[25] = "Profiles";
        names[26] = "Large scale classification";
        names[27] = "Classification";
        names[28] = "View new classification map";
                
        return names;
    }
    
    public String getWindowMessage() 
    {
        return "Select a window, please";
    }    
    
    public String getSliderLabel() 
    {
      return "Image Percent";
    }
    
    public String getImagePercentMessage() 
    {
      return "image percent cannot be zero";  
    }
    
    public String[] getToolTips() 
    {
       String[] names = new String[4];
       names[0] = "open image";
       names[1] = "open classification";
       names[2] = "land cover detection";
       names[3] = "Close program";
       
       return names;
    }    
    
    public String getClassMessage() 
    {
        return "Please, make a classification";
    }
    
    public String[] getAuxToolTips() 
    {
        String[] names = new String[7];
        names[0] = "save image";
        names[1] = "Undo zoom";
        names[2] = "undo changes";
        names[3] = "Full image";
        names[4] = "image info";
        names[5] = "Width ";
        names[6] = "Height ";
        
        return names;
    }    
    
    public String[] getPopupTexts() 
    {
        String[] names = new String[7];
        names[0] = "Copy";
        names[1] = "Legend";
        names[2] = "Little scrub";
        names[3] = "Scrub";
        names[4] = "Bare soil";
        names[5] = "Bare soil with grass";
        names[6] = "Grass land";
        
        return names;
    }    
    
    public String getSavedImageMessage() 
    {
        return " created";
    }

    public String[] getBandMergeLabels()
    {
      String[] labels = new String[7];
      labels[0] = "Image1";
      labels[1] = "Image2";
      labels[2] = "Image3";
      labels[3] = "Final Image";
      labels[4] = "browse";
      labels[5] = "Tiff Image Files";
      labels[6] = " created";

      return labels;
    }
    
    public Vector getInfoFrameTexts() 
    {
        Vector names = new Vector();
        names.add(0, "Image info");
        names.add(1, "Image Width");
        names.add(2, "Image Height");
        
        return names;
    }
    
    public Vector getGCPTexts() 
    {
        Vector names = new Vector();
        names.add(0, "Control points sellection");
        names.add(1, "Begin");
        names.add(2, "input points number");
        names.add(3, "input or select file name");
        names.add(4, "Save sellected points");
        names.add(5, "browse");
        names.add(6, "Text Files");
        names.add(7, "Numbers of controll points");
        names.add(8, "Save");
        names.add(9, "Delete point");
        
        return names;
    }

    public Vector getGeocorrectionTexts()
    {
       Vector names = new Vector();
        names.add(0, "Geometric Correction");
        names.add(1, "Begin");
        names.add(2, "Wrong image points File");
        names.add(3, "Browse");
        names.add(4, "Text Files");
        names.add(5, "Correct image points file");
        names.add(6, "Wrong Image");
        names.add(7, "Desired Degree");
        names.add(8, "source archive is null");
        names.add(9, "Destiny archive is null");
        names.add(10, "Invalid degree");
        names.add(11, "degree must be 1,2 or 3");
        names.add(12, "Correction has been made");
        
        return names;      
    }

    public String[] getProfilesLabels()
    {
        String[] nomes = new String[25];
        nomes[0] = "NOAA channel1 image ";
        nomes[1] = "NOAA channel2 image";
        nomes[2] = "Classification image";
        nomes[3] = "Mask image";
        nomes[4] = "Number of classes";
        nomes[5] = "NDVI output file";
        nomes[6] = "Browse";
        nomes[7] = "Start";
        nomes[8] = "noaa Canal1 Image is empty";
        nomes[9] = "noaa Canal2 Image is empty";
        nomes[10] = "noaa mask Image is empty";
        nomes[11] = "class number is empty";
        nomes[12] = "Inria Image Files";
        nomes[13] = "CLOUD DETECTION COMPLETED, ";
        nomes[14] = "result is in ";
        nomes[15] = "Process ended with errors";
        nomes[16] = "CLOUD CORRECTION COMPLETED, ";
        nomes[17] = "results are ";
        nomes[18] = "and";
        nomes[19] = "PROPORTIONS COMPLETED";
        nomes[20] = "REFLECTANCE PROFILES ...Channel 1";
        nomes[21] = "REFLECTANCE for Channel 1 completed";
        nomes[22] = "REFLECTANCE PROFILES ...Channel 2";
        nomes[23] = "REFLECTANCE for Channel 2 completed";
        nomes[24] = "NDVI PROFILES completed";

        return nomes;
    }

    public String[] getLargeScaleNames()
    {
       String[] nomes = new String[4];
       nomes[0] = "Large scale classification COMPLETED ";
       nomes[1] = "process ended with errors";
       nomes[2] = "Proportion estimation file(.inr)";
       nomes[3] = "Make profile classification first";
       
       return nomes;
    }

    public String[] getTrainingSetMakerLabels()
    {
       String[] labels = new String[18];
       labels[0] = "Training set maker";
       labels[1] = "Old image";
       labels[2] = "Old image map";
       labels[3] = "Recent image";
       labels[4] = "Browse";
       labels[5] = "Training set file( the name)";
       labels[6] = "Filtering type";
       labels[7] = "Trust level";
       labels[8] = "Class percent";
       labels[9] = "Image percent";
       labels[10] = "Tiff Image Files";
       labels[11] = "File is empty";
       labels[12] = "old image is empty";
       labels[13] = "old image map is empty";
       labels[14] = "Training Set Maker is done";
       labels[15] = "Image percent is empty";
       labels[16] = "ClassMaker is done";
       labels[17] = "Mat Files";
       
       return labels;
    }
}
