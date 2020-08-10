package check.out.game.maingame.nonfermions;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class SoundEffectHandler {
    Map<String,Sound> soundSet;

    public SoundEffectHandler(){
        soundSet = new HashMap<>();
        loadSoundFiles();
    }

    protected void loadSoundFiles(){
        String id = "ding";
        soundSet.put(id, Gdx.audio.newSound(Gdx.files.internal("sounds/"+id+".wav")));
    }

    public void playSound(String id){
        soundSet.get(id).play();
    }

    public void dispose(){
        for(Map.Entry<String, Sound> entry: soundSet.entrySet()){
            entry.getValue().dispose();
        }
    }
}
