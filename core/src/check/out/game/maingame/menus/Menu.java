package check.out.game.maingame.menus;

import check.out.game.MyGdxGame;
public abstract class Menu {
    protected MyGdxGame game;

    public Menu(MyGdxGame game){
        this.game = game;
    }

    public abstract void create();
    public abstract void render(float deltaTime);
    public abstract void dispose();
}
