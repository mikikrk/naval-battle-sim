package unit;

/**
 *
 * @author Miki
 */
public class TestShip extends Ship {
    public TestShip(float x, float y, double course){
        this.posX=x;
        this.posY=y;
        this.course=Math.toRadians(course);
        this.wantedCourse=this.course;
        this.maxTurnInIteration=Math.toRadians(5);
        
        this.velocity=0.05;
        
        this.range=10;
        this.cannonballs=100;
        this.chainshot=100;
        this.grapeshotes=100;
        
        this.crewAmount=100;
        this.hullMass=100;
        this.sailesSize=100;
        this.cannones=20;
        this.goal=Goal.ATTACK;
        this.reloadingTime=5;
    }
}
