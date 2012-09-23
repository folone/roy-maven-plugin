package info.folone.roy.maven {

  import java.io.File
  import scala.util.matching.Regex
  import scala.sys.process._
  import org.apache.maven.plugin.logging.Log
  import scalaz._
  import std.list._
  import syntax.traverse._
  import effect.IO

  object MojoHelper {
    def doActualWork(in: File, out: File, logger: Log) = {
      implicit val log = logger
      implicit val o = out
      for {
        filesToCompile ← listFiles(in, """.*\.roy$""".r)
        result         ← filesToCompile.toList.map(compileFile).sequence
      } yield {
        log.info("Compiling " + filesToCompile.size + " roy files...")
        out.mkdirs()
        result
      }
    }

    private def listFiles(f: File, r: Regex) = IO {
      def recursiveListFiles(f: File): Array[File] = {
        val these = f.listFiles
        val good  = these.filter(f ⇒ r.findFirstIn(f.getName).isDefined)
        good ++ these.filter(_.isDirectory).flatMap(recursiveListFiles(_))
      }
      recursiveListFiles(f)
    }

    private def compileFile(f: File)(implicit log: Log, o: File) = IO {
      val result = ("roy " + f.getPath) !!
      val jsFile = new File(fileExtToJs(f.getPath))
      jsFile.renameTo(new File(o, jsFile.getName))
      log.info("Compiled. Result: " + result)
      result
    }

    private def fileExtToJs(roy: String) = roy.split('.').init :+ "js" mkString "."
  }
}
