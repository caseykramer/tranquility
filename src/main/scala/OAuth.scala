package org.drrandom.tranquility

class OAuth(key:String) extends RequestExtension {
	override def preProcess(r:Request) {
		val value = "OAuth "+key
		r.headers = r.headers ++ Map("Authorization" -> value)
	}
}

object OAuth {
	def apply(key:String) = new OAuth(key)
}

class Headers(headers:Map[String,String]) extends RequestExtension {
	override def preProcess(r:Request) {
		r.headers = r.headers ++ headers
	}
}

object Headers{
	def apply(headers:Map[String,String]) = new Headers(headers)
	def apply(headers:(String,String)*) = new Headers(Map(headers:_*))
}

class JSON extends RequestExtension {
	override def preProcess(r:Request) {
		r.headers = r.headers ++ Map("Content-Type" -> "application/json")
	}
}

object JSON {
	def apply() = new JSON
}

class Args(args:Map[String,String]) extends RequestExtension {
}

object Args{
	def apply(args:Map[String,String]) = new Args(args)
	def apply(args:(String,String)*) = new Args(Map(args:_*))
}