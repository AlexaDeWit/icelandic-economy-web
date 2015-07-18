package alexadewit_on_github.icelandic_economy

import scala.util._
import java.security.SecureRandom
import java.security.MessageDigest

object TokenGenerator {

  val tokenCharacters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_.-"
  lazy val secureRandom = new SecureRandom()

  def randomToken( length: Int = 30 ): String  = {
    Stream.continually( secureRandom.nextInt( tokenCharacters.size )).
      map( tokenCharacters ).take( length ).mkString
  }

}
