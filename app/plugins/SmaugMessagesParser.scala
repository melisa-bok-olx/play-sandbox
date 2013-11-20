package plugins

import scala.util.parsing.combinator.RegexParsers
import com.github.tototoshi.csv.CSVReader
import play.api.Logger
import java.io.File

  class SmaugMessagesParser(messageSourcePath: String) extends RegexParsers {
  
    def parse = {

      val reader = CSVReader.open(new File(messageSourcePath))
      //m(1) -> source, m(2) -> target
      //Logger.info(reader.all.filter(_.length < 2).toString)
      val messages = reader.all.flatMap(m => Map(m(1) -> m(2)))
      
      //Logger.info("messages: " + messages)
      reader.close
      messages      

    }

  }