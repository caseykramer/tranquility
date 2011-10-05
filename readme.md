Tranquility - A Peaceful REST client for Scala

You know, cUrl is ok...particularly if you don't mind copying commands from a text editor and pasting them into your console.  But sometimes you want something more.  The goal of this project for me was to try and make a highly usable REST client that would allow for quick and easy testing, and hopefully even easy integration into other projects.

The current status of this is...rough.  I'm actually using it to test some REST services, so things are getting added as I need them.  At this point it is more or less able to do what you can do with cUrl, but slightly prettier. If you want to get crazy you can combine it with the most excellent Specs2 library, and use some of the JSON matchers included to build some nice test cases.

#### Future Plans
I would like this to be a reasonably intelligent library.  It would be nice if it could do some smart things, like actually inspect the headers and result codes returned from the server, and handle cases correctly.  It would also be cool if it could support things like versioning utilizing the content-type header, and generally just be useful.

#### Usage
Ok, so here are some samples of how the API currently looks:

	Get("http://www.myservice.com/rest") -> match {
		case response is 200 => println(response.body)
		case response is 404 => println("Can't find it")
		case response is 500 => println("Something bad happened")
		case _ => println("Something really, really bad happened")
	}

Pattern matching against the Response object that gets returned from the `->()` method is not required, of course, but it makes it easy
to check for specific HTTP result codes.  In addition to the response body, you also have access to the response headers, and the time in 
milliseconds the request took (using the `headers` and `time` properties of the response).  The headers are a `Map[String,String]` of all the
response headers.  At this point I'm assuming the response body is going to be a string, but that is only temporary.

Now what about doing something like adding an OAuth token to the Authorization header on the request?  Well, I needed to do that too,
so I made it easy:

	Get("http://www.myservice.com/rest") :: OAuth("my oauth token") -> match {
		case response is 200 => println("Hey it worked")
	}

This will add an OAuth 2 style authorization header
	Authorization: OAuth <token>

At this point I don't have a way to pass it as an URL parameter, but that may be added.

Now that we have OAuth, what about using POST/PUT to send data?  Got you covered:

	Post("http://www.myservice.com/rest","This is a post") :: OAuth("my token") -> match {
		case response is 200 => println("Looks like the data was sent")
	}

The `body` parameter on the `Post` constructor is actually a generic, so you could pass anything in after the URL.  In practice, though, I'm
assuming you're giving me either text or binary data.  If it's binary, I'm expecting some form of byte array.

If you wanted to send JSON, you can do that as well.  You can use any one of the nice libraries around for generating JSON text, or enter it in
long-hand.  
To set the Content-Type header correctly you'll need to set another option on the call though:

	Put("http://www.myservice.com/rest","""{ name: "Bob" }""") :: OAuth("my token") :: JSON() -> match {
		case response is 200 => println("I sent some JSON")
	}

To be honest, I may make the default content type `application/json`at some point in the future, or provide some other way of telling the library that you're talking JSON or XML, or something else.


-------------------