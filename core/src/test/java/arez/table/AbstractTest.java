package arez.table;

import arez.testng.ArezTestSupport;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;

public abstract class AbstractTest
  implements ArezTestSupport
{
  @Nonnull
  private final TestLogger _logger = new TestLogger();

  @BeforeMethod
  public void preTest()
    throws Exception
  {
    ArezTestSupport.super.preTest();
    ArezTableTestUtil.resetConfig( false );
    _logger.getEntries().clear();
    ArezTableTestUtil.setLogger( _logger );
  }

  @AfterMethod
  public void postTest()
  {
    ArezTableTestUtil.resetConfig( true );
    ArezTestSupport.super.postTest();
  }

  @Nonnull
  protected final TestLogger getTestLogger()
  {
    return _logger;
  }

  protected final void assertDefaultToString( @Nonnull final Object object )
  {
    assertEquals( object.toString(), object.getClass().getName() + "@" + Integer.toHexString( object.hashCode() ) );
  }

  protected final void assertInvariantFailure( @Nonnull final ThrowingRunnable throwingRunnable,
                                               @Nonnull final String message )
  {
    assertEquals( expectThrows( IllegalStateException.class, throwingRunnable ).getMessage(), message );
  }
}
