package main.firefighters;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import main.api.City;
import main.api.CityNode;
import main.api.FireDispatch;
import main.api.Firefighter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class FireDispatchImpl implements FireDispatch {

  private final City city;
  private final CityNode stationLocation;
  private final List<Firefighter> firefightersRoster;

  public FireDispatchImpl(City city) {
    this.city = city;
    this.stationLocation = city.getFireStation().getLocation();
    this.firefightersRoster = new ArrayList<>();
  }

  @Override
  public void setFirefighters(int numFirefighters) {
    for(int i = 0; i < numFirefighters; i++) {
      firefightersRoster.add(new FirefighterImpl(this.stationLocation));
    }
  }

  @Override
  public List<Firefighter> getFirefighters() {
    return this.firefightersRoster;
  }

  @Override
  public void dispatchFirefighers(CityNode... burningBuildings) {
    return;
  }

  /**
   * Dispatch firefighters 100% optimally. Generate all possible firefighter permutations and calculate the collective
   * distance travelled for each firefighter being sent to each burning building.
   * Dispatch firefighters using the firefighter permutation that minimizes the total distance traveled.
   * @param burningBuildings CityNodes with burning buildings
   */
  private void dispatchFirefightersOptimallysButSlow(CityNode... burningBuildings) {

  }

  /**
   * Dispatch firefighters greedily, iterating through burningBuildings and sending the closest firefighter
   * @param burningBuildings CityNodes with burning buildings
   */
  private void dispatchFirefightersFasterButSubOptimally(CityNode... burningBuildings) {
    throw new NotImplementedException();
  }

  /**
   * Firefighter that minimzes distance to burningBuilding
   * @param burningBuilding
   * @return Firefighter that minimzes distance
   */
  private Optional<Firefighter> getClosestFirefighter(CityNode burningBuilding) {

    if(firefightersRoster.isEmpty()) return Optional.empty();

    int minDistance = Integer.MAX_VALUE;
    int currDistance;
    Firefighter closestFirefighter = firefightersRoster.get(0);

    for (Firefighter firefighter : firefightersRoster) {
      currDistance = getDistanceBetweenFirefighterAndCityNode(firefighter, burningBuilding);
      if (currDistance < minDistance) {
        closestFirefighter = firefighter;
      }
    }

    return Optional.of(closestFirefighter);
  }

  private int getDistanceBetweenFirefighterAndCityNode(Firefighter firefighter, CityNode cityNode) {
    return Math.abs(firefighter.getLocation().getX() - cityNode.getX()) +
            Math.abs(firefighter.getLocation().getY() - cityNode.getY());
  }


}
