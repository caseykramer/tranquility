package org.drrandom.tranquility

trait Response {
	protected var _body:String = ""
	protected var _headers:Map[String,String] = Map.empty[String,String]
	
	def setHeaders(h:Map[String,String]) { _headers = h }
	def setBody(b:String) { _body = b }
	def body:String = _body
}



case class Received(code:Int,status:String) extends Response {}


object is {
	def unapply(r:Received):Option[(Received,Int)] = Some((r,r.code))
}