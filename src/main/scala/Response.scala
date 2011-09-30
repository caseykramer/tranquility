package org.drrandom.tranquility

trait Response {
	protected var _body:String = ""
	protected var _headers:Map[String,String] = Map.empty[String,String]
	protected var _time:Long = 0
	
	def setHeaders(h:Map[String,String]) { _headers = h }
	def setBody(b:String) { _body = b }
	def setTime(t:Long) {_time = t}

	def body:String = _body
	def time:Long = _time
}



case class Received(code:Int,status:String) extends Response {}


object is {
	def unapply(r:Received):Option[(Received,Int)] = Some((r,r.code))
}

case class ErrorResult(x:Throwable) extends Response