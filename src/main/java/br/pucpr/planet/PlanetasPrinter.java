package br.pucpr.planet;

import br.pucpr.user.Theme;
import java.util.ArrayList;

public class PlanetasPrinter {
  private static final double KM_PER_UA = 149_600_000.0; // Distância Terra-Sol

  public void print(ArrayList<Planet> planets, boolean alignRight, Theme theme) {
    if (planets == null || planets.isEmpty()) {
      System.out.println("ERRO: Lista de planetas vazia ou nula.");
      return;
    }
    final var borderChar = theme.getBorderChar();

    // Borda superior e cabeçalho
    final var BORDER_WIDTH = 75;
    var sb = new StringBuilder();
    sb.repeat(borderChar, BORDER_WIDTH).append("\n");
    sb.append(
        String.format(
            "| %-10s | %-12s | %-15s | %-14s | %-8s |%n",
            "Nome", "Diâmetro", "Dist. sol (km)", "Dist. sol (ua)", "Tipo"));
    sb.repeat(borderChar, BORDER_WIDTH).append("\n");
    for (var planet : planets) {
      if (planet == null) {
        continue;
      }
      sb.append(
          String.format(
              "| %-10s | %-12s | %-15s | %-14s | %-8s |%n",
              formatName(planet.name()),
              formatDiameter(planet.diameterKm()),
              formatSunDistanceKm(planet.sunDistanceKm()),
              formatSunDistanceUa(planet.sunDistanceKm()),
              formatType(planet.type())));
    }
    // Borda inferior
    sb.repeat(borderChar, BORDER_WIDTH).append("\n");

    // Espaçamento
    if (alignRight) {
      var lines = sb.toString().split("\n");
      for (var line : lines) {
        System.out.println("                    " + line);
      }
    } else {
      System.out.print(sb);
    }
  }

  private static String formatName(String name) {
    return name == null || name.isEmpty() ? "NÃO INFORMADO" : name;
  }

  private static String formatDiameter(double diameterKm) {
    return String.format("%,.1f", diameterKm);
  }

  private static String formatSunDistanceKm(long sunDistanceKm) {
    return String.format("%,d", sunDistanceKm);
  }

  private static String formatSunDistanceUa(long sunDistanceKm) {
    return String.format("%,.2f", sunDistanceKm / KM_PER_UA);
  }

  private static String formatType(PlanetType type) {
    return switch (type) {
      case ROCK -> "Rochoso";
      case GAS -> "Gososo";
      case ICE -> "Gelado";
      case DWARF -> "Anão";
    };
  }
}
