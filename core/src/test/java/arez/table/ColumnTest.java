package arez.table;

import arez.Disposable;
import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public final class ColumnTest
  extends AbstractTest
{
  @Test
  public void create()
  {
    final var columnDef = ColumnDef.createAccessor( "Name", MyEntity::getName );
    final var column = Column.create( columnDef );

    assertEquals( column.getColumnDef(), columnDef );
    safeAction( () -> assertTrue( column.isVisible() ) );
  }

  @Test
  public void visibility()
  {
    final var columnDef = ColumnDef.createAccessor( ValueUtil.randomString(), MyEntity::getName );
    final var column = Column.create( columnDef );

    safeAction( () -> assertTrue( column.isVisible() ) );

    // Show when already shown is fine
    safeAction( column::show );

    safeAction( () -> assertTrue( column.isVisible() ) );

    safeAction( column::hide );

    safeAction( () -> assertFalse( column.isVisible() ) );

    // Hide when already hidden is fine
    safeAction( column::hide );

    safeAction( () -> assertFalse( column.isVisible() ) );

    safeAction( column::toggleVisibility );

    safeAction( () -> assertTrue( column.isVisible() ) );

    safeAction( column::toggleVisibility );

    safeAction( () -> assertFalse( column.isVisible() ) );
  }

  @Test
  public void safeVisibility()
  {
    final var columnDef = ColumnDef.createAccessor( ValueUtil.randomString(), MyEntity::getName );
    final var column = Column.create( columnDef );

    safeAction( () -> assertTrue( column.isVisible() ) );

    column.safeHide();

    safeAction( () -> assertFalse( column.isVisible() ) );

    column.safeShow();

    safeAction( () -> assertTrue( column.isVisible() ) );

    column.safeToggleVisibility();

    safeAction( () -> assertFalse( column.isVisible() ) );

    Disposable.dispose( column );

    // All the following "safe" methods are fine to call even if the column is disposed

    column.safeShow();
    column.safeHide();
    column.safeToggleVisibility();
  }

  @Test
  public void attemptedVisibilityChangeButColumnDefIsNotHidingEnabled()
  {
    final var columnDef = ColumnDef.createAccessor( ValueUtil.randomString(),
                                                    ValueUtil.randomString(),
                                                    MyEntity::getName,
                                                    null,
                                                    null,
                                                    null,
                                                    false );
    final var column = Column.create( columnDef );

    safeAction( () -> assertTrue( column.isVisible() ) );

    assertInvariantFailure( () -> safeAction( column::toggleVisibility ),
                            "Arbl-006: Column.setVisible(false) invoked but column does not have hiding enabled" );
  }
}
