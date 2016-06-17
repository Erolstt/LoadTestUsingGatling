package gatling

import io.gatling.core.structure.ScenarioBuilder
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import gatling.FrapsSimulation._

/**
 * Created by erol on 24.12.2014.
 */
class GetPaymentType extends FrapsSimulation{

  lazy val getPaymentType = authHeaders(http("Get Payment type").get("payment_types/36").check(status.is(200)))
  lazy val sign = signer("GET", "payment_types")
  lazy val scn = scenario("Get Payment Type").feed(sign).exec(getPaymentType)
}
