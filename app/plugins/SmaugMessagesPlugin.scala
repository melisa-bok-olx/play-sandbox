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
