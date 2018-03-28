package util

import java.util.Properties
object Utils {

  def getProperty: Properties = {
    val prop = new Properties()

    val is = Utils.getClass.getResourceAsStream("/application.properties")
    prop.load(is)
    return prop

  }

}