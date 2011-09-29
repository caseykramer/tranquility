package org.drrandom.tranquility.tests

import org.specs2.mutable._
import org.drrandom.tranquility._

class JsonSpec extends Specification {
	"JSON function" should {
		"encode basic name value pairs of strings" in {
			val result = json(("one" -> "1") ~ ("two" -> "2") )

			result must beEqualTo("""{"one":"1","two":"2"}""")
		}
	}
}
