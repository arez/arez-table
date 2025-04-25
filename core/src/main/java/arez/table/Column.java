package arez.table;

import arez.Disposable;
import arez.annotations.Action;
import arez.annotations.ArezComponent;
import arez.annotations.Observable;
import java.util.Objects;
import javax.annotation.Nonnull;
import static org.realityforge.braincheck.Guards.*;

/**
 * The model representing a column in a table structure with customizable visibility and hiding functionality.
 *
 * @param <TableDataT>  The type of data representing the table.
 * @param <RowDataT>    The type of data representing the row.
 * @param <CellDataT>   The type of data representing the cell.
 * @param <RenderTypeT> The rendering type associated with the column.
 */
@ArezComponent
public abstract class Column<TableDataT, RowDataT, CellDataT, RenderTypeT>
{
  @Nonnull
  private final ColumnDef<TableDataT, RowDataT, CellDataT, RenderTypeT> _columnDef;
  private boolean _visible = true;

  @Nonnull
  static <TableDataT, RowDataT, CellDataT, RenderTypeT>
  Column<TableDataT, RowDataT, CellDataT, RenderTypeT> create( @Nonnull final ColumnDef<TableDataT, RowDataT, CellDataT, RenderTypeT> columnDef )
  {
    return new Arez_Column<>( columnDef );
  }

  Column( @Nonnull final ColumnDef<TableDataT, RowDataT, CellDataT, RenderTypeT> columnDef )
  {
    _columnDef = Objects.requireNonNull( columnDef );
  }

  /**
   * Return true if the column is visible.
   *
   * @return true if the column is visible, false otherwise.
   */
  @Observable
  public boolean isVisible()
  {
    return _visible;
  }

  void setVisible( final boolean visible )
  {
    final boolean hidingEnabled = getColumnDef().isHidingEnabled();
    if ( ArezTable.shouldCheckApiInvariants() )
    {
      apiInvariant( () -> hidingEnabled,
                    () -> "Arbl-006: Column.setVisible(" + visible + ") invoked but " +
                          "column does not have hiding enabled" );
    }
    if ( hidingEnabled )
    {
      _visible = visible;
    }
  }

  /**
   * Hide the column.
   * This should not be invoked if the {@link ColumnDef} does not have column hiding enabled and
   * will result in an assertion failure in development mode.
   */
  @Action
  public void hide()
  {
    setVisible( false );
  }

  /**
   * A safe version of {@link #hide()} that checks that the component is not disposed before invoking method.
   * This is useful when this method is invoked from an EventHandler which may be called after the component is disposed.
   */
  public void safeHide()
  {
    if ( Disposable.isNotDisposed( this ) )
    {
      hide();
    }
  }

  /**
   * Show the column.
   * This should not be invoked if the {@link ColumnDef} does not have column hiding enabled and
   * will result in an assertion failure in development mode.
   */
  @Action
  public void show()
  {
    setVisible( true );
  }

  /**
   * A safe version of {@link #show()} that checks that the component is not disposed before invoking method.
   * This is useful when this method is invoked from an EventHandler which may be called after the component is disposed.
   */
  public void safeShow()
  {
    if ( Disposable.isNotDisposed( this ) )
    {
      show();
    }
  }

  /**
   * Toggles the visibility state of the column.
   * <ul>
   *   <li>If the column is currently visible, calling this method will hide it by invoking {@link #hide()}.</li>
   *   <li>If the column is currently hidden, calling this method will show it by invoking {@link #show()}.</li>
   * </ul>
   * This should not be invoked if the {@link ColumnDef} does not have column hiding enabled and
   * will result in an assertion failure in development mode.
   */
  @Action
  public void toggleVisibility()
  {
    if ( _visible )
    {
      hide();
    }
    else
    {
      show();
    }
  }

  /**
   * A safe version of {@link #toggleVisibility()} that checks that the component is not disposed before invoking method.
   * This is useful when this method is invoked from an EventHandler which may be called after the component is disposed.
   */
  public void safeToggleVisibility()
  {
    if ( Disposable.isNotDisposed( this ) )
    {
      toggleVisibility();
    }
  }

  @Nonnull
  public ColumnDef<TableDataT, RowDataT, CellDataT, RenderTypeT> getColumnDef()
  {
    return _columnDef;
  }
}
