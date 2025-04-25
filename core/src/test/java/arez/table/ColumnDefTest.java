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
  public void constructor_header_presence()
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
                            "Arbl-005: ColumnDef(): header MUST NOT be null" );
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
    final var columnDef =
      ColumnDef.createAccessor( id,
                                header,
                                dataAccessor,
                                headerFormatter,
                                cellFormatter,
                                footerFormatter,
                                isHidingEnabled );

    assertEquals( columnDef.getKind(), ColumnKind.ACCESSOR );
    assertEquals( columnDef.getId(), id );
    assertEquals( columnDef.getHeader(), header );
    assertEquals( columnDef.getDataAccessor(), dataAccessor );
    assertEquals( columnDef.getHeaderFormatter(), headerFormatter );
    assertEquals( columnDef.getCellFormatter(), cellFormatter );
    assertEquals( columnDef.getFooterFormatter(), footerFormatter );
    assertEquals( columnDef.isHidingEnabled(), isHidingEnabled );
  }

  @Test
  public void createAccessor_almostMinimalData()
  {
    final String id = ValueUtil.randomString();
    final String header = ValueUtil.randomString();

    final CellDataAccessor<MyEntity, String> dataAccessor = MyEntity::getName;

    final var columnDef = ColumnDef.createAccessor( id, header, dataAccessor );

    assertEquals( columnDef.getKind(), ColumnKind.ACCESSOR );
    assertEquals( columnDef.getId(), id );
    assertEquals( columnDef.getHeader(), header );
    assertEquals( columnDef.getDataAccessor(), dataAccessor );
    assertNull( columnDef.getHeaderFormatter() );
    assertNull( columnDef.getCellFormatter() );
    assertNull( columnDef.getFooterFormatter() );
    assertTrue( columnDef.isHidingEnabled() );
  }

  @Test
  public void createAccessor_deriveId()
  {
    final String header = "Hello Foo.";

    final CellDataAccessor<MyEntity, String> dataAccessor = MyEntity::getName;

    final var columnDef = ColumnDef.createAccessor( header, dataAccessor );

    assertEquals( columnDef.getKind(), ColumnKind.ACCESSOR );
    assertEquals( columnDef.getId(), "hello_foo_" );
    assertEquals( columnDef.getHeader(), header );
    assertEquals( columnDef.getDataAccessor(), dataAccessor );
    assertNull( columnDef.getHeaderFormatter() );
    assertNull( columnDef.getCellFormatter() );
    assertNull( columnDef.getFooterFormatter() );
    assertTrue( columnDef.isHidingEnabled() );
  }

  @Test
  public void getDataAccessor()
  {
    final CellDataAccessor<MyEntity, String> dataAccessor = MyEntity::getName;

    final var columnDef = ColumnDef.createAccessor( ValueUtil.randomString(), dataAccessor );

    assertEquals( columnDef.getDataAccessor(), dataAccessor );
  }

  @Test
  public void getDataAccessor_missingDataAccessor()
  {
    final String id = ValueUtil.randomString();
    final var columnDef =
      new ColumnDef<MyEntitySet, MyEntity, String, String>( ColumnKind.DISPLAY,
                                                            id,
                                                            ValueUtil.randomString(),
                                                            null,
                                                            null,
                                                            null,
                                                            null,
                                                            true );

    assertInvariantFailure( columnDef::getDataAccessor,
                            "Arbl-001: ColumnDef.getDataAccessor() invoked on column " +
                            id + " but dataAccessor is null" );
  }

  @Test
  public void getCellData()
  {
    final var columnDef = ColumnDef.createAccessor( ValueUtil.randomString(), MyEntity::getName );

    final MyEntity rowData = new MyEntity();
    assertEquals( columnDef.getCellData( rowData ), rowData.getName() );
  }

  @Test
  public void getCellData_missingDataAccessor()
  {
    final String id = ValueUtil.randomString();
    final var columnDef =
      new ColumnDef<MyEntitySet, MyEntity, String, String>( ColumnKind.DISPLAY,
                                                            id,
                                                            ValueUtil.randomString(),
                                                            null,
                                                            null,
                                                            null,
                                                            null,
                                                            true );

    final MyEntity rowData = new MyEntity();
    assertInvariantFailure( () -> columnDef.getCellData( rowData ),
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
