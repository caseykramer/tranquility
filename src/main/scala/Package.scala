package org.drrandom

import org.apache.http.entity._
import org.apache.http.client.methods._

package object tranquility {
	implicit def reqExtToSeq[T <: RequestExtension](r:T):RequestExtensionList = new RequestExtensionList(r)
	
	// Some magik to convert to the Apache HttpClient types
	implicit def requestWithBodyToMethod[T](r:RequestWithBody[T]) = new RequestWithBodyToMethod[T](r)
	implicit def requestToMethod(r:Request):RequestToMethod = new RequestToMethod(r)
	
	
	def withTimer[T](action:() => T):(T,Long) = {
		val start = System.currentTimeMillis()
		val result = action()
		val end = System.currentTimeMillis()
		(result,end - start)
	}

	final class RequestWithBodyToMethod[T](r:RequestWithBody[T]) {
		def getMethod():HttpUriRequest = {
			r match {
				case x:Put[T] => {
					val put = new HttpPut(x.url)
					r.body match {
						case b:String => put.setEntity(new StringEntity(b))
						case b:Array[Byte] => put.setEntity(new ByteArrayEntity(b))
						case _ => throw new IllegalStateException("Unknown entity type: "+r.body.getClass)
					}
					put
				}	
				case x:Post[T] =>{
					val post = new HttpPost(x.url)
					r.body match {
						case b:String => post.setEntity(new StringEntity(b))
						case b:Array[Byte] => post.setEntity(new ByteArrayEntity(b))
						case _ => throw new IllegalStateException("Unknown entity type: "+r.body.getClass)
					}
					post
				} 
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
