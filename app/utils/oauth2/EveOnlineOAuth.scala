package alexadewit_on_github.icelandic_economy.oauth2

import scalaz._, scalaz.syntax.either._
import org.http4s._
import org.http4s.Http4s._
import org.http4s.util._
import org.http4s.headers._
import org.apache.commons.codec.binary.Base64

import argonaut._, Argonaut._
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

  def getAccessToken( authCode: String, keys: OAuth2Keys ):String = {

    val client = org.http4s.client.blaze.defaultClient

    val base64encoded = Base64.encodeBase64String(
      s"${keys.clientId}:${keys.secretKey}".getBytes() 
    )

    val headers = Headers( 
      Header( "Authorization", s"Basic ${base64encoded}" ),
      Header( "Host", "login.eveonline.com" ),
      `Content-Type`(  MediaType.`application/x-www-form-urlencoded` )
    )
    val requestBody = Map( 
      "grant_type" -> Seq("authorization_code"),
      "code" -> Seq(authCode)
    )

    val request = Request(
      Method.POST,
      uri("https://login.eveonline.com/oauth/token"),
      HttpVersion.`HTTP/1.1`,
      headers
      ).withBody( UrlForm( requestBody ) )

    client( request ).as[String].run
  }

}
