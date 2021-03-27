package main.firefighters;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.api.City;
import main.api.CityNode;
import main.api.FireDispatch;
import main.api.Firefighter;
import main.api.exceptions.NoFireFoundException;


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
    if (burningBuildings == null || burningBuildings.length ==0 ) return;

    dispatchFirefightersFasterButSubOptimally(burningBuildings);
  }

  /**
   * TODO: Write tests and implement the function to get the permutation that minimizes distance travelled
   *
   * Dispatch firefighters 100% optimally. Generate all possible firefighter permutations and calculate the collective
   * distance travelled for each firefighter being sent to each burning building.
   * Dispatch firefighters using the firefighter permutation that minimizes the total distance traveled.
   * @param burningBuildings CityNodes with burning buildings
   */
  private void dispatchFirefightersOptimallyButSlow(CityNode... burningBuildings) {

    // Generate all possible permutations of firefightersRoster.
    List<Firefighter[]> allFirefighterPermutations = FireDispatchUtils.permute(firefightersRoster.toArray(new Firefighter[0]));

    // Get the permutation that minimizes that total distance travelled for each firefighter
    Firefighter[] bestPermutation = FireDispatchUtils.getPermutationThatMinimizesDistance(allFirefighterPermutations);

    // Send the ith firefighter to the ith burning building
    for (int i = 0; i < bestPermutation.length; i++) {
      dispatchFirefighter(bestPermutation[i], burningBuildings[i]);
    }

  }

  /**
   * Dispatches the firefighter to the CityNode burningNode and the fire is extinguished.
   * Firefighters location and distance travelled are updated.
   *
   * @param firefighter Firefighter to dispatch
   * @param burningNode CityNode that has a burning building
   */
  private void dispatchFirefighter(final Firefighter firefighter, final CityNode burningNode) {
    firefighter.moveToLocation(burningNode, getDistanceBetweenFirefighterAndCityNode(firefighter, burningNode));
    try {
      city.getBuilding(burningNode).extinguishFire();
    } catch (NoFireFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Dispatch firefighters greedily, iterating through burningBuildings and sending the closest firefighter.
   * If no firefighter is available, do nothing.
   * @param burningBuildings CityNodes with burning buildings
   */
  private void dispatchFirefightersFasterButSubOptimally(CityNode... burningBuildings) {
    for (CityNode nodeWithBurningBuilding : burningBuildings) {
      getClosestFirefighter(nodeWithBurningBuilding)
              .ifPresent(firefighter -> dispatchFirefighter(firefighter, nodeWithBurningBuilding));
    }
  }

  /**
   * Firefighter that minimizes distance to burningBuilding
   * @param burningBuilding
   * @return Firefighter that minimizes distance
   */
  private Optional<Firefighter> getClosestFirefighter(CityNode burningBuilding) {

    if(firefightersRoster.isEmpty()) return Optional.empty();

    int minDistance = Integer.MAX_VALUE;
    int currDistance;
    Firefighter closestFirefighter = firefightersRoster.get(0);

    for (Firefighter firefighter : firefightersRoster) {
      currDistance = getDistanceBetweenFirefighterAndCityNode(firefighter, burningBuilding);
      if (currDistance < minDistance) {
        minDistance = currDistance;
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
