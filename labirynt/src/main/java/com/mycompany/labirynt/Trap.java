package com.mycompany.labirynt;

import static com.raylib.Jaylib.WHITE;
import static com.raylib.Raylib.*;

/**
 * Represents a trap in the game.
 */
public class Trap extends Renderable {
    private int m_PosX = 0;
    private int m_PosY = 0;
    private static final int c_Size = 32;
    private boolean m_PlayerCollided = false; // Indicates if the player has collided with the trap

    /** The texture of the trap. */
    public static Texture s_TrapTexture = LoadTexture("Trap.png");

    /**
     * Constructor for creating a trap object.
     *
     * @param x The x-coordinate of the trap.
     * @param y The y-coordinate of the trap.
     */
    public Trap(int x, int y) {
        Create(x, y);
    }

    @Override
    public void Create(int x, int y) {
        m_PosX = x;
        m_PosY = y;
    }

    @Override
    public void Render() {
        DrawTexture(s_TrapTexture, m_PosX, m_PosY, WHITE);
    }

    @Override
    public void Destroy() {
    }

    /**
     * Get the size of the trap.
     *
     * @return The size of the trap.
     */
    public static int GetSize() {
        return c_Size;
    }

    /**
     * Get the x-coordinate of the trap.
     *
     * @return The x-coordinate of the trap.
     */
    public int GetX() {
        return m_PosX;
    }

    /**
     * Get the y-coordinate of the trap.
     *
     * @return The y-coordinate of the trap.
     */
    public int GetY() {
        return m_PosY;
    }

    /**
     * Check if the player has collided with the trap.
     *
     * @return True if the player has collided with the trap, otherwise false.
     */
    public boolean HasPlayerCollided() {
        return m_PlayerCollided;
    }

    /**
     * Set the flag indicating that the player has collided with the trap.
     */
    public void PlayerCollision() {
        m_PlayerCollided = true;
    }

    /**
     * Reset the collision state of the trap.
     */
    public void ResetCollision() {
        m_PlayerCollided = false;
    }
}
