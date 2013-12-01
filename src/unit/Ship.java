package unit;

import java.util.LinkedList;

/**
 *
 * @author bober
 */
public class Ship {

    int posX, posY;				//pozycja statku
    int range, leftSideReloadingTime, rightSideReloadingTime, reloadTimer;	//zasiêg strza³u, czas potrzebny na prze³adowanie, licznik czasu prze³adowania
    int cannonballs, grapeshotes, chainshot;	//ilo¶æ kul zwyk³ych (najwiêksze obra¿enia dla kad³uba), kartaczy (najwiêksze obra¿enia dla za³ogi), i kul ³añcuchowych (na ¿agle)
    int cannones;			//ilo¶æ armat, do obsadzenia 1 armaty mo¿na za³o¿yæ, ¿e potrzeba 2 ludzi		
    int hull, crew, sailes;	//stan: kad³ubu, za³ogi, ¿agli (stan kad³ubu zatapia statek, stan za³ogi zmniejsza ilo¶æ obsadzonych armat (np na 1 armate potrzeba 2 za³ogantów) do tego przydaje siê przy zak³adaniu czy robiæ aborda¿ czy nie, stan o¿aglowania zmiejsza szybko¶æ jachtu.
    int hullMass, crewAmount, sailesSize;	//pocz±tkowa masa kad³uba, liczno¶æ za³ogi, rozmiar ¿agli
    int hullLimit, crewLimit, sailesLimit; //limity, po których przekroczeniu mo¿na tylko uciekaæ, bo nie ma jak walczyæ
    int velocity, direction;
    int speedConst[] = new int[360];
    LinkedList<Ship> alies, opponents;

    enum Goal {

        FIRE, BOARDING, ESCAPE
    };
    Goal goal = Goal.FIRE;

    Ship findTarget() {	//wyliczanie celu bior±c pod uwagê odleg³o¶æ statku i szanse pokonania go, trzeba tylko zdecydowaæ, czy robiæ to na poziomie ka¿dego statku równie¿ czy na poziomie ca³ej armii
        return null;
    }

    void countVelocity(int windDir, int windSpeed) {
        velocity = speedConst[windDir > 0 ? windDir - direction : 360 + windDir - direction] * windSpeed * (sailes / sailesSize) * 100;	//za pomoc± tablicy wspolczynnikow dla danego kierunku wiatru, oraz stanu o¿aglowania i si³y wiatru i co tam jeszcze przyjdzie na my¶l ustalanie prêdko¶ci
    }

    void setDirection(int direction) {	//ustawianie kierunku poruszania siê statku
        this.direction = direction;
    }

    void chooseGoal() {		//decyzje statku, mo¿na te¿ zrobiæ na poziomie ca³ej armii
        if (crew < crewLimit) {
            goal = Goal.ESCAPE;
        } else if (sailes < sailesLimit) {
            goal = Goal.ESCAPE;
        }
    }
}