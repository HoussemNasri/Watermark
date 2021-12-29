import org.hyperskill.hstest.dynamic.DynamicTest
import org.hyperskill.hstest.stage.StageTest
import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testing.TestedProgram
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.abs

class CardGameTest : StageTest<Any>() {

    @DynamicTest(order = 1)
    fun imageNotExistTest5(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        outputString = main.execute("test/notexist.png").trim().lowercase()
        position = checkOutput(outputString, 0, "The file test/notexist.png doesn't exist.".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Incorrect output, when a non existing filename was input.")

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        return CheckResult.correct()
    }

    @DynamicTest(order = 2)
    fun imageColorCompLess3Test5(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile = "test/grey.png"
            val inputFile = File(infile)
            if (!inputFile.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test/grey.png").trim().lowercase()
            position = checkOutput(outputString, 0, "The number of image color components isn't 3.".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, when a an image that doesn't have 3 color components was input."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        return CheckResult.correct()
    }

    @DynamicTest(order = 3)
    fun imageBitsPerPixelTest5(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile = "test/bits16.png"
            val inputFile = File(infile)
            if (!inputFile.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test/bits16.png").trim().lowercase()
            position = checkOutput(outputString, 0, "The image isn't 24 or 32-bit.".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, when a an image with no 24 or 32 bits per pixel was input."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        return CheckResult.correct()
    }

    @DynamicTest(order = 4)
    fun watermarkNotExistTest5(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile = "test/image5.png"
            val inputFile = File(infile)
            if (!inputFile.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test/image5.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Input the watermark image filename:".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after an image filename was input."
            )

            outputString = main.execute("test/notexist.png").trim().lowercase()
            position = checkOutput(outputString, 0, "The file test/notexist.png doesn't exist.".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, when a non existing watermark filename was input."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        return CheckResult.correct()
    }

    @DynamicTest(order = 5)
    fun watermarkColorCompLess3Test5(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile1 = "test/image5.png"
            val inputFile1 = File(infile1)
            if (!inputFile1.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            val infile2 = "test/grey.png"
            val inputFile2 = File(infile2)
            if (!inputFile2.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test/image5.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Input the watermark image filename:".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after an image filename was input."
            )

            outputString = main.execute("test/grey.png").trim().lowercase()
            position = checkOutput(outputString, 0, "The number of watermark color components isn't 3.".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, when a a watermark that doesn't have 3 color components was input."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        return CheckResult.correct()
    }

    @DynamicTest(order = 6)
    fun watermarkBitsPerPixelTest5(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile1 = "test/image5.png"
            val inputFile1 = File(infile1)
            if (!inputFile1.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            val infile2 = "test/bits16.png"
            val inputFile2 = File(infile2)
            if (!inputFile2.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test/image5.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Input the watermark image filename:".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after an image filename was input."
            )

            outputString = main.execute("test/bits16.png").trim().lowercase()
            position = checkOutput(outputString, 0, "The watermark isn't 24 or 32-bit.".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, when a watermark with no 24 or 32 bits per pixel was input."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        return CheckResult.correct()
    }

    @DynamicTest(order = 7)
    fun wideWatermarkTest5(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile1 = "test/image5.png"
            val inputFile1 = File(infile1)
            if (!inputFile1.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            val infile2 = "test/wide.png"
            val inputFile2 = File(infile2)
            if (!inputFile2.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test/image5.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Input the watermark image filename:".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after an image filename was input."
            )

            outputString = main.execute("test/wide.png").trim().lowercase()
            position = checkOutput(outputString, 0, "The watermark's dimensions are larger.".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, when the watermark image is wider than the original image."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        return CheckResult.correct()
    }

    @DynamicTest(order = 8)
    fun tallWatermarkTest5(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile1 = "test/image5.png"
            val inputFile1 = File(infile1)
            if (!inputFile1.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            val infile2 = "test/tall.png"
            val inputFile2 = File(infile2)
            if (!inputFile2.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test/image5.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Input the watermark image filename:".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after an image filename was input."
            )

            outputString = main.execute("test/tall.png").trim().lowercase()
            position = checkOutput(outputString, 0, "The watermark's dimensions are larger.".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, when the watermark image is taller than the original image."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        return CheckResult.correct()
    }

    @DynamicTest(order = 9)
    fun invalidPositionMethodTest5(): CheckResult {
        val posMethodInputs = listOf("pos", "0 0", "top")
        for (posMethodInput in posMethodInputs) {
            val main = TestedProgram()
            var outputString = main.start().trim().lowercase()
            var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
            if (position == -1) return CheckResult(false, "Invalid prompt for the image filename.")

            try {
                val infile1 = "test/image5.png"
                val inputFile1 = File(infile1)
                if (!inputFile1.exists()) {
                    return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
                }
                val infile2 = "test/logorgb.png"
                val inputFile2 = File(infile2)
                if (!inputFile2.exists()) {
                    return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
                }
                outputString = main.execute("test/image5.png").trim().lowercase()
                position = checkOutput(outputString, 0, "Input the watermark image filename:".lowercase())
                if (position == -1) return CheckResult(
                    false,
                    "Incorrect output, after an image filename was input."
                )

                outputString = main.execute("test/logorgb.png").trim().lowercase()
                position = checkOutput(outputString, 0, "Do you want to set a transparency color?".lowercase())
                if (position == -1) return CheckResult(
                    false,
                    "Incorrect output, after a watermark filename was input."
                )
            } catch (e: Exception) {
                return CheckResult(false, "An exception was thrown, when trying to open an image file.")
            }

            outputString = main.execute("yes").trim().lowercase()
            position = checkOutput(outputString, 0, "Input a transparency color ([Red] [Green] [Blue]):".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after accepting the use of a transparent color."
            )

            outputString = main.execute("0 0 0").trim().lowercase()
            position =
                checkOutput(outputString, 0, "Input the watermark transparency percentage (Integer 0-100):".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after a transparent color input."
            )

            outputString = main.execute("20").trim().lowercase()
            position = checkOutput(outputString, 0, "Choose the position method (single, grid):".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, when the transparency percentage was input."
            )

            outputString = main.execute(posMethodInput).trim().lowercase()
            position = checkOutput(outputString, 0, "The position method input is invalid.".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after wrong input position method."
            )

            if (!main.isFinished) return CheckResult(false, "The application didn't exit.")
        }

        return CheckResult.correct()
    }

    @DynamicTest(order = 10)
    fun invalidPositionTest5(): CheckResult {
        val posInputs = listOf("top", "up right", "100-100")
        for (posInput in posInputs) {
            val main = TestedProgram()
            var outputString = main.start().trim().lowercase()
            var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
            if (position == -1) return CheckResult(false, "Invalid prompt for the image filename.")

            try {
                val infile1 = "test/image5.png"
                val inputFile1 = File(infile1)
                if (!inputFile1.exists()) {
                    return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
                }
                val infile2 = "test/logorgb.png"
                val inputFile2 = File(infile2)
                if (!inputFile2.exists()) {
                    return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
                }
                outputString = main.execute("test/image5.png").trim().lowercase()
                position = checkOutput(outputString, 0, "Input the watermark image filename:".lowercase())
                if (position == -1) return CheckResult(
                    false,
                    "Incorrect output, after an image filename was input."
                )

                outputString = main.execute("test/logorgb.png").trim().lowercase()
                position = checkOutput(outputString, 0, "Do you want to set a transparency color?".lowercase())
                if (position == -1) return CheckResult(
                    false,
                    "Incorrect output, after a watermark filename was input."
                )
            } catch (e: Exception) {
                return CheckResult(false, "An exception was thrown, when trying to open an image file.")
            }

            outputString = main.execute("yes").trim().lowercase()
            position = checkOutput(outputString, 0, "Input a transparency color ([Red] [Green] [Blue]):".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after accepting the use of a transparent color."
            )

            outputString = main.execute("0 0 0").trim().lowercase()
            position =
                checkOutput(outputString, 0, "Input the watermark transparency percentage (Integer 0-100):".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after a transparent color input."
            )

            outputString = main.execute("20").trim().lowercase()
            position = checkOutput(outputString, 0, "Choose the position method (single, grid):".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, when the transparency percentage was input."
            )

            outputString = main.execute("single").trim().lowercase()
            position = checkOutput(outputString, 0, "Input the watermark position ([x 0-300], [y 0-600]):".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after wrong input position method."
            )

            outputString = main.execute(posInput).trim().lowercase()
            position = checkOutput(outputString, 0, "The position input is invalid.".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after wrong position input."
            )

            if (!main.isFinished) return CheckResult(false, "The application didn't exit.")
        }

        return CheckResult.correct()
    }

    @DynamicTest(order = 11)
    fun outOfRangeTest5(): CheckResult {
        val posInputs = listOf("-1 300", "301 600", "0 -1", "0 601")
        for (posInput in posInputs) {
            val main = TestedProgram()
            var outputString = main.start().trim().lowercase()
            var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
            if (position == -1) return CheckResult(false, "Invalid prompt for the image filename.")

            try {
                val infile1 = "test/image5.png"
                val inputFile1 = File(infile1)
                if (!inputFile1.exists()) {
                    return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
                }
                val infile2 = "test/logorgb.png"
                val inputFile2 = File(infile2)
                if (!inputFile2.exists()) {
                    return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
                }
                outputString = main.execute("test/image5.png").trim().lowercase()
                position = checkOutput(outputString, 0, "Input the watermark image filename:".lowercase())
                if (position == -1) return CheckResult(
                    false,
                    "Incorrect output, after an image filename was input."
                )

                outputString = main.execute("test/logorgb.png").trim().lowercase()
                position = checkOutput(outputString, 0, "Do you want to set a transparency color?".lowercase())
                if (position == -1) return CheckResult(
                    false,
                    "Incorrect output, after a watermark filename was input."
                )
            } catch (e: Exception) {
                return CheckResult(false, "An exception was thrown, when trying to open an image file.")
            }

            outputString = main.execute("yes").trim().lowercase()
            position = checkOutput(outputString, 0, "Input a transparency color ([Red] [Green] [Blue]):".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after accepting the use of a transparent color."
            )

            outputString = main.execute("0 0 0").trim().lowercase()
            position =
                checkOutput(outputString, 0, "Input the watermark transparency percentage (Integer 0-100):".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after a transparent color input."
            )

            outputString = main.execute("20").trim().lowercase()
            position = checkOutput(outputString, 0, "Choose the position method (single, grid):".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, when the transparency percentage was input."
            )

            outputString = main.execute("single").trim().lowercase()
            position = checkOutput(outputString, 0, "Input the watermark position ([x 0-300], [y 0-600]):".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after wrong input position method."
            )

            outputString = main.execute(posInput).trim().lowercase()
            position = checkOutput(outputString, 0, "The position input is out of range.".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after input out of range position."
            )

            if (!main.isFinished) return CheckResult(false, "The application didn't exit.")
        }

        return CheckResult.correct()
    }

    @DynamicTest(order = 12)
    fun gridPositionUsingTransparencyTest5(): CheckResult {
        try {
            val outFile = File("test/out1.png")
            if (outFile.exists()) outFile.delete()
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to delete a previous created output file.")
        }

        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile1 = "test/image5.png"
            val inputFile1 = File(infile1)
            if (!inputFile1.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            val infile2 = "test/logorgb.png"
            val inputFile2 = File(infile2)
            if (!inputFile2.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test/image5.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Input the watermark image filename:".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after an image filename was input."
            )

            outputString = main.execute("test/logorgb.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Do you want to set a transparency color?".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after a watermark filename was input."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        outputString = main.execute("yes").trim().lowercase()
        position = checkOutput(outputString, 0, "Input a transparency color ([Red] [Green] [Blue]):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, after accepting the use of a transparent color."
        )

        outputString = main.execute("0 0 0").trim().lowercase()
        position = checkOutput(outputString, 0, "Input the watermark transparency percentage (Integer 0-100):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, after a transparent color input."
        )

        outputString = main.execute("15").trim().lowercase()
        position = checkOutput(outputString, 0, "Choose the position method (single, grid):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, when the transparency percentage was input."
        )

        outputString = main.execute("grid").trim().lowercase()
        position = checkOutput(outputString, 0, "Input the output image filename (jpg or png extension):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, after the position method input."
        )

        outputString = main.execute("test/out1.png").trim().lowercase()
        position = checkOutput(outputString, 0, "The watermarked image test/out1.png has been created.".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, when the output filename was input."
        )

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        val outFile = File("test/out1.png")
        if (!outFile.exists()) return CheckResult(
            false,
            "The output file hasn't been created."
        )

        if (!checkIfCorrectOutputFileTransparencyColorGridS5(15, 50000,
                "test/image5.png", "test/logorgb.png", "test/out1.png", Color(0, 0, 0)))
            return CheckResult(
                false,
                "Incorrect output image file."
            )

        return CheckResult.correct()
    }

    @DynamicTest(order = 13)
    fun gridPositionAlphaChannelTest5(): CheckResult {
        try {
            val outFile = File("test/out2.png")
            if (outFile.exists()) outFile.delete()
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to delete a previous created output file.")
        }

        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile1 = "test/image5.png"
            val inputFile1 = File(infile1)
            if (!inputFile1.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            val infile2 = "test/logorgba.png"
            val inputFile2 = File(infile2)
            if (!inputFile2.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test/image5.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Input the watermark image filename:".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after an image filename was input."
            )

            outputString = main.execute("test/logorgba.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Do you want to use the watermark's Alpha channel?".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after a watermark filename was input."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        outputString = main.execute("yes").trim().lowercase()
        position = checkOutput(outputString, 0, "Input the watermark transparency percentage (Integer 0-100):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, after accepting the use of a transparent color."
        )

        outputString = main.execute("25").trim().lowercase()
        position = checkOutput(outputString, 0, "Choose the position method (single, grid):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, when the transparency percentage was input."
        )

        outputString = main.execute("grid").trim().lowercase()
        position = checkOutput(outputString, 0, "Input the output image filename (jpg or png extension):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, after the position method input."
        )

        outputString = main.execute("test/out2.png").trim().lowercase()
        position = checkOutput(outputString, 0, "The watermarked image test/out2.png has been created.".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, when the output filename was input."
        )

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        val outFile = File("test/out2.png")
        if (!outFile.exists()) return CheckResult(
            false,
            "The output file hasn't been created."
        )

        if (!checkIfCorrectOutputFileAlphaChannelGridS5(25, 50000,
                "test/image5.png", "test/logorgba.png", "test/out2.png"))
            return CheckResult(
                false,
                "Incorrect output image file."
            )

        return CheckResult.correct()
    }

    @DynamicTest(order = 14)
    fun singlePositionUsingTransparencyTest5(): CheckResult {
        try {
            val outFile = File("test/out3.png")
            if (outFile.exists()) outFile.delete()
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to delete a previous created output file.")
        }

        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile1 = "test/image5.png"
            val inputFile1 = File(infile1)
            if (!inputFile1.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            val infile2 = "test/logorgb.png"
            val inputFile2 = File(infile2)
            if (!inputFile2.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test/image5.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Input the watermark image filename:".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after an image filename was input."
            )

            outputString = main.execute("test/logorgb.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Do you want to set a transparency color?".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after a watermark filename was input."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        outputString = main.execute("yes").trim().lowercase()
        position = checkOutput(outputString, 0, "Input a transparency color ([Red] [Green] [Blue]):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, after accepting the use of a transparent color."
        )

        outputString = main.execute("0 0 0").trim().lowercase()
        position = checkOutput(outputString, 0, "Input the watermark transparency percentage (Integer 0-100):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, after a transparent color input."
        )

        outputString = main.execute("35").trim().lowercase()
        position = checkOutput(outputString, 0, "Choose the position method (single, grid):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, when the transparency percentage was input."
        )

        outputString = main.execute("single").trim().lowercase()
        position = checkOutput(outputString, 0, "Input the watermark position ([x 0-300], [y 0-600]):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, after the position method input."
        )

        outputString = main.execute("200 200").trim().lowercase()
        position = checkOutput(outputString, 0, "Input the output image filename (jpg or png extension):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, after the position input."
        )

        outputString = main.execute("test/out3.png").trim().lowercase()
        position = checkOutput(outputString, 0, "The watermarked image test/out3.png has been created.".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, when the output filename was input."
        )

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        val outFile = File("test/out3.png")
        if (!outFile.exists()) return CheckResult(
            false,
            "The output file hasn't been created."
        )

        if (!checkIfCorrectOutputFileTransparencyColorSingleS5(35, 50000,
                "test/image5.png", "test/logorgb.png", "test/out3.png", Color(0, 0, 0),
            200, 200))
            return CheckResult(
                false,
                "Incorrect output image file."
            )

        return CheckResult.correct()
    }

    @DynamicTest(order = 15)
    fun singlePositionAlphaChannelTest5(): CheckResult {
        try {
            val outFile = File("test/out4.png")
            if (outFile.exists()) outFile.delete()
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to delete a previous created output file.")
        }

        val main = TestedProgram()
        var outputString = main.start().trim().lowercase()
        var position = checkOutput(outputString, 0, "Input the image filename:".lowercase())
        if ( position  == -1 ) return CheckResult(false, "Invalid prompt for the image filename.")

        try {
            val infile1 = "test/image5.png"
            val inputFile1 = File(infile1)
            if (!inputFile1.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            val infile2 = "test/logorgba.png"
            val inputFile2 = File(infile2)
            if (!inputFile2.exists()) {
                return CheckResult(false, "An input test image file doesn't exist. Try reloading the project.")
            }
            outputString = main.execute("test/image5.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Input the watermark image filename:".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after an image filename was input."
            )

            outputString = main.execute("test/logorgba.png").trim().lowercase()
            position = checkOutput(outputString, 0, "Do you want to use the watermark's Alpha channel?".lowercase())
            if (position == -1) return CheckResult(
                false,
                "Incorrect output, after a watermark filename was input."
            )
        } catch  (e: Exception) {
            return CheckResult(false, "An exception was thrown, when trying to open an image file.")
        }

        outputString = main.execute("yes").trim().lowercase()
        position = checkOutput(outputString, 0, "Input the watermark transparency percentage (Integer 0-100):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, after accepting the use of a transparent color."
        )

        outputString = main.execute("50").trim().lowercase()
        position = checkOutput(outputString, 0, "Choose the position method (single, grid):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, when the transparency percentage was input."
        )

        outputString = main.execute("single").trim().lowercase()
        position = checkOutput(outputString, 0, "Input the watermark position ([x 0-300], [y 0-600]):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, after the position method input."
        )

        outputString = main.execute("300 400").trim().lowercase()
        position = checkOutput(outputString, 0, "Input the output image filename (jpg or png extension):".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, after the position input."
        )

        outputString = main.execute("test/out4.png").trim().lowercase()
        position = checkOutput(outputString, 0, "The watermarked image test/out4.png has been created.".lowercase())
        if (position == -1) return CheckResult(
            false,
            "Incorrect output, when the output filename was input."
        )

        if (!main.isFinished) return CheckResult(false, "The application didn't exit.")

        val outFile = File("test/out4.png")
        if (!outFile.exists()) return CheckResult(
            false,
            "The output file hasn't been created."
        )

        if (!checkIfCorrectOutputFileAlphaChannelSingleS5(50, 50000,
                "test/image5.png", "test/logorgba.png", "test/out4.png", 300, 400))
            return CheckResult(
                false,
                "Incorrect output image file."
            )

        return CheckResult.correct()
    }

}

fun checkOutput(outputString: String, searchPos: Int, vararg checkStr: String): Int {
    var searchPosition = searchPos
    for (str in checkStr) {
        val findPosition = outputString.indexOf(str, searchPosition)
        if (findPosition == -1) return -1
        if ( outputString.substring(searchPosition until findPosition).isNotBlank() ) return -1
        searchPosition = findPosition + str.length
    }
    return searchPosition
}

fun checkIfCorrectOutputFileAlphaChannelSingleS5(per: Int, err: Long, imageStr: String, watStr: String,
                                                    outStr: String, posX: Int, posY: Int): Boolean {
    val imageFile = File(imageStr)
    val image = ImageIO.read(imageFile)
    val watermarkFile = File(watStr)
    val wat = ImageIO.read(watermarkFile)
    val watermark = BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_ARGB)
    for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            val color = if (x in posX until (posX + wat.width) && y in posY until (posY + wat.height))
                Color(wat.getRGB(x - posX, y - posY), true)
            else Color(0, 0, 0, 0)
            watermark.setRGB(x, y, color.rgb)
        }
    }
    val outFile = File(outStr)
    val outputImage = ImageIO.read(outFile)
    var diff: Long = 0
    for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            val i = Color(image.getRGB(x, y))
            val w = Color(watermark.getRGB(x, y), true)
            val o = Color(outputImage.getRGB(x ,y))
            val oc = if (w.alpha == 0) {
                Color(i.red, i.green, i.blue)}
            else Color(
                ((100 - per) * i.red + per * w.red) / 100,
                ((100 - per) * i.green + per * w.green) / 100,
                ((100 - per) * i.blue + per * w.blue) / 100
            )
            diff += abs(oc.red - o.red) + abs(oc.green - o.green) + abs(oc.blue - o.blue)
        }
    }
    return diff <= err
}

fun checkIfCorrectOutputFileTransparencyColorSingleS5(per: Int, err: Long, imageStr: String, watStr: String,
                                                    outStr: String, trColor: Color, posX: Int, posY: Int): Boolean {
    val imageFile = File(imageStr)
    val image = ImageIO.read(imageFile)
    val watermarkFile = File(watStr)
    val wat = ImageIO.read(watermarkFile)
    val watermark = BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_RGB)
    for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            val color = if (x in posX until (posX + wat.width) && y in posY until (posY + wat.height))
                Color(wat.getRGB(x - posX, y - posY))
            else trColor
            watermark.setRGB(x, y, color.rgb)
        }
    }
    val outFile = File(outStr)
    val outputImage = ImageIO.read(outFile)
    var diff: Long = 0
    for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            val i = Color(image.getRGB(x, y))
            val w = Color(watermark.getRGB(x, y))
            val o = Color(outputImage.getRGB(x ,y))
            val oc = if (w.red == trColor.red && w.green == trColor.green && w.blue == trColor.blue)
                Color(i.red, i.green, i.blue)
            else Color(
                ((100 - per) * i.red + per * w.red) / 100,
                ((100 - per) * i.green + per * w.green) / 100,
                ((100 - per) * i.blue + per * w.blue) / 100
            )
            diff += abs(oc.red - o.red) + abs(oc.green - o.green) + abs(oc.blue - o.blue)
        }
    }
    return diff <= err
}

fun checkIfCorrectOutputFileAlphaChannelGridS5(per: Int, err: Long,
                                                    imageStr: String, watStr: String, outStr: String): Boolean {
    val imageFile = File(imageStr)
    val image = ImageIO.read(imageFile)
    val watermarkFile = File(watStr)
    val watermark = ImageIO.read(watermarkFile)
    val outFile = File(outStr)
    val outputImage = ImageIO.read(outFile)
    var diff: Long = 0
    for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            val i = Color(image.getRGB(x, y))
            val w = Color(watermark.getRGB(x % watermark.width, y % watermark.height), true)
            val o = Color(outputImage.getRGB(x ,y))
            val oc = if (w.alpha == 0)
                Color(i.red, i.green, i.blue)
            else Color(
                ((100 - per) * i.red + per * w.red) / 100,
                ((100 - per) * i.green + per * w.green) / 100,
                ((100 - per) * i.blue + per * w.blue) / 100
            )
            diff += abs(oc.red - o.red) + abs(oc.green - o.green) + abs(oc.blue - o.blue)
        }
    }
    return diff <= err
}

fun checkIfCorrectOutputFileTransparencyColorGridS5(per: Int, err: Long,
                                                imageStr: String, watStr: String, outStr: String, trColor: Color): Boolean {
    val imageFile = File(imageStr)
    val image = ImageIO.read(imageFile)
    val watermarkFile = File(watStr)
    val watermark = ImageIO.read(watermarkFile)
    val outFile = File(outStr)
    val outputImage = ImageIO.read(outFile)
    var diff: Long = 0
    for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            val i = Color(image.getRGB(x, y))
            val w = Color(watermark.getRGB(x % watermark.width, y % watermark.height))
            val o = Color(outputImage.getRGB(x ,y))
            val oc = if (w.red == trColor.red && w.green == trColor.green && w.blue == trColor.blue)
                Color(i.red, i.green, i.blue)
            else Color(
                ((100 - per) * i.red + per * w.red) / 100,
                ((100 - per) * i.green + per * w.green) / 100,
                ((100 - per) * i.blue + per * w.blue) / 100
            )
            diff += abs(oc.red - o.red) + abs(oc.green - o.green) + abs(oc.blue - o.blue)
        }
    }
    return diff <= err
}


