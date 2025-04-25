package arez.table;

import java.util.Objects;
import javax.annotation.Nonnull;
import org.realityforge.guiceyloops.shared.ValueUtil;

final class MyEntity
{
  @Nonnull
  private final String _id;
  @Nonnull
  private final String _name;
  @Nonnull
  private final String _column1;
  @Nonnull
  private final String _column2;

  MyEntity()
  {
    this( ValueUtil.randomString(), ValueUtil.randomString(), ValueUtil.randomString(), ValueUtil.randomString() );
  }

  MyEntity( @Nonnull final String id,
            @Nonnull final String name,
            @Nonnull final String column1,
            @Nonnull final String column2 )
  {
    _id = Objects.requireNonNull( id );
    _name = Objects.requireNonNull( name );
    _column1 = Objects.requireNonNull( column1 );
    _column2 = Objects.requireNonNull( column2 );
  }

  @Nonnull
  String getId()
  {
    return _id;
  }

  @Nonnull
  String getName()
  {
    return _name;
  }

  @Nonnull
  String getColumn1()
  {
    return _column1;
  }

  @Nonnull
  String getColumn2()
  {
    return _column2;
  }
}
