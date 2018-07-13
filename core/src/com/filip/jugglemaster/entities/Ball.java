package com.filip.jugglemaster.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.filip.jugglemaster.JuggleMasterGame;
import com.filip.jugglemaster.assets.Assets;

public class Ball extends Image
{
	private static final int width = Gdx.graphics.getWidth()/5;
	private static final int height = Gdx.graphics.getWidth()/5;
	private static final int starting_x = 190;
	private static final int starting_y = 0;
	private Vector2 speed;
	private float angleSpeed = 0;
	private boolean onTheFloor;
	private Sound kick;

	public Ball()
	{
		super(Assets.manager.get(Assets.ball, Texture.class));
		init();
	}

	private void init()
	{
		speed = new Vector2(0, 0);
		kick = Assets.manager.get(Assets.kick, Sound.class);
		onTheFloor = true;
		this.setDebug(false);
		this.setOrigin(width / 2, height / 2);
		this.setSize(width, height);
		this.setPosition(starting_x, starting_y);
	}

	public void reactOnClick(final float x, final float y)
	{
		Action bump = Actions.run(new Runnable()
		{
			@Override
			public void run()
			{
				kick.play();
				Vector2 displacement = new Vector2(-(x - width/2 - getX()), -(y - (getY() + height/2)));
				speed = displacement.scl(Gdx.graphics.getWidth() /(float)(5*width/8));
				System.out.println(displacement +  Integer.toString(5*width/8));


				angleSpeed -= displacement.x * Math.signum(displacement.y) / (width/130f);
				if (onTheFloor && displacement.y > 0)
					onTheFloor = false;
			}
		});

		this.addAction(bump);
	}



	public void update(final Vector2 gravity)
	{
		int xSlowdown = 20 * width/100;
		int rotationSlowdown = 50;
		updatePosition(gravity, xSlowdown);
		updateRotation(rotationSlowdown);

	}

	private void updatePosition(Vector2 gravity, int xSlowdown)
	{
		this.setPosition(this.getX() + Gdx.graphics.getDeltaTime() * speed.x, this.getY() + Gdx.graphics.getDeltaTime() * speed.y);
		if (this.getY() <= 0)
		{
			onTheFloor = true;
			if(Math.abs(speed.y) > 50)
			{	if(Math.abs(speed.y / 500f) < 1f)
					kick.play(Math.abs(speed.y / 500f));
				else
					kick.play();
			}
			this.setY(0);
			speed.y = -speed.y / 2.5f;
			xSlowdown = 500 * width/100;

		}

		speed.x -= Math.signum(speed.x) * xSlowdown * Gdx.graphics.getDeltaTime();
		if (Math.abs(speed.x) < 10)
			speed.x = 0;
		speed.add(0, gravity.y * Gdx.graphics.getDeltaTime());

		if (getX() <= -1)
		{
			setX(0);
			if(Math.abs(speed.x / 500f) < 1f)
				kick.play(Math.abs(speed.x / 500f));
			else
				kick.play();
			speed.x = -speed.x;
		} else if ((getX() + width) >= Gdx.graphics.getWidth())
		{
			setX(Gdx.graphics.getWidth() - width);
			if(Math.abs(speed.x / 500f) < 1f)
				kick.play(Math.abs(speed.x / 500f));
			else
				kick.play();
			speed.x = -speed.x;
		}
	}

	private void updateRotation(int rotationSlowdown)
	{

		if (getX() <= 0)
			angleSpeed = speed.y/(width/140f);

		else if ((getX() + width) >= Gdx.graphics.getWidth())
			angleSpeed = -speed.y/(width/140f);

		if(this.getY() <= 0)
		{
			angleSpeed = -speed.x/(width/140f) /*(((1 + e) * speed.x + alpha * (1 + e) * radius * angleSpeed) / ((1 + alpha) * radius))*//* * (360f / 6.28f)*/;
			System.out.println(angleSpeed);
		}

		angleSpeed -= Math.signum(angleSpeed) * rotationSlowdown * Gdx.graphics.getDeltaTime();
		if (Math.abs(angleSpeed) < 5)
			angleSpeed = 0;
		this.rotateBy(angleSpeed * Gdx.graphics.getDeltaTime());
	}

	//----------------------------

	public boolean isOnTheFloor()
	{
		return onTheFloor;
	}

	public boolean collides(Coin coin)
	{
		float w = 0.5f * (this.getWidth() + coin.getWidth());
		float h = 0.5f * (this.getHeight() + coin.getHeight());
		float dx = this.getX() + this.getWidth()/2 - coin.getX() - coin.getWidth()/2;
		float dy = this.getY() + this.getHeight()/2 - coin.getY() - coin.getHeight()/2;

		if (Math.abs(dx) <= w && Math.abs(dy) <= h)
		{
			return true;
		}
		return false;
	}
}
