package com.mycompany.labirynt;
import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

public class PointsSystem extends Renderable
{
    private static final int c_Size = 32;
    private int m_Points;
    public PointsSystem(int x, int y)
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
        DrawText("Points: " + Integer.toString(m_Points), m_PosX, m_PosY, c_Size, WHITE);
    }
    @Override
    public void Destroy()
    {
        
    }
    public void AddPoints(int numberOfPoints)
    {
        m_Points += numberOfPoints;
    }
    public void ResetPoints()
    {
        m_Points = 0;
    }
    public static int GetSize()
    {
        return c_Size;
    }
}
