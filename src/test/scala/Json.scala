package org.drrandom.tranquility.tests

import org.specs2.mutable._
import org.drrandom.tranquility.json._

class JsonSpec extends Specification {
	"JSON function" should {
		"encode basic name value pairs of strings" in {
			val result = json(("one" -> "1") ~ ("two" -> "2") )

			result must beEqualTo("""{"one":"1","two":"2"}""")
		}

		"encode nested tuples" in {
			val result = json(
				("person" ->
			 		("name" -> "Bob")
			 	)
			)

			result must beEqualTo("""{ "person": { "name": "Bob" } }""")
		}

		"encode multiple tuples as JSON object" in {
			val result = json(
				("person" -> 
					("name" -> "Bob", "age" -> "26")
				)
			)

			result must beEqualTo("""{ "person": {"name":"Bob","age":"26"} }""")
		}

		"fantacySyntax" in {
			val json = j{ "person" -> { "name" -> "Bob" } }
		}
	}
}
