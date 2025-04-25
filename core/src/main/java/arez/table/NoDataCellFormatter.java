package arez.table;

import javax.annotation.Nonnull;

/**
 * The interface for formatting a cell in a column where there is no row data into a specific renderable format.
 * Typically used to render a footer or header for a column.
 *
 * @param <TableDataT>  the type of the table data that needs to be formatted.
 * @param <RenderTypeT> the type of the rendered output.
 */
@FunctionalInterface
public interface NoDataCellFormatter<TableDataT, RenderTypeT>
{
  RenderTypeT get( @Nonnull TableDataT tableData );
}
