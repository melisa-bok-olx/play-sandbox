package plugins

import scala.util.parsing.combinator.RegexParsers
import com.github.tototoshi.csv.CSVReader
import play.api._
import play.core._
import java.io.File
import play.api.i18n.Lang
import com.github.tototoshi.csv.DefaultCSVFormat

object SmaugMessages {

  /**
   * Translates a message.
   *
   * Uses `java.text.MessageFormat` internally to format the message.
   *
   * @param key the message key
   * @param args the message arguments
   * @return the formatted message or a default rendering if the key wasn’t defined
   */
  def apply(key: String, args: Any*)(implicit lang: Lang): String = {
    Logger.info("Get message for key: " + key)
    Play.maybeApplication.flatMap { app =>
      app.plugin[SmaugMessagesPlugin].map(_.api.translate(key, args)).getOrElse(throw new Exception("this plugin was not registered or disabled"))
    }.getOrElse(noMatch(key, args))
  }

  /**
   * Retrieves all messages defined in this application.
   */
  def messages(implicit app: Application): Map[String, Map[String, String]] = {
    app.plugin[SmaugMessagesPlugin].map(_.api.messages).getOrElse(throw new Exception("this plugin was not registered or disabled"))
  }

  private def noMatch(key: String, args: Seq[Any]) = key


}