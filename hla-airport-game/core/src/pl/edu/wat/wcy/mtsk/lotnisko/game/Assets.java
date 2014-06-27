package pl.edu.wat.wcy.mtsk.lotnisko.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {
	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	private AssetManager assetManager;
	
	// tlo lotniska
	public Texture lotniskoBackground;
	
	// samolot
	public Texture samolotIMG;
	
	// czcionka
	public AssetFonts fonts;
	
	// prywatny konstruktor oznacza, ze klasa jest Singletonem - nie mozna jej inicjalizowac z innych klas
	private Assets() {}
	
	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		assetManager.setErrorListener(this);
		
		assetManager.load("images/lotnisko-bg.png", Texture.class);
		assetManager.load("images/samolot.png", Texture.class);
		
		assetManager.finishLoading();
		
		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		
		for(String a : assetManager.getAssetNames()) {
			Gdx.app.debug(TAG, "asset: " + a);
		}
		
		// utworz czcionki
		fonts = new AssetFonts("fonts/arial_pl.fnt", false);
		
		// zaladuj textury
		lotniskoBackground = assetManager.get("images/lotnisko-bg.png", Texture.class);
		samolotIMG = assetManager.get("images/samolot.png", Texture.class);
	}
	
	public class AssetFonts {
		public final BitmapFont defaultSmall;
		public final BitmapFont defaultNormal;
		public final BitmapFont defaultBig;
		
		public AssetFonts(String fontName, boolean flip) {
			defaultSmall = new BitmapFont(Gdx.files.internal(fontName), flip);
			defaultNormal = new BitmapFont(Gdx.files.internal(fontName), flip);
			defaultBig = new BitmapFont(Gdx.files.internal(fontName), flip);
			
			// rozmiary czcionek
			defaultSmall.setScale(0.75f);
			defaultNormal.setScale(0.95f);
			defaultBig.setScale(1.5f);
			
			// wlacz 'linear-filtering' by wygladzic czcionki
			defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}
	
	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);
	}
	@Override
	public void dispose() {
		assetManager.dispose();
	}

}
