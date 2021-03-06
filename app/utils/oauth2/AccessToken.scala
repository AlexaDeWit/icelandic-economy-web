package alexadewit_on_github.icelandic_economy.oauth2

import scalaz._, scalaz.syntax.either._
import argonaut._, Argonaut._

import org.http4s._
import org.http4s.util._
import org.http4s.argonaut.jsonOf

case class AccessToken( token: String,
                        tokenType: String,
                        expiresIn: Int, 
                        refreshToken: Option[String] ) {

}

object AccessToken {
  implicit def accessTokenCodecJson : CodecJson[AccessToken] = {
    casecodec4( AccessToken.apply, AccessToken.unapply )(
      "access_token", "token_type", "expires_in", "refresh_token"
    )
  }

  implicit def accessTokenDecoder = jsonOf[AccessToken]
}

