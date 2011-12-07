package info.folone.roy.maven {

  import java.io.File
  import scala.util.matching.Regex
  import scala.sys.process._
  import org.apache.maven.plugin.logging.Log

  object MojoHelper {
    private var log: Log = _

    def doActualWork(in: File, out: File, royHome: File, logger: Log) {
      log = logger
      val filesToCompile = recursiveListFiles(in, """.*\.roy$""".r)
      log.info("Compiling " + filesToCompile.size + " roy files...")
      filesToCompile foreach { compileFile(out, royHome) }
    }

    private def recursiveListFiles(f: File, r: Regex): Array[File] = {
      val these = f.listFiles
      val good = these.filter(f => r.findFirstIn(f.getName).isDefined)
      good ++ these.filter { _.isDirectory }.flatMap { recursiveListFiles(_, r) }
    }

    private def compileFile(o: File, roy: File)(f: File) = {
      val result = ("node " + roy.getPath + "/roy " + f.getPath) !!
      val jsFile = new File(fileExtToJs(f.getPath))
      jsFile.renameTo(new File(o, jsFile.getName))
      log.info(result)
    }

    private def fileExtToJs(roy: String) = roy.split('.').init :+ "js" mkString "."
  }
}
