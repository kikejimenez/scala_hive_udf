package udf

import org.scalatest.FunSuite

class CallTablePointOfContactName extends FunSuite{

  val callTable = new CallTable
  val pointOfContactName= callTable.PointOfContactName

  test("Remove hyphens"){
    val regexReplaceHyphen = pointOfContactName.regexReplaceHyphen
    assert( regexReplaceHyphen("43-22--23-2") =="4322232")
  }
  test("Remove plus sign"){
    val regexReplacePlus = pointOfContactName.regexReplacePlus
    assert( regexReplacePlus("+1999") =="1999")
  }
  test("Clean Phone Number"){
    val cleanPhoneNumber = pointOfContactName.cleanPhoneNumber
    assert(cleanPhoneNumber("+001-888-4000") == "0018884000")
  }
  test("Regex Extract Phone"){
    val regexExtractPhone = pointOfContactName.regexExtractPhone
    assert(regexExtractPhone(" 1113334444 ") == "1113334444")
    assert(regexExtractPhone(" 0111113334444 ") =="0111113334444")
    assert(regexExtractPhone("111333444") =="")
  }
  test(" Extract Phone"){
    val extractPhone = pointOfContactName.extractPhone
    assert(extractPhone(" +111-333-4444 ") == "1113334444")
    assert(extractPhone(" 011-111-333-4444 ") =="0111113334444")
    assert(extractPhone("111-333-444") =="")
  }
  test("Extract Email"){
    val extractEmail = pointOfContactName.extractEmail
    assert(extractEmail(" enrique@gmail.com ") == "enrique@gmail.com")
    assert(extractEmail(" enriquegmail.com ") =="")
  }

  test("Point of Contact Name"){

   assert(pointOfContactName.pointOfContactName("Video Call ", "Skype") == "Skype")
   assert(pointOfContactName.pointOfContactName("Email"," enrique@gmail.com ") == "enrique@gmail.com")
   assert(pointOfContactName.pointOfContactName("Phone Call"," +555-777-4444 ") == "5557774444")
   assert(pointOfContactName.pointOfContactName("Phone Call"," 244455555 ") == "")

  }
}
