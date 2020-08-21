package check.out.game.maingame.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import check.out.game.MyGdxGame;

public class MainMenu extends Menu {
    private Stage stage;

    public MainMenu(MyGdxGame game) {
        super(game);
    }

    @Override
    public void create() {
        stage=new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table=new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("Heist",game.skin,"default");
        table.add(title).align(Align.center).space(Value.percentHeight(0.1f,table));
        table.row();

        table.defaults()
                .width(Value.percentWidth(.5f,table))
                .space(Value.percentHeight(.25f));

        TextButton buttonPlay = new TextButton("Play",game.skin);
        buttonPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setGame_state(MyGdxGame.GAME_STATE.RUNNING);
            }
        });
        table.add(buttonPlay);
        table.row();

        TextButton buttonQuit = new TextButton("Quit",game.skin);
        buttonQuit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.quitGame();
            }
        });
        table.add(buttonQuit);
        table.row();
    }

    @Override
    public void render(float deltaTime) {
        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        stage.dispose();
    }
}
