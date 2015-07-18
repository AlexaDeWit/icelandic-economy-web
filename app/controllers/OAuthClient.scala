package controllers

import play.api._
import play.api.mvc._
import scalaz._
import alexadewit_on_github.icelandic_economy._
import alexadewit_on_github.icelandic_economy.oauth2._

class OAuthClient extends Controller {

  def authorize = Action { implicit request =>
    OAuth2Keys.default match {
      case \/-( keys ) => {
        val queryString = EveOnlineOAuth.loginQueryString(
          keys, "NERDASS", List("publicData"), "http://localhost:9000/oauth_client/callback"
        )
        Redirect(
          s"${EveOnlineOAuth.authUrl}?${queryString}",
          EveOnlineOAuth.redirectCode
          ).withSession( "oauth2state" ->  TokenGenerator.randomToken( 30 ) )
      }
      case _ => NotFound( "OAuth2 Client not configured" )
    }
  }

  def callback( code: Option[String], state: Option[String] )  = Action { implicit request =>
    Ok( views.html.index( s"${code} --- ${state} --- ${request.session.get("oauth2state")}" ) )
  }

}
