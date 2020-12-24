package ai.data.preparer;

import java.io.File

fun main() {
    println("Hello World!")
    createConfigFile()
    prepareLearnData()
}

fun prepareLearnData() {
    val dataLines = File(getFullPath("iris.data"))
            .readLines()
            .shuffled()
    val numberOfParts = 3
    val splitDataLines = dataLines
            .chunked(dataLines.size / numberOfParts)
    for (i in 0 until numberOfParts) {
        saveDividedTestData(splitDataLines[i], splitDataLines[(i + 1) % 3].plus(splitDataLines[(i + 2) % 3]), i)
    }
}

fun getFullPath(fileName: String): String {
    return "src/main/resources/$fileName"
}

fun saveDividedTestData(testData: List<String>, learnData: List<String>, numberOfIteration: Int) {
    saveDataFile("iris-learn$numberOfIteration.data", learnData)
    saveDataFile("iris-test$numberOfIteration.data", testData)
}

private fun saveDataFile(fileName: String, testData: List<String>) {
    val newLearnFile = File(getFullPath(fileName))
    newLearnFile.createNewFile()
    newLearnFile.printWriter().use { out ->
        testData.forEach { out.println(it) }
    }
}

private fun createConfigFile() {
    val configFile = File(getFullPath("ai.properties"))
    configFile.createNewFile()
    configFile.printWriter().use { out ->
        out.println("[knn]")
        out.println("neighbours=5")
        out.println("parts=3")
        out.println("learnFile=iris-learn0.data")
        out.println("testFile=iris-test0.data")
        out.println("resultFile=iris-result.data")
        out.println("includeSepalLength=true")
        out.println("includeSepalWidth=true")
        out.println("includePetalLength=true")
        out.println("includePetalWidth=true")
        out.println("delimiter=,")
    }
}