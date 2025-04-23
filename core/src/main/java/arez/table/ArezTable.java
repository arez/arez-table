package arez.table;

import grim.annotations.OmitClinit;
import grim.annotations.OmitSymbol;

/**
 * Provide an interface to interact with ArezTable toolkit as well as global configuration settings.
 */
@OmitClinit
public final class ArezTable
{
  private ArezTable()
  {
  }

  /**
   * Return true if apiInvariants will be checked.
   *
   * @return true if apiInvariants will be checked.
   */
  @OmitSymbol
  public static boolean shouldCheckApiInvariants()
  {
    return ArezTableConfig.shouldCheckApiInvariants();
  }
}
