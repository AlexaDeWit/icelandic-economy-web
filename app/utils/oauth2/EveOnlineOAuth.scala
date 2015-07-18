package alexadewit_on_github.icelandic_economy.oauth2

import scalaz._, scalaz.syntax.either._
import org.http4s._
import org.http4s.util._

import argonaut._, Argonaut._, DecodeResult._
import AccessToken._

object EveOnlineOAuth {

  val authUrl = "https://login.eveonline.com/oauth/authorize/"
  val redirectCode = 302

  def loginQueryString( keys: OAuth2Keys,
                        state: String,
                        scopes: List[String],
                        redirectUri: String ): String = {
    val query = Query(
      "response_type" -> Some("code"),
      "redirect_uri"  -> Some(redirectUri),
      "client_id"     -> Some(keys.clientId),
      "scope"         -> Some(scopes.mkString(" ")),
      "state"         -> Some(state)
    )
    query.renderString
  }

  def getAccessToken( authCode: String, keys: OAuth2Keys ): String \/ AccessToken = {
     //write client post request logic here.
  }

}
