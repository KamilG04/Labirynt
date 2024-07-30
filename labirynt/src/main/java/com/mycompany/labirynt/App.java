package com.mycompany.labirynt;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import javax.imageio.ImageIO;
import static com.raylib.Raylib.*;
import java.io.File;

public class App {
    private String[] m_Args = null;
    private Camera m_Camera = null;
    private Renderer m_Renderer = null;
    private int WindowWidth = 800;
    private int WindowHeight = 450;
    private SaveLoadSystem m_SaveLoadSystem = new SaveLoadSystem();
    
    public App(String[] args)
    {
        m_Args = args;
        m_Renderer = new Renderer(800, 450, 60);
        m_Camera = new Camera(0.0f, 0.0f, 1.0f);
    }
    public void Run() throws IOException
    {
        Init();
        MainLoop();
        Shutdown();
    }
    public void Init()
    {
        m_Renderer.Init();
        m_Renderer.SetRenderables(m_SaveLoadSystem.LoadGame());
        m_Camera.SetOffset((WindowWidth / 2) - (Player.GetSize() / 2), (WindowHeight / 2) - (Player.GetSize() / 2));
    }
    public void MainLoop() throws IOException
    {
        boolean correct = true;
        while (!WindowShouldClose() && correct)
        {
            m_Renderer.Render(m_Camera);
            m_Renderer.GetPlayer().Move();
            for (Renderable renderable : m_Renderer.getRenderables()) {
                if (renderable instanceof Enemy) {
                    ((Enemy) renderable).Move(); // Enemy moves
                }
            }
            if (m_Renderer.GetPlayer().GetHealth() <= 0) {
                correct = false;
            }

            m_Renderer.CheckForCollisions();

            m_Camera.SetTarget(m_Renderer.GetPlayer().GetX(), m_Renderer.GetPlayer().GetY());

            if(m_Renderer.ShouldChangeLevel())
            {
                m_Renderer.ClearRenderables();
                m_Renderer.GetPointsSystem().ResetPoints();
                correct = m_SaveLoadSystem.NextLevel();
                m_SaveLoadSystem.SaveGame();
                m_Renderer.SetRenderables(m_SaveLoadSystem.LoadGame());
                m_Camera.SetOffset((WindowWidth / 2) - (Player.GetSize() / 2), (WindowHeight / 2) - (Player.GetSize() / 2));
                m_Renderer.FlipShouldChangeLevel();
            }
        }
    }
    public void Shutdown()
    {
        m_Renderer.Shutdown();
        m_Renderer = null;
        UnloadTexture(Wall.s_WallTexture);
        UnloadTexture(Floor.s_FloorTexture);
        UnloadTexture(Player.s_PlayerTexture);
        m_Args = null;
    }
}
