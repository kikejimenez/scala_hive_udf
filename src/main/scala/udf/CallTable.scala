package udf


class CallTable {

  object StartDate {
    val regexReplaceSlash: (String)=> String = str =>  str.replaceAll("/","-")
    val regexInvertDate:  (String)=> String = str =>  str.replaceAll( "(\\d{1,2})-(\\d{1,2})-(\\d{4})",
      "$3-$2-$1")
    val regexPadZeroMonth: (String)=> String = str =>  str.replaceAll( "(\\d{4})-(\\d)-(\\d{1,2})",
      "$1-0$2-$3")
    val regexPadZeroDay: (String)=> String = str =>  str.replaceAll( "(\\d{4})-(\\d{2})-(\\d)([^\\d]|$)",
      "$1-$2-0$3$4")
    val regexPadZero:(String)=> String = str => regexPadZeroDay(regexPadZeroMonth(str))
    val regexExtractDate:(String)=> String = str => "\\d{4}-\\d{2}-\\d{2}".r.findFirstIn(str).getOrElse("")

    val startDate:(String)=> String = str => regexExtractDate(regexPadZero(regexInvertDate(regexReplaceSlash(str))))
  }

  object PointOfContactName {
    val regexReplaceHyphen: (String) => String = str => str.replaceAll("-", "")
    val regexReplacePlus: (String) => String = str => str.replaceAll("\\+", "")
    val cleanPhoneNumber: (String) => String = str => regexReplaceHyphen(regexReplacePlus(str))
    val regexExtractPhone: (String) => String = str => "(\\d{13}|\\d{10})".r.findFirstIn(str).getOrElse("")

    val extractPhone: (String) => String = str => regexExtractPhone(cleanPhoneNumber(str))

    val extractEmail: (String) => String = str => "(.*)@(.*)".r.findFirstIn(str).getOrElse("").trim

    val pointOfContactName: (String,String) =>  String = (mediaName,pointName)=> {

      val mediaNameLower = mediaName.toLowerCase()
      println(mediaNameLower contains "phone")
      mediaNameLower  match {
        case x if (x contains "phone") => extractPhone(pointName)
        case x if (x contains  "email") => extractEmail(pointName)
        case _ => pointName
      }
    }
  }

}



