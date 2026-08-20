package br.pucpr.table;

import java.util.List;

public interface TableData {
    List<String> getHeaders();

    List<List<String>> getRows();

    String getBorderChar();

    boolean isAlignRight();
}