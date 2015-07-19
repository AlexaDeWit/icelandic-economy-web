package alexadewit_on_github.icelandic_economy.oauth2

import scalaz._, scalaz.syntax.either._
import play.api._

case class OAuth2Keys( clientId: String, secretKey: String )

object OAuth2Keys {

  def default: String \/ OAuth2Keys = fromPlayConfig( Play.current.configuration )

  def fromPlayConfig( playConfig: Configuration ): String \/ OAuth2Keys = {
    val cidOpt = playConfig.getString("oauth2.clientid")
    val skOpt = playConfig.getString("oauth2.secret_key")
    ( cidOpt, skOpt ) match {
      case ( _, None ) | ( None, _ ) =>  "Could not acquire keys from config file.".left
      case ( Some( id ), Some( sk ) ) => OAuth2Keys( id, sk ).right
    }
  }

}
