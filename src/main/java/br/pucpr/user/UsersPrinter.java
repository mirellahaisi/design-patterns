package br.pucpr.user;

import br.pucpr.table.TableData;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapta uma lista de {@link User} para a interface {@link TableData}, permitindo que
 * a classe {@link br.pucpr.table.Table} a imprima sem conhecer detalhes de User.
 */
public class UsersPrinter implements TableData {
  private static final List<String> HEADERS = List.of("ID", "NOME", "EMAIL", "CPF");

  private final List<User> users;
  private final boolean maskCpf;
  private final boolean alignRight;
  private final Theme theme;

  public UsersPrinter(List<User> users, boolean maskCpf, boolean alignRight, Theme theme) {
    this.users = users;
    this.maskCpf = maskCpf;
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
    if (users == null) {
      return rows;
    }
    for (var user : users) {
      if (user == null) {
        continue;
      }
      rows.add(
              List.of(
                      formatId(user.id()),
                      formatName(user),
                      validateAndFormatEmail(user.email()),
                      formatCpf(user.cpf(), maskCpf)));
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

  private static String formatId(Long id) {
    return id != null ? id.toString() : "0";
  }

  private static String formatCpf(String cpf, boolean mask) {
    if (cpf == null || cpf.length() != 11) {
      return "CPF INVÁLIDO";
    }
    if (mask) {
      return "***." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-**";
    }
    return cpf.substring(0, 3)
            + "."
            + cpf.substring(3, 6)
            + "."
            + cpf.substring(6, 9)
            + "-"
            + cpf.substring(9, 11);
  }

  private static String validateAndFormatEmail(String email) {
    return email == null || !email.contains("@") ? "INVÁLIDO" : email;
  }

  private static String formatName(User user) {
    var name = user.name();
    // O truncamento para caber na coluna é responsabilidade da classe Table,
    // que conhece a largura de cada coluna (definida pelo cabeçalho).
    return name == null || name.isEmpty() ? "NÃO INFORMADO" : name;
  }
}