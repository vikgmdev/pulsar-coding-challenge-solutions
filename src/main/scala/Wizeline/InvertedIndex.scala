package Wizeline

import com.spotify.scio._
import com.spotify.scio.values.SCollection
import org.apache.beam.sdk.io.FileSystems
import scala.collection.JavaConverters._
import java.util.UUID.randomUUID

/*
sbt "runMain [PACKAGE].InvertedIndex
  --project=[PROJECT] --runner=DataflowRunner --zone=[ZONE]
  --input=gs://dataflow-samples/shakespeare/kinglear.txt
  --output=gs://[BUCKET]/[PATH]/InvertedIndex"
*/
object InvertedIndex {
  def main(cmdlineArgs: Array[String]): Unit = {
    val (sc, args) = ContextAndArgs(cmdlineArgs)

    val exampleInputData = "./datasets/4*"
    val outputPath = "./output"
    val input = args.getOrElse("input", exampleInputData)
    val output = args.getOrElse("output", outputPath)


    // Initialize `FileSystems`
    FileSystems.setDefaultPipelineOptions(sc.options)

    // List files from the input path
    val uris = FileSystems
      .`match`(input)
      .metadata()
      .asScala
      .map(_.resourceId().toString)

    val uriToContent = SCollection.unionAll(
      uris.map(uri =>
        sc.textFile(uri)
          .keyBy(_ => uri)
          .flatMap {
            case (path, line) =>
              line.split("\\W+")
                .filter(_.nonEmpty)
                .map(word => ((word.toLowerCase, uri.split("/").last), 1))
          }
      ))

    val wordsIds = uriToContent
      .groupByKey
      .keys
      .map {
        case (word, paths) => (word, randomUUID().toString)
      }
      .reduceByKey {
        case (n1, n2) => n1
      }

    wordsIds.saveAsTextFile(output, 1, ".dictionnary")

    uriToContent
      .reduceByKey {
        // Group all (word, fileName) pairs and sum the counts
        case (n1, n2) => n1 + n2
      }.map {
        // Transform tuple into (word, (fileName, count))
        case ((w, p), n) => (w, p)
      }.groupBy {
        // Group by words
        case (w, p) => w
      }
      .map {

        case (w, seq) =>
          val seq2 = seq map {
            case (_, p) => p
          }
          (w, seq2.toSeq.sorted)
      }
      .join(wordsIds)
      .map { case (w, (d, i)) => (i, d) }
      .saveAsTextFile(output, 1, ".inverted")

    val result = sc.close().waitUntilFinish()
  }
}
