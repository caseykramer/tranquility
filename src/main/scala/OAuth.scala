package org.drrandom.tranquility

class OAuth(key:String) extends RequestExtension {
	
}

object OAuth {
	def apply(key:String) = new OAuth(key)
}

class Headers(headers:Map[String,String]) extends RequestExtension {
}

object Headers{
	def apply(headers:Map[String,String]) = new Headers(headers)
	def apply(headers:(String,String)*) = new Headers(Map(headers:_*))
}

class Args(args:Map[String,String]) extends RequestExtension {
}

object Args{
	def apply(args:Map[String,String]) = new Args(args)
	def apply(args:(String,String)*) = new Args(Map(args:_*))
}