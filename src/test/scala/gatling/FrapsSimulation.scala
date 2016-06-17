package gatling

import CryptoUtils._
import io.gatling.core.scenario.Simulation
import scala.concurrent.duration._
import io.gatling.http.request.builder.{ HttpRequestBuilder => HRB }
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import io.gatling.http.request.StringBody
import io.gatling.core.structure.ScenarioBuilder
import javax.crypto.spec.SecretKeySpec
import javax.crypto.Mac
import org.apache.commons.codec.binary.Base64
import javax.crypto.spec.SecretKeySpec
import javax.crypto.Mac
import org.apache.commons.codec.binary.Base64



object CryptoUtils {

  private final val HmacSha512 = "HmacSHA512"

  def signHmacSha512(key: String, data: String): String = {
    if (key == null || data == null || key.isEmpty) {
      ""
    } else {
      // get an hmac_sha512 key
      val keyBytes = key.getBytes
      val signingKey = new SecretKeySpec(keyBytes, HmacSha512)

      // get an hmac_sha512 instance
      val mac = Mac.getInstance(HmacSha512)
      mac.init(signingKey)

      // compute the hmac
      val raw = mac.doFinal(data.getBytes)
      Base64.encodeBase64String(raw)
    }
  }
}


object FrapsSimulation {


     def authHeaders(a: HRB): HRB = a.headers(Map(
    "leaf-api-timestamp" -> "${lat}",
    "leaf-api-signature-sha512" -> "${las}"))


}
trait FrapsSimulation extends Simulation {


  lazy val url = "https://api.lsomeserver.com/"

  def signer(method: String, api: String) = Iterator.continually(signature(method, api))

  lazy val globalToken = "0000000-sometoken"

  def signature(method: String, api: String): Map[String, String] = {
    val timestamp = System.currentTimeMillis / 1000
    val sign = signHmacSha512(globalToken, timestamp.toString + "," + method.toString.toUpperCase + "," + api)
    Map(
      "lat" -> timestamp.toString(),
      "las" -> sign)
  }

  lazy val protocol = http.baseURL(url).contentTypeHeader("application/json")

  lazy val user = 1
  lazy val time = 1

  def scn: ScenarioBuilder

  setUp(scn.inject(rampUsers(user) over (time seconds)))
    .protocols(protocol)
    .assertions(global.successfulRequests.percent.is(100))





}
