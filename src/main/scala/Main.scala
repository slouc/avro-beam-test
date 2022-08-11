
import org.apache.beam.sdk.schemas.utils.AvroUtils

object Main {

  def main(args: Array[String]): Unit = {

    AvroUtils.getFromRowFunction(this.getClass) // whatever, just to summon AvroUtils

    Foo.newBuilder().build()

    // Exception in thread "main" org.apache.avro.AvroRuntimeException:
    // org.apache.avro.AvroRuntimeException:
    // Cannot convert 1970-01-01T00:00:00.000Z:DateTime: expected generic type

  }
}