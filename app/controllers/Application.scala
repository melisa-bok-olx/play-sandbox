package controllers

import play.api.i18n.Lang
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.Logger
import plugins.SmaugMessages


object Application extends Controller {
  
  def index(key:String, lang: String) = Action {
    
    val translated = SmaugMessages(key)(Lang(lang))
    Ok(lang + ": " + translated)
  }
  
}