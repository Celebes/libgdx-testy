package pl.edu.wat.wcy.mtsk.lotnisko.game;

import pl.edu.wat.wcy.mtsk.lotnisko.game.screens.AirportScreen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

public class LotniskoGame extends Game {

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Assets.instance.init(new AssetManager());
		
		setScreen(new AirportScreen(this));
	}
	
}
