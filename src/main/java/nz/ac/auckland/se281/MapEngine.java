package nz.ac.auckland.se281;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {
  Set<Country> infoCountries = new LinkedHashSet<>();

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    for (String s : countries) {
      String[] info = s.split(",");
      Country country = new Country(info[0], info[1], info[2]);
      infoCountries.add(country);
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    MessageCli.INSERT_COUNTRY.printMessage();
    String country = Utils.scanner.nextLine();
    for (Country s : infoCountries) {
      if (country.equals(s.getName())) {
        System.out.println(s);
        return;
      }
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
