package nz.ac.auckland.se281;

/** Represents a country with its name, continent and tax in the map. */
public class Country {
  private String name;
  private String continent;
  private String tax;

  /**
   * Constructs a country with its name, continent and tax.
   *
   * @param name name of the country
   * @param continent continent country is located in
   * @param tax cost for entering the country
   */
  public Country(String name, String continent, String tax) {
    this.name = name;
    this.continent = continent;
    this.tax = tax;
  }

  public String getName() {
    return name;
  }

  public String getContinent() {
    return continent;
  }

  public String getTax() {
    return tax;
  }

  @Override
  public String toString() {
    return name;
  }
}
