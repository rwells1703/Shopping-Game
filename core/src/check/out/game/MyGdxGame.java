package check.out.game;

import check.out.game.maingame.menus.MainMenu;
import check.out.game.maingame.menus.Menu;
import check.out.game.maingame.stellar.SupernovaShop;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import fernebon.core.base.Nebula;
import fernebon.core.base.Supernova;

public class MyGdxGame extends ApplicationAdapter {
    Nebula nebula;
    private Menu menu;
    public Skin skin;
    private GAME_STATE game_state;

    @Override
    public void create() {
        Supernova supernova = new SupernovaShop();
        nebula = supernova.generateNebula();
        skin = new Skin(Gdx.files.internal("skin/flat-earth/flat-earth-ui.json"));
        setGame_state(GAME_STATE.MAIN_MENU);
        supernova.dispose();
    }

    @Override
    public void render() {
        if(game_state==GAME_STATE.RUNNING) {
            nebula.update(Gdx.graphics.getDeltaTime());
        }
        else {
            if(nebula==null) {
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            }
            else {
                nebula.draw(0);
            }
            menu.render(Gdx.graphics.getDeltaTime());
        }
    }


    @Override
    public void dispose() {
        if(menu!=null) menu.dispose();
        skin.dispose();
        nebula.dispose();
    }

    private void setMenu(Menu menu) {
        if(this.menu!=null)	this.menu.dispose();

        if(menu!=null)		menu.create();

        this.menu = menu;
    }



    public void setGame_state(GAME_STATE game_state) {
        switch (game_state){
            case MAIN_MENU:
                setMenu(new MainMenu(this));
                break;
            case RUNNING:
                setMenu(null);
                break;
        }

        this.game_state = game_state;
    }

    public enum GAME_STATE{
        MAIN_MENU,
        RUNNING,
    }

    public void quitGame(){
        Gdx.app.exit();
    }

}
