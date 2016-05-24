package com.example.app

import org.scalatra.test.specs2._

// Reference: http://scalatra.org/guides/testing/specs2.html

class MyScalatraServletSpec extends MutableScalatraSpec {

  addServlet(classOf[MyScalatraServlet], "/*")

  "GET /" should {
    "return status 400" in {
      get("/"
      ) {
        status must_== 400
        body must_== "{\"status\":\"Failure\",\"data\":\"\"}"
      }
    }
  }

  "POST /service/test" should {
    "return status 200" in {
      post("/service/test", "{\"param1\":\"value1\",\"param2\":[\"value3\",\"value4\"]}"
      ) {
        status must_== 200
        body must_== "{\"status\":\"Success\",\"data\":\"data-here\"}"
      }
    }
  }

  "GET /does-not-exist" should {
    "return status 'Not found.'" in {
      get("/does-not-exist"
      ) {
        status must_== 200
        body must_== "Not found."
      }
    }
  }

}
