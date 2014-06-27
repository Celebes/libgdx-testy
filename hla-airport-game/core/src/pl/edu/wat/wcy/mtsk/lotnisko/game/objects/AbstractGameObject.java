package pl.edu.wat.wcy.mtsk.lotnisko.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractGameObject {
	
	public int AKTUALNY_STAN = -1;
	boolean ustawionoNowyPunkt = false;
	Vector2[] punktyLadowania = new Vector2[] { };

	public Vector2 position;				// aktualna pozycja
	public Vector2 destination;				// punkt w ktory ma sie przemiescic
	public Vector2 dimension;
	public Vector2 origin;
	public Vector2 scale;
	
	public float rotation;
	public float angle;
	
	public Vector2 velocity;
	public Vector2 direction;
	public Vector2 movement;

	public Rectangle bounds;				// obramowanie do kolizji
	
	boolean moving = false;
	float epsilon = 0.0001f;
	float speed = 300f;

	public AbstractGameObject() {
		position = new Vector2();
		position.set(512.0f + 113.0f, -512.0f - 93.0f - 32.0f);		// poczatkowe wspolrzedne wszystkich samolotow
		
		dimension = new Vector2(1, 1);
		origin = new Vector2(16, 16);
		scale = new Vector2(1, 1);
		rotation = 0;
		velocity = new Vector2();
		direction = new Vector2();
		destination = new Vector2();
		movement = new Vector2();

		bounds = new Rectangle();
	}
	
	public void update (float deltaTime) {
		
		// aktualizuj stan
		
		if(ustawionoNowyPunkt == false) {
			switch(AKTUALNY_STAN) {
			case -1:
				ustawionoNowyPunkt = true;
				setDestination(new Vector2(-512.0f + 16.0f, 512.0f - 184.0f -16.0f));
				AKTUALNY_STAN++;
				break;
			case 0:
				ustawionoNowyPunkt = true;
				setDestination(new Vector2(-512.0f + 16.0f + 32.0f, 512.0f - 184.0f -16.0f + 32.0f));
				AKTUALNY_STAN++;
				break;
			case 1:
				ustawionoNowyPunkt = true;
				setDestination(new Vector2(-512.0f + 16.0f + 32.0f + 106.0f, 512.0f - 184.0f -16.0f + 32.0f + 12.0f));
				AKTUALNY_STAN++;
				break;
			case 2:
				ustawionoNowyPunkt = true;
				setDestination(new Vector2(-512.0f + 16.0f + 32.0f + 106.0f + 146.0f, 512.0f - 184.0f -16.0f + 32.0f + 12.0f - 95.0f));
				AKTUALNY_STAN++;
				break;
			case 3:
				ustawionoNowyPunkt = true;
				setDestination(new Vector2(-512.0f + 16.0f + 32.0f + 106.0f + 146.0f + 33.0f, 512.0f - 184.0f -16.0f + 32.0f + 12.0f - 95.0f + 44f));
				AKTUALNY_STAN++;
				break;
			case 5:
				ustawionoNowyPunkt = true;
				setDestination(new Vector2(-512.0f + 16.0f + 32.0f + 106.0f + 146.0f, 512.0f - 184.0f -16.0f + 32.0f + 12.0f - 95.0f));
				AKTUALNY_STAN++;
				break;
			case 6:
				ustawionoNowyPunkt = true;
				setDestination(new Vector2(-512.0f + 16.0f + 32.0f + 106.0f - 10.0f, 512.0f - 184.0f -16.0f + 32.0f + 12.0f));
				AKTUALNY_STAN++;
				break;
			case 7:
				ustawionoNowyPunkt = true;
				setDestination(new Vector2(-512.0f + 16.0f + 32.0f + 106.0f + 167.0f - 10.0f, 512.0f - 184.0f -16.0f + 32.0f + 12.0f - 877.0f));
				AKTUALNY_STAN++;
				break;
			}
		}		
		
		if(moving == true) {
			
			updateRotation();
			
			direction.set(destination).sub(position).nor();
			velocity.set(direction).scl(speed);
			movement.set(velocity).scl(deltaTime);
			
			if(position.dst2(destination) > movement.len2()) {
				position.add(movement);
			} else {
				position.set(destination);
				moving = false;
				
				if(AKTUALNY_STAN != 4 || AKTUALNY_STAN != 8) {
					ustawionoNowyPunkt = false;
				}
				
				if(AKTUALNY_STAN == 8) {
					AKTUALNY_STAN++;
				}
			}
		}

	}
	
	public abstract void render (SpriteBatch batch);
	
	public void setDestination(Vector2 newDestination) {
		moving = true;
		destination = newDestination;
		
		updateRotation();
	}
	
	private void updateRotation() {
		angle = (float) Math.atan2(destination.y - position.y, destination.x - position.x);
		angle *= (180.0 / Math.PI);
		
		if (angle < 0) {
			angle = 360 - (-angle);
		}
		
		rotation = -90 + angle;
	}

}