# scalatra-minimal

_Starting point for Scalatra-based projects._

According to the [documentation](http://scalatra.org/getting-started/installation.html), `conscript` + `giter8` is the recommended way to generate a skeleton project for Scalatra. This is based on that approach but the resulting skeleton is already produced and some useful things have been added.

**Notes**:

- This has been generated starting with a blank `sbt` project in IntelliJ and then adding the necessary files:
    - `project/assembly.sbt`
    - `project/build.scala`
    - `project/plugins.sbt` [already there, updated]
    - `src/main/resources/logback.xml`
    - `src/main/scala/JettyLauncher`
    - `src/main/scala/ScalatraBootstrap`
    - `src/main/scala/com/example/app/MyScalatraServlet`
    - `src/main/webapp/WEB-INF/*`
    - `src/test/scala/com/example/app/MyScalatraServletSpec.scala`
- The original skeleton includes an `sbt` script in the root folder which has been skipped since using your regular `sbt` is just fine
- [spray-json](https://github.com/spray/spray-json) has been added to parse and serialize JSON data
- [sbt-assembly](https://github.com/sbt/sbt-assembly) plugin has been included to be able to generate a fat JAR
- `JettyLauncher` has been provided to support standalone mode, which is useful when a fat JAR is generated and when you need to debug using your IDE
- The sample `MyScalatraServlet` implements:
    - `GET /`:
        - It simply generates an error to block access to `/`
    - `POST /service/test`, that expects a JSON body that can be parsed as:
        - `case class Request(param1: String, param2: Array[String])`
    - `notFound`:
        - A "Not found." message is shown
        - If you remove this then trying to access something that does not exist will list the valid endpoints

To run it standalone:

- `sbt run`
- (or simply Run or Debug `JettyLauncher` using e.g. IntelliJ)

It will listen on port `8080` by default.

To send some test data using `curl` (example of POST):

```
echo "{\"param1\":\"value1\",\"param2\":[\"value3\",\"value4\"]}" | curl -X POST -d @- http://localhost:8080/service/test --header "Content-Type:application/json"
```

 If you need to run it in a different port you can set the environment variable `PORT`. Example that starts it on port `9999`:

- `PORT=9999 sbt run`

To run it using Jetty as a container:

- `sbt "~;jetty:stop;jetty:start"`

That will reload automatically after changes in the source code.

To generate a fat JAR (it will run the tests automatically):

- `sbt assembly`

Then you can run it using the generated JAR:

- `java -jar target/scala-2.11/scalatra-minimal-assembly-1.0.jar`

If you only want to run the tests:

- `sbt test`