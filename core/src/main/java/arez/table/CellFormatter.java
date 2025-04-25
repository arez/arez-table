package arez.table;

import javax.annotation.Nonnull;

/**
 * Functional interface for formatting and rendering cell data in a table.
 *
 * <p>This interface provides a method to transform and format the data from
 *  * a specific table cell into a desired renderable format.</p>
 *
 * @param <TableDataT>  the type of the table data containing the rows and their cells.
 * @param <RowDataT>    the type representing a single row in the table.
 * @param <CellDataT>   the type representing the data for a specific cell.
 * @param <RenderTypeT> the type representing the transformed or rendered data for the cell.
 */
@FunctionalInterface
public interface CellFormatter<TableDataT, RowDataT, CellDataT, RenderTypeT>
{
  RenderTypeT get( @Nonnull TableDataT tableData, @Nonnull RowDataT rowData, CellDataT cellData );
}
