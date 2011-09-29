package org.drrandom.tranquility

case class Post[T](override var url:String,override val body:T) extends RequestWithBody[T] {
}