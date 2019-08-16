import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.util.ByteString
import akka.http.scaladsl.model.headers.RawHeader

import scala.concurrent.Future
import scala.util.{Failure, Success}

object Client {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher
    val cookieHeader: HttpHeader = RawHeader(
      "Cookie",
      "JSESSIONID=C7B94E92B81A879A92DA138E351DEC6B; vid=rBEAAl04MiY6WgBISUOcAg==; D_IID=09D61804-4E2C-376D-B3B2-232C5B963C4A; D_UID=C50AF571-7D60-3382-9F41-8B0DA10B30A9; D_ZID=AC8964EF-236C-35C0-9C62-25805BDF05A2; D_ZUID=5D5E1DF8-6984-3034-AA2D-2878F0BD7473; D_HID=9FFFD8D3-95E1-3128-9C02-6844518C9154; D_SID=103.62.237.65:iofim6Lw/nbEvV1n3FoMPWFpcOSKvqSTG5HuC3pQ1do; _ga=GA1.2.1018737922.1563963946; _gid=GA1.2.88332800.1563963946; amplitude_id_e102edba5e9caea6b89e3c04fac87a4dowler.com=eyJkZXZpY2VJZCI6IjQyNzFlNTIxLWVkNDktNDViYy1hZjY5LTg2NTlkYzgxZjNiMFIiLCJ1c2VySWQiOiI1OTEyNTYyIiwib3B0T3V0IjpmYWxzZSwic2Vzc2lvbklkIjoxNTYzOTY5MzY1Mjc5LCJsYXN0RXZlbnRUaW1lIjoxNTYzOTY5NjQ1MjIyLCJldmVudElkIjoyMiwiaWRlbnRpZnlJZCI6MTAsInNlcXVlbmNlTnVtYmVyIjozMn0=; li_c=1; __qca=P0-1428244705-1563963947904; d7s_uid=jyh3rc7ao1y4oc; __gads=ID=81f3f3ac89fb3840:T=1563963963:S=ALNI_Mb66lskUADreULNiiZBeiMv9LoZCg; onBoardingEmail=anshika.agrawal@knoldus.in; NC_VARNISH=true; fts=sNkSEdxpNsq0jvM_UIaWkA; ntv_fpc=4f4404ff-400b-42c3-b43f-4cc19a048b5c; OWLER_WEBID=5d383a596e0f26497d6be3f9; OWLER_PC=66-P_tC5n76BXNm4jTcvkCHQVD-D8Ka_adfyqOOS9Se2ZNdwROXjVVav6n-xocpaxGKiGTZwrDRDIDiXW1cnh1i0SJcagcQcc7HWHZQkOietUeLrow_ZrSAAS4s6lwuzeuF79OLQ2oKbEYS1EO9eZw; __rtgt_sid=jyh6z9v0xynlgq; d7s_spc=2"
    )
    val userAgent: HttpHeader = RawHeader(
      "User-Agent",
      "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0"
    )
    val responseFuture: Future[HttpResponse] = Http().singleRequest(
      HttpRequest(uri = "https://www.owler.com/company/knoldus")
        .withHeaders(cookieHeader, userAgent)
    )

    responseFuture
      .onComplete {
        case Success(res) => {
          val HttpResponse(statusCodes, headers, entity, _) = res
          println(statusCodes)
          println(headers)
          println(res)
          res.entity.dataBytes
            .map(_.utf8String)
            .runForeach(data => println(data))
          // system.terminate()
        }
        case Failure(_) => sys.error("something wrong")
      }
  }
}
