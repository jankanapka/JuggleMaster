package com.filip.jugglemaster.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.filip.jugglemaster.entities.Ball;

public class BallButton extends Button
{

	Ball ball;

	public BallButton(Ball ball, final IClickCallback callback)
	{
		super(new Button.ButtonStyle());
		this.ball = ball;
		init(callback);

	}

	private void init(final IClickCallback callback)
	{
		setWidth(ball.getWidth());
		setHeight(ball.getHeight());
		setX(ball.getX());
		setY(ball.getY());
		setDebug(false);
		this.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				callback.onClick();
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	}

	public void updatePosition()
	{
		setX(ball.getX());
		setY(ball.getY());
	}
}