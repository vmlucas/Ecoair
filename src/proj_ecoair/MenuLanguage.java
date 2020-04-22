/*
 * Projeto Ecoair
 */

package proj_ecoair;

import java.util.Vector;

/**
 * Interface para a criação dos menus e dos textos da aplicação
 * 
 * @author  Victor Motta
 */
public interface MenuLanguage 
{
    public String[] getMenuNames();
    
    public String getWindowMessage();
    
    public String getSliderLabel();
    
    public String getImagePercentMessage();
    
    public String[] getToolTips();
    
    public String getClassMessage();
    
    public String[] getAuxToolTips();
    
    public String[] getPopupTexts();
    
    public String getSavedImageMessage();

    public String[] getBandMergeLabels();
    
    public Vector getInfoFrameTexts();
    
    public Vector getGCPTexts();

    public Vector getGeocorrectionTexts();

    public String[] getProfilesLabels();

    public String[] getLargeScaleNames();

    public String[] getTrainingSetMakerLabels();
}
