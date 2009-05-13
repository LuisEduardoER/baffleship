//TetrisMusic class for playing music and sound effects

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class TetrisMusic {

	URL url1, url2, url3, url4;
    	AudioClip ac1, ac2, ac3, ac4;

	boolean isplaying;  //used for toggling music on and off

  TetrisMusic(){
   try {
      	url1 = new URL("file:sounds/Korobeiniki.wav" );
		url2 = new URL("file:sounds/correct.wav" );
		url3 = new URL("file:sounds/lose.wav" );
		url4 = new URL("file:sounds/win.wav" );
	    	ac1 = Applet.newAudioClip(url1);
		ac2 = Applet.newAudioClip(url2);
		ac3 = Applet.newAudioClip(url3);
		ac4 = Applet.newAudioClip(url4);
    } catch (Exception e) { System.out.println(e);  }

  }

	public void startMusic(){
		ac1.loop();
		isplaying=true;
	}

	public void stopMusic(){
		ac1.stop();
		isplaying=false;
	}

	public void toggleMusic(){
		if (isplaying) stopMusic();
		else startMusic();
	}
	
	public void rowSound(){
		ac2.play();
	}

	public void loseSound(){
		ac3.play();
	}

	public void winSound(){
		ac4.play();
	}
}    

