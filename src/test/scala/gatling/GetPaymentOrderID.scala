package gatling

import gatling.FrapsSimulation._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
 * Created by Vngrs on 24.12.2014.
 */
class GetPaymentOrderID extends FrapsSimulation{

  lazy val getPaymentOrderID = authHeaders(http("Get paymnent based on order id").get("payments").queryParam("order_id", "123456"))
  lazy val sign = signer("GET", "payments")
  lazy val scn = scenario("Get Payment Type").feed(sign).exec(getPaymentOrderID)

}
