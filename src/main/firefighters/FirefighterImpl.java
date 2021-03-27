package main.firefighters;

import main.api.CityNode;
import main.api.Firefighter;

public class FirefighterImpl implements Firefighter {

  private CityNode location;
  private int totalDistanceTraveled;

  public FirefighterImpl(CityNode location) {
    this.location = location;
    this.totalDistanceTraveled = 0;
  }
  @Override
  public CityNode getLocation() {
    return this.location;
  }

  @Override
  public int distanceTraveled() {
    return this.totalDistanceTraveled;
  }

  public void moveToLocation(CityNode newLocation, int distanceTravelled) {
    this.totalDistanceTraveled += distanceTravelled;
    this.location = newLocation;
  }
}
