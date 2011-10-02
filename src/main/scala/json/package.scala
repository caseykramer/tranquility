package org.drrandom.tranquility

package object json {
	
	type string = String

	implicit def seqOfTuplesToTupleList(s:Tuple2[String,Any],sn:Tuple2[String,Any]*):List[Tuple2[String,Any]] = s :: sn.toList

	implicit def stringKeyToTuplePairList(s:String):StringKeyToTupleList = new StringKeyToTupleList(s)

	def json(jObject:Tuple2[String,Any]):String = {
		"{ " + encodeTuple(jObject) + " }"
	}

	def json(obj:Tuple2[String,Any],objs:Tuple2[String,Any]*):String = {
		json(obj :: objs.toList)
	} 

	def json[T <: List[Tuple2[String,Any]]](obj:T):String = {
		"{ " + encodeTupleList(obj) + " }"
	}

	private def encodeTuple(jObject:Tuple2[String,Any]): String = {
		var result = encodeString(jObject._1) + ": "
		val nested = jObject._2 match {
			case x:String => encodeString(x)
			case x:List[Tuple2[String,Any]] => "{ " + encodeTupleList(x) + " }"
			case x:Tuple2[String,Any] => "{ " + encodeTuple(x) + " }"
			case x:Tuple2[Tuple2[String,Any],Tuple2[String,Any]] => "{ " + encodeTupleList(x.productIterator.toList.asInstanceOf[List[Tuple2[String,Any]]]) + " }"
		}
		result + nested
	}

	private def encodeTupleList(obj:List[Tuple2[String,Any]]):String = {
		obj.map(t => encodeTuple(t)).reduceLeft(_ + ", " + _)
	}

	private def encodeString(s:String):String = {
		"\"" + s.replace("\"","\\\"") + "\""
	}

	final class StringKeyToTupleList(key:String) {
		def ->(s:String):Tuple2[String,Any] = (key,s)
		def ->(pair1:Tuple2[String,Any],pairs:Tuple2[String,Any]*):Tuple2[String,Any] = (key,pair1 :: pairs.toList)
	}
}