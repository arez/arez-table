package arez.table;

import javax.annotation.Nonnull;

/**
 * Accessor to access underlying data representation.
 * This accessor is primarily used for sorting, filtering, grouping, etc.
 *
 * @param <RowDataT> The type representing a row element.
 * @param <CellDataT> The type representing the cell element.
 */
@FunctionalInterface
public interface CellDataAccessor<RowDataT, CellDataT>
{
  /**
   * Retrieves the cell data associated with the specified row data.
   *
   * @param rowData the row data for which the corresponding cell data is to be retrieved; must not be null.
   * @return the cell data associated with the provided row data.
   */
  CellDataT get( @Nonnull RowDataT rowData );
}
