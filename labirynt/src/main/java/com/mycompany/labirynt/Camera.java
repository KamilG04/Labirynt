package com.mycompany.labirynt;
import static com.raylib.Raylib.*;

public class Camera {
    private Camera2D m_Camera = null;
    public Camera(float x, float y, float zoom)
    {
        m_Camera = new Camera2D();
        m_Camera.zoom(zoom);
        m_Camera.target().x(x).y(y);
    }
    public void SetTarget(float x, float y)
    {
        m_Camera.target().x(x).y(y);
    }
    public void SetOffset(float x, float y)
    {
        m_Camera.offset().x(x).y(y);
    }
    public Vector2 GetTarget()
    {
        return m_Camera.target();
    }
    public void Destroy()
    {
        m_Camera = null;
    }
    public Camera2D GetNativeCamera()
    {
        return m_Camera;
    }
}
