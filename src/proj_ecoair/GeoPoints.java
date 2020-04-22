package proj_ecoair;

import java.io.*;
import java.util.*;

public class GeoPoints 
{
  public GeoPoints(String s, int pontos)
  {
     pointx=pointy=0;
     count = 0;
     flag = false;
     startPointsCollection(s, pontos);
  }

  public int getCount()
  {
    return count;
  }

  public void setCount(int number)
  {
    count = number;   
  }

  public int getPointx()
  {
    return pointx;
  }

  public void setPointx(int number)
  {
    pointx = number;   
  }

  public int getPointy()
  {
    return pointy;
  }

  public void setPointy(int number)
  {
    pointy = number;   
  }

  public int getPontos()
  {
     return pontos;  
  }
  
  public void setPontos(int number)
  {
      pontos = number;  
  }
  
  public void setMousePoints(int x, int y)
  {
     pointx=x;
     pointy=y;
     if(getCount() != getPontos())
     {
        pointVector[count][0] = count;
        pointVector[count][1] = pointx;
        pointVector[count][2] = pointy;
        count++;
     }
     else flag = false;
  }

  public void subtractCount()
  {
    if(count != 0)
    {
        count--;
    }  
  }

  public boolean isReady()
  {
    return flag;
  }
  
  private void startPointsCollection(String s, int pontos)
  {
     flag = true;
     file =  new File(s);
     if(!file.exists())//verifica se o arquivo contendo os pontos já existe
     {
        setPontos(pontos);
        pointVector = new int[getPontos()][3];
     }
     else //existindo o arquivo, o arquivo é lido e é guardado em vetores os pontos
     {
        try
        {
           BufferedReader buff = new BufferedReader(new FileReader(file));
           String linha = buff.readLine();
           StringTokenizer tok = new StringTokenizer(linha,";");
           String st = tok.nextToken();
           setPontos(Integer.parseInt(st));

           pointVector = new int[getPontos()][3];
           linha="";
           setCount(0);
           boolean eof= false;
           while((!eof))
           {
               linha = buff.readLine();
               if(linha == null)
                   eof=true;
               else
               {
                  tok = new StringTokenizer(linha,";");
                  for(int j=0;j<3;j++)
                  {
                     pointVector[getCount()][j]=Integer.parseInt(tok.nextToken());
                  }
                  count++;
               }
           }
        }
        catch(IOException exception)
        {
          System.out.println(exception);
        }          
     }      
  }
  
  public void writeFile( )
  {
     try
     {
         FileWriter out = new FileWriter(file);
         out.write(String.valueOf(pontos)+";");
         for (int i=0;i<count;i++)
         {
              out.write("\n");
              for(int j=0;j<3;j++)
              {
                  out.write(String.valueOf(pointVector[i][j]));
                  out.write(";");
              }

          }
          out.close();
       }
       catch(IOException ex)
       {
          System.out.print(ex);
       }       
  }
  
  private int pointx, pointy;
  private int[][] pointVector;
  private int pontos;
  private int count;
  private File file;
  private boolean flag;

}