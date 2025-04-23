package arez.table;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public final class ArezTableTest
  extends AbstractTest
{
  @Test
  public void shouldCheckApiInvariants()
  {
    assertTrue( ArezTable.shouldCheckApiInvariants() );
    ArezTableTestUtil.noCheckApiInvariants();
    assertFalse( ArezTable.shouldCheckApiInvariants() );
  }
}
