package br.pucpr.table.model;

/**
 * Decorator que adiciona paginação a qualquer implementação de TableData.
 * Dessa forma, não precisamos alterar a classe Table e também conseguimos
 * utilizar a paginação com futuras implementações de TableData.
 */
public final class PaginatedTableData implements TableData {

    private final TableData data;
    private final int pageSize;
    private int currentPage;

    public PaginatedTableData(TableData data, int pageSize) {

        if (data == null) {
            throw new IllegalArgumentException(
                    "Os dados não podem ser nulos");
        }

        if (pageSize <= 0) {
            throw new IllegalArgumentException(
                    "O tamanho da página deve ser maior que zero");
        }

        this.data = data;
        this.pageSize = pageSize;
        this.currentPage = 1;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageCount() {

        if (data.rowCount() == 0) {
            return 0;
        }

        return (data.rowCount() + pageSize - 1) / pageSize;
    }

    public boolean hasNextPage() {
        return currentPage < getPageCount();
    }

    public boolean hasPreviousPage() {
        return currentPage > 1;
    }

    public PaginatedTableData nextPage() {

        if (hasNextPage()) {
            currentPage++;
        }

        return this;
    }

    public PaginatedTableData previousPage() {

        if (hasPreviousPage()) {
            currentPage--;
        }

        return this;
    }

    public PaginatedTableData goToPage(int page) {

        final var pageCount = getPageCount();

        if (pageCount == 0) {

            if (page != 1) {
                throw new IllegalArgumentException(
                        "Uma tabela vazia aceita apenas a página 1");
            }

        } else if (page < 1 || page > pageCount) {

            throw new IllegalArgumentException(
                    "A página deve estar entre 1 e "
                            + pageCount
                            + ", mas foi informada a página "
                            + page);
        }

        currentPage = page;

        return this;
    }

    @Override
    public int rowCount() {

        final var remainingRows =
                data.rowCount() - firstRowIndex();

        return Math.max(
                0,
                Math.min(pageSize, remainingRows));
    }

    @Override
    public int colCount() {
        return data.colCount();
    }

    @Override
    public String header(int col) {
        return data.header(col);
    }

    @Override
    public String get(int row, int col) {

        if (row < 0 || row >= rowCount()) {
            throw new IndexOutOfBoundsException(
                    "Linha fora da página atual: " + row);
        }

        return data.get(
                firstRowIndex() + row,
                col);
    }

    private int firstRowIndex() {
        return (currentPage - 1) * pageSize;
    }
}