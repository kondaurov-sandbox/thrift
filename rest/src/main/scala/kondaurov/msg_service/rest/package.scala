package kondaurov.msg_service

package object rest {

  object PlayJson {

    import com.github.kondaurovdev.play_json.helper.TryHelper

    object Try extends TryHelper
  }

  object Snippets {

    import com.github.kondaurovdev.snippets.lang._
    import com.github.kondaurovdev.snippets.TryHelper

    object Lang {
      object TryHelper extends TryHelper
      object Future extends FutureHelper(TryHelper)
    }
  }

}
