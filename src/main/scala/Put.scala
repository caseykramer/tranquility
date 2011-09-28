package org.drrandom.tranquility

case class Put[T](override val url:String,override val body:T) extends RequestWithBody[T] {
}