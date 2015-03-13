package de.tu_darmstadt.gdi1.gorillas.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.EditField.Callback;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.slick.BasicTWLGameState;
import de.matthiasmann.twl.slick.RootPane;
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.action.basicactions.MoveDownAction;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.DestructibleImageEntity;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.Event;
import eea.engine.event.basicevents.CollisionEvent;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.LoopEvent;
import eea.engine.interfaces.IDestructible;

/**
 * @author Namen hier einfuegen ;)
 * 
 *         Diese Klasse repraesentiert das Spielfenster, indem sich 2 Gorillas auf jeweils einem
 *         Hochaus mit Bananen abwerfen.
 */
public class GameplayState extends BasicTWLGameState {

	private int stateID; // Identifier dieses BasicTWLGameState
	private StateBasedEntityManager entityManager; // zugehoeriger entityManager
	private Label xLabel;
	EditField xInput;
	private Label yLabel;
	EditField yInput;
	private Button dropButton;

	GameplayState(int sid) {
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
	}

	/**
	 * Wird vor dem (erstmaligen) Starten dieses States ausgefuehrt
	 */
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

		// Hintergrund laden
		Entity background = new Entity("background"); // Entitaet fuer den Hintergrund
		background.setPosition(new Vector2f(400, 300)); // Startposition des Hintergrunds
		background.addComponent(new ImageRenderComponent(new Image(
				"/assets/gorillas/background.png"))); // Bildkomponente

		// Hintergrund-Entitaet an StateBasedEntityManager uebergeben
		StateBasedEntityManager.getInstance().addEntity(stateID, background);

		// Bei Druecken der ESC-Taste zurueck ins Hauptmenue wechseln
		Entity escListener = new Entity("ESC_Listener");
		KeyPressedEvent escPressed = new KeyPressedEvent(Input.KEY_ESCAPE);
		escPressed.addAction(new ChangeStateAction(Gorillas.GAMEPLAYSTATE));
		escListener.addComponent(escPressed);
		entityManager.addEntity(stateID, escListener);


		int widthcounter = 0;
		int counter = 1;
		while ( 10 > counter) {
		    Random rand1 = new Random();
		    int min1 = 25;
			int max1 = 44;
			int randomwidth = rand1.nextInt((max1 - min1) + 1) + min1;
			randomwidth = randomwidth * 2;
			
		    Random rand2 = new Random();
		    int min2 = 100;
			int max2 = 500;
			int randomheight = rand2.nextInt((max2 - min2) + 1) + min2;
			
		    Random rand3 = new Random();
		    int min3 = 0;
			int max3 = 255;
			int randomred = rand3.nextInt((max3 - min3) + 1) + min3;
			
		    Random rand4 = new Random();
		    int min4 = 0;
			int max4 = 255;
			int randomgreen = rand4.nextInt((max4 - min4) + 1) + min4;
			
		    Random rand5 = new Random();
		    int min5 = 0;
			int max5 = 255;
			int randomblue = rand5.nextInt((max5 - min5) + 1) + min5;
			
			System.out.println(randomheight);
			
			widthcounter = randomwidth + widthcounter;
			
		// erstelle ein Bild der Breite 500 und der Hoehe 200
		BufferedImage image = new BufferedImage(randomwidth, randomheight,
				BufferedImage.TYPE_INT_ARGB);
		// mit Graphics2D laesst sich das Bild bemalen
		Graphics2D graphic = image.createGraphics();
		// die folgende Zeile bewirkt, dass sich auch wieder "ausradieren" laesst
		graphic.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		// bemale das vollstaendige Bild mit einer zufalls generierten Farbe
		graphic.setColor(new Color(randomred, randomgreen, randomblue));
		graphic.fillRect(0, 0, randomwidth, randomheight);

		// erstelle eine DestructibleImageEntity mit dem gerade gemalten Bild
		// als Image, das durch das Zerstoerungs-Pattern destruction.png zerstoert
		// werden kann
		DestructibleImageEntity obstacle = new DestructibleImageEntity(
				"obstacle", image, "gorillas/destruction.png", false);
		obstacle.setPosition(new Vector2f(game.getContainer().getWidth() - (widthcounter - (randomwidth / 2)),
				game.getContainer().getHeight() - (randomheight / 2)));

		entityManager.addEntity(stateID, obstacle);
		counter++;
		}
	}

	/**
	 * Wird vor dem Frame ausgefuehrt
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// StatedBasedEntityManager soll alle Entities aktualisieren
		entityManager.updateEntities(container, game, delta);
	}

	/**
	 * Wird mit dem Frame ausgefuehrt
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		// StatedBasedEntityManager soll alle Entities rendern
		entityManager.renderEntities(container, game, g);
	}

	@Override
	public int getID() {
		return stateID;
	}

	/**
	 * In dieser Methode werden in einem BasicTWLGameSate alle GUI-Elemente dem
	 * GameState mit Hilfe einer RootPane hinzugefuegt
	 */
	@Override
	protected RootPane createRootPane() {

		// erstelle die RootPane
		RootPane rp = super.createRootPane();

		// erstelle ein Label mit der Aufschrift "x:"
		xLabel = new Label("x:");
		// erstelle ein EditField. Es dient der Eingabe von Text
		xInput = new EditField();
		// mit der Methode addCallBack lï¿½sst sich dem EditField ein CallBack
		// hinzufuegen, in dessen Methode callback(int key) bestimmt wird, was
		// geschehen soll, wenn ein Zeichen eingegeben wird
		xInput.addCallback(new Callback() {
			public void callback(int key) {
				// in unserem Fall wird der Input in der Methode
				// handleEditFieldInput verarbeitet (siehe weiter unten in
				// dieser Klasse, was diese tut, und was es mit ihren Parametern
				// auf sich hat)
				handleEditFieldInput(key, xInput, this, 1000);
			}
		});

		// analog zu einer Eingabemïoeglichkeit fuer x-Werte wird auch eine fuer y-Werte kreiert
		yLabel = new Label("y:");
		yInput = new EditField();
		yInput.addCallback(new Callback() {
			public void callback(int key) {
				handleEditFieldInput(key, yInput, this, 500);
			}
		});

		// zuletzt wird noch ein Button hinzugefuegt
		dropButton = new Button("drop");
		// Aehnlich wie einem EditField kann auch einem Button ein CallBack
		// hinzugefuegt werden
		// Hier ist es jedoch von Typ Runnable, da keine Parameter (z. B. welche
		// Taste wurde gedrï¿½ckt) benoetigt werden
		dropButton.addCallback(new Runnable() {
			@Override
			public void run() {
				// ein Klick auf den Button wird in unserem Fall in der
				// folgenden Methode verarbeitet
				inputFinished();
			}
		});

		// am Schluss der Methode mï¿½ssen alle GUI-Elemente der Rootpane
		// hinzugefï¿½gt werden
		rp.add(xLabel);
		rp.add(xInput);

		rp.add(yLabel);
		rp.add(yInput);

		rp.add(dropButton);

		// ... und die fertige Rootpane zurï¿½ckgegeben werden
		return rp;
	}

	/**
	 * in dieser Methode des BasicTWLGameState werden die erstellten
	 * GUI-Elemente platziert
	 */
	@Override
	protected void layoutRootPane() {

		int xOffset = 50;
		int yOffset = 50;
		int gap = 5;

		// alle GUI-Elemente mï¿½ssen eine Grï¿½ï¿½e zugewiesen bekommen. Soll die
		// Grï¿½ï¿½e automatisch ï¿½ber die Beschriftung des GUI-Elements bestimmt
		// werden, so muss adjustSize() aufgerufen werden.
		xLabel.adjustSize();
		yLabel.adjustSize();

		// Ansonsten wird die Grï¿½ï¿½e manuell mit setSize() gesetzt
		xInput.setSize(50, 25);
		yInput.setSize(50, 25);
		dropButton.setSize(50, 25);

		// Nachdem alle Grï¿½ï¿½en adjustiert wurden, muss allen GUI-Elementen eine
		// Position (linke obere Ecke) zugewiesen werden
		xLabel.setPosition(xOffset, yOffset);
		xInput.setPosition(xOffset + xLabel.getWidth() + gap, yOffset);

		yLabel.setPosition(xOffset, yOffset + xLabel.getHeight() + gap);
		yInput.setPosition(xOffset + yLabel.getWidth() + gap,
				yOffset + xLabel.getHeight() + gap);

		dropButton.setPosition(xOffset + yLabel.getWidth() + gap, yOffset
				+ xLabel.getHeight() + gap + yLabel.getHeight() + gap);
	}

	/**
	 * Diese Methode wird aufgerufen, wenn ein Zeichen in ein EditField eingegeben wurde.
	 * 
	 * @param key
	 * 			die gedrï¿½ckte Taste
	 * @param editField
	 * 			das EditField, in das ein Zeichen eingefï¿½gt wurde
	 * @param callback
	 * 			der CallBack, der dem EditField hinzugefï¿½gt wurde
	 * @param maxValue
	 * 			die grï¿½ï¿½te Zahl, die in das <code>editField</code> eingegeben werden kann
	 */
	void handleEditFieldInput(int key, EditField editField,
			Callback callback, int maxValue) {

		if (key == de.matthiasmann.twl.Event.KEY_NONE) {
			String inputText = editField.getText();

			if (inputText.isEmpty()) {
				return;
			}

			char inputChar = inputText.charAt(inputText.length() - 1);
			if (!Character.isDigit(inputChar)
					|| Integer.parseInt(inputText) > maxValue) {
				// a call of setText on an EditField triggers the callback, so
				// remove callback before and add it again after the call
				editField.removeCallback(callback);
				editField
						.setText(inputText.substring(0, inputText.length() - 1));
				editField.addCallback(callback);
			}
		}
	}

	/**
	 * diese Methode wird bei Klick auf den Button ausgefï¿½hrt
	 */
	void inputFinished() {

		// Wassertropfen wird erzeugt
		Entity drop = new Entity("drop of water");
		drop.setPosition(new Vector2f(Integer.parseInt(xInput.getText()),
				Integer.parseInt(yInput.getText())));

		try {
			// Bild laden und zuweisen
			drop.addComponent(new ImageRenderComponent(new Image(
					"assets/gorillas/banana.png")));
		} catch (SlickException e) {
			System.err.println("Cannot find file assets/gorillas/banana.png!");
			e.printStackTrace();
		}

		// Wassertropfen faellt nach unten
		LoopEvent loop = new LoopEvent();
		loop.addAction(new MoveDownAction(0.5f));
		drop.addComponent(loop);

		Event collisionEvent = new CollisionEvent();
		collisionEvent.addAction(new Action() {
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {

				// hole die Entity, mit der kollidiert wurde
				CollisionEvent collider = (CollisionEvent) event;
				Entity entity = collider.getCollidedEntity();

				// wenn diese durch ein Pattern zerstï¿½rt werden kann, dann caste
				// zu IDestructible
				// ansonsten passiert bei der Kollision nichts
				IDestructible destructible = null;
				if (entity instanceof IDestructible) {
					destructible = (IDestructible) entity;
				} else {
					return;
				}

				// zerstï¿½re die Entitï¿½t (dabei wird das der Entitï¿½t
				// zugewiese Zerstï¿½rungs-Pattern benutzt)
				destructible.impactAt(event.getOwnerEntity().getPosition());
			}
		});
		collisionEvent.addAction(new DestroyEntityAction());
		drop.addComponent(collisionEvent);

		entityManager.addEntity(stateID, drop);
	}
}
