package models

import play.api.libs.json._

case class User(id: Long, firstName: String, lastName: String, mobile: String, email: String, age: Int)

object User {
  implicit val userFormat = Json.format[User]
}
