package controllers

import play.api._
import play.api.mvc._
import scalaz._
import alexadewit_on_github.icelandic_economy._
import alexadewit_on_github.icelandic_economy.oauth2._

class OAuthClient extends Controller {

  def authorize = Action { implicit request =>
    withOAuthKeysOrNotFound( keys => {
      val stateToken = TokenGenerator.randomToken( 30 )
      val queryString = EveOnlineOAuth.loginQueryString(
        keys, stateToken, List("publicData"), "http://localhost:9000/oauth_client/callback"
      )
      Redirect(
        s"${EveOnlineOAuth.authUrl}?${queryString}",
        EveOnlineOAuth.redirectCode ).withSession( "oauth2state" -> stateToken )
    })
  }

  def callback( code: Option[String], state: Option[String] )  = Action { implicit request =>
    ( code, state ) match {
      case ( None, _ ) | ( _ , None ) => Ok( views.html.index("FUCK!") )
      case ( Some(authCode), Some( stateToken ) ) => {
        Ok( views.html.index( s"${EveOnlineOAuth.getAccessToken(authCode, OAuth2Keys.default.toOption.get)}" ) )
      }
    }
  }

  def withOAuthKeysOrNotFound( f: OAuth2Keys => Result ) : Result = {
    OAuth2Keys.default.fold( s => NotFound( "OAuth2 Client not configured." ) , f )
  }

}
