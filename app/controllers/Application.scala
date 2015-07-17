package controllers

import play.api._
import play.api.mvc._
import scalaz._
import alexadewit_on_github.icelandic_economy.oauth2._

class Application extends Controller {

  def index = Action {
    OAuth2Keys.default match {
      case \/-( keys ) => {
        Ok(views.html.index(
          EveOnlineOAuth.loginQueryString( 
            keys, "NERDASS", List("publicData"),"http://localhost:9000/oauth_client/callback"
          )
        ))
      }
      case -\/( _ ) =>
        Ok(views.html.index("Butt"))
    }
  }

}
