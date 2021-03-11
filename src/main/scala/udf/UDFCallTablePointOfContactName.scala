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


class UDFCallTablePointOfContactName extends GenericUDF {
  var inputOI: PrimitiveObjectInspector = _
  var outputOI: PrimitiveObjectInspector = _

  @throws[UDFArgumentException]
  override def initialize(args: Array[ObjectInspector]): ObjectInspector = {
    // This UDF accepts two arguments
    assert(args.length == 2)
    // The arguments are primitive types
    assert(args(0).getCategory eq Category.PRIMITIVE)
    assert(args(1).getCategory eq Category.PRIMITIVE)
    inputOI = args(0).asInstanceOf[PrimitiveObjectInspector]
    /* We only support STRING type */ assert(inputOI.getPrimitiveCategory eq PrimitiveCategory.STRING)
    /* And we'll return a type int, so let's return the corresponding object inspector */
    outputOI = PrimitiveObjectInspectorFactory.writableStringObjectInspector
    outputOI
  }

  @throws[HiveException]
  override def evaluate(args: Array[GenericUDF.DeferredObject]): Any = {
    if (args.length != 2) return null
    // Access the deferred value. Hive passes the arguments as "deferred" objects
    // to avoid some computations if we don't actually need some of the values
    val str1 = args(0).get
    val str2 = args(1).get
    if (str1 == null || str2 == null) return null
    val mediaName = inputOI.getPrimitiveJavaObject(str1).asInstanceOf[String]
    val contactName = inputOI.getPrimitiveJavaObject(str2).asInstanceOf[String]
    val output = (new CallTable).PointOfContactName.pointOfContactName(mediaName,contactName)
    new Text(output)
  }

  override def getDisplayString(args: Array[String]) = "Call Table UDFS"
}