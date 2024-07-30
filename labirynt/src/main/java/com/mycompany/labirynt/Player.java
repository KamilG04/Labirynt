package com.mycompany.labirynt;
import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

public class Player extends Renderable
{
    private int m_LastPosX = 0;
    private int m_LastPosY = 0;
    private int m_Points = 0;
    private boolean m_HasKey = false;
    private static final int c_Size = 32;
    public static Texture s_PlayerTexture = LoadTexture("Player1.png");
    private int m_Health = 100; // Initial player health
    private int m_TrapHits = 0; // Number of times hit by trap
    private float m_MoveAmount = 2.0f;

    public Player(int x, int y)
    {
        Create(x, y);
    }
    @Override
    public void Create(int x, int y)
    {
        m_PosX = x;
        m_PosY = y;
        m_LastPosX = m_PosX;
        m_LastPosY = m_PosY;  
    }
    @Override
    public void Render()
    {
        DrawTexture(s_PlayerTexture, m_PosX, m_PosY, WHITE);
    }
    @Override
    public void Destroy()
    {

    }
    /**
     * Gets the X position of the player.
     *
     * @return The X position of the player.
     */
    public int GetX() {
        return m_PosX;
    }

    /**
     * Gets the Y position of the player.
     *
     * @return The Y position of the player.
     */
    public int GetY() {
        return m_PosY;
    }
    public static int GetSize()
    {
        return c_Size;
    }
    public void CollectKey()
    {
        m_HasKey = true;
    }
    public void DrawPoints()
    {
        //DrawText(RLGL_VERSION, KEY_I, c_Size, c_Size, WHITE);
    }

    /**
     * Inflicts damage on the player by the specified amount.
     *
     * @param amount The amount of damage to inflict.
     */
    public void takeDamage(int amount) {
        m_Health -= amount;
    }
    /**
     * Increases the player's health by the specified amount.
     *
     * @param amount The amount of health to increase.
     */
    public void Heal(int amount) {
        m_Health += amount;
    }
    /**
     * Gets the current health of the player.
     *
     * @return The current health of the player.
     */
    public int GetHealth() {
        return m_Health;
    }
    /**
     * Checks if the player is still alive.
     *
     * @return True if the player's health is greater than zero, otherwise false.
     */
    public boolean isAlive() {
        return m_Health > 0;
    }
    public boolean HasKey()
    {
        return m_HasKey;
    }
    public void Move()
    {
        m_LastPosX = m_PosX;
        m_LastPosY = m_PosY;
        if (IsKeyDown(KEY_RIGHT) || IsKeyDown(KEY_D)) m_PosX += m_MoveAmount;
        if (IsKeyDown(KEY_LEFT) || IsKeyDown(KEY_A)) m_PosX -= m_MoveAmount;
        if (IsKeyDown(KEY_UP) || IsKeyDown(KEY_W)) m_PosY -= m_MoveAmount;
        if (IsKeyDown(KEY_DOWN) || IsKeyDown(KEY_S)) m_PosY += m_MoveAmount;
    }
    public void BackToLastPosition()
    {
        m_PosX = m_LastPosX;
        m_PosY = m_LastPosY;
    }
}
