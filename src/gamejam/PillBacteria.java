/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Kevin
 */
public class PillBacteria extends Intruder {
   public static final SpriteSet SP = SpriteSet.load("resources/images/pills.txt");
   protected PillBacteria parent;
   public static final double DIST_CUTOFF = SP.getSpriteWidth();

   private static final double aggression = 2.0;

   public PillBacteria() {
      x = Math.random() * Engine.getWidth();
      y = Math.random() * Engine.getHeight();
      maxVel = .5;
      vel = Math.random() * maxVel;
      theta = fTheta = Math.random() * Math.PI * 2;
      col = ColorType.values()[(int) (Math.random() * 4)];
      sprite = SP;
      ratUp = 3;
      ratDown = 7;
      primeDist = sprite.getSpriteWidth()/2;
      hp = 500;
   }

   public enum ColorType {

      green, lime, cyan, violet
   };
   ColorType col;

   public PillBacteria(double tx, double ty, double tTheta, ColorType c) {
      col = c;
      x = tx;
      y = ty;
      theta = tTheta;
      hp = 500;
   }

   public Point head(){
      return new Point((int)(x+Math.cos(theta)*sprite.getSpriteWidth()/2), (int)(y+Math.sin(theta)*sprite.getSpriteWidth()/2));
   }
   
   public Point tail(){
      return new Point((int)(x-Math.cos(theta)*sprite.getSpriteWidth()/2), (int)(y-Math.sin(theta)*sprite.getSpriteWidth()/2));
   }
   
   @Override
   public void render(Graphics2D g){
      sprite.enact(col.name());
      super.render(g);
   }
   
   @Override
   public void act() {
      if(parent != null){
         if(parent.disposable){
            parent = null;
         }
      }
      if (parent == null){
         Tower nTower = Engine.getBloodVessel().nearestTower(this);
         PillBacteria nPill = Engine.getBloodVessel().nearestPill(this);
      
         if(dist(nPill)<DIST_CUTOFF){
            parent = nPill;
            target = nPill;
         }else if (nPill != null && nTower != null) {
            if (dist(nPill) * aggression < dist(nTower)) {
               target = nPill;
            } else {
               target = nTower;
            }
         } else if (nPill != null) {
            target = nPill;
         }
      }else if(parent != this){
         target = parent;
      }else{
         target = Engine.getBloodVessel().nearestTower(this);
      }
      super.act();
   }
}
