package br.pucpr.table;

import java.util.List;

public class Table {
    private static final String INDENT = "                    ";

    public void print(TableData data) {
        if (data == null || isEmpty(data)) {
            System.out.println("ERRO: Lista de dados vazia ou nula.");
            return;
        }

        final var headers = data.getHeaders();
        final var rows = data.getRows();
        final var widths = columnWidths(headers);
        final var borderChar = data.getBorderChar();
        final var borderWidth = borderWidth(widths);

        var sb = new StringBuilder();

        // Borda superior e cabeçalho
        sb.repeat(borderChar, borderWidth).append("\n");
        appendRow(sb, headers, widths);
        sb.repeat(borderChar, borderWidth).append("\n");

        // Linhas de dados
        for (var row : rows) {
            if (row == null) {
                continue;
            }
            appendRow(sb, row, widths);
        }

        // Borda inferior
        sb.repeat(borderChar, borderWidth).append("\n");

        print(sb, data.isAlignRight());
    }

    private boolean isEmpty(TableData data) {
        return data.getHeaders() == null
                || data.getHeaders().isEmpty()
                || data.getRows() == null
                || data.getRows().isEmpty();
    }

    /**
     * A largura de cada coluna é dada exclusivamente pela largura do seu cabeçalho.
     * Valores de linha maiores que o cabeçalho são truncados por {@link #fit}.
     */
    private int[] columnWidths(List<String> headers) {
        var widths = new int[headers.size()];
        for (var i = 0; i < headers.size(); i++) {
            widths[i] = headers.get(i).length();
        }
        return widths;
    }

    private int borderWidth(int[] widths) {
        // "| " + conteúdo + " " para cada coluna, mais o "|" final
        var total = 1;
        for (var width : widths) {
            total += width + 3;
        }
        return total;
    }

    private void appendRow(StringBuilder sb, List<String> values, int[] widths) {
        sb.append("|");
        for (var i = 0; i < widths.length; i++) {
            var value = i < values.size() && values.get(i) != null ? values.get(i) : "";
            sb.append(String.format(" %-" + widths[i] + "s |", fit(value, widths[i])));
        }
        sb.append("\n");
    }

    private String fit(String value, int width) {
        if (value.length() <= width) {
            return value;
        }
        if (width <= 3) {
            return value.substring(0, width);
        }
        return value.substring(0, width - 3) + "...";
    }

    private void print(StringBuilder sb, boolean alignRight) {
        if (!alignRight) {
            System.out.print(sb);
            return;
        }
        for (var line : sb.toString().split("\n")) {
            System.out.println(INDENT + line);
        }
    }
}