package arez.table;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

class MyEntitySet
{
  @Nonnull
  private final List<MyEntity> _list = new ArrayList<>();

  void append( @Nonnull final MyEntity entity )
  {
    _list.add( entity );
  }

  @Nonnull
  public List<MyEntity> getList()
  {
    return _list;
  }
}
