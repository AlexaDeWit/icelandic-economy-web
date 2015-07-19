package alexadewit_on_github.icelandic_economy.oauth2

import scalaz._, scalaz.syntax.either._, scalaz.concurrent._
import org.http4s._
import org.http4s.Http4s._
import org.http4s.util._
import org.http4s.headers._
import org.apache.commons.codec.binary.Base64

import argonaut._, Argonaut._
import AccessToken._

object OAuth2 {

  def accessTokenRequest( requestUri: Uri, authCode: String, keys: OAuth2Keys ): Task[Request] = {
    Request(
      Method.POST,
      requestUri, 
      HttpVersion.`HTTP/1.1`,
      oauth2AuthHeaders( base64EncodedKeys( keys ) )
    ).withBody( UrlForm( tokenRequestMap( authCode ) ) )
  }

  def base64EncodedKeys( keys: OAuth2Keys ) : String = {
    Base64.encodeBase64String (
      s"${keys.clientId}:${keys.secretKey}".getBytes()
    )
  }

  def oauth2AuthHeaders( base64Keys: String ) : Headers = {
    Headers( 
      Header( "Authorization", s"Basic ${base64Keys}" ),
      Header( "Host", "login.eveonline.com" ),
      `Content-Type`(  MediaType.`application/x-www-form-urlencoded` )
    )
  }

  def tokenRequestMap( authCode: String ): Map[String,Seq[String]] = {
    Map( 
      "grant_type" -> Seq("authorization_code"),
      "code" -> Seq(authCode)
    )
  }

}
