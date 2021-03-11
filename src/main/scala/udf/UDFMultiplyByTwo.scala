package udf

import org.apache.hadoop.hive.ql.exec.UDFArgumentException
import org.apache.hadoop.hive.ql.metadata.HiveException
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector.Category
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector.PrimitiveCategory
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory
import org.apache.hadoop.io.IntWritable


class UDFMultiplyByTwo extends GenericUDF {
  var inputOI: PrimitiveObjectInspector = null
  var outputOI: PrimitiveObjectInspector = null

  @throws[UDFArgumentException]
  override def initialize(args: Array[ObjectInspector]): ObjectInspector = { // This UDF accepts one argument
    assert(args.length == 1)
    // The first argument is a primitive type
    assert(args(0).getCategory eq Category.PRIMITIVE)
    inputOI = args(0).asInstanceOf[PrimitiveObjectInspector]
    /* We only support INTEGER type */ assert(inputOI.getPrimitiveCategory eq PrimitiveCategory.INT)
    /* And we'll return a type int, so let's return the corresponding object inspector */
    outputOI = PrimitiveObjectInspectorFactory.writableIntObjectInspector
    outputOI
  }

  @throws[HiveException]
  override def evaluate(args: Array[GenericUDF.DeferredObject]): Any = {
    if (args.length != 1) return null
    // Access the deferred value. Hive passes the arguments as "deferred" objects
    // to avoid some computations if we don't actually need some of the values
    val oin = args(0).get
    if (oin == null) return null
    val value = inputOI.getPrimitiveJavaObject(oin).asInstanceOf[Integer]
    val output = value * 2
    new IntWritable(output)
  }

  override def getDisplayString(args: Array[String]) = "Here, write a nice description"
}