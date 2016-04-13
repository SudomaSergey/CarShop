package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import controller.ShopManager;

public abstract class AbstractPanel extends JPanel{

	private Image background;
	private CopyOnWriteArrayList<AnimationObject> animationObjects = new CopyOnWriteArrayList<>();
	
	public AbstractPanel() throws IOException{
		initBackGround();
		initAnimation();
	}



	private void initAnimation() throws IOException {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				try {
					new AnimationObject();
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}
		}, 0, 3000);
		
	}



	private void initBackGround() throws IOException {
		background = ImageIO.read(this.getClass().getResourceAsStream("/Resources/background.jpg"));	
	}
	
	private void killObject(AnimationObject animationObject){
		animationObjects.remove(animationObject);
		animationObject = null;
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
		for(AnimationObject item : animationObjects){
			item.draw(g);
		}
	}
	
	class AnimationObject{
		
		private Image image;
		private int speed;
		private boolean direction;
		private int x;
		
		public AnimationObject() throws IOException{
			animationObjects.add(this);
			initImage();
			initSettings();
			activateAnimation(this);
		}
		
		private void initSettings() {
			Random rnd = new Random();
			direction = rnd.nextBoolean();
			this.speed = rnd.nextInt(15) + 5;
		}

		private void initImage() throws IOException {
			Random rnd = new Random();
			int randomDigit = rnd.nextInt(10) + 1;
			image = ImageIO.read(this.getClass().getResourceAsStream("/Resources/carModel" + randomDigit + ".png"));			
		}
		
		private void moveRight(){
			x = 950;
			while(x >= -190){
				try {
					x -= 1;
					Thread.sleep(speed);
					repaint();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		private void moveLeft(){
			x = -190;
			
			while(x <= 950){
				try {
					x += 1;
					Thread.sleep(speed);
					repaint();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void draw(Graphics g){
			int yPositionOfCarModel = 427;
			g.drawImage(image, x, yPositionOfCarModel, null);
		}

		private void activateAnimation(AnimationObject animationObject) {
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					if(direction){
						moveRight();
					}
					else{
						AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
						tx.translate(-image.getWidth(null), 0);
						AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
						image = op.filter((BufferedImage) image, null);
						moveLeft();
					}
					killObject(animationObject);
				}
			});
			thread.start();		
		}		
	}
}
