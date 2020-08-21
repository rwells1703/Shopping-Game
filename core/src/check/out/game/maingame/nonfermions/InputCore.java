package check.out.game.maingame.nonfermions;

import check.out.game.maingame.ConstShop;
import check.out.game.maingame.fermions.Projectile;
import com.badlogic.gdx.InputProcessor;

public class InputCore implements InputProcessor {

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        Projectile.SELECTED_TYPE += (amount>0?1:-1);
        Projectile.SELECTED_TYPE = Projectile.SELECTED_TYPE%ConstShop.HOTBAR_MAX;
        return true;
    }
}
