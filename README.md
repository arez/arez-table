# Arez-Persist

[![Build Status](https://api.travis-ci.com/arez/arez-table.svg?branch=main)](http://travis-ci.com/arez/arez-table)
[<img src="https://img.shields.io/maven-central/v/org.realityforge.arez.table/arez-table.svg?label=latest%20release"/>](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.realityforge.arez.table%22)

This library provides a Headless UI library for building powerful tables & datagrids.

## Quick Start

The simplest way to use the library;

* add the following dependencies into the build system. i.e.

```xml
<dependency>
   <groupId>org.realityforge.arez.table</groupId>
   <artifactId>arez-table</artifactId>
   <version>0.01</version>
</dependency>
```

* If you are using ArezTable within a GWT application you will also need to inherit the appropriate
  GWT module in your `.gwt.xml` file. It is usually enough to add:

```xml
<module>
  ...
  <inherits name='arez.table.Table'/>
  ...
</module>
```

  If you want the framework to perform validation and invariant checking you can instead inherit
  the `Dev` module instead. The `Dev` module is beneficial during development as it adds a
  level of safety and error checking, but it should not be used in production environments as it adds
  some overhead in terms of code size and execution speed. The `Dev` module can be added via:

```xml
<module>
  ...
  <inherits name='arez.table.TableDev'/>
  ...
</module>
```

# More Information

For more information about component, please see the [Website](https://arez.github.io/table). For the
source code and project support please visit the [GitHub project](https://github.com/arez/arez-table).

# Contributing

The component was released as open source so others could benefit from the project. We are thankful for any
contributions from the community. A [Code of Conduct](CODE_OF_CONDUCT.md) has been put in place and
a [Contributing](CONTRIBUTING.md) document is under development.

# License

The component is licensed under [Apache License, Version 2.0](LICENSE).

# Credit

* [Stock Software](http://www.stocksoftware.com.au/) for providing significant support in building and
  maintaining Arez-Table.
