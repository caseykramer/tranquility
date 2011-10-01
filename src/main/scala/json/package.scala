package org.drrandom.tranquility

package object json {
	
	implicit def jsonElementConst(first:(String,String)):JsonElementCons = new JsonElementCons(first)


	def j(f: => Tuple2[String,Any]):Tuple2[String,Any] = f

	def json(jObject:Tuple2[String,Any]):String = {
		var result = "{ \"" + jObject._1 + "\": "
		var nested = jObject._2 match {
			case x:String => "\"" + x + "\""
			case x:Seq[Tuple2[String,String]] => json(x)
			case x:Tuple2[String,Any] => json(x)
		}
		result = result + nested + " }"
		result
	}

	def json(pairs:Seq[(String,String)]):String = {
		"{" + pairs.map(pair => "\"" + pair._1 + "\":\"" + pair._2 + "\"").reduceLeft(_ + "," + _) + "}"
	}

	final class JsonElementCons(first:(String,String)) {
		def ~(second:(String,String)):Seq[(String,String)] = {
			Seq(first,second)
		}

		def ::(second:(String,String)):Seq[(String,String)] = {
			this.~(second)
		}
	}
	

}