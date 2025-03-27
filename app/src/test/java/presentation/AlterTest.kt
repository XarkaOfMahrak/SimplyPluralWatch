package presentation

import androidx.compose.ui.graphics.Color
import com.example.simplypluralwatch.presentation.Alter
import com.example.simplypluralwatch.presentation.SPAlter
import com.example.simplypluralwatch.presentation.SPAlterContainer
import com.example.simplypluralwatch.presentation.SPFrontContainer
import com.example.simplypluralwatch.presentation.SPFrontRead
import com.example.simplypluralwatch.presentation.spAlterContainerToAlter
import com.example.simplypluralwatch.presentation.spFrontContainerToAlter
import com.example.simplypluralwatch.presentation.getAlterNames
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.Test

class AlterTest {
    @Test
    @ExperimentalStdlibApi
    @ExperimentalSerializationApi
    fun spAlterContainerToAlterTest() {
        runTest {
            var testData = listOf<SPAlterContainer>(SPAlterContainer("a", SPAlter("A", "#0000FF", 0)))
            var expectedResult = arrayOf(Alter("A", "a", Color(0xFF0000FF), null, null))
            var result = spAlterContainerToAlter(testData)
            if (result[0].name != expectedResult[0].name ||
                result[0].id != expectedResult[0].id ||
                result[0].color != expectedResult[0].color ||
                result[0].docID != expectedResult[0].docID ||
                result[0].startTime != expectedResult[0].startTime) {
                error("Results do not match expected result")
            }
        }
    }

    @Test
    @ExperimentalSerializationApi
    fun spFrontContainerToAlterTest() {
        runTest {
            var allTestAlters = arrayListOf(Alter("A", "a", Color(0xFF0000FF)), Alter("B", "b", Color(0xFF00FF00)), Alter("C", "c", Color(0xFFFF0000)))
            var allTestCustom = arrayListOf(Alter("Unknown", "ukn", Color(0xFF000000)))
            var testData = arrayOf(SPFrontContainer("x", SPFrontRead(0, "a")))
            var expectedResult = arrayOf(Alter("A", "a", Color(0xFF0000FF), 0, "x"))
            var result = spFrontContainerToAlter(testData, allTestAlters + allTestCustom)
            if (result[0].name != expectedResult[0].name ||
                result[0].id != expectedResult[0].id ||
                result[0].color != expectedResult[0].color ||
                result[0].docID != expectedResult[0].docID ||
                result[0].startTime != expectedResult[0].startTime ||
                allTestAlters[0].docID != expectedResult[0].docID ||
                allTestAlters[0].startTime != expectedResult[0].startTime) {
                error("Results do not match expected result")
            }
        }
    }

    @Test
    fun getAlterNamesTest(){
        runTest {
            var testData = arrayListOf<Alter>(Alter("A", "a", Color(0xFF0000FF)))
            var expectedResult = "A"
            var result = getAlterNames(testData)
            if (result != expectedResult) {
                error("Results do not match expected result")
            }

            testData = arrayListOf<Alter>(Alter("A", "a", Color(0xFF0000FF)), Alter("B", "b", Color(0xFF0000FF)))
            expectedResult = "A, B"
            result = getAlterNames(testData)
            if (result != expectedResult) {
                error("Results do not match expected result")
            }
        }
    }
}
