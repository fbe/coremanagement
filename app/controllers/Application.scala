package controllers

import play.api._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.Future

object AuthorizedAction extends ActionBuilder[Request] {

  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]): Future[Result] = {
    val maybeToken = request.headers.get("X-XSRF-TOKEN")

    maybeToken.map(token =>
      block(request)
    ).getOrElse(
      Future.successful(Results.Unauthorized)
    )
  }

}
case class SampleJSON(foo: String, bar: String)

object Application extends Controller {

  def index = Action { request =>
    Ok(views.html.main())
  }

  def login = Action { request =>
    // set cookie XSRF-TOKEN
      Ok(views.html.login())
  }

  def sampleJson = AuthorizedAction {

    implicit val sampleFormats = Json.format[SampleJSON]

    val serialized = Json.toJson(SampleJSON("foo","bar"))

    Ok(serialized)
  }

}