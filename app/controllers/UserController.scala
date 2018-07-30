package controllers

import javax.inject._

import models._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class UserController @Inject()(repo: UserRepository,
                               cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  /**
   * The mapping for the user form.
   */
  val userForm: Form[CreateUserForm] = Form {
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "mobile" -> nonEmptyText,
      "email" -> nonEmptyText,
      "age" -> number.verifying(min(0), max(140))
    )(CreateUserForm.apply)(CreateUserForm.unapply)
  }

  /**
   * The index action.
   */
  def index = Action { implicit request =>
    Ok(views.html.index(userForm))
  }

  /**
   * The add user action.
   *
   * This is asynchronous, since we're invoking the asynchronous methods on UserRepository.
   */
  def addUser = Action.async { implicit request =>
    // Bind the form first, then fold the result, passing a function to handle errors, and a function to handle succes.
    userForm.bindFromRequest.fold(

      errorForm => {
        Future.successful(Ok(views.html.index(errorForm)))
      },

      user => {
        repo.create(user.firstName, user.lastName, user.mobile, user.email, user.age).map { _ =>
          Redirect(routes.UserController.index).flashing("success" -> "user.created")
        }
      }
    )
  }

  /**
   * A REST endpoint that gets all the people as JSON.
   */
  def getUser = Action.async { implicit request =>
    repo.list().map { user =>
      Ok(Json.toJson(user))
    }
  }
}

/**
 * The create user form.
 *
**/
case class CreateUserForm(firstName: String, lastName: String, mobile: String, email: String, age: Int)
