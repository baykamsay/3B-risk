package game;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class SoundEngine {
    private final Media MENU_MUSIC = new Media(new File("src\\sound\\menu_music.mp3").toURI().toString());
    private final Media GAME_MUSIC = new Media(new File("src\\sound\\game_music.mp3").toURI().toString());
    private MediaPlayer music;
    private boolean musicMuted, soundFXMuted;
    private double musicVolume, soundFXVolume;
    private final AudioClip buttonSound = new AudioClip(new File("src\\sound\\button_click.wav").toURI().toString());

    public SoundEngine(){
        musicMuted = false;
        soundFXMuted = false;
        musicVolume = 0.15;
        soundFXVolume = 1.0;
        buttonSound.setVolume(soundFXVolume);
        music = new MediaPlayer(MENU_MUSIC);
        initMusic();
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
}
