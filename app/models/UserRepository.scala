package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
 * A repository for user.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
 */
@Singleton
class UserRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._

  /**
   * Here we define the table. It will have a name of user
   */
  private class UserTable(tag: Tag) extends Table[User](tag, "user") {

    /** The ID column, which is the primary key, and auto incremented */
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    /** The firstName column */
    def firstName = column[String]("firstName")

    /** The lastName column */
    def lastName = column[String]("lastName")

    /** The mobile column */
    def mobile = column[String]("mobile")

    /** The email column */
    def email = column[String]("email")

    /** The age column */
    def age = column[Int]("age")

    /**
     * This is the tables default "projection".
     *
     * It defines how the columns are converted to and from the User object.
     *
     * apply and unapply methods.
     */
    def * = (id, firstName, lastName, mobile, email, age) <> ((User.apply _).tupled, User.unapply)
  }

  /**
   * The starting point for all queries on the people table.
   */
  private val users = TableQuery[UserTable]

  /**
   * Create a user with the given name and age.
   *
   * This is an asynchronous operation, it will return a future of the created user, which can be used to obtain the
   * id for that user.
   */
  def create(firstName: String, lastName: String, mobile: String, email: String, age: Int): Future[User] = db.run {
    (users.map(u => (u.firstName, u.lastName, u.mobile, u.email, u.age))
      returning users.map(_.id)
      // returned id
      into ((userData, id) => User(id, userData._1, userData._2, userData._3, userData._4, userData._5))
    ) += (firstName, lastName, mobile, email, age)
  }

  /**
   * List all the user in the database.
   */
  def list(): Future[Seq[User]] = db.run {
    users.result
  }
}
