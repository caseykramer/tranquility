package org.drrandom

import org.apache.http.client.methods._

package object tranquility {
	implicit def reqExtToCons[T <: RequestExtension](r:T):RequestExtCons = new RequestExtCons(r)
	implicit def reqExtToExtCons[T <: RequestExtension](r:T):RequestExtToReqestExtCons = new RequestExtToReqestExtCons(r)

	// Some magik to convert to the Apache HttpClient types
	implicit def requestToMethod[T <: Request](r:T):RequestToMethod = new RequestToMethod(r)
	implicit def requestWithBodyToMethod[T[x] <: RequestWithBody[x],U](r:T[U]) = new RequestWithBodyToMethod[U](r)

	// For pattern matching results
	implicit def resultToInt(r:Received):Int = r.code

	final class RequestWithBodyToMethod[T](r:RequestWithBody[T]) {
		def getMethod():HttpUriRequest = {
			r match {
				case x:Put[T] => new HttpPut(x.url)
				case x:Post[T] => new HttpPost(x.url)
			}
		}
	}

	final class RequestToMethod(r:Request) {
		def getMethod():HttpUriRequest = {
			r match {
				case x:Get => new HttpGet(x.url)
				case x:Delete => new HttpDelete(x.url)
			}
		}
	}

	final class RequestExtCons(r:RequestExtension) {
		def ::(req:Request):Request = {
			r.request = req
			req
		}
	}

	final class RequestExtToReqestExtCons(r:RequestExtension) {
		def ::(rex:RequestExtension):RequestExtension = {
			rex.request = (r.request)
			r
		}
	}
}
