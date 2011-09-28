package org.drrandom.tranquility.tests

import org.specs2.mutable._
import org.drrandom.tranquility._

class SampleSyntaxSpec extends Specification {
	val authKey = "0T4xRKAZ9xYTjBs0I0hhvM3plLzYjOOij9kCEFh8dmhU"
	
	"Simple Requests" should {
		"work for HTTP GET" in {			
			val result = Get("http://localhost/rest") :: Headers("X-APIKey" -> "myKey") :: OAuth(authKey) -> match {
				case result is 200 =>  {
					println(result.body)
					"Yay!"
				}
				case result is 404 => "Boo"
				case _ => "Big Boo!"
			} 
			result must beEqualTo("Yay!")
		}
	}
}