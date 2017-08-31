package kondaurov.msg_service

package object service {

  object PlayJson {

    import com.github.kondaurovdev.play_json.helper._

    object Try extends TryHelper
    object Yaml extends YamlHelper(Try)
    object JsError extends JsErrorHelper
    object String extends StringHelper
    object Validate extends ValidateHelper(includeInput = true, jsErrorHelper = JsError)
    object JsValue extends JsValueHelper(Validate, String)

  }

  object Typeconfig {

    import com.github.kondaurovdev.typeconfig._

    object ConfigHelper extends ConfigHelper(PlayJson.Try, PlayJson.Validate)
    object ConfigLoader extends ConfigLoader(PlayJson.Try)

  }

  object Snippets {

    import com.github.kondaurovdev.snippets._

    object TryHelper extends TryHelper
    object HashHelper extends text.HashHelper(TryHelper)

  }

}
