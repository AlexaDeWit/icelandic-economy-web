package controllers

import play.api._
import play.api.mvc._
import scalaz._
import alexadewit_on_github.icelandic_economy.oauth2._

class Application extends Controller {

  def index = Action {
    OAuth2Keys.default match {
      case -\/( _ ) => 
        Ok(views.html.index("Missing keys!" ))
      case \/-( keys ) =>
        Ok(views.html.index(keys.toString ))
    }
  }

}
