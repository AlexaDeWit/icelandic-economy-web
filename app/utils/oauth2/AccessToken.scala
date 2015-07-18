package alexadewit_on_github.icelandic_economy.oauth2

import scalaz._, scalaz.syntax.either._
import org.http4s._
import org.http4s.util._
import argonaut._, Argonaut._

case class AccessToken( token: String,
                        tokenType: String,
                        expiresIn: Int, 
                        refreshToken: Option[String] ) {

}

object AccessToken {
  implicit def accessTokenCodecJson : CodecJson[AccessToken] = {
    casecodec2( AccessToken.apply, AccessToken.unapply )(
      "access_token", "token_type", "expires_in", "refresh_token"
    )
  }
}

