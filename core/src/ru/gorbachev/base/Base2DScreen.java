package ru.gorbachev.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.gorbachev.math.MatrixUtils;
import ru.gorbachev.math.Rect;
import ru.gorbachev.object.Background;

public class Base2DScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;

    private Rect screenBounds; // границы области рисования в пикселях
    private Rect worldBounds; // границы проекции мировых координат
    private Rect glBounds; // границы проэкции world - gl

    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;

    protected static TextureAtlas backgroundAtlas;

    private Vector2 touch;

    protected float screenWidth;
    protected float screenHeight;

    protected Background background;

    @Override
    public void show() {
        this.batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        this.screenBounds = new Rect();
        this.worldBounds = new Rect();
        this.glBounds = new Rect(0, 0, 1f, 1f);
        this.worldToGl = new Matrix4();
        this.screenToWorld = new Matrix3();
        this.touch = new Vector2();
        backgroundAtlas = new TextureAtlas(Gdx.files.internal("background.atlas"));
        screenHeight = 1000f;
        screenWidth = 1000f;
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize w = " + width + " h = " + height);
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width / (float) height;
        worldBounds.setHeight(screenHeight);
        worldBounds.setWidth(screenWidth *aspect);
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDown(touch, pointer);
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchUp(touch, pointer);
        return false;
    }

    private boolean touchUp(Vector2 touch, int pointer) {
        System.out.println("Screenx = " + touch);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        System.out.println("scrolled");
        return false;
    }
}
