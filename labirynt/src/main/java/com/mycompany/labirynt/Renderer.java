package com.mycompany.labirynt;
import java.util.ArrayList;
import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Raylib.*;
import com.raylib.Jaylib.Color;


public class Renderer {
    private int m_WindowWidth = 0;
    private int m_WindowHeight = 0;
    private int m_TargetFPS = 60;
    private ArrayList<Renderable> m_Renderables = new ArrayList<>();
    private PointsSystem m_PointsSystem = new PointsSystem(10, 10);
    private boolean m_ShouldClose = false;
    private boolean m_ShouldChangeLevel = false;
    public ArrayList<Renderable> getRenderables() {
        return m_Renderables;
    }
    public Renderer(int width, int height, int targetFps)
    {
        m_WindowWidth = width;
        m_WindowHeight = height;
        m_TargetFPS = targetFps;
    }

    public void Init()
    {
        InitWindow(m_WindowWidth, m_WindowHeight, "Labirynt");
        SetTargetFPS(m_TargetFPS);
    }
    public void PreRender(Camera camera)
    {
        BeginDrawing();
        ClearBackground(RAYWHITE);
        BeginMode2D(camera.GetNativeCamera());
    }
    public void Render(Camera camera)
    {
        PreRender(camera);
        for(int i = 0; i < m_Renderables.size(); i++)
        {
            m_Renderables.get(i).Render();
        }
        PostRender();
    }
    public void PostRender()
    {
        EndMode2D();
        //DrawFPS(10, 10);
        Player player = GetPlayer();
        if (player != null) {
            // Define the color BLACK
            Color BLACK = new Color(255, 255, 255, 255);
            DrawText("Health: " + player.GetHealth(), 40, 50, 20, BLACK);
        }
        m_PointsSystem.Render();

        EndDrawing();
    }
    public void Shutdown()
    {
        ClearRenderables();
        m_Renderables = null;
        CloseWindow();
    }
    public void PushRenderable(Renderable renderable)
    {
        m_Renderables.add(renderable);
    }
    public void RemoveRenderable(int index)
    {
        m_Renderables.remove(index);
    }
    public void SetRenderables(ArrayList<Renderable> renderables)
    {
        m_Renderables = renderables;
    }
    public void ClearRenderables()
    {
        m_Renderables.clear();
    }
    public Player GetPlayer()
    {
        for(int i = 0; i < m_Renderables.size(); i++)
        {
            if (m_Renderables.get(i) instanceof Player)
            {
                return (Player)m_Renderables.get(i);        
            }
        }
        return null;
    }


    public void CheckForCollisions()
    {
        Player p = GetPlayer();
        for(int i = 0; i < m_Renderables.size(); i++)
        {
            //Note:(Jakub) AABB Collisions for Walls
            if (m_Renderables.get(i) instanceof Wall)
            {
                Wall wall = (Wall)m_Renderables.get(i);
                if (
                  p.GetX() < wall.GetX() + Wall.GetSize() &&
                  p.GetX() + Player.GetSize() > wall.GetX() &&
                  p.GetY() < wall.GetY() + Wall.GetSize() &&
                  p.GetY() + Player.GetSize() > wall.GetY()) 
                {
                  p.BackToLastPosition();
                }
            }
            else if (m_Renderables.get(i) instanceof Trap) {
                Trap trap = (Trap) m_Renderables.get(i);
                if (p.GetX() < trap.GetX() + Trap.GetSize() &&
                        p.GetX() + Player.GetSize() > trap.GetX() &&
                        p.GetY() < trap.GetY() + Trap.GetSize() &&
                        p.GetY() + Player.GetSize() > trap.GetY()) {
                    if (!trap.HasPlayerCollided()) {
                        trap.PlayerCollision();
                        //player.hitByTrap();
                        p.takeDamage(5);
                        if (!p.isAlive()) {
                            m_ShouldClose = true;
                        }
                    }
                } else {

                    trap.ResetCollision();
                }
            }
            else if (m_Renderables.get(i) instanceof Treasure)
            {
                Treasure treasure = (Treasure)m_Renderables.get(i);
                if (
                  p.GetX() < treasure.GetX() + Treasure.GetSize() &&
                  p.GetX() + Player.GetSize() > treasure.GetX() &&
                  p.GetY() < treasure.GetY() + Treasure.GetSize() &&
                  p.GetY() + Player.GetSize() > treasure.GetY())
                {
                    m_PointsSystem.AddPoints(10);
                    treasure.Destroy();
                    m_Renderables.remove(treasure);
                }
            }
            else if (m_Renderables.get(i) instanceof Key)
            {
                Key key = (Key)m_Renderables.get(i);
                if (
                  p.GetX() < key.GetX() + Key.GetSize() &&
                  p.GetX() + Player.GetSize() > key.GetX() &&
                  p.GetY() < key.GetY() + Key.GetSize() &&
                  p.GetY() + Player.GetSize() > key.GetY())
                {
                    p.CollectKey();
                    key.Destroy();
                    m_Renderables.remove(key);
                }
            }
            else if (m_Renderables.get(i) instanceof Door)
            {
                Door door = (Door)m_Renderables.get(i);
                if (
                  p.GetX() < door.GetX() + Door.GetSize() &&
                  p.GetX() + Player.GetSize() > door.GetX() &&
                  p.GetY() < door.GetY() + Door.GetSize() &&
                  p.GetY() + Player.GetSize() > door.GetY()) 
                {
                    if(p.HasKey())
                    {
                        door.Destroy();
                        m_Renderables.remove(door);
                    }
                    else
                    {
                        p.BackToLastPosition();                        
                    }
                }
            }
            else if (m_Renderables.get(i) instanceof EndGoal)
            {
                EndGoal endGoal = (EndGoal)m_Renderables.get(i);
                if (
                  p.GetX() < endGoal.GetX() + EndGoal.GetSize() &&
                  p.GetX() + Player.GetSize() > endGoal.GetX() &&
                  p.GetY() < endGoal.GetY() + EndGoal.GetSize() &&
                  p.GetY() + Player.GetSize() > endGoal.GetY())
                {
                    m_ShouldChangeLevel = true;
                }
            }
            else if (m_Renderables.get(i) instanceof Enemy) {
                Enemy enemy = (Enemy) m_Renderables.get(i);

                if (p.GetX() < enemy.GetX() + Enemy.GetSize() &&
                        p.GetX() + Player.GetSize() > enemy.GetX() &&
                        p.GetY() < enemy.GetY() + Enemy.GetSize() &&
                        p.GetY() + Player.GetSize() > enemy.GetY()) {
                    // Handle player-enemy collision
                    p.takeDamage(1); // Inflict damage to player
                    if (!p.isAlive()) {
                        m_ShouldClose = true;
                    }
                    if (!enemy.HasPlayerCollided()) {
                        enemy.PlayerCollision();
                        //player.hitByTrap(); etc.
                        p.takeDamage(1);


                    }
                } else {
                    enemy.ResetCollision();
                }
            }

        }
        for (Renderable renderable : m_Renderables) {
            if (renderable instanceof Enemy) {
                Enemy enemy = (Enemy) renderable;
                for (Renderable wallRenderable : m_Renderables) {
                    if (wallRenderable instanceof Wall) {
                        Wall wall = (Wall) wallRenderable;
                        if (enemy.GetX() < wall.GetX() + Wall.GetSize() &&
                                enemy.GetX() + Enemy.GetSize() > wall.GetX() &&
                                enemy.GetY() < wall.GetY() + Wall.GetSize() &&
                                enemy.GetY() + Enemy.GetSize() > wall.GetY()) {
                            enemy.BackToLastPosition();
                        }
                    }
                }
            }
        }
    }
    public PointsSystem GetPointsSystem()
    {
        return m_PointsSystem;
    }
    public boolean ShouldClose()
    {
        return m_ShouldClose;
    }
    public boolean ShouldChangeLevel()
    {
        return m_ShouldChangeLevel;
    }
    public void FlipShouldChangeLevel()
    {
        m_ShouldChangeLevel = !m_ShouldChangeLevel;
    }
}
