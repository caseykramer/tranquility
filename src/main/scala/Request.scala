package org.drrandom.tranquility

import org.apache.http._
import org.apache.http.util._
import org.apache.http.client._
import org.apache.http.impl.client._

trait Request {
	val url:String = ""
	var headers:Map[String,String] = Map.empty[String,String]

	private var extensions = List[RequestExtension]()
	def addExtension(ext:RequestExtension):Request = {
		extensions = extensions :+ ext
		this
	}
	
	def -> :Response = {
		val client = new DefaultHttpClient()
		val handler = new ReceievedResponseHandler()
		val response = client.execute(this.getMethod)
		handler.handleResponse(response)
	}

	private[tranquility] def url_=():String = this.url
}

class ReceievedResponseHandler {
	def handleResponse(response:HttpResponse):Received = {
		val statusLine = response.getStatusLine
		var received = new Received(statusLine.getStatusCode,statusLine.getReasonPhrase)
		received.setHeaders(Map(response.getAllHeaders.map(h => h.getName -> h.getValue):_*))
		var entity = response.getEntity()
		if(entity != null)
			received.setBody(EntityUtils.toString(entity))
		received
	}
}

trait RequestWithBody[T] extends Request {
	val body:T
}

trait RequestExtension {
	private var r:Request = null
	def request :Request = this.r
	def request_=(r:Request) = this.r = r
}

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