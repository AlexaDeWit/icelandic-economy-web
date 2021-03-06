package alexadewit_on_github.icelandic_economy.oauth2

import scalaz._, scalaz.syntax.either._, scalaz.concurrent._
import argonaut._, Argonaut._
import argonaut.DecodeJson

import org.http4s._
import org.http4s.Http4s._
import org.http4s.client._
import org.http4s.util._
import org.http4s.headers._
import org.http4s.Status.NotFound
import org.http4s.Status.ResponseClass.Successful

import org.apache.commons.codec.binary.Base64

import AccessToken._

object OAuth2 {

  def accessTokenRequest(
    requestUri: Uri,
    authCode: String,
    keys: OAuth2Keys,
    host: String )
    : Task[Request] = {

    Request(
      Method.POST,
      requestUri,
      HttpVersion.`HTTP/1.1`,
      accessTokenGrantHeaders( base64EncodedKeys( keys ), host )
    ).withBody( UrlForm( tokenRequestMap( authCode ) ) )
  }

  /*
  def refreshTokenRequest(
    requestUri: Uri,
    keys: OAuth2Keys,
    host: String,
    accessToken: AccessToken 
    ) : Task[Request] = {

    Request(
      Method.POST,
      requestUri,
      HttpVersion.`HTTP/1.1`,
      refreshTokenGrantHeaders( base64EncodedKeys( keys ), host )
    ).withBody( UrlForm( tokenRequestMap( authCode ) ) )

  }
  */

  def accessToken(
    request: Task[Request],
    client: Client = org.http4s.client.blaze.defaultClient  )
      : Task[String \/ AccessToken] = {

    client( request ).flatMap {
      case Successful( jsonResp ) => jsonResp.as[AccessToken].map( _.right )
      case NotFound( resp )       => Task.now("404 Not Found".left)
      case resp                   => Task.now(s"Failed: ${resp}".left)
    }
  }


  def base64EncodedKeys( keys: OAuth2Keys ) : String = {
    Base64.encodeBase64String (
      s"${keys.clientId}:${keys.secretKey}".getBytes()
    )
  }

  def accessTokenGrantHeaders( base64Keys: String, host: String ) : Headers = {
    Headers(
      Header( "Authorization", s"Basic ${base64Keys}" ),
      Header( "Host", host ),
      `Content-Type`(  MediaType.`application/x-www-form-urlencoded` )
    )
  }
  
  def refreshTokenGrantHeaders( base64Keys: String, host: String ) : Headers = {
    Headers(
      Header( "Authorization", s"Basic ${base64Keys}" ),
      Header( "Host", host ),
      `Content-Type`(  MediaType.`application/x-www-form-urlencoded` )
    )
  }

  def accessTokenRequestMap( authCode: String ): Map[String,Seq[String]] = {
    Map(
      "grant_type" -> Seq("authorization_code"),
      "code" -> Seq(authCode)
    )
  }

  /*
  def refreshTokenRequestMap( authCode: String ): Map[String,Seq[String]] = {
    Map(
      "grant_type" -> Seq("authorization_code"),
      "code" -> Seq(authCode)
    )
  }
  */


}
