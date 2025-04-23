package arez.table;

import java.lang.reflect.Field;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Utility class for interacting with ArezTable config settings in tests.
 */
@SuppressWarnings( "WeakerAccess" )
@GwtIncompatible
public final class ArezTableTestUtil
{
  private ArezTableTestUtil()
  {
  }

  /**
   * Interface to intercept log messages emitted by ArezTable runtime.
   */
  public interface Logger
  {
    void log( @Nonnull String message, @Nullable Throwable throwable );
  }

  /**
   * Reset the state of ArezTable config to either production or development state.
   *
   * @param production true to set it to production environment configuration, false to set it to development environment config.
   */
  public static void resetConfig( final boolean production )
  {
    if ( ArezTableConfig.isProductionEnvironment() )
    {
      throw new IllegalStateException( "Unable to reset config as ArezTable is in production mode" );
    }

    if ( production )
    {
      noCheckApiInvariants();
    }
    else
    {
      checkApiInvariants();
    }
    resetState();
  }

  /**
   * Reset the state of ArezTable.
   * This occasionally needs to be invoked after changing configuration settings in tests.
   */
  public static void resetState()
  {
    setLogger( null );
  }

  /**
   * Specify the logger to use to capture logging in tests
   *
   * @param logger the logger.
   */
  public static void setLogger( @Nullable final Logger logger )
  {
    if ( ArezTableConfig.isProductionEnvironment() )
    {
      throw new IllegalStateException( "Unable to call ArezTestUtil.setLogger() as ArezTable is in production mode" );
    }

    final LogUtil.ProxyLogger proxyLogger = (LogUtil.ProxyLogger) LogUtil.getLogger();
    proxyLogger.setLogger( null == logger ? null : logger::log );
  }

  /**
   * Set the `arez.table.check_api_invariants` setting to true.
   */
  public static void checkApiInvariants()
  {
    setCheckApiInvariants( true );
  }

  /**
   * Set the `arez.table.check_api_invariants` setting to false.
   */
  public static void noCheckApiInvariants()
  {
    setCheckApiInvariants( false );
  }

  /**
   * Configure the `arez.table.check_api_invariants` setting.
   *
   * @param setting the setting.
   */
  private static void setCheckApiInvariants( final boolean setting )
  {
    setConstant( "CHECK_API_INVARIANTS", setting );
  }

  /**
   * Set the specified field name on ArezTableConfig.
   */
  @SuppressWarnings( { "NonJREEmulationClassesInClientCode", "SameParameterValue" } )
  private static void setConstant( @Nonnull final String fieldName, final boolean value )
  {
    if ( ArezTableConfig.isProductionEnvironment() )
    {
      throw new IllegalStateException( "Unable to change constant " + fieldName +
                                       " as ArezTable is in production mode" );
    }
    else
    {
      try
      {
        final Field field = ArezTableConfig.class.getDeclaredField( fieldName );
        field.setAccessible( true );
        field.set( null, value );
      }
      catch ( final NoSuchFieldException | IllegalAccessException e )
      {
        throw new IllegalStateException( "Unable to change constant " + fieldName, e );
      }
    }
  }
}
