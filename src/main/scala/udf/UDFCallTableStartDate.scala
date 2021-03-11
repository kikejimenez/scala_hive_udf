package udf

import org.apache.hadoop.hive.ql.exec.UDFArgumentException
import org.apache.hadoop.hive.ql.metadata.HiveException
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector.Category
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector.PrimitiveCategory
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory
import org.apache.hadoop.io.Text


class UDFCallTableStartDate extends GenericUDF {
  var inputOI: PrimitiveObjectInspector = _
  var outputOI: PrimitiveObjectInspector = _

  @throws[UDFArgumentException]
  override def initialize(args: Array[ObjectInspector]): ObjectInspector = {
    // This UDF accepts two arguments
    assert(args.length == 1)
    // The arguments are primitive types
    assert(args(0).getCategory eq Category.PRIMITIVE)
    inputOI = args(0).asInstanceOf[PrimitiveObjectInspector]
    /* We only support STRING type */
    assert(inputOI.getPrimitiveCategory eq PrimitiveCategory.STRING)
    /* And we'll return a type int, so let's return the corresponding object inspector */
    outputOI = PrimitiveObjectInspectorFactory.writableStringObjectInspector
    outputOI
  }

  @throws[HiveException]
  override def evaluate(args: Array[GenericUDF.DeferredObject]): Any = {
    if (args.length != 1) return null
    // Access the deferred value. Hive passes the arguments as "deferred" objects
    // to avoid some computations if we don't actually need some of the values
    val str1 = args(0).get
    if (str1 == null) return null
    val date = inputOI.getPrimitiveJavaObject(str1).asInstanceOf[String]
    val output = (new CallTable).StartDate.startDate(date)
    new Text(output)
  }

  override def getDisplayString(args: Array[String]) = "Start Date UDFS"
}