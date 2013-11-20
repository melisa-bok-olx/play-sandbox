package plugins

import java.io._
import play.api.Plugin
import play.api.i18n.MessagesApi
import play.api.i18n.Lang
import play.api._
import play.core._
import play.api.i18n._
import scala.language.postfixOps
import play.api._
import play.core._
import java.io._
import scala.util.parsing.input._
import scala.util.parsing.combinator._
import scala.util.matching._
import scala.util.control.NonFatal
import com.github.tototoshi.csv.CSVReader

//object SmaugMessages {
//
//  /**
//   * Translates a message.
//   *
//   * Uses `java.text.MessageFormat` internally to format the message.
//   *
//   * @param key the message key
//   * @param args the message arguments
//   * @return the formatted message or a default rendering if the key wasn’t defined
//   */
//  def apply(key: String, args: Any*)(implicit lang: Lang): String = {
//    Logger.info("Get message for key: " + key)
//    Play.maybeApplication.flatMap { app =>
//      app.plugin[SmaugMessagesPlugin].map(_.api.translate(key, args)).getOrElse(throw new Exception("this plugin was not registered or disabled"))
//    }.getOrElse(noMatch(key, args))
//  }
//
//  /**
//   * Retrieves all messages defined in this application.
//   */
//  def messages(implicit app: Application): Map[String, Map[String, String]] = {
//    app.plugin[SmaugMessagesPlugin].map(_.api.messages).getOrElse(throw new Exception("this plugin was not registered or disabled"))
//  }
//
//  private def noMatch(key: String, args: Seq[Any]) = key
//  
//  private class SmaugMessageParser(messageSourcePath: String) extends RegexParsers {
//
//    def parse = {
//      
//      val reader = CSVReader.open(new File(messageSourcePath))
//      //m(1) -> source, m(2) -> target
//      val messages = reader.all.flatMap(m => Map(m(1) -> m(2)))
//      reader.close
//      messages
//    }
//
//}
//
//
//}


class SmaugMessagesPlugin(app: Application) extends Plugin {

  import scala.collection.JavaConverters._

  import scalax.file._
  import scalax.io.JavaConverters._

  private def loadMessages(file: String): Map[String, String] = {
    Logger.info("loadMessages: " + file)
    app.resource(file).map { messageFile =>
      Logger.info("messagefile: " + messageFile.getFile())
      new SmaugMessagesParser(messageFile.getFile()).parse
    }.foldLeft(Map.empty[String, String]) { _ ++ _ }
  }

  private lazy val messages = {
    MessagesApi {
      Lang.availables(app).map(_.code).map { lang =>
        (lang, loadMessages("translations/" + lang + ".csv"))
      }.toMap + ("default" -> loadMessages("translations/default.csv"))
    }
  }

  /**
   * The underlying internationalisation API.
   */
  def api = messages

  /**
   * Loads all configuration and message files defined in the classpath.
   */
  override def onStart() {
    Logger.info("Starting plugin...")
    messages
  }
  
  override def onStop() {
    Logger.info("Stopping plugin...")
  }
  
  override lazy val enabled = {
    Logger.info("enabled?")
    true
  }
  
  
}
