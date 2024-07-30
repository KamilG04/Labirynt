package com.mycompany.labirynt;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.io.*;  
import java.util.Scanner;  
import javax.imageio.ImageIO;

public class SaveLoadSystem {
   private static final String c_SaveFile = "Save.txt";
   private final String c_MapFileName = "Map.png";
   private final String c_MapFileName1 = "Map1.png";
   private final String c_MapFileName2 = "Map2.png";
   private final int c_MapSize = 64;
   private int m_Level = 0;
   SaveLoadSystem()
   {
   }
   ArrayList<Renderable> LoadGame()
   {
       try 
       {
        File file = new File(c_SaveFile);
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) 
        {
            m_Level = myReader.nextInt();
            return LoadLevel();
        }
        myReader.close();
       }
       catch(FileNotFoundException e)
       {
            System.out.println("An error occurred.");
            e.printStackTrace();
       }
       return new ArrayList<Renderable>();
   }
   void SaveGame() throws IOException
   {
       Writer wr = new FileWriter(c_SaveFile);
       wr.write(Integer.toString(m_Level));
       wr.close();
   }
   ArrayList<Renderable> LoadLevel()
   {
       BufferedImage map = new BufferedImage(c_MapSize, c_MapSize, BufferedImage.TYPE_INT_ARGB);
       switch(m_Level)
       {
           case 0:
           {
                try
                {       
                    File file  = new File(c_MapFileName);
                    map = ImageIO.read(file);
                }
                catch(IOException exception)
                {
                    System.out.println("Exception: " + exception);
                } 
               break;
           }
           case 1:
           {
                try
                {       
                    File file  = new File(c_MapFileName1);
                    map = ImageIO.read(file);
                }
                catch(IOException exception)
                {
                    System.out.println("Exception: " + exception);
                }
               break;
           }
           case 2:
           {
                try
                {       
                    File file  = new File(c_MapFileName2);
                    map = ImageIO.read(file);
                }
                catch(IOException exception)
                {
                    System.out.println("Exception: " + exception);
                }
               break;
           }
       }
       
       ArrayList<Renderable> renderables = new ArrayList<>();
       
        final byte[] pixels = ((DataBufferByte) map.getRaster()
                .getDataBuffer()).getData();
        
        boolean hasAlphaChannel = map.getAlphaRaster() != null;
        int pixelLength = 3;
        if (hasAlphaChannel)
            pixelLength = 4;
        
        System.out.println(pixels[0]);
        boolean playerExists = false;
        boolean endGoalExists = false;
        //Note:(Jakub) FLOOR ONLY
        for(int i = -16; i < c_MapSize + 16; i++)
        {
            for(int j = -16; j < c_MapSize + 16; j++)
            {
                renderables.add(new Floor(i * Floor.GetSize(), j * Floor.GetSize()));
            }
        }

        //Note:(Jakub) PLAYER, KEYS, ENEMIES, DOORS ETC.
        for(int i = 0; i < c_MapSize; i++)
        {
            for(int j = 0; j < c_MapSize; j++)
            {
                int pos = (j * pixelLength * map.getWidth()) + (i * pixelLength);
                short rgb[] = new short[4];
                if (hasAlphaChannel)
                    rgb[3] = (short) (pixels[pos++] & 0xFF); // Alpha
                rgb[2] = (short) (pixels[pos++] & 0xFF); // Blue
                rgb[1] = (short) (pixels[pos++] & 0xFF); // Green
                rgb[0] = (short) (pixels[pos++] & 0xFF); // Red
                if(rgb[0] == 136 && rgb[1] == 0 && rgb[2] == 21)
                {
                    renderables.add(new Wall(i * Wall.GetSize(), j * Wall.GetSize()));
                }
                else if(rgb[0] == 255 && rgb[1] == 174 && rgb[2] == 201)
                {
                    renderables.add(new Treasure(i * Treasure.GetSize(), j * Treasure.GetSize()));
                }
                else if(rgb[0] == 255 && rgb[1] == 127 && rgb[2] == 39)
                {
                    renderables.add(new Key(i * Key.GetSize(), j * Key.GetSize()));
                }
                else if(rgb[0] == 0 && rgb[1] == 162 && rgb[2] == 232)
                {
                    renderables.add(new Door(i * Door.GetSize(), j * Door.GetSize()));
                }
                else if(rgb[0] == 50 && rgb[1] == 40 && rgb[2] == 130)
                {
                    renderables.add(new Trap(i * Trap.GetSize(), j * Trap.GetSize()));
                }
                else if(rgb[0] == 115 && rgb[1] == 251 && rgb[2] == 253)
                {
                    renderables.add(new Enemy(i * Enemy.GetSize(), j * Enemy.GetSize()));
                }
                else if(rgb[0] == 255 && rgb[1] == 242 && rgb[2] == 0)
                {
                    if(!endGoalExists)
                    {
                        renderables.add(new EndGoal(i * Player.GetSize(), j * Player.GetSize()));
                        endGoalExists = true;
                    }                    
                }
                else if(rgb[0] == 34 && rgb[1] == 177 && rgb[2] == 76)
                {
                    if(!playerExists)
                    {
                        renderables.add(new Player(i * Player.GetSize(), j * Player.GetSize()));
                        playerExists = true;
                    }
                }

            }
        }
        return renderables;
   }
   boolean NextLevel()
   {
       m_Level += 1;
       m_Level %= 3;
       if(m_Level == 0)
           return false;
       return true;
   }
}
