package com.mycompany.labirynt;

/**
 * Class representing the health system in the labyrinth application.
 */
public class HealthSystem {
    private int m_Health;

    /**
     * Constructs a new HealthSystem with the specified initial health.
     *
     * @param initialHealth The initial health value.
     */
    public HealthSystem(int initialHealth) {
        m_Health = initialHealth;
    }

    /**
     * Inflicts damage on the entity by the specified amount.
     *
     * @param amount The amount of damage to be inflicted.
     */
    public void takeDamage(int amount) {
        m_Health -= amount;
        // Check if health has dropped below zero
        if (m_Health < 0) {
            m_Health = 0;
        }
    }

    /**
     * Restores health to the entity by the specified amount.
     *
     * @param amount The amount of health to be restored.
     */
    public void Heal(int amount) {
        m_Health += amount;
        // Limit health to maximum value
        // Here you can set the maximum health if required
    }

    /**
     * Returns the current health of the entity.
     *
     * @return The current health value.
     */
    public int GetHealth() {
        return m_Health;
    }

    /**
     * Checks if the entity is still alive based on its current health.
     *
     * @return {@code true} if the entity's health is greater than 0, {@code false} otherwise.
     */
    public boolean isAlive() {
        return m_Health > 0;
    }
}
