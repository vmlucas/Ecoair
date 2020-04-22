/*
 * Projeto Ecoair
 */

package proj_ecoair;

import java.util.*;

/**
 * @author  Victor Motta
 */
public class PtMenuLanguage implements MenuLanguage 
{
    
    /**
     * Creates a new instance of PtMenuLanguage 
     */
    public PtMenuLanguage()
    {
    }
    
    public String[] getMenuNames() 
    {
      String [] names = new String[29];
      names[0] = "Arquivo";
      names[1] = "Abrir";
      names[2] = "Imprimir";
      names[3] = "Fechar";
      names[4] = "Ferramentas";
      names[5] = "Filtro";
      names[6] = "Sharping";
      names[7] = "Composi��o de cor";
      names[8] = "Georrefer�ncia";
      names[9] = "Sele��o dos pontos de controle";
      names[10] = "Corre��o geom�trica";
      names[11] = "Visualiza��o";
      names[12] = "Classifica��o";
      names[13] = "Iniciar classifica��o";
      names[14] = "Detec��o Land use/ land cover";
      names[15] = "Mapa de mudan�as";
      names[16] = "Verde escuro";
      names[17] = "Verde Claro";
      names[18] = "Laranja";
      names[19] = "Amarelo";
      names[20] = "Azul";
      names[21] = "Supervisionada";
      names[22] = "N�o supervisionada";
      names[23] = "Regress�o linear";
      names[24] = "Fus�o NOAA/LandSat";
      names[25] = "Perfis";
      names[26] = "Classifica��o em larga escala";
      names[27] = "Classifica��o";
      names[28] = "Vizualizar novo mapa de classifica��o";
      
      return names;
    }
    
    public String getWindowMessage() 
    {
        return "Selecione uma janela, por favor";
    }    
    
    public String getSliderLabel() 
    {
        return "Percentual da imagem";
    }
    
    public String getImagePercentMessage() 
    {
        return "Percentual da Imagem n�o pode ser zero";
    }
    
    public String[] getToolTips() 
    {
       String[] names = new String[4];
       names[0] = "Abrir imagem";
       names[1] = "Iniciar classifica��o";
       names[2] = "land cover detection";
       names[3] = "Fechar programa";
       
       return names;
    }
    
    public String getClassMessage() 
    {
        return "Por favor, fa�a uma classifica��o completa";
    }
    
    public String[] getAuxToolTips() 
    {
        String[] names = new String[7];
        names[0] = "Salvar imagem";
        names[1] = "Desfazer zoom";
        names[2] = "Desfazer mudan�as";
        names[3] = "Imagem total";
        names[4] = "Informa��o da imagem";
        names[5] = "Largura ";
        names[6] = "Altura ";
        
        return names;
    }
    
    public String[] getPopupTexts() 
    {
        String[] names = new String[7];
        names[0] = "Copiar";
        names[1] = "Legenda";
        names[2] = "Cerradinho";
        names[3] = "Cerrado";
        names[4] = "Solo exposto";
        names[5] = "Pastagem com solo exposto";
        names[6] = "Pastagem";
        
        return names;
    }
    
    public String getSavedImageMessage() 
    {
        return " salva";
    }

    public String[] getBandMergeLabels()
    {
      String[] labels = new String[7];
      labels[0] = "Imagem1";
      labels[1] = "Imagem2";
      labels[2] = "Imagem3";
      labels[3] = "Imagem final";
      labels[4] = "Procurar";
      labels[5] = "Imagens Tiff";
      labels[6] = " criado";

      return labels;
    }    
    
    public Vector getInfoFrameTexts() 
    {
        Vector names = new Vector();
        names.add(0, "Informa��o da imagem");
        names.add(1, "Largura da imagem");
        names.add(2, "Altura da imagem");
        
        return names;
    }
    
    public Vector getGCPTexts() 
    {
        Vector names = new Vector();
        names.add(0, "Sele��o dos pontos de controle");
        names.add(1, "Come�ar");
        names.add(2, "N�mero de pontos de controle");
        names.add(3, "Informe o nome do arquivo");
        names.add(4, "Salvar os pontos selecionados");
        names.add(5, "Procurar");
        names.add(6, "Arquivos textos");
        names.add(7, "N�mero de pontos de controle");
        names.add(8, "Salvar");
        names.add(9, "Apagar ponto");
        
        return names;
    }

    public Vector getGeocorrectionTexts()
    {
      Vector names = new Vector();
      names.add(0, "Corre��o geom�trica");
        names.add(1, "Come�ar");
        names.add(2, "Pontos da imagem errada");
        names.add(3, "Procurar");
        names.add(4, "Arquivos texto");
        names.add(5, "Pontos da imagem correta");
        names.add(6, "Imagem errada");
        names.add(7, "Grau desejado");
        names.add(8, "Arquivo fonte � nulo");
        names.add(9, "Arquivo de destino � nulo");
        names.add(10, "Grau inv�lido");
        names.add(11, "Grau deve ser 1,2 or 3");
        names.add(12, "Corre��o foi feita");
      return names;
    }

    public String[] getProfilesLabels()
    {
        String[] nomes = new String[25];
        nomes[0] = "Imagem do NOAA canal 1";
        nomes[1] = "Imagem do NOAA canal 2";
        nomes[2] = "Imagem classificada";
        nomes[3] = "M�scara";
        nomes[4] = "Numero de classes";
        nomes[5] = "Sa�da NDVI";
        nomes[6] = "Procurar";
        nomes[7] = "Come�ar";
        nomes[8] = "Imagem do NOAA canal 1 em branco";
        nomes[9] = "Imagem do NOAA Canal 2 em branco";
        nomes[10] = "M�scara em branco";
        nomes[11] = "Numero de classes em branco";
        nomes[12] = "Arquivos de imagem do INRIA";
        nomes[13] = "CLOUD DETECTION COMPLETADO";
        nomes[14] = null;
        nomes[15] = "Ocorreram erros no processo";
        nomes[16] = "CLOUD CORRECTION COMPLETADO";
        nomes[17] = null;
        nomes[18] = null;
        nomes[19] = "PROPORTIONS COMPLETADO";
        nomes[20] = "REFLECTANCE PROFILES ...Canal 1";
        nomes[21] = "REFLECTANCE for Channel 1 completado";
        nomes[22] = "REFLECTANCE PROFILES ...Canal 2";
        nomes[23] = "REFLECTANCE for Channel 2 completado";
        nomes[24] = "NDVI PROFILES completado";

        return nomes;
    }

    public String[] getLargeScaleNames()
    {
       String[] nomes = new String[4];
       nomes[0] = "Large scale classification completada ";
       nomes[1] = "Ocorreram erros no processo";
       nomes[2] = "Arquivo de Proportion estimation(.inr)";
       nomes[3] = "Fa�a uma profile classification primeiro";
       
       return nomes;
    }

    public String[] getTrainingSetMakerLabels()
    {
       String[] labels = new String[18];
       labels[0] = "Conjunto de treinamento";
       labels[1] = "Imagem antiga";
       labels[2] = "Mapa da Imagem antiga";
       labels[3] = "Imagem recente";
       labels[4] = "Procurar";
       labels[5] = "Arquivo do conjunto de trainamento(o nome)";
       labels[6] = "Tipo de filtro";
       labels[7] = "N�vel de confian�a";
       labels[8] = "Percentual da classe";
       labels[9] = "Percentual da imagem";
       labels[10] = "Arquivos TIFF";
       labels[11] = "Arquivo vazio";
       labels[12] = "Imagem antiga vazia";
       labels[13] = "Mapa da imagem antiga vazio";
       labels[14] = "Conjunto de treinamento feito";
       labels[15] = "Percentual da imagem vazio";
       labels[16] = "Mapa da classe feito";
       labels[17] = "Arquivos Mat";
       
       return labels;
    }
}
