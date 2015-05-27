package com.gdxjam.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gdxjam.Assets;
import com.gdxjam.GameManager;
import com.gdxjam.components.FactionComponent.Faction;
import com.gdxjam.utils.Constants;
import com.gdxjam.utils.Constants.WorldSize;

public class ConnectNewGameScreen extends AbstractScreen {

	Stage stage;
	Table table;
	final Skin skin = Assets.skin;

	@Override
	public void show() {
		stage = new Stage();
		table = new Table();
		table.setFillParent(true);
		table.defaults().pad(10);

		/**
		 * Moved descriptions into the faction enum with the names already
		 * there. Removes need to hardcode any faction related elements.
		 * Creation of new factions should be as simple as draging 3 new
		 * asset files into the folder and creating an entry in the faction
		 * enum for them
		 */



		NinePatchDrawable draw = new NinePatchDrawable(Assets.hotkey.button);

		TextButtonStyle textStyle = new ImageTextButtonStyle();
		textStyle.up = draw;
		textStyle.down = draw.tint(Color.DARK_GRAY);
		textStyle.checked = draw;
		textStyle.font = Assets.fonts.font;
		TextField ip = new TextField("0.0.0.0", skin);
		ip.setAlignment(Align.center);
		TextButton connect = new TextButton("Connect", textStyle);
		connect.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				GameManager.setScreen(new MainMenuScreen());
			}
		});
		
		TextButton back = new TextButton("Back", textStyle);
		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				GameManager.setScreen(new MainMenuScreen());
			}
		});

		table.row();
		table.add(ip);
		table.row();
		table.add(connect);
		table.add(back);
		stage.addActor(table);

		Gdx.input.setInputProcessor(stage);

	}

	// Creates the faction buttons using the faction enum. Removes need to
	// hard code the buttons

	public ImageButton createFactionButton(final Faction faction) {
		ImageButton button = newImageButton(Assets.spacecraft.ships.get(faction.ordinal()));
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				showFaction(faction);
			}
		});
		return button;
	}

	// Now uses the parameters in the faction enum rather than constants
	public void showFaction(Faction faction) {
	}

	public ImageButton newImageButton(TextureRegion region) {
		TextureRegionDrawable drawable = new TextureRegionDrawable(region);
		return new ImageButton(drawable);
	}

	public void start(Faction faction) {
		Constants.playerFaction = faction;
		GameManager.setScreen(new GameScreen());
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		stage.act();
		stage.draw();

	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}