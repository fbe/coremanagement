package controllers

import play.api.{Logger, Play}
import play.api.mvc.{Action, Controller}


trait DemoService {
  def getDemoString: String
}

trait RealDemoService extends DemoService {
  override def getDemoString: String = throw new RuntimeException("i can't reach the bus! ohnoez")
}

trait MockDemoService extends DemoService {
  override def getDemoString = "Hello World! demo service here (mock)"
}

trait DemoController extends Controller {
  this: DemoService =>

  def getDemoData = Action {
    Ok(getDemoString)
  }

}


package object ControllerRegistry {

  val configuration = Play.current.configuration
  val demoMode = configuration.getBoolean("mockMode").get

  def ctrl[T <: Controller](demoController: T, productionController: T): T = if(demoMode) demoController else productionController

  lazy val demoController = ctrl(new DemoController with MockDemoService,new DemoController with RealDemoService)

}