package de.tu_darmstadt.gdi1.gorillas.main;
import java.net.URL;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.matthiasmann.twl.slick.TWLStateBasedGame;
import eea.engine.entity.StateBasedEntityManager;

/**
 * @author Timo Bähr, Peter Kl�ckner
 * 
 *         Diese Klasse startet das Spiel "Drop of Water". Es enthaelt zwei
 *         State's für das Menue und das eigentliche Spiel.
 */
public class Gorillas extends TWLStateBasedGame {

	// Jeder State wird durch einen Integer-Wert gekennzeichnet
	//public static final int MAINMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 0;

	public Gorillas() {
		super("Gorillas");
	}

	public static void main(String[] args) throws SlickException {
		// Setze den library Pfad abhaengig vom Betriebssystem
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			System.setProperty("org.lwjgl.librarypath",
					System.getProperty("user.dir")
							+ "/lib/lwjgl-2.9.1/native/windows");
		} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			System.setProperty("org.lwjgl.librarypath",
					System.getProperty("user.dir") + "/lib/lwjgl-2.9.1/native/macosx");
		} else {
			System.setProperty("org.lwjgl.librarypath",
					System.getProperty("user.dir") + "/lib/lwjgl-2.9.1/native/"
							+ System.getProperty("os.name").toLowerCase());
		}

		// Setze dieses StateBasedGame in einen App Container (oder Fenster)
		AppGameContainer app = new AppGameContainer(new Gorillas());

		// Lege die Einstellungen des Fensters fest und starte das Fenster
		// (nicht aber im Vollbildmodus)
		app.setDisplayMode(800, 600, false);
		app.setTargetFrameRate(120);
		app.start();
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {

		// Fuege dem StateBasedGame die States hinzu
		// (der zuerst hinzugefuegte State wird als erster State gestartet)
		//addState(new MainMenuState(MAINMENUSTATE));
		addState(new GameplayState(GAMEPLAYSTATE));

		// Fuege dem StateBasedEntityManager die States hinzu
		//StateBasedEntityManager.getInstance().addState(MAINMENUSTATE);
		StateBasedEntityManager.getInstance().addState(GAMEPLAYSTATE);
	}

	@Override
	protected URL getThemeURL() {
		return getClass().getResource("/theme.xml");
	}
}