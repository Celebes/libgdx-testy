package pl.edu.wat.wcy.mtsk.lotnisko.game;

import pl.edu.wat.wcy.mtsk.lotnisko.game.objects.Samolot;
import pl.edu.wat.wcy.mtsk.lotnisko.game.utils.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class WorldRenderer implements Disposable {
	public static final String TAG = WorldRenderer.class.getName();

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private WorldController worldController;
	
	private Texture background;
	
	public WorldRenderer(WorldController worldController) {
		this.worldController = worldController;
		init();
	}
	
	private void init() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		
		this.worldController.setWorldRenderer(this);
		
		background = Assets.instance.lotniskoBackground;
	}
	
	public void render() {
		batch.begin();
		
		renderWorld(batch);
		renderGuiFpsCounter(batch);
		renderGuiSamolotyCounter(batch);
		
		batch.end();
	}
	
	private void renderWorld(SpriteBatch batch) {
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);

		batch.draw(background, -(background.getWidth() / 2), -(background.getHeight() / 2));
		
		for(Samolot s : worldController.samoloty) {
			s.render(batch);
		}

	}
	
	private void renderGuiFpsCounter(SpriteBatch batch) {
		float x = 512 - 125;
		float y = 512 - 10;
		int fps = Gdx.graphics.getFramesPerSecond();
		BitmapFont fpsFont = Assets.instance.fonts.defaultBig;
		if (fps >= 45) {
			// 45 or more FPS show up in green
			fpsFont.setColor(0, 1, 0, 1);
		} else if (fps >= 30) {
			// 30 or more FPS show up in yellow
			fpsFont.setColor(1, 1, 0, 1);
		} else {
			// less than 30 FPS show up in red
			fpsFont.setColor(1, 0, 0, 1);
		}
		fpsFont.draw(batch, "FPS: " + fps, x, y);
		fpsFont.setColor(1, 1, 1, 1); // white
	}
	
	private void renderGuiSamolotyCounter(SpriteBatch batch) {
		float x = -512 + 10;
		float y = 512 - 10;
		int fps = Gdx.graphics.getFramesPerSecond();
		BitmapFont fpsFont = Assets.instance.fonts.defaultBig;
		if (fps >= 45) {
			// 45 or more FPS show up in green
			fpsFont.setColor(0, 1, 0, 1);
		} else if (fps >= 30) {
			// 30 or more FPS show up in yellow
			fpsFont.setColor(1, 1, 0, 1);
		} else {
			// less than 30 FPS show up in red
			fpsFont.setColor(1, 0, 0, 1);
		}
		fpsFont.draw(batch, "LICZBA SAMOLOTÓW: " + WorldController.LICZBA_SAMOLOTOW, x, y);
		fpsFont.setColor(1, 1, 1, 1); // white
	}
	
	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

}
