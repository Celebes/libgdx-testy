package pl.edu.wat.wcy.mtsk.lotnisko.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import pl.edu.wat.wcy.mtsk.lotnisko.game.LotniskoGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "MTSK | WAT | 2014 | GURNIAK + JEDYNAK + KOTOWSKI + KIELAN";
		cfg.width = 1024;
		cfg.height = 1024;
		
		cfg.resizable = false;
		
		new LwjglApplication(new LotniskoGame(), cfg);
	}
}
