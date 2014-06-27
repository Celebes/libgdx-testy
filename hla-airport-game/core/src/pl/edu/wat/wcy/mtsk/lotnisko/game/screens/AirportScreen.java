package pl.edu.wat.wcy.mtsk.lotnisko.game.screens;

import pl.edu.wat.wcy.mtsk.lotnisko.game.WorldController;
import pl.edu.wat.wcy.mtsk.lotnisko.game.WorldRenderer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AirportScreen extends AbstractGameScreen {
	
	SpriteBatch batch;
	Texture img;
	
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	
	private boolean paused;

	public AirportScreen(Game game) {
		super(game);
	}

	@Override
	public InputProcessor getInputProcessor() {
		return worldController;
	}

	@Override
	public void render(float deltaTime) {
		if (!paused) {
			worldController.update(deltaTime);
		}
		
		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		worldRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	public void show() {
		worldController = new WorldController(game);
		worldRenderer = new WorldRenderer(worldController);
	}

	@Override
	public void hide() {
		worldRenderer.dispose();
	}
	
	@Override
	public void resume() {
		super.resume();
		paused = false;
	}

	@Override
	public void pause() {
		paused = true;
	}

}
