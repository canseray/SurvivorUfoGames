package com.example.survivorufogames;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class SurvivorUfoGames extends ApplicationAdapter {
	Random random;
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	Circle ufoCircle;
	Texture background;
	Texture ufo;
	Texture ocu;
	Texture ocu2;
	Texture ocu3;
	float ufoX, ufoY;
	float velocity;
	float gravity;
	float ocuX;
	float ocuVelocity = 2;
	float distance = 0;
	int gameState;
	int numberOfOcus = 5;
	int score = 0;
	int scoreOcu = 0;

	float ocuOffset = new float[numberOfOcus];
	float ocuOffset2 = new float[numberOfOcus];
	float ocuOffset3 = new float[numberOfOcus];
	float [] ocuX = new float[numberOfOcus];

	BitmapFont font;
	BitmapFont font2;


	Circle[] ocuCircle;
	Circle[] ocuCircle2;
	Circle[] ocuCircle3;


	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("space.png");
		ufo = new Texture("ufo.png");
		ocu = new Texture("ocu.png");
		ocu2 = new Texture("ocu.png");
		ocu3 = new Texture("ocu.png");

		ufoX = Gdx.graphics.getWidth() / 2;

		ufoY = Gdx.graphics.getHeight() / 2;

		ufoCircle = new Circle();

		shapeRenderer = new ShapeRenderer();

		ocuCircle = new Circle[numberOfOcus];
		ocuCircle2 = new Circle[numberOfOcus];
		ocuCircle3 = new Circle[numberOfOcus];

		distance = Gdx.graphics.getWidth() / 2 ;

		random = new Random();

		font = new BitmapFont();
		font.setColor(Color.BLUE);
		font.getData().setScale(3);

		font2 = new BitmapFont();
		font.setColor(Color.BLUE);
		font.getData().setScale(6);

		for (int i = 0; i<numberOfOcus; i++){


			ocuOffset[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-100);
			ocuOffset2[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-100);
			ocuOffset3[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-100);


			ocuX[i] = Gdx.graphics.getWidth() - ocu.getWidth() / 2 + i * distance;

			ocuCircle[i] = new Circle();
			ocuCircle2[i] = new Circle();
			ocuCircle3[i] = new Circle();


		}
	}

	@Override
	public void render() {
		//oyun baslamasa da background göster
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());



		//baslat
		if (Gdx.input.justTouched()) {
			gameState = 1;

			if(ocuX[scoreOcu] < Gdx.graphics.getWidth() / 2){
				score++;
				if (scoreOcu < 3 ){
					scoreOcu++;
				}else{
					scoreOcu = 0;
				}

			}
		}


		//oyun basladıysa
		if (gameState == 1) {
			//arılar oyun baslayınca gelsin



			for (int i = 0; i<numberOfOcus; i++) {

				if(ocuX[i] < Gdx.graphics.getWidth() / 15){
					ocuX[i] = ocuX[i] + numberOfOcus * distance;

					//-0,5  asagı yukarı hareket - +
					ocuOffset[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-100);
					ocuOffset2[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-100);
					ocuOffset3[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-100);

				} else {
					ocuX[i] = ocuX[i] - ocuVelocity;
				}


				ocuX[i] = ocuX[i] - ocuVelocity;

				batch.draw(ocu, ocuX[i], 50, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 15);
				batch.draw(ocu2, ocuX[i], 50, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 15);
				batch.draw(ocu3, ocuX[i], 50, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 15);

				ocuCircle[i] = new Circle(ocuX[i]) + Gdx.graphics.getWidth() /30,
						Gdx.graphics.getHeight() /2,
						ocuOffset[i] + Gdx.graphics.getHeight()/ 20,
						Gdx.graphics.getWidth()/30);

				ocuCircle2[i] = new Circle(ocuX[i]) + Gdx.graphics.getWidth() /30,
						Gdx.graphics.getHeight() /2,
						ocuOffset2[i] + Gdx.graphics.getHeight()/ 20,
						Gdx.graphics.getWidth()/30);

				ocuCircle3[i] = new Circle(ocuX[i]) + Gdx.graphics.getWidth() /30,
						Gdx.graphics.getHeight() /2,
						ocuOffset3[i] + Gdx.graphics.getHeight()/ 20,
						Gdx.graphics.getWidth()/30);



			}

			if (Gdx.input.justTouched()) {
				velocity = -10;

				if (ufoY > 0) {
					velocity = velocity + gravity;
					ufoY = ufoY - velocity;
				} else{   //kus zemine düserse
					gameState = 2;
				}
			} else {
				if (Gdx.input.justTouched()) {
					gameState = 1;
				}
			} else if (gameState == 2) { //oyun biterse

				font2.draw(batch"Game over,tap tp play again",100,100);
				if (Gdx.input.justTouched()) { //tekrar baslayabilir
					gameState = 1;

					for (int i = 0; i<numberOfOcus; i++){


						ocuOffset[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-100);
						ocuOffset2[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-100);
						ocuOffset3[i] = (random.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-100);


						ocuX[i] = Gdx.graphics.getWidth() - ocu.getWidth() / 2 + i * distance;

						ocuCircle[i] = new Circle();
						ocuCircle2[i] = new Circle();
						ocuCircle3[i] = new Circle();


					}

					velocity = 0;
					scoreOcu = 0;
					score = 0 ;
			}



			batch.draw(ufo, ufoX,
					ufoY,
					Gdx.graphics.getWidth() / 10,
					Gdx.graphics.getHeight() / 10);

			font.draw(batch,String.valueOf(score),100,100);

			batch.end();

			//ufonun ustundeki görünmeyecek daire
			ufoCircle.set(ufoX,ufoY,Gdx.graphics.getWidth() / 30);
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.setColor(Color.CYAN);
			shapeRenderer.circle(ufoCircle.x,ufoCircle.y,ufoCircle.radius);
			shapeRenderer.end();


			for (int i = 0 ; i < numberOfOcus; i++){
				shapeRenderer.circle(ocuX[i]) + Gdx.graphics.getWidth() /30,
						Gdx.graphics.getHeight() /2,
						ocuOffset[i] + Gdx.graphics.getHeight()/ 20,
						Gdx.graphics.getWidth()/30);

				shapeRenderer.circle(ocuX[i]) + Gdx.graphics.getWidth() /30,
						Gdx.graphics.getHeight() /2,
						ocuOffset2[i] + Gdx.graphics.getHeight()/ 20,
						Gdx.graphics.getWidth()/30);

				shapeRenderer.circle(ocuX[i]) + Gdx.graphics.getWidth() /30,
						Gdx.graphics.getHeight() /2,
						ocuOffset3[i] + Gdx.graphics.getHeight()/ 20,
						Gdx.graphics.getWidth()/30);

				//oyun bitince-çarpışmak
				if(Intersector.overlaps(ufoCircle,ocuCircle[i])) || Intersector.overlaps(ufoCircle,ocuCircle2[i])) || Intersector.overlaps(ufoCircle,ocuCircle3[i])) ){
					System.out.println("oops");
				}
			}
		}
	}

		@Override
		public void dispose () {

		}
	}

