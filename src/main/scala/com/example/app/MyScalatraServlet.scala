package com.example.app

import org.scalatra._
import org.slf4j.LoggerFactory
import spray.json._

case class Request(param1: String, param2: Array[String])
case class Result(status: String, data: String)

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit val requestFormat = jsonFormat2(Request)
  implicit val resultFormat = jsonFormat2(Result)
}

class MyScalatraServlet extends ScalatraServlet {

  val logger = LoggerFactory.getLogger(getClass)

  import MyJsonProtocol._

  def badRequest = halt(400, Result("Failure", "").toJson)

  get("/") {
    // Block access to "/"
    halt(403, badRequest)
  }

  post("/service/test") {
    val bodyObject = request.body.parseJson.convertTo[Request]
    logger.info(s"Received: param1=${bodyObject.param1} param2=${bodyObject.param2.mkString(",")}")
    Result("Success", "data-here").toJson
  }

  notFound {
    "Not found."
  }

}
