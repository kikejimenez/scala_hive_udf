package udf

import org.scalatest.FunSuite

class CallTableStartDate extends FunSuite{

  val callTable = new CallTable
  val startDate= callTable.StartDate

  test("regex replace slash"){
    val regexReplaceHyphen = startDate.regexReplaceSlash
    assert( regexReplaceHyphen("43/34/2") =="43-34-2")
  }
  test("regex invert date"){
    val regexReplacePlus = startDate.regexInvertDate
    assert( regexReplacePlus("01-02-1999") =="1999-02-01")
  }
  test("regex pad zero day"){
    val regexPadZeroDay = startDate.regexPadZeroDay
    assert(regexPadZeroDay("1999-01-1") == "1999-01-01")
   // assert(regexPadZeroDay("1999-1-01") == "1999-01-01")
  }
  test("regex pad zero month"){
    val regexPadZeroMonth = startDate.regexPadZeroMonth
    assert(regexPadZeroMonth("1999-1-1") == "1999-01-1")
  }

  test("regex pad zero"){
    val regexPadZero = startDate.regexPadZero
    assert(regexPadZero("1999-1-1") == "1999-01-01")
  }
  test("regex extract Date"){
    val regexExtractDate = startDate.regexExtractDate
    assert(regexExtractDate("....1999-01-01.....") == "1999-01-01")
    assert(regexExtractDate("....1999-011.....") == "")
  }
  test("start date"){
    assert(startDate.startDate("1/2/1999 35") ==  "1999-02-01")
  }
}