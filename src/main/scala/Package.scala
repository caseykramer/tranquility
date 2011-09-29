package org.drrandom

import org.apache.http.client.methods._

package object tranquility {
	//implicit def reqToExtCons[T <: List[RequestExtension]](r:T):RequestExtCons = new RequestExtCons(r)
	implicit def reqExtToSeq[T <: RequestExtension](r:T):RequestExtensionList = new RequestExtensionList(r)
	//implicit def extractRequestFromSeq[T <: List[U,_],U <: Reqest](l:T):Request = l.head
	
	// Some magik to convert to the Apache HttpClient types
	implicit def requestToMethod[T <: Request](r:T):RequestToMethod = new RequestToMethod(r)
	implicit def requestWithBodyToMethod[T[x] <: RequestWithBody[x],U](r:T[U]) = new RequestWithBodyToMethod[U](r)
	
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

	final class RequestExtCons(rEx:List[RequestExtension]) {
		def ::(r:Request):Request = {
			r.extensions = rEx
			r
		}
	}

	final class RequestExtensionList(r:RequestExtension) {
		var extensions = List(r)

		def ::(rEx:RequestExtension):RequestExtensionList = {
			extensions = extensions :+ rEx
			this
		}

		def ::(r:Request):Request = {
			r.extensions = extensions
			r	
		}
	}
}
