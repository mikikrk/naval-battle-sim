package unit;

import java.util.LinkedList;

/**
 *
 * @author bober
 */
public class Ship {

    int posX, posY;				//pozycja statku
    int range, leftSideReloadingTime, rightSideReloadingTime, reloadTimer;	//zasi�g strza�u, czas potrzebny na prze�adowanie, licznik czasu prze�adowania
    int cannonballs, grapeshotes, chainshot;	//ilo�� kul zwyk�ych (najwi�ksze obra�enia dla kad�uba), kartaczy (najwi�ksze obra�enia dla za�ogi), i kul �a�cuchowych (na �agle)
    int cannones;			//ilo�� armat, do obsadzenia 1 armaty mo�na za�o�y�, �e potrzeba 2 ludzi		
    int hull, crew, sailes;	//stan: kad�ubu, za�ogi, �agli (stan kad�ubu zatapia statek, stan za�ogi zmniejsza ilo�� obsadzonych armat (np na 1 armate potrzeba 2 za�ogant�w) do tego przydaje si� przy zak�adaniu czy robi� aborda� czy nie, stan o�aglowania zmiejsza szybko�� jachtu.
    int hullMass, crewAmount, sailesSize;	//pocz�tkowa masa kad�uba, liczno�� za�ogi, rozmiar �agli
    int hullLimit, crewLimit, sailesLimit; //limity, po kt�rych przekroczeniu mo�na tylko ucieka�, bo nie ma jak walczy�
    int velocity, direction;
    int speedConst[] = new int[360];
    LinkedList<Ship> alies, opponents;

    enum Goal {

        FIRE, BOARDING, ESCAPE
    };
    Goal goal = Goal.FIRE;

    Ship findTarget() {	//wyliczanie celu bior�c pod uwag� odleg�o�� statku i szanse pokonania go, trzeba tylko zdecydowa�, czy robi� to na poziomie ka�dego statku r�wnie� czy na poziomie ca�ej armii
        return null;
    }

    void countVelocity(int windDir, int windSpeed) {
        velocity = speedConst[windDir > 0 ? windDir - direction : 360 + windDir - direction] * windSpeed * (sailes / sailesSize) * 100;	//za pomoc� tablicy wspolczynnikow dla danego kierunku wiatru, oraz stanu o�aglowania i si�y wiatru i co tam jeszcze przyjdzie na my�l ustalanie pr�dko�ci
    }

    void setDirection(int direction) {	//ustawianie kierunku poruszania si� statku
        this.direction = direction;
    }

    void chooseGoal() {		//decyzje statku, mo�na te� zrobi� na poziomie ca�ej armii
        if (crew < crewLimit) {
            goal = Goal.ESCAPE;
        } else if (sailes < sailesLimit) {
            goal = Goal.ESCAPE;
        }
    }
}