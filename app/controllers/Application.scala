package controllers

import play.api._
import play.api.mvc._

class Application extends Controller {

  def index = Action {
    val cid = Play.current.configuration.getString("oauth2.clientid")
    Ok(views.html.index(cid.getOrElse("Not Found") ))
  }

}
