package arez.table;

/**
 * The column kind describes the overall category of {@link ColumnDef}s.
 */
public enum ColumnKind
{
  /**
   * Accessor columns have an underlying data model which means they can be
   * sorted, filtered, grouped, etc.
   */
  ACCESSOR,
  /**
   * Display columns do not have a data model which means they cannot be sorted,
   * filtered, etc., but they can be used to display arbitrary content in the table.
   * For example, a row actions button, checkbox, expander, etc.
   */
  DISPLAY,
  /**
   * Group columns do not have a data model, so they too cannot be sorted, filtered, etc.
   * Group columns are used to group other columns together. It's common to define a header
   * or footer for a column group.
   */
  GROUPING
}
