package game;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URISyntaxException;

public class SoundEngine {

    private static SoundEngine instance;
    private Media MENU_MUSIC;
    private Media GAME_MUSIC;
    private MediaPlayer music;
    private boolean musicMuted, soundFXMuted;
    private double musicVolume, soundFXVolume;
    private AudioClip buttonSound, winSound, territoryClick, diceRoll, objectiveCompleted, objectiveFailed, battleVictory;

    private SoundEngine(){
        musicMuted = false;
        soundFXMuted = false;
        musicVolume = 0.15;
        soundFXVolume = 1.0;
        try {
            MENU_MUSIC = new Media(getClass().getResource("/sound/menu_music.mp3").toURI().toString());
            GAME_MUSIC = new Media(getClass().getResource("/sound/game_music.mp3").toURI().toString());
            buttonSound = new AudioClip(getClass().getResource("/sound/button_click.wav").toURI().toString());
            winSound = new AudioClip(getClass().getResource("/sound/win_sound.wav").toURI().toString());
            territoryClick = new AudioClip(getClass().getResource("/sound/territory_click.wav").toURI().toString());
            diceRoll = new AudioClip(getClass().getResource("/sound/dice_roll.wav").toURI().toString());
            objectiveCompleted = new AudioClip(getClass().getResource("/sound/objective_completed.wav").toURI().toString());
            objectiveFailed = new AudioClip(getClass().getResource("/sound/objective_failed.wav").toURI().toString());
            battleVictory = new AudioClip(getClass().getResource("/sound/battle_victory.wav").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        buttonSound.setVolume(soundFXVolume);
        music = new MediaPlayer(MENU_MUSIC);
        initMusic();
    }

    public static SoundEngine getInstance() {
        if (instance == null) {
            synchronized (SoundEngine.class) {
                if (instance == null) {
                    instance = new SoundEngine();
                }
            }
        }
        return instance;
    }

    public boolean isMusicMuted() {
        return musicMuted;
    }

    public void setMusicMuted(boolean musicMuted) {
        this.musicMuted = musicMuted;
        music.setMute(musicMuted);
    }

    public boolean isSoundFXMuted() {
        return soundFXMuted;
    }

    public void setSoundFXMuted(boolean soundFXMuted) {
        this.soundFXMuted = soundFXMuted;
    }

    public double getMusicVolume() {
        return musicVolume * 100.0;
    }

    public void setMusicVolume(double musicVolume) {
        this.musicVolume = musicVolume / 100.0;
        music.setVolume(this.musicVolume);
    }

    public double getSoundFXVolume() {
        return soundFXVolume * 100.0;
    }

    public void setSoundFXVolume(double soundFXVolume) {
        this.soundFXVolume = soundFXVolume / 100.0;
        buttonSound.setVolume(this.soundFXVolume);
        territoryClick.setVolume(this.soundFXVolume);
        diceRoll.setVolume(this.soundFXVolume);
        objectiveCompleted.setVolume(this.soundFXVolume);
        objectiveFailed.setVolume(this.soundFXVolume);
        battleVictory.setVolume(this.soundFXVolume);
    }

    public void stopMusic(){
        music.stop();
    }

    public void startMusic(){
        music.play();
    }

    public void playButtonSound(){
        if(!soundFXMuted){
            buttonSound.play();
        }
    }

    public void playBattleVictory(){
        battleVictory.play();
    }

    public void changeToGameMusic(){
        music.stop();
        music = new MediaPlayer(GAME_MUSIC);
        initMusic();
    }

    public void changeToMenuMusic(){
        music.stop();
        music = new MediaPlayer(MENU_MUSIC);
        initMusic();
    }

    public void initMusic(){
        music.setMute(musicMuted);
        music.setVolume(musicVolume);
        music.setOnEndOfMedia(() -> {
            music.seek(Duration.ZERO);
            music.play();
        });
        music.play();
    }

    public void playWinSound(){
        winSound.play();
    }

    public void clickTerritory() { territoryClick.play();}

    public void playDiceRoll() { diceRoll.play();}

    public void objectiveCompleted() { objectiveCompleted.play(); }

    public void objectiveFailed() { objectiveFailed.play(); }
}
