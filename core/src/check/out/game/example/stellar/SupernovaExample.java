package check.out.game.example.stellar;

import check.out.game.example.ConstantsExample;
import fernebon.core.impl.SupernovaPartial;
import fernebon.core.util.effects.DrawCaller;
import fernebon.gdx.util.artists.ScreenClearer;

public class SupernovaExample extends SupernovaPartial<NebulaExample> {
    @Override
    protected NebulaExample getNewNebula() {
        return new NebulaExample();
    }

    @Override
    protected void setupNebula(NebulaExample nebulaImplemented) {
        nebulaImplemented.effects().add(() -> new DrawCaller() {
            @Override
            public int getPriority() {
                return ConstantsExample.EP_DRAW;
            }
        });
        nebulaImplemented.artists().add(() -> new ScreenClearer() {
            @Override
            public int getPriority() {return ConstantsExample.AP_SCREEN_CLEAR;}
        });
    }

    @Override
    public void dispose() {

    }
}
