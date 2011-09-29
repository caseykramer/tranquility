package org.drrandom.tranquility

import org.apache.http._
import org.apache.http.util._
import org.apache.http.client._
import org.apache.http.impl.client._


trait Request {
	val url:String = ""

	var extensions:List[RequestExtension] = List[RequestExtension]()

	var headers:Map[String,String] = Map.empty[String,String]

	var _preprocessors:List[Request => Unit] = List[Request=>Unit]()
	var _postprocessors:List[Request => Unit] = List[Request=>Unit]()

	def -> :Response = {
		val client = new DefaultHttpClient()
		extensions.foreach(ex => ex.preProcess(this))
		println(headers.map(kv => kv._1 + " -> " + kv._2).reduceLeft(_ + ", " + _))
		val handler = new ReceievedResponseHandler()
		val method = this.getMethod
		headers.foreach(kv => method.addHeader(kv._1,kv._2))
		val response = client.execute(method)
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
	protected var _r:Request = null
	def request :Request = _r
	def request_=(r:Request) {
		_r = r	
	} 

	def preProcess(r:Request) = {}
}