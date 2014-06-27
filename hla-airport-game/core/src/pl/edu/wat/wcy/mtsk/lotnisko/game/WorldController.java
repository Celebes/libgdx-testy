package pl.edu.wat.wcy.mtsk.lotnisko.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import pl.edu.wat.wcy.mtsk.lotnisko.game.objects.Samolot;
import pl.edu.wat.wcy.mtsk.lotnisko.game.utils.CameraHelper;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class WorldController extends InputAdapter {
	
	public static final String TAG = WorldController.class.getSimpleName();
	
	public static int LICZBA_SAMOLOTOW = 0;
	
	private WorldRenderer worldRenderer;
	public Game game;
	public List<Samolot> samoloty;
	public CameraHelper cameraHelper;
	
	// Detekcja kolizji
	private Rectangle r1 = new Rectangle();
	private Rectangle r2 = new Rectangle();
	
	Random random;
	
	public WorldController(Game game) {
		this.game = game;
		init();
	}

	private void init() {
		cameraHelper = new CameraHelper();
		samoloty = new ArrayList<Samolot>();
		random = new Random();
		Gdx.input.setInputProcessor(this);
	}
	
	public void update(float deltaTime) {
		handleDebugInput(deltaTime);
		
		for(Samolot s : samoloty) {
			s.update(deltaTime);
		}
		
		testCollisions();
		testDeletions();
	}
	
	// usuwa samoloty ktore odlecialy
	private void testDeletions() {
		Iterator<Samolot> iter = samoloty.iterator();
		while (iter.hasNext()) {
		    if (iter.next().AKTUALNY_STAN == 9) {
		        iter.remove();
		        LICZBA_SAMOLOTOW--;
		    }
		}
	}

	private void testCollisions() {
		if(samoloty.size() < 2) {
			return;
		}
		
		for(int i=0; i<samoloty.size(); i++) {
			for(int j=(i+1); j<samoloty.size(); j++) {
				Samolot s1 = samoloty.get(i);
				Samolot s2 = samoloty.get(j);
				
				r1.set(s1.position.x, s1.position.y, s1.bounds.width, s1.bounds.height);
				r2.set(s2.position.x, s2.position.y, s2.bounds.width, s2.bounds.height);
				
				if(!r1.overlaps(r2)) {
					continue;
				}
				
				onSamolotyCollision(s1, s2);
			}
		}
	}

	private void onSamolotyCollision(Samolot s1, Samolot s2) {
		Gdx.app.log(TAG, "Kolizja samolotow!");
	}

	private void handleDebugInput(float deltaTime) {
		if(Gdx.app.getType() != ApplicationType.Desktop) {
			return;
		}
		
		// sterowanie kamera
		float camMoveSpeed = 160 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			camMoveSpeed *= camMoveSpeedAccelerationFactor;
		}
		
		if(Gdx.input.isKeyPressed(Keys.A)) {
			moveCamera(-camMoveSpeed, 0);
		}
		
		if(Gdx.input.isKeyPressed(Keys.D)) {
			moveCamera(camMoveSpeed, 0);
		}
		
		if(Gdx.input.isKeyPressed(Keys.W)) {
			moveCamera(0, camMoveSpeed);
		}
		
		if(Gdx.input.isKeyPressed(Keys.S)) {
			moveCamera(0, -camMoveSpeed);
		}
		
		if(Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			cameraHelper.setPosition(0, 0);
		}
		
		// przyblizanie kamery
		float camZoomSpeed = 10 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		}
		
		if(Gdx.input.isKeyPressed(Keys.COMMA)) {
			cameraHelper.addZoom(camZoomSpeed);
		}
		
		if(Gdx.input.isKeyPressed(Keys.PERIOD)) {
			cameraHelper.addZoom(-camZoomSpeed);
		}
		
		if(Gdx.input.isKeyPressed(Keys.SLASH)) {
			cameraHelper.setZoom(1);
		}
	}
	
	@Override
	public boolean keyUp(int keycode) {
		
		if(keycode == Keys.SPACE) {
			utworzNowySamolot();
		}
		
		if(keycode == Keys.X) {
			wystartujSamolot();
		}
		
		if(keycode == Keys.T) {
			
			float x = random.nextFloat() * 1024.0f - 512.0f;
			float y = random.nextFloat() * 1024.0f - 512.0f;
			Vector2 newDestination = new Vector2(x, y);
			
			for(Samolot s : samoloty) {
				s.setDestination(newDestination);
			}
			
			System.out.println("Dodano nowy punkt docelowy dla samolotow: [" + x + "," + y + "]");
		}
		
		return false;
	}
	
	private void wystartujSamolot() {
		for(Samolot s : samoloty) {
			if(s.AKTUALNY_STAN == 4) {
				s.AKTUALNY_STAN = 5;
				break;
			}
		}
	}

	private void utworzNowySamolot() {
		Samolot s = new Samolot();
		//s.position = new Vector2(0, 0);
		
		samoloty.add(s);
		
		//System.out.println("Dodano samolot na pozycji: " + s.position);
		LICZBA_SAMOLOTOW++;
	}

	private void moveCamera(float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}
	
	public WorldRenderer getWorldRenderer() {
		return worldRenderer;
	}

	public void setWorldRenderer(WorldRenderer worldRenderer) {
		this.worldRenderer = worldRenderer;
	}

}
