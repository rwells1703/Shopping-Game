package check.out.game.desktop;

import check.out.game.maingame.ConstShop;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import check.out.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.forceExit = false;

		if (ConstShop.FULLSCREEN) {
			config.fullscreen = true;
			config.height = 768;
			config.width = 1366;
		}

		new LwjglApplication(new MyGdxGame(), config);

	}
}
