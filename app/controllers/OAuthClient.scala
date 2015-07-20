package controllers

import play.api._
import play.api.mvc._
import scalaz._
import scalaz.std.option._
import scalaz.syntax.apply._
import alexadewit_on_github.icelandic_economy._
import alexadewit_on_github.icelandic_economy.oauth2._
import org.http4s.Uri.uri

class OAuthClient extends Controller {

  def authorize = Action { implicit request =>
    withOAuthKeysOrNotFound( OAuth2Keys.default )( keys => {
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
    ( code |@| state ) { case ( authCode, stateToken ) => 
      withOAuthKeysOrNotFound( OAuth2Keys.default )( keys => {
        EveOnlineOAuth.accessTokenRequest( authCode, keys ).run.fold( s => {
          InternalServerError(
            "Received an invalid response from Eve Online's OAuth2 Service." 
        )},
      token => Ok( views.html.index( token.toString ) )
      )
      })
    } getOrElse ( NotFound( "REKT CUNT" ) )
  }

  def withOAuthKeysOrNotFound( possibleKeys: String \/ OAuth2Keys )( f: OAuth2Keys => Result ) : Result = {
    possibleKeys.fold( _ => NotFound( "OAuth2 Client not configured." ) , f )
  }

}
