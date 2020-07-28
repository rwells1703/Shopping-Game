package check.out.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import check.out.game.example.stellar.SupernovaExample;
import fernebon.core.base.Nebula;
import fernebon.core.base.Supernova;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Nebula nebula;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		Supernova supernova=new SupernovaExample();
		nebula=supernova.generateNebula();
		supernova.dispose();
	}

	@Override
	public void render () {
		nebula.update(Gdx.graphics.getDeltaTime());

		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		nebula.dispose();
	}
}
