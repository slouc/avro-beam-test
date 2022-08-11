# Reproducing the conversion exception when using AvroUtils with timestamp fields in the avro schema

AvroUtils invokes the [following static block](https://github.com/apache/beam/blob/master/sdks/java/core/src/main/java/org/apache/beam/sdk/schemas/utils/AvroUtils.java#L109).

This causes the `conversions` field to be populated with "timestamp-millis" [here](https://github.com/apache/avro/blob/master/lang/java/avro/src/main/java/org/apache/avro/generic/GenericData.java#L139.)

And that eventually leads to an exception when getting the default value for the unspecified timestamp
field in our avro schema, during [this conversion](https://github.com/apache/avro/blob/release-1.8.2/lang/java/avro/src/main/java/org/apache/avro/data/RecordBuilderBase.java#L159).

### Steps to reproduce
```
> sbt clean compile run
```

Expected output:
> [error] org.apache.avro.AvroRuntimeException: org.apache.avro.AvroRuntimeException: Cannot convert 1970-01-01T00:00:00.000Z:DateTime: expected generic type

There is a [bug report in Apache Beam](https://github.com/apache/beam/issues/20205) regarding this automatic conversion.

Note:

Apache Beam is [using avro 1.8.2](https://github.com/apache/beam/blob/434427e90b55027c5944fa73de68bff4f9a4e8fe/buildSrc/src/main/groovy/org/apache/beam/gradle/BeamModulePlugin.groovy#L519
), which is the problematic one. If we instead explicitly bring avro 1.11.1  (latest version right now) into our dependency tree,
then the conversion exception goes away, because RecordBuilderBase uses a significantly different version of the
[defaultValue() method](https://github.com/apache/avro/blob/release-1.11.1/lang/java/avro/src/main/java/org/apache/avro/data/RecordBuilderBase.java#L137) 
than the one used in [1.8.2](https://github.com/apache/avro/blob/release-1.8.2/lang/java/avro/src/main/java/org/apache/avro/data/RecordBuilderBase.java#L152). 

This, however, doesn't mean that AvroUtils stops converting long to DateTime automatically (which is what the bug report is about); 
it only means that getting an avro runtime exception while building avro records with empty timestamp fields is no longer a possible consequence.