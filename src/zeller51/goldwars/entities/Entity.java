package zeller51.goldwars.entities;

import java.awt.Graphics;

import zeller51.goldwars.map.Map;

public abstract class Entity {
	
	protected float x, y;
	protected int identifier;
	
	public Entity(int identifier) {
		this.identifier = identifier;
	}
	
	public abstract void update(Map map);
	
	public void render(Graphics g, int offsetX, int offsetY) {
		
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public boolean isCollision() {
		return false;
	}
}
