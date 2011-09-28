package org.drrandom.tranquility

case class Post[T](override val url:String,override val body:T) extends RequestWithBody[T] {
}