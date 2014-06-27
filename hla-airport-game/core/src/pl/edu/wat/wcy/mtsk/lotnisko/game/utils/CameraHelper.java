package pl.edu.wat.wcy.mtsk.lotnisko.game.utils;

import pl.edu.wat.wcy.mtsk.lotnisko.game.objects.AbstractGameObject;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CameraHelper {
	private static final String TAG = CameraHelper.class.getName();
	
	private final float MAX_ZOOM_IN = 16.0f;
	private final float MAX_ZOOM_OUT = 32.0f;
	
	private float POSITION_X_LIMIT = 0.0f;
	private float POSITION_Y_LIMIT = 0.0f;
	
	private Vector2 position;
	private float zoom;
	
	public CameraHelper() {
		position = new Vector2();
		zoom = 32.0f;
	}
	
	public void applyTo(OrthographicCamera camera) {
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}
	
	public void setPosition(float x, float y) {
		
		if(x >= -POSITION_X_LIMIT && x <= POSITION_X_LIMIT) {
			this.position.x = x;
		} else {
			
			if(x < -POSITION_X_LIMIT) {
				this.position.x = -POSITION_X_LIMIT;
			} else {
				this.position.x = POSITION_X_LIMIT;
			}
		}
		
		if(y >= -POSITION_Y_LIMIT && y <= POSITION_Y_LIMIT) {
			this.position.y = y;
		} else {
			if(y < -POSITION_Y_LIMIT) {
				this.position.y = -POSITION_X_LIMIT;
			} else {
				this.position.y = POSITION_X_LIMIT;
			}
		}
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public void addZoom(float amount) {
		setZoom(zoom + amount);
		updateLimits();
		setPosition(position.x, position.y);
	}
	
	public void setZoom(float zoom) {
		this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
		updateLimits();
		setPosition(position.x, position.y);
	}
	
	public float getZoom() {
		return zoom;
	}
	
	private void updateLimits() {
		if(Math.abs(zoom - MAX_ZOOM_IN) < 0.00001f) {
			POSITION_X_LIMIT = POSITION_Y_LIMIT = 256.0f;
		} else if(Math.abs(zoom - MAX_ZOOM_OUT) < 0.00001f) {
			POSITION_X_LIMIT = POSITION_Y_LIMIT = 0.0f;
		} else {
			POSITION_X_LIMIT = POSITION_Y_LIMIT = (MAX_ZOOM_OUT - zoom) * 16.0f;
		}
	}
}