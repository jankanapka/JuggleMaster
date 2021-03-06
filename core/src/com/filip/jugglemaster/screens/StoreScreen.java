package com.filip.jugglemaster.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.filip.jugglemaster.JuggleMasterGame;
import com.filip.jugglemaster.assets.Assets;
import com.filip.jugglemaster.ui.IClickCallback;
import com.filip.jugglemaster.ui.ItemSlider;
import com.filip.jugglemaster.ui.MenuButton;

public class StoreScreen extends AbstractMenuScreen
{

	ItemSlider slider;

	public StoreScreen(JuggleMasterGame game)
	{
		super(game);
		initItems();
	}

	private void initItems()
	{
		initTitle();
		initSlider();
		initBackButton();
		addItemsToSlider();
	}

	private void addItemsToSlider()
	{
		slider.addItem(Assets.ball, game.getBallService().getCurrentBall());
		slider.addItem(Assets.rainbowBall, game.getBallService().getCurrentBall());
		slider.addItem(Assets.billiardBall, game.getBallService().getCurrentBall());

	}

	private void initSlider()
	{
		slider = new ItemSlider(Gdx.graphics.getHeight()/4f, stage);
		addToBottom(slider, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/4f);
		stage.addActor(slider);
	}

	private void initBackButton()
	{
		MenuButton backButton = new MenuButton(0, 0, "Back", new IClickCallback()
		{
			@Override
			public void onClick()
			{
				game.setScreen(new MainMenuScreen(game));
				game.getBallService().setCurrentBall(StoreScreen.this.slider.getCurrentItem());
			}
		});
		addToBottom(backButton);
		stage.addActor(backButton);
	}

	private void initTitle()
	{
		Skin skin = (Assets.manager.get(Assets.uiSkin, Skin.class));
		Label.LabelStyle style = skin.get("title", Label.LabelStyle.class);
		style.font = Assets.manager.get(Assets.font, BitmapFont.class);
		Label label = new Label("Store", style);
		label.setAlignment(Align.center);
		addToBottom(label);
		stage.addActor(label);
	}

	@Override
	public void render(float delta)
	{
		super.render(delta);
		stage.act(delta);
		batch.begin();
		stage.draw();
		batch.end();
		if (Gdx.input.isKeyJustPressed(Input.Keys.BACK))
			game.setScreen(new MainMenuScreen(game));
	}
}
