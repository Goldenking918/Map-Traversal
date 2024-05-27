package nz.ac.auckland.se281;

public class Country {
  private String name;
  private String continent;
  private String tax;

  public Country(String name, String continent, String tax) {
    this.name = name;
    this.continent = continent;
    this.tax = tax;
  }

  @Override
  public String toString() {
    return MessageCli.COUNTRY_INFO.getMessage(name, continent, tax);
  }

}
