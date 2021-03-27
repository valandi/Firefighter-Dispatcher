package main.firefighters;

import java.util.ArrayList;
import java.util.List;

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
    firefightersRoster = new ArrayList<>();
  }

  @Override
  public void setFirefighters(int numFirefighters) {
    for(int i = 0; i < numFirefighters; i++) {
      firefightersRoster.add(new FirefighterImpl(this.stationLocation));
    }
  }

  @Override
  public List<Firefighter> getFirefighters() {
    return firefightersRoster;
  }

  @Override
  public void dispatchFirefighers(CityNode... burningBuildings) {
    return;
  }
}
