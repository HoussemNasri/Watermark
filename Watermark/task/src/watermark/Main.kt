package watermark

import java.awt.Color
import java.awt.Transparency
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.system.exitProcess

enum class WatermarkStyle {
    SINGLE,
    GRID
}

sealed class WatermarkConfig(
    val posX: Int,
    val posY: Int,
    val watermarkStyle: WatermarkStyle,
    val useAlphaChannel: Boolean,
    val watermarkWeight: Int = 100,
    val transparencyColor: Color? = null
) {

    class Single(
        posX: Int,
        posY: Int,
        useAlphaChannel: Boolean = false,
        watermarkWeight: Int = 100,
        transparencyColor: Color? = null
    ) :
        WatermarkConfig(
            posX, posY, WatermarkStyle.SINGLE, useAlphaChannel, watermarkWeight, transparencyColor)
    class Grid(
        useAlphaChannel: Boolean = false,
        transparencyWeight: Int = 100,
        transparencyColor: Color? = null
    ) :
        WatermarkConfig(
            0, 0, WatermarkStyle.GRID, useAlphaChannel, transparencyWeight, transparencyColor)
}

fun input(prompt: String): String {
    println(prompt)
    println("> ")
    return readLine()!!
}

fun exit(cause: String) {
    println(cause)
    exitProcess(0)
}

fun newImage(imageFilename: String): BufferedImage {
    val imageFile = File(imageFilename)
    if (!imageFile.exists()) {
        exit("The file $imageFilename doesn't exist.")
    }
    val image = ImageIO.read(imageFile)
    if (image.colorModel.numColorComponents != 3) {
        exit("The number of image color components isn't 3.")
    } else if (!(image.colorModel.pixelSize == 24 || image.colorModel.pixelSize == 32)) {
        exit("The image isn't 24 or 32-bit.")
    }
    return image
}

fun newWatermarkImage(watermarkImageFilename: String, sourceImage: BufferedImage): BufferedImage {
    val watermarkFile = File(watermarkImageFilename)
    if (!watermarkFile.exists()) {
        exit("The file $watermarkImageFilename doesn't exist.")
    }
    val watermarkImage = ImageIO.read(watermarkFile)
    if (watermarkImage.colorModel.numColorComponents != 3) {
        exit("The number of watermark color components isn't 3.")
    } else if (!(watermarkImage.colorModel.pixelSize == 24 ||
        watermarkImage.colorModel.pixelSize == 32)) {
        exit("The watermark isn't 24 or 32-bit.")
    } else if (sourceImage.width < watermarkImage.width ||
        sourceImage.height < watermarkImage.height) {
        exit("The watermark's dimensions are larger.")
    }
    return watermarkImage
}

fun parseWatermarkWeightInput(transparencyInput: String): Int {
    val percentage: Int? = transparencyInput.toIntOrNull()
    when (percentage) {
        null -> exit("The transparency percentage isn't an integer number.")
        !in 0..100 -> exit("The transparency percentage is out of range.")
    }
    return percentage!!
}

fun drawWatermark(
    posX: Int,
    posY: Int,
    watermark: BufferedImage,
    outImage: BufferedImage,
    watermarkConfig: WatermarkConfig
) {
    for (x in posX until (posX + watermark.width).coerceAtMost(outImage.width)) {
        for (y in posY until (posY + watermark.height).coerceAtMost(outImage.height)) {
            val originalC = Color(outImage.getRGB(x, y))
            val watermarkX = x - posX
            val watermarkY = y - posY
            val weight = watermarkConfig.watermarkWeight
            val watermarkC =
                Color(watermark.getRGB(watermarkX, watermarkY), watermarkConfig.useAlphaChannel)
            if (watermarkC.alpha != 0 && watermarkC != watermarkConfig.transparencyColor) {
                val watermarkedColor =
                    Color(
                        (weight * watermarkC.red + (100 - weight) * originalC.red) / 100,
                        (weight * watermarkC.green + (100 - weight) * originalC.green) / 100,
                        (weight * watermarkC.blue + (100 - weight) * originalC.blue) / 100)
                outImage.setRGB(x, y, watermarkedColor.rgb)
            }
        }
    }
}

fun blendSourceAndWatermark(
    outImageFilename: String,
    srcImage: BufferedImage,
    watermark: BufferedImage,
    watermarkConfig: WatermarkConfig
): BufferedImage {
    val outImageFormat = outImageFilename.substringAfterLast(".", "")
    if (outImageFormat.lowercase() !in listOf("png", "jpg")) {
        exit("""The output file extension isn't "jpg" or "png".""")
    }
    val outImage = copyImage(srcImage)

    if (watermarkConfig.watermarkStyle == WatermarkStyle.SINGLE) {
        drawWatermark(
            watermarkConfig.posX, watermarkConfig.posY, watermark, outImage, watermarkConfig)
    } else {
        for (y in 0 until outImage.height step watermark.height) {
            for (x in 0 until outImage.width step watermark.width) {
                drawWatermark(x, y, watermark, outImage, watermarkConfig)
            }
        }
    }

    try {
        ImageIO.write(outImage, outImageFormat, File(outImageFilename))
    } catch (e: Exception) {
        exit("")
    }
    print("The watermarked image $outImageFilename has been created.")

    return outImage
}

fun parseTransparencyColorInput(transparencyColorInput: String): Color {
    val channels = transparencyColorInput.trim().split(" ")
    if (channels.size != 3 ||
        channels.any { it.toIntOrNull() == null || (it.toInt() !in 0..255) }) {
        exit("The transparency color input is invalid.")
    }
    val (r, g, b) = channels.map { it.toInt() }
    return Color(r, g, b)
}

/**
 * This is a slightly modified version of the Stackoverflow post below, with the alpha channel
 * ignored: https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage
 */
fun copyImage(bi: BufferedImage): BufferedImage =
    BufferedImage(bi.colorModel, bi.copyData(null), false, null)

fun main() {
    val srcImageFilename = input("Input the image filename:")
    val sourceImage = newImage(srcImageFilename)
    val watermarkFilename = input("Input the watermark image filename:")
    val watermarkImage = newWatermarkImage(watermarkFilename, sourceImage)
    var transparencyColor: Color? = null
    val useAlphaChannel: Boolean =
        if (watermarkImage.transparency == Transparency.TRANSLUCENT) {
            input("Do you want to use the watermark's Alpha channel?") == "yes"
        } else {
            val useTransparencyColor = input("Do you want to set a transparency color?") == "yes"
            if (useTransparencyColor) {
                val transparencyColorInput =
                    input("Input a transparency color ([Red] [Green] [Blue]):")
                transparencyColor = parseTransparencyColorInput(transparencyColorInput)
            }
            false
        }
    val watermarkWeightInput = input("Input the watermark transparency percentage (Integer 0-100):")
    val watermarkWeight = parseWatermarkWeightInput(watermarkWeightInput)
    val watermarkPositionMethodInput = input("Choose the position method (single, grid):")
    val watermarkStyle = parseWatermarkPositionMethodInput(watermarkPositionMethodInput)

    val watermarkConfig =
        if (watermarkStyle == WatermarkStyle.SINGLE) {
            val (watermarkPosX, watermarkPosY) = readWatermarkPosition(sourceImage, watermarkImage)
            WatermarkConfig.Single(
                watermarkPosX, watermarkPosY, useAlphaChannel, watermarkWeight, transparencyColor)
        } else {
            WatermarkConfig.Grid(useAlphaChannel, watermarkWeight, transparencyColor)
        }

    val outputImageFilename = input("Input the output image filename (jpg or png extension):")
    blendSourceAndWatermark(outputImageFilename, sourceImage, watermarkImage, watermarkConfig)
}

fun readWatermarkPosition(sourceImage: BufferedImage, watermark: BufferedImage): Array<Int> {
    val sourceWidth = sourceImage.width
    val sourceHeight = sourceImage.height
    val watermarkWidth = watermark.width
    val watermarkHeight = watermark.height
    val maxY = sourceHeight - watermarkHeight
    val maxX = sourceWidth - watermarkWidth
    val positionArray = input("Input the watermark position ([x 0-$maxX], [y 0-$maxY]):").split(" ")
    if (positionArray.size == 2 && positionArray.all { it.toIntOrNull() != null }) {
        val (x, y) = positionArray
        if (x.toInt() in 0..maxX && y.toInt() in 0..maxY) {
            return arrayOf(x.toInt(), y.toInt())
        } else {
            exit("The position input is out of range.")
        }
    } else {
        exit("The position input is invalid.")
    }

    throw IllegalStateException("Wrong universe!")
}

fun parseWatermarkPositionMethodInput(watermarkPositionMethod: String): WatermarkStyle {
    when (watermarkPositionMethod) {
        "single" -> {
            return WatermarkStyle.SINGLE
        }
        "grid" -> {
            return WatermarkStyle.GRID
        }
        else -> {
            exit("The position method input is invalid.")
        }
    }
    throw IllegalStateException("Wrong universe!")
}
