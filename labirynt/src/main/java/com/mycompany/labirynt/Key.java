package com.mycompany.labirynt;
import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

public class Key extends Renderable
{
    private static final int c_Size = 32;
    public static Texture s_KeyTexture = LoadTexture("Key.png");
    public Key(int x, int y)
    {
        Create(x, y);
    }
    @Override
    public void Create(int x, int y)
    {
        m_PosX = x;
        m_PosY = y;
    }
    @Override
    public void Render()
    {
        DrawTexture(s_KeyTexture, m_PosX, m_PosY, WHITE);
    }
    @Override
    public void Destroy()
    {
        
    }
    public static int GetSize()
    {
        return c_Size;
    }
}
