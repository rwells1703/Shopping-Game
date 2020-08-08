package check.out.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import check.out.game.maingame.stellar.SupernovaShop;
import fernebon.core.base.Nebula;
import fernebon.core.base.Supernova;

public class MyGdxGame extends ApplicationAdapter {
	Nebula nebula;
	
	@Override
	public void create () {
		Supernova supernova=new SupernovaShop();
		nebula=supernova.generateNebula();
		supernova.dispose();
	}

	@Override
	public void render() {
		nebula.update(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
		nebula.dispose();
	}
}
