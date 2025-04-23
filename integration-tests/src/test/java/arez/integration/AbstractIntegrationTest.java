package arez.integration;

import arez.table.ArezTableTestUtil;
import arez.testng.ArezTestSupport;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class AbstractIntegrationTest
  implements ArezTestSupport
{
  @BeforeMethod
  public void preTest()
    throws Exception
  {
    ArezTestSupport.super.preTest();
    ArezTableTestUtil.resetConfig( false );
  }

  @AfterMethod
  public void postTest()
  {
    ArezTableTestUtil.resetConfig( true );
    ArezTestSupport.super.postTest();
  }
}
