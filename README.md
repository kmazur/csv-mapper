# CSV Mapper Library

This is the home page of the CSV Mapper Library.

## What is New?

- 2022-11-21: Project was created!

## What is CSV Mapper?

CSV Mapper is a library to read and write CSV content as well as serialize and deserialize Java objects.

## Features

- reading CSV contents based on `java.io.Reader` to `List<String>`
- writing a list of type `List<String>` as CSV row using `java.io.Writer`
- serializing annotated Java classes to `List<String>`
- deserializing `List<String>` to annotated Java classes

## CSV Supported Format

You can set up the escape, quote, value separator and row separator characters but there are some differences in 
CSV handling that are implemented in this library:

- the value can be escaped using `escape` (default: back-slash) character (e.g. `\,`). 
  Any character after escape character is not interpreted in the tokenization.

## Limitations and future development

- deserialization currently is limited to only Java classes with default constructor available
- only field level annotations are supported in deserializing `List<String>` to Java class (no method level, etc.)
- there is no support for generic types via type reference capture.
- no support for feature flags e.g. allow/disallow unquoted strings
- no support for nested classes / embedded classes
- no support for CSV header and field annotations by column name
- no class has any support for multithreaded environment or thread-safe usage
- no validation checks on indexes in annotations and no metamodel creation and caching
