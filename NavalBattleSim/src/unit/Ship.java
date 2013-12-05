package unit;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author bober
 */
public class Ship {

    public float posX, posY;				//pozycja statku
    int range, leftSideReloadingTimer, rightSideReloadingTimer, reloadingTime;	//zasi�g strza�u, licznik czasu prze�adowania lewej i prawej strony, czas potrzebny na prze�adowanie 
    int cannonballs, grapeshotes, chainshot;	//ilo�� kul zwyk�ych (najwi�ksze obra�enia dla kad�uba), kartaczy (najwi�ksze obra�enia dla za�ogi), i kul �a�cuchowych (na �agle)
    int cannones;			//ilo�� armat, do obsadzenia 1 armaty mo�na za�o�y�, �e potrzeba 2 ludzi		
    int hullState, crewState, sailesState;	//stan: kad�ubu, za�ogi, �agli (stan kad�ubu zatapia statek, stan za�ogi zmniejsza ilo�� obsadzonych armat (np na 1 armate potrzeba 2 za�ogant�w) do tego przydaje si� przy zak�adaniu czy robi� aborda� czy nie, stan o�aglowania zmiejsza szybko�� jachtu.
    int hullMass, crewAmount, sailesSize;	//pocz�tkowa masa kad�uba, liczno�� za�ogi, rozmiar �agli
    int hullLimit, crewLimit, sailesLimit; //limity, po kt�rych przekroczeniu mo�na tylko ucieka�, bo nie ma jak walczy�
    int damage=0;
    double velocity, course, wantedCourse, maxTurnInIteration;
    int speedConst[] = new int[360];
    LinkedList<Ship> alies, opponents;
    
    Ship target;
    double targetLastCourse;

    public int getDamage(){
        return damage;
    }
    enum Goal {

        ATTACK, BOARDING, ESCAPE
    };
    Goal goal = Goal.ATTACK;

    Ship findTarget() {	//wyliczanie celu bior�c pod uwag� odleg�o�� statku i szanse pokonania go, trzeba tylko zdecydowa�, czy robi� to na poziomie ka�dego statku r�wnie� czy na poziomie ca�ej armii
        return null;
    }

    private void countVelocity(int windDir, int windSpeed) {
        //velocity = speedConst[windDir > 0 ? windDir - direction : 360 + windDir - direction] * windSpeed * (sailes / sailesSize) * 100;	//za pomoc� tablicy wspolczynnikow dla danego kierunku wiatru, oraz stanu o�aglowania i si�y wiatru i co tam jeszcze przyjdzie na my�l ustalanie pr�dko�ci
    }
/**
 * Sprawdzanie, czy jest potrzebna korekta kursu i je�li tak to wyliczanie nowego kursu
 */
    void countCourse() {	//ustawianie kierunku poruszania si� statku
        if(course!=wantedCourse){
            double diff=wantedCourse-Math.PI;
            
            if (diff>course){
                if(course>wantedCourse && course-maxTurnInIteration<wantedCourse)
                    course=wantedCourse;
                else
                    course-=maxTurnInIteration;
                if (course<0){
                    course+=(2*Math.PI);
                }
            }else{
                if(course<wantedCourse && course+maxTurnInIteration<wantedCourse)
                    course=wantedCourse;
                else
                    course+=maxTurnInIteration;
                if(course>=2*Math.PI){
                    course-=(2*Math.PI);
                }
            }
        }
    }

    void chooseGoal() {		//decyzje statku, mo�na te� zrobi� na poziomie ca�ej armii
        if (crewState < crewLimit) {
            goal = Goal.ESCAPE;
        } else if (sailesState < sailesLimit) {
            goal = Goal.ESCAPE;
        }
    }
    
    /**
     * Zmiana pozycji statku na podstawie kursu i predkosci
     */
    public void move(){
        //countVelocity();
        countCourse();
        posX+=velocity*Math.cos(course);
        posY+=velocity*Math.sin(course);
        
    }
    
    double distanceTo(Ship target){
        return Math.sqrt(Math.pow(posX-target.posX,2)+Math.pow(posY-target.posY, 2));
    }
    
    public void attack(Ship target){
        if (distanceTo(target)<range){
            target.damage++;
        }
    }
    /**
     * Obieranie kursu na zadany cel. Na podstawie kursu celu wylicza jaki kurs musi obra�, aby po pewnym czasie cel znalaz� si� w polu ra�enia 
     * @param target - statek, kt�ry ma zosta� zaatakowany
     */
    public void takeCourse(Ship target){
        if(target.course!=targetLastCourse){
            double targetMovementX=target.velocity*Math.cos(target.course);
            double targetMovementY=target.velocity*Math.sin(target.course);
            double distance=distanceTo(target);
            double MposX,MposY,TposX=target.posX,TposY=target.posY;
            double dirToTarget;
            if(TposX>posX){
                if(TposY>posY){
                    dirToTarget=Math.atan((TposY-posY)/(TposX-posX));
                }else{
                    dirToTarget=Math.PI*2+Math.atan((TposY-posY)/(TposX-posX));
                }
            }else{
                dirToTarget=Math.PI+Math.atan((TposY-posY)/(TposX-posX));
            }
            int i=0;
            //System.out.println(Math.toDegrees(dirToTarget));
            while (distance>range && i<1000){
                  TposX+=targetMovementX;
                  TposY+=targetMovementY;
                  i+=1;
                  if(TposX>posX){
                      if(TposY>posY){
                          dirToTarget=Math.atan((TposY-posY)/(TposX-posX));
                      }else{
                          dirToTarget=Math.PI*2+Math.atan((TposY-posY)/(TposX-posX));
                      }
                  }else{
                      dirToTarget=Math.PI+Math.atan((TposY-posY)/(TposX-posX));
                  }

                  MposX=posX+i*velocity*Math.cos(dirToTarget);
                  MposY=posY+i*velocity*Math.sin(dirToTarget);
                  distance=Math.sqrt(Math.pow(MposX-TposX,2)+Math.pow(MposY-TposY, 2));
                  //System.out.println("My: "+MposX+" "+MposY+" "+Math.toDegrees(dirToTarget)+"   Target: "+TposX+" "+TposY+" distance "+distance);
                  //TimeUnit.SECONDS.sleep(1);
            }
            wantedCourse=dirToTarget;
            System.out.println("wanted: "+Math.toDegrees(wantedCourse)+" course: "+Math.toDegrees(course)+" iterations "+i);
        }
    }   
    
    public String toString(){
        StringBuilder str=new StringBuilder();
        str.append(" PosX=").append(posX).append(" PosY=").append(posY).append(" direction=").append(Math.toDegrees(course)).append(" demage=").append(damage);
        return str.toString();
    }
}