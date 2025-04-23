package arez.table;

import grim.annotations.OmitType;
import javax.annotation.Nonnull;
import org.realityforge.braincheck.BrainCheckConfig;

/**
 * Location of all compile time configuration settings for the framework.
 */
@SuppressWarnings( "FieldMayBeFinal" )
@OmitType
final class ArezTableConfig
{
  @Nonnull
  private static final ConfigProvider PROVIDER = new ConfigProvider();
  private static final boolean PRODUCTION_ENVIRONMENT = PROVIDER.isProductionEnvironment();
  private static boolean CHECK_API_INVARIANTS = PROVIDER.shouldCheckApiInvariants();
  @Nonnull
  private static final String LOGGER_TYPE = PROVIDER.loggerType();

  private ArezTableConfig()
  {
  }

  static boolean isProductionEnvironment()
  {
    return PRODUCTION_ENVIRONMENT;
  }

  static boolean shouldCheckApiInvariants()
  {
    return BrainCheckConfig.checkApiInvariants() && CHECK_API_INVARIANTS;
  }

  @Nonnull
  static String loggerType()
  {
    return LOGGER_TYPE;
  }

  private static final class ConfigProvider
    extends AbstractConfigProvider
  {
    @GwtIncompatible
    boolean isProductionEnvironment()
    {
      return "production".equals( System.getProperty( "arez.table.environment", "production" ) );
    }

    @GwtIncompatible
    @Override
    boolean shouldCheckApiInvariants()
    {
      return "true".equals( System.getProperty( "arez.table.check_api_invariants",
                                                isProductionEnvironment() ? "false" : "true" ) );
    }

    @GwtIncompatible
    @Override
    @Nonnull
    String loggerType()
    {
      return System.getProperty( "arez.table.logger", isProductionEnvironment() ? "basic" : "proxy" );
    }
  }

  @SuppressWarnings( { "unused", "StringEquality" } )
  private static abstract class AbstractConfigProvider
  {
    boolean isProductionEnvironment()
    {
      return "production" == System.getProperty( "arez.table.environment" );
    }

    boolean shouldCheckApiInvariants()
    {
      return "true" == System.getProperty( "arez.table.check_api_invariants" );
    }

    @Nonnull
    String loggerType()
    {
      /*
       * Valid values are: "none", "console" and "proxy" (for testing)
       */
      return System.getProperty( "arez.table.logger" );
    }
  }
}
