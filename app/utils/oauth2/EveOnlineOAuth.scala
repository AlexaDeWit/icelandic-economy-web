package alexadewit_on_github.icelandic_economy.oauth2

import scalaz._, scalaz.syntax.either._, scalaz.concurrent._
import argonaut._, Argonaut._

import org.http4s._
import org.http4s.Http4s._
import org.http4s.util._
import org.http4s.headers._
import org.apache.commons.codec.binary.Base64

import AccessToken._

object EveOnlineOAuth {

  val authUrl = "https://login.eveonline.com/oauth/authorize/"
  val redirectCode = 302

  def loginQueryString(
    keys: OAuth2Keys,
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

  def accessTokenRequest( authCode: String, keys: OAuth2Keys ): Task[String \/ AccessToken] = {
    val client = org.http4s.client.blaze.defaultClient
    val loginUri = uri("https://login.eveonline.com/oauth/token")
    val request = OAuth2.accessTokenRequest(
      loginUri, 
      authCode,
      keys,
      "login.eveonline.com"
    )
    OAuth2.accessToken( request )
  }

}
