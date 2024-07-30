package com.mycompany.labirynt;

import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

/**
 * Class representing an enemy entity in the labyrinth application.
 */
public class Enemy extends Renderable {
    private int m_PosX;
    private int m_PosY;
    private int m_LastPosX;
    private int m_LastPosY;
    private int m_Direction;
    private static final int c_Size = 32;
    public static Texture s_EnemyTexture = LoadTexture("Enemy.png");
    private boolean m_PlayerCollided = false;

    /**
     * Constructor for the Enemy class.
     * @param x The initial x-coordinate of the enemy.
     * @param y The initial y-coordinate of the enemy.
     */
    public Enemy(int x, int y) {
        m_PosX = x;
        m_PosY = y;
        m_LastPosX = x;
        m_LastPosY = y;
        m_Direction = (int) (Math.random() * 4); // Random initial direction
    }

    @Override
    public void Create(int x, int y) {
        m_PosX = x;
        m_PosY = y;
    }

    @Override
    public void Render() {
        DrawTexture(s_EnemyTexture, m_PosX, m_PosY, WHITE);
    }

    @Override
    public void Destroy() {
    }

    /**
     * Gets the size of the enemy.
     * @return The size of the enemy.
     */
    public static int GetSize() {
        return c_Size;
    }

    /**
     * Gets the x-coordinate of the enemy.
     * @return The x-coordinate of the enemy.
     */
    public int GetX() {
        return m_PosX;
    }

    /**
     * Gets the y-coordinate of the enemy.
     * @return The y-coordinate of the enemy.
     */
    public int GetY() {
        return m_PosY;
    }

    /**
     * Moves the enemy based on its current direction.
     */
    public void Move() {
        m_LastPosX = m_PosX;
        m_LastPosY = m_PosY;

        switch (m_Direction) {
            case 0:
                m_PosY -= 2.0f; // Up
                break;
            case 1:
                m_PosY += 2.0f; // Down
                break;
            case 2:
                m_PosX -= 2.0f; // Left
                break;
            case 3:
                m_PosX += 2.0f; // Right
                break;
        }
    }

    /**
     * Changes the direction of the enemy randomly.
     */
    public void ChangeDirection() {
        m_Direction = (int) (Math.random() * 4);
    }

    /**
     * Moves the enemy back to its last position and changes its direction.
     */
    public void BackToLastPosition() {
        m_PosX = m_LastPosX;
        m_PosY = m_LastPosY;
        ChangeDirection();
    }

    public boolean HasPlayerCollided() {
        return m_PlayerCollided;
    }

    public void PlayerCollision() {
        m_PlayerCollided = true;
    }

    public void ResetCollision() {
        m_PlayerCollided = false;
    }
}
