package br.pucpr.planet;

import br.pucpr.table.TableData;
import br.pucpr.user.Theme;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapta uma lista de {@link Planet} para a interface {@link TableData}, permitindo que
 * a classe {@link br.pucpr.table.Table} a imprima sem conhecer detalhes de Planet.
 */
public class PlanetasPrinter implements TableData {
  private static final List<String> HEADERS =
          List.of("Nome", "Diâmetro", "Dist. sol (km)", "Dist. sol (ua)", "Tipo");

  private final List<Planet> planets;
  private final boolean alignRight;
  private final Theme theme;

  public PlanetasPrinter(List<Planet> planets, boolean alignRight, Theme theme) {
    this.planets = planets;
    this.alignRight = alignRight;
    this.theme = theme;
  }

  @Override
  public List<String> getHeaders() {
    return HEADERS;
  }

  @Override
  public List<List<String>> getRows() {
    var rows = new ArrayList<List<String>>();
    if (planets == null) {
      return rows;
    }
    for (var planet : planets) {
      if (planet == null) {
        continue;
      }
      rows.add(
              List.of(
                      formatName(planet.name()),
                      String.format("%,.1f", planet.diameterKm()),
                      String.format("%,d", planet.sunDistanceKm()),
                      String.format("%.2f", Planet.kmToAu(planet.sunDistanceKm())),
                      formatType(planet.type())));
    }
    return rows;
  }

  @Override
  public String getBorderChar() {
    return theme.getBorderChar();
  }

  @Override
  public boolean isAlignRight() {
    return alignRight;
  }

  private static String formatName(String name) {
    // O truncamento para caber na coluna é responsabilidade da classe Table,
    // que conhece a largura de cada coluna (definida pelo cabeçalho).
    return name == null || name.isEmpty() ? "NÃO INFORMADO" : name;
  }

  private static String formatType(PlanetType type) {
    return switch (type) {
      case ROCK -> "Rochoso";
      case GAS -> "Gasoso";
      case ICE -> "Gelado";
      case DWARF -> "Anão";
    };
  }
}