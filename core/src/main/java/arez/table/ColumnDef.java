package arez.table;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.jetbrains.annotations.VisibleForTesting;
import static org.realityforge.braincheck.Guards.*;

/**
 * Column definition for the table.
 *
 * <p>These entities are responsible for:</p>
 * <ul>
 *   <li>Building the underlying data model that will be used for everything including sorting, filtering, grouping, etc.</li>
 *   <li>Formatting the data model into what will be displayed in the table</li>
 *   <li>Creating header groups, headers and footers</li>
 *   <li>Creating columns for display-only purposes, eg. action buttons, checkboxes, expanders, sparklines, etc.</li>
 * </ul>
 */
public final class ColumnDef<TableDataT, RowDataT, CellDataT, RenderTypeT>
{
  /**
   * Represents the type of the column defined by this {@link ColumnDef}.
   * This determines the behavior and characteristics of the column,
   * such as whether it has an underlying data model or supports features
   * like sorting, filtering and grouping. The value is provided as an
   * instance of {@link ColumnKind}.
   */
  @Nonnull
  private final ColumnKind _kind;
  /**
   * Unique identifier for this {@link ColumnDef}.
   */
  @Nonnull
  private final String _id;
  @Nonnull
  private final String _header;
  @Nullable
  private final CellDataAccessor<RowDataT, CellDataT> _dataAccessor;
  @Nullable
  private final NoDataCellFormatter<TableDataT, RenderTypeT> _headerFormatter;
  @Nullable
  private final CellFormatter<TableDataT, RowDataT, CellDataT, RenderTypeT> _cellFormatter;
  @Nullable
  private final NoDataCellFormatter<TableDataT, RenderTypeT> _footerFormatter;

  @Nonnull
  public static <TableDataT, RowDataT, CellDataT, RenderTypeT>
  ColumnDef<TableDataT, RowDataT, CellDataT, RenderTypeT> createAccessor( @Nonnull final String header,
                                                                          @Nonnull final CellDataAccessor<RowDataT, CellDataT> dataAccessor )
  {
    if ( ArezTable.shouldCheckApiInvariants() )
    {
      //noinspection ConstantValue
      apiInvariant( () -> null != header, () -> "header MUST not be null" );
    }
    //noinspection ConstantValue
    assert null != header;
    return createAccessor( deriveId( header ), header, dataAccessor );
  }

  @Nonnull
  public static <TableDataT, RowDataT, CellDataT, RenderTypeT>
  ColumnDef<TableDataT, RowDataT, CellDataT, RenderTypeT> createAccessor( @Nonnull final String id,
                                                                          @Nonnull final String header,
                                                                          @Nonnull final CellDataAccessor<RowDataT, CellDataT> dataAccessor )
  {
    return createAccessor( id, header, dataAccessor, null, null, null );
  }

  @Nonnull
  public static <TableDataT, RowDataT, CellDataT, RenderTypeT>
  ColumnDef<TableDataT, RowDataT, CellDataT, RenderTypeT> createAccessor( @Nonnull final String id,
                                                                          @Nonnull final String header,
                                                                          @Nonnull final CellDataAccessor<RowDataT, CellDataT> dataAccessor,
                                                                          @Nullable final NoDataCellFormatter<TableDataT, RenderTypeT> headerFormatter,
                                                                          @Nullable final CellFormatter<TableDataT, RowDataT, CellDataT, RenderTypeT> cellFormatter,
                                                                          @Nullable final NoDataCellFormatter<TableDataT, RenderTypeT> footerFormatter )
  {
    return new ColumnDef<>( ColumnKind.ACCESSOR,
                            id,
                            header,
                            dataAccessor,
                            headerFormatter,
                            cellFormatter,
                            footerFormatter );
  }

  @VisibleForTesting
  ColumnDef( @Nonnull final ColumnKind kind,
             @Nonnull final String id,
             @Nonnull final String header,
             @Nullable final CellDataAccessor<RowDataT, CellDataT> dataAccessor,
             @Nullable final NoDataCellFormatter<TableDataT, RenderTypeT> headerFormatter,
             @Nullable final CellFormatter<TableDataT, RowDataT, CellDataT, RenderTypeT> cellFormatter,
             @Nullable final NoDataCellFormatter<TableDataT, RenderTypeT> footerFormatter )
  {
    if ( ArezTable.shouldCheckApiInvariants() )
    {
      apiInvariant( () -> ( ColumnKind.ACCESSOR != kind && null == dataAccessor ) ||
                          ( ColumnKind.ACCESSOR == kind && null != dataAccessor ),
                    () -> "Arbl-002: ColumnDef(): dataAccessor MUST be null unless kind is ACCESSOR otherwise it must not be null" );
      apiInvariant( () -> ColumnKind.GROUPING != kind || null == cellFormatter,
                    () -> "Arbl-003: ColumnDef(): cellFormatter MUST be null when kind is GROUPING" );
      //noinspection ConstantValue
      apiInvariant( () -> null != id, () -> "Arbl-004: ColumnDef(): id MUST NOT be null" );
      //noinspection ConstantValue
      apiInvariant( () -> null != header, () -> "Arbl-004: ColumnDef(): header MUST NOT be null" );
    }
    assert ( ColumnKind.ACCESSOR != kind && null == dataAccessor ) ||
           ( ColumnKind.ACCESSOR == kind && null != dataAccessor );
    assert ColumnKind.GROUPING != kind || null == cellFormatter;
    _kind = Objects.requireNonNull( kind );
    _id = Objects.requireNonNull( id );
    _header = Objects.requireNonNull( header );
    _dataAccessor = dataAccessor;
    _headerFormatter = headerFormatter;
    _cellFormatter = cellFormatter;
    _footerFormatter = footerFormatter;
  }

  @Nonnull
  public ColumnKind getKind()
  {
    return _kind;
  }

  @Nonnull
  public String getId()
  {
    return _id;
  }

  @Nonnull
  public String getHeader()
  {
    return _header;
  }

  public CellDataT getCellData( @Nonnull final RowDataT rowData )
  {
    return getDataAccessor().get( rowData );
  }

  @Nullable
  public NoDataCellFormatter<TableDataT, RenderTypeT> getHeaderFormatter()
  {
    return _headerFormatter;
  }

  @Nullable
  public CellFormatter<TableDataT, RowDataT, CellDataT, RenderTypeT> getCellFormatter()
  {
    return _cellFormatter;
  }

  @Nullable
  public NoDataCellFormatter<TableDataT, RenderTypeT> getFooterFormatter()
  {
    return _footerFormatter;
  }

  @VisibleForTesting
  @Nonnull
  CellDataAccessor<RowDataT, CellDataT> getDataAccessor()
  {
    if ( ArezTable.shouldCheckApiInvariants() )
    {
      apiInvariant( () -> null != _dataAccessor,
                    () -> "Arbl-001: ColumnDef.getDataAccessor() invoked on column " + getId() + " but dataAccessor is null" );
    }
    assert null != _dataAccessor;
    return _dataAccessor;
  }

  @VisibleForTesting
  @Nonnull
  static String deriveId( @Nonnull final String value )
  {
    return value.replace( '.', '_' ).replace( ' ', '_' ).toLowerCase();
  }
}
