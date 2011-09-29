## Tranquility
## A Peaceful REST client for Scala
***

You know, cUrl is ok...particularly if you don't mind copying commands from a text editor and pasting them into your console.  But sometimes you want something more.  The goal of this project for me was to try and make a highly usable REST client that would allow for quick and easy testing, and hopefully even easy integration into other projects.

The current status of this is...rough.  I'm actually using it to test some REST services, so things are getting added as I need them.  At this point it is more or less able to do what you can do with cUrl, but slightly prettier. If you want to get crazy you can combine it with the most excellent Specs2 library, and use some of the JSON matchers included to build some nice test cases.

#### Future Plans
I would like this to be a reasonably intelligent library.  It would be nice if it could do some smart things, like actually inspect the headers and result codes returned from the server, and handle cases correctly.  It would also be cool if it could support things like versioning utilizing the content-type header, and generally just be useful.