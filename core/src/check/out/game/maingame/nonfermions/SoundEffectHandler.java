package check.out.game.maingame.nonfermions;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.HashMap;
import java.util.Map;

public class SoundEffectHandler {
    Map<String,Sound> soundSet;

    public SoundEffectHandler(){
        soundSet = new HashMap<>();
        loadSoundFiles();
    }

    protected void loadSoundFiles(){
        FileHandle file = Gdx.files.internal("sounds/fileNames.txt");
        String text = file.readString();
        String[] fileNames = text.split(",");
        
        for(String id:fileNames) {
            try {
                soundSet.put(id, Gdx.audio.newSound(Gdx.files.internal("sounds/" + id + ".wav")));
            } catch (GdxRuntimeException e) {
                e.printStackTrace();
                soundSet.put(id, null);
            }
        }
    }

    public void playSound(String id){
        if(soundSet.get(id)!=null) {
            soundSet.get(id).play();
        }
    }

    public void dispose(){
        for(Map.Entry<String, Sound> entry: soundSet.entrySet()){
            if(entry.getValue()!=null) {
                entry.getValue().dispose();
            }
        }
    }
}
