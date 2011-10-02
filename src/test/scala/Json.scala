package org.drrandom.tranquility.tests

import org.specs2.mutable._
import org.drrandom.tranquility.json._

class JsonSpec extends Specification {
	"JSON function" should {
		"encode basic name value pair" in {
			val result = json("one" -> "1")

			result must beEqualTo("""{ "one": "1" }""")
		}

		"encode two name-value paris" in {
			val result = json("one" -> "1", "two" -> "2")

			result must beEqualTo("""{ "one": "1", "two": "2" }""")
		}

		"encode three or more name-value pairs" in {
			var result = json("one" -> "1", "two" -> "2", "three" -> "3")

			result must beEqualTo("""{ "one": "1", "two": "2", "three": "3" }""")

			result = json("one" -> "1", "two" -> "2", "three" -> "3", "four" -> "4", "five" -> "5", "six" -> "6")

			result must beEqualTo("""{ "one": "1", "two": "2", "three": "3", "four": "4", "five": "5", "six": "6" }""")
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
					("name" -> "Bob", "age" -> "26", "occupation" -> "geek")
				)
			)

			result must beEqualTo("""{ "person": { "name": "Bob", "age": "26", "occupation": "geek" } }""")
		}
	}
}
