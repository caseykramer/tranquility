package org.drrandom.tranquility

case class Put[T](override var url:String,override val body:T) extends RequestWithBody[T] {
}