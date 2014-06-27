package pl.edu.wat.wcy.mtsk.lotnisko.game.objects;

import pl.edu.wat.wcy.mtsk.lotnisko.game.Assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Samolot extends AbstractGameObject {
	
	private Texture samolotTexture;
	
	public Samolot() {
		init();
	}

	private void init() {
		dimension.set(1.0f, 1.0f);
		bounds.set(0, 0, dimension.x, dimension.y);
		samolotTexture = Assets.instance.samolotIMG;
	}

	@Override
	public void render(SpriteBatch batch) {
		
		if(AKTUALNY_STAN != 4) {
			batch.draw(samolotTexture, position.x, position.y, origin.x, origin.y, samolotTexture.getWidth(), samolotTexture.getHeight(), 1.0f, 1.0f,
					rotation, 0, 0, samolotTexture.getWidth(), samolotTexture.getHeight(), false, false);
		}
		
		
		//batch.draw(samolotTexture, position.x, position.y);
	}

}
