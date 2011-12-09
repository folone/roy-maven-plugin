package info.folone.roy.maven {

  import java.io.File
  import scala.util.matching.Regex
  import scala.sys.process._
  import org.apache.maven.plugin.logging.Log

  object MojoHelper {
    def doActualWork(in: File, out: File, logger: Log) {
      implicit val log = logger
      implicit val o = out
      val filesToCompile = recursiveListFiles(in, """.*\.roy$""".r)
      log.info("Compiling " + filesToCompile.size + " roy files...")
      out.mkdirs()
      filesToCompile foreach { compileFile }
    }

    private def recursiveListFiles(f: File, r: Regex): Array[File] = {
      val these = f.listFiles
      val good = these.filter(f => r.findFirstIn(f.getName).isDefined)
      good ++ these.filter { _.isDirectory }.flatMap { recursiveListFiles(_, r) }
    }

    private def compileFile(f: File)(implicit log: Log, o: File) = {
      val result = ("roy " + f.getPath) !!
      val jsFile = new File(fileExtToJs(f.getPath))
      jsFile.renameTo(new File(o, jsFile.getName))
      log.info("Compiled. Result: " + result)
      result
    }

    private def fileExtToJs(roy: String) = roy.split('.').init :+ "js" mkString "."
  }
}
