/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

/**
 *
 * @author Clem
 */
public class Basophil extends Tower {
    public Basophil()
    {
        x = Math.random()*Engine.getWidth();
        y = Math.random() * Engine.getHeight();
        maxVel = .68;
        vel = 0;
        fTheta =Math.random() * Math.PI * 2;
        theta = 0;
        sprite = null;
        ratUp=2;
        ratDown=8;
        primeDist = 100;
        hp=430;
    }
    
    public Basophil(double placewidth, double placeheight)
    {
        x = placewidth;
        y = placeheight;
        maxVel = .68;
        vel = 0;
        fTheta =Math.random() * Math.PI * 2;
        theta = 0;
        sprite = null;
        ratUp=2;
        ratDown=8;
        primeDist = 100;
        hp=430;
    }
    
    public void act()
    {
        if (target != null && target.disposable)
            target=null;
        if (target==null)
        {
            if ((target = Engine.getBloodVessel().nearestIntruder(this))!=null)
                super.act();
            else
            {
                targetVel = 0;
            }
        }
        //hits the target lightly, then calls nearby towers for help
        if (target != null && Helper.intersects(this.getBounds(), target.getBounds()))
        {
            target.damage(7);
            if (!target.disposable)
            {
                Tower[] heyyou = Engine.getBloodVessel().towersNearby(this, 200);
                for (int j=0; j<heyyou.length; j++)
                {
                    heyyou[j].target=this.target;
                }
            }
        }
    }
}
