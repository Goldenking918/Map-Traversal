package nz.ac.auckland.se281;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {
  private Set<Country> infoCountries = new LinkedHashSet<>();
  private String country;
  private Graph<Country> graph = new Graph<>();
  private Country startCountry;
  private Country endCountry;
  private HashSet<String> continents = new LinkedHashSet<>();
  private Integer tax = 0;

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
    for (String s : adjacencies) {
      String[] info = s.split(",");
      List<Country> countriesList = new LinkedList<>();

      for (String countryName : info) {
        for (Country c : infoCountries) {
          if (c.getName().equals(countryName)) {
            countriesList.add(c);
            break;
          }
        }
      }

      for (int i = 1; i < countriesList.size(); i++) {
        graph.addEdge(countriesList.get(0), countriesList.get(i));
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    MessageCli.INSERT_COUNTRY.printMessage();
    while (true) {
      try {
        Country countryInfo = countryCheck();
        MessageCli.COUNTRY_INFO.printMessage(
            countryInfo.getName(), countryInfo.getContinent(), countryInfo.getTax());
        return;
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(country);
      }
    }
  }

  public Country countryCheck() {
    this.country = Utils.scanner.nextLine();
    System.out.println(country);

    String[] words = country.split("\\s+");
    country = "";
    for (String word : words) {
      if (!word.isEmpty()) {
        String capital = word.substring(0, 1).toUpperCase();
        String other = word.substring(1);
        country += capital + other + " ";
      }
    }
    country = country.trim();
    for (Country s : infoCountries) {
      if (country.equals(s.getName())) {
        return s;
      }
    }
    throw new InvalidCountryException();
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    MessageCli.INSERT_SOURCE.printMessage();
    while (true) {
      try {
        startCountry = countryCheck();
        break;
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(country);
      }
    }
    MessageCli.INSERT_DESTINATION.printMessage();
    while (true) {
      try {
        endCountry = countryCheck();
        break;
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(country);
      }
    }
    if (startCountry == endCountry) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }
    List<Country> path = graph.shortestPath(startCountry, endCountry);
    tax -= Integer.parseInt(startCountry.getTax());
    for (Country c : path) {
      continents.add(c.getContinent());
      tax += Integer.parseInt(c.getTax());
    }
    MessageCli.ROUTE_INFO.printMessage(path.toString());
    MessageCli.CONTINENT_INFO.printMessage(continents.toString());
    MessageCli.TAX_INFO.printMessage(tax.toString());
  }
}
