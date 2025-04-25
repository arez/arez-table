package arez.table;

import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public final class ColumnDefTest
  extends AbstractTest
{
  @Test
  public void constructor_dataAccessor_presence()
  {
    assertInvariantFailure( () -> new ColumnDef<>( ColumnKind.ACCESSOR,
                                                   ValueUtil.randomString(),
                                                   ValueUtil.randomString(),
                                                   null,
                                                   null,
                                                   null,
                                                   null,
                                                   true ),
                            "Arbl-002: ColumnDef(): dataAccessor MUST be null unless kind is ACCESSOR otherwise it must not be null" );

    assertInvariantFailure( () -> new ColumnDef<>( ColumnKind.DISPLAY,
                                                   ValueUtil.randomString(),
                                                   ValueUtil.randomString(),
                                                   MyEntity::getName,
                                                   null,
                                                   null,
                                                   null,
                                                   true ),
                            "Arbl-002: ColumnDef(): dataAccessor MUST be null unless kind is ACCESSOR otherwise it must not be null" );

    assertInvariantFailure( () -> new ColumnDef<>( ColumnKind.GROUPING,
                                                   ValueUtil.randomString(),
                                                   ValueUtil.randomString(),
                                                   MyEntity::getName,
                                                   null,
                                                   null,
                                                   null,
                                                   true ),
                            "Arbl-002: ColumnDef(): dataAccessor MUST be null unless kind is ACCESSOR otherwise it must not be null" );
  }

  @Test
  public void constructor_cellFormatter_presence()
  {
    assertInvariantFailure( () -> new ColumnDef<>( ColumnKind.GROUPING,
                                                   ValueUtil.randomString(),
                                                   ValueUtil.randomString(),
                                                   null,
                                                   null,
                                                   ( tableData, rowData, cellData ) -> "",
                                                   null,
                                                   true ),
                            "Arbl-003: ColumnDef(): cellFormatter MUST be null when kind is GROUPING" );
  }

  @Test
  public void constructor_id_presence()
  {
    //noinspection DataFlowIssue
    assertInvariantFailure( () -> new ColumnDef<>( ColumnKind.ACCESSOR,
                                                   null,
                                                   ValueUtil.randomString(),
                                                   MyEntity::getName,
                                                   null,
                                                   null,
                                                   null,
                                                   true ),
                            "Arbl-004: ColumnDef(): id MUST NOT be null" );
  }

  @Test
  public void constructor_id_header()
  {
    //noinspection DataFlowIssue
    assertInvariantFailure( () -> new ColumnDef<>( ColumnKind.ACCESSOR,
                                                   ValueUtil.randomString(),
                                                   null,
                                                   MyEntity::getName,
                                                   null,
                                                   null,
                                                   null,
                                                   true ),
                            "Arbl-004: ColumnDef(): header MUST NOT be null" );
  }

  @Test
  public void createAccessor_allData()
  {
    final String id = ValueUtil.randomString();
    final String header = ValueUtil.randomString();

    final CellDataAccessor<MyEntity, String> dataAccessor = MyEntity::getName;
    final NoDataCellFormatter<MyEntitySet, String> headerFormatter = tableData -> "Name";
    final CellFormatter<MyEntitySet, MyEntity, String, String> cellFormatter =
      ( tableData, rowData, cellData ) -> cellData;
    final NoDataCellFormatter<MyEntitySet, String> footerFormatter = tableData -> "";
    final boolean isHidingEnabled = ValueUtil.randomBoolean();
    final var accessor =
      ColumnDef.createAccessor( id,
                                header,
                                dataAccessor,
                                headerFormatter,
                                cellFormatter,
                                footerFormatter,
                                isHidingEnabled );

    assertEquals( accessor.getKind(), ColumnKind.ACCESSOR );
    assertEquals( accessor.getId(), id );
    assertEquals( accessor.getHeader(), header );
    assertEquals( accessor.getDataAccessor(), dataAccessor );
    assertEquals( accessor.getHeaderFormatter(), headerFormatter );
    assertEquals( accessor.getCellFormatter(), cellFormatter );
    assertEquals( accessor.getFooterFormatter(), footerFormatter );
    assertEquals( accessor.isHidingEnabled(), isHidingEnabled );
  }

  @Test
  public void createAccessor_minimalData()
  {
    final String id = ValueUtil.randomString();
    final String header = ValueUtil.randomString();

    final CellDataAccessor<MyEntity, String> dataAccessor = MyEntity::getName;

    final var accessor = ColumnDef.createAccessor( id, header, dataAccessor );

    assertEquals( accessor.getKind(), ColumnKind.ACCESSOR );
    assertEquals( accessor.getId(), id );
    assertEquals( accessor.getHeader(), header );
    assertEquals( accessor.getDataAccessor(), dataAccessor );
    assertNull( accessor.getHeaderFormatter() );
    assertNull( accessor.getCellFormatter() );
    assertNull( accessor.getFooterFormatter() );
    assertTrue( accessor.isHidingEnabled() );
  }

  @Test
  public void createAccessor_deriveId()
  {
    final String header = "Hello Foo.";

    final CellDataAccessor<MyEntity, String> dataAccessor = MyEntity::getName;

    final var accessor = ColumnDef.createAccessor( header, dataAccessor );

    assertEquals( accessor.getKind(), ColumnKind.ACCESSOR );
    assertEquals( accessor.getId(), "hello_foo_" );
    assertEquals( accessor.getHeader(), header );
    assertEquals( accessor.getDataAccessor(), dataAccessor );
    assertNull( accessor.getHeaderFormatter() );
    assertNull( accessor.getCellFormatter() );
    assertNull( accessor.getFooterFormatter() );
    assertTrue( accessor.isHidingEnabled() );
  }

  @Test
  public void getDataAccessor()
  {
    final CellDataAccessor<MyEntity, String> dataAccessor = MyEntity::getName;

    final var accessor = ColumnDef.createAccessor( ValueUtil.randomString(), dataAccessor );

    assertEquals( accessor.getDataAccessor(), dataAccessor );
  }

  @Test
  public void getDataAccessor_missingDataAccessor()
  {
    final String id = ValueUtil.randomString();
    final var accessor =
      new ColumnDef<MyEntitySet, MyEntity, String, String>( ColumnKind.DISPLAY,
                                                            id,
                                                            ValueUtil.randomString(),
                                                            null,
                                                            null,
                                                            null,
                                                            null,
                                                            true );

    assertInvariantFailure( accessor::getDataAccessor,
                            "Arbl-001: ColumnDef.getDataAccessor() invoked on column " +
                            id + " but dataAccessor is null" );
  }

  @Test
  public void getCellData()
  {
    final var accessor = ColumnDef.createAccessor( ValueUtil.randomString(), MyEntity::getName );

    final MyEntity rowData = new MyEntity();
    assertEquals( accessor.getCellData( rowData ), rowData.getName() );
  }

  @Test
  public void getCellData_missingDataAccessor()
  {
    final String id = ValueUtil.randomString();
    final var accessor =
      new ColumnDef<MyEntitySet, MyEntity, String, String>( ColumnKind.DISPLAY,
                                                            id,
                                                            ValueUtil.randomString(),
                                                            null,
                                                            null,
                                                            null,
                                                            null,
                                                            true );

    final MyEntity rowData = new MyEntity();
    assertInvariantFailure( () -> accessor.getCellData( rowData ),
                            "Arbl-001: ColumnDef.getDataAccessor() invoked on column " +
                            id + " but dataAccessor is null" );
  }

  @Test
  public void deriveId()
  {
    assertEquals( ColumnDef.deriveId( "abc" ), "abc" );
    assertEquals( ColumnDef.deriveId( "a b c" ), "a_b_c" );
    assertEquals( ColumnDef.deriveId( "AbC" ), "abc" );
    assertEquals( ColumnDef.deriveId( "A.C" ), "a_c" );
    assertEquals( ColumnDef.deriveId( " .A.C. " ), "__a_c__" );
  }
}
