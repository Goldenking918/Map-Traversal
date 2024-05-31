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
  private StringBuilder sb = new StringBuilder();

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    // add the countries to a LinkedHashSet.
    for (String s : countries) {
      String[] info = s.split(",");
      Country country = new Country(info[0], info[1], info[2]);
      infoCountries.add(country);
    }
    for (String s : adjacencies) {
      String[] info = s.split(",");
      List<Country> countriesList = new LinkedList<>();

      // adds the countries to a list in order.
      for (String countryName : info) {
        for (Country c : infoCountries) {
          if (c.getName().equals(countryName)) {
            countriesList.add(c);
            break;
          }
        }
      }

      // adds the edges to the graph in order.
      for (int i = 1; i < countriesList.size(); i++) {
        graph.addEdge(countriesList.get(0), countriesList.get(i));
      }
    }
  }

  /**
   * this method is invoked when the user run the command info-country. It displays the name,
   * contient and tax of the country
   */
  public void showInfoCountry() {
    MessageCli.INSERT_COUNTRY.printMessage();
    // while loop to keep asking the user to enter a valid country.
    while (true) {
      try {
        // check if the country is valid or not and print the country information.
        Country countryInfo = countryCheck();
        MessageCli.COUNTRY_INFO.printMessage(
            countryInfo.getName(), countryInfo.getContinent(), countryInfo.getTax());
        return;
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(country);
      }
    }
  }

  /**
   * checks the country inputted and whether or not its a valid country in the map.
   *
   * @return the country object if the country is valid.
   */
  public Country countryCheck() throws InvalidCountryException {
    this.country = Utils.scanner.nextLine();

    // capatalise the first letter of each word in the country name.
    String[] words = country.split("\\s+");
    sb = new StringBuilder();
    for (String word : words) {
      if (!word.isEmpty()) {
        String capital = word.substring(0, 1).toUpperCase();
        String other = word.substring(1);
        sb.append(capital).append(other).append(" ");
      }
    }
    country = sb.toString().trim();
    System.out.println(country);
    // check if the country is valid or not.
    for (Country s : infoCountries) {
      if (country.equals(s.getName())) {
        return s;
      }
    }
    // if the country is not valid throw an exception.
    throw new InvalidCountryException();
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    // while loop to keep asking the user to enter a valid country.
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
    List<Country> path = graph.findShortestPath(startCountry, endCountry);
    // gets the tax and the continents of the path.
    tax -= Integer.parseInt(startCountry.getTax());
    for (Country c : path) {
      continents.add(c.getContinent());
      tax += Integer.parseInt(c.getTax());
    }
    // print the path, the continents and the tax.
    MessageCli.ROUTE_INFO.printMessage(path.toString());
    MessageCli.CONTINENT_INFO.printMessage(continents.toString());
    MessageCli.TAX_INFO.printMessage(tax.toString());
  }
}
