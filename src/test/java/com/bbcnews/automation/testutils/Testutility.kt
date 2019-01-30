package com.bbcnews.automation.testutils

import com.aventstack.extentreports.Status
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import ru.yandex.qatools.ashot.AShot
import java.awt.Toolkit
import java.awt.image.PixelGrabber
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*
import javax.imageio.ImageIO

class Testutility {
    /**
     * Function to create a folder with the project path
     * @param, Directory path
     */
    fun extentResultFolder(path: String): String? {
        var strManyDirectories: String? = null
        try {
            //  String strDirectoy = path;
            strManyDirectories = path

            // Create one directory
            val
            // Create multiple directories
                    success = File(strManyDirectories).mkdirs()
            if (success) {
                println("Directories: "
                        + strManyDirectories + " created")
            }

        } catch (e: Exception) {//Catch exception if any
            System.err.println("Error: " + e.message)
        }

        return strManyDirectories
    }

    /**
     * Function which compare the two images bi pixels and by dimension
     *
     * @throws IOException
     */

    @Throws(IOException::class)
    fun comparetwoimages() {
        val expected = File("./Screenshots/Before")
        val actual = File("./Screenshots/After")

        //var expectedresults = ArrayList<String>()
       var  expectedresults = getAllImages(expected, false)
        val expectedimages = expectedresults.toTypedArray()

       // var actualresults: ArrayList<String> = ArrayList()
        var actualresults = getAllImages(actual, false)
        val actualimages = actualresults.toArray(arrayOfNulls<String>(actualresults.size))
        var i = 0
        while (i < expectedimages.size && i < actualimages.size) {
            println("Expected Image :=" + expectedimages[i])
            println("Actual Image :=" + actualimages[i])

            compareImage(File(expectedimages[i]), File(actualimages[i]))
            processImage(expectedimages[i], actualimages[i])
            i++


        }
    }


    /**
     * Returns all png images from a directory in an array.
     *
     * @param directory                 the directory to start with
     * @param descendIntoSubDirectories should we include sub directories?
     * @return an ArrayList<String> containing all the files or nul if none are found..
     * @throws IOException
    </String> */
    @Throws(IOException::class)
    fun getAllImages(directory: File, descendIntoSubDirectories: Boolean): ArrayList<String> {
        val resultList = ArrayList<String>(256)
        val f = directory.listFiles()
        for (file in f!!) {
            if (file != null && file.name.toLowerCase().endsWith(".png")) {
                resultList.add(file.canonicalPath)
            }
            if (descendIntoSubDirectories && file!!.isDirectory) {
                val tmp = getAllImages(file, true)
                if (true) {
                    resultList.addAll(tmp)
                }
            }
        }
        if (resultList.size > 0)
            return resultList
        else
            return null!!

    }

    /**
     * Function to compare two images and display the diffrence
     * @param fileA
     * @param fileB
     * @return
     */
    fun compareImage(fileA: File, fileB: File): Float {

        var percentage = 0f
        try {
            // take buffer data from both image files //
            val biA = ImageIO.read(fileA)
            val dbA = biA.getData().getDataBuffer()
            val sizeA = dbA.getSize()
            val biB = ImageIO.read(fileB)
            val dbB = biB.getData().getDataBuffer()
            val sizeB = dbB.getSize()
            var count = 0
            // compare data-buffer objects //
            if (sizeA == sizeB) {

                for (i in 0 until sizeA) {

                    if (dbA.getElem(i) === dbB.getElem(i)) {
                        count = count + 1
                    }

                }
                percentage = (count * 100 / sizeA).toFloat()
                println("Image Difference Percentage --> :- $percentage")
                // test?.log(Status.PASS,"Image Difference Percentage --> :- " + percentage);

            } else {
                println("Both the images are not of same size")
                // test.log(Status.FAIL,"Both the images are not of same size");
            }

        } catch (e: Exception) {
            println("Failed to compare image files ...")
        }

        return percentage
    }


    /**
     * Function to compare the images by pixel
     * @param expected
     * @param actual
     */
    private fun processImage(expected: String?, actual: String?) {
        val imagesrc = Toolkit.getDefaultToolkit().getImage(expected)
        val imagecom = Toolkit.getDefaultToolkit().getImage(actual)

        try {
            val grab1 = PixelGrabber(imagesrc, 0, 0, -1, -1, false)
            val grab2 = PixelGrabber(imagecom, 0, 0, -1, -1, false)

            var data1: IntArray? = null

            if (grab1.grabPixels()) {
           //     val width = grab1.getWidth()
             //   val height = grab1.getHeight()
                //data1 = IntArray(width * height)
                data1 = grab1.getPixels() as IntArray?
            }

            var data2: IntArray? = null

            if (grab2.grabPixels()) {
             //   val width = grab2.getWidth()
               // val height = grab2.getHeight()
                //data2 = IntArray(width * height)
                data2 = grab2.getPixels() as IntArray?
            }

            println("Pixels equal: " + java.util.Arrays.equals(data1, data2))
            //  test?.log(Status.INFO, "Pixels equal: " + java.util.Arrays.equals(data1, data2))

        } catch (e1: InterruptedException) {
            e1.printStackTrace()
        }

    }


    /**
     * Function to take screenshot of page using the Ashot API
     * @param driver
     * @param folder
     * @param imagename
     * @throws IOException
     */
    @Throws(IOException::class)
    fun AshotScreenshot(androiddriver: AndroidDriver<MobileElement>, folder: String, imagename: String) {
        val dateName = SimpleDateFormat("dd-M-yyyy hh:mm").format(Date())
        val directory = "Screenshots"
        val screenshotPaths = extentResultFolder(directory)

        extentResultFolder(folder)

        File(screenshotPaths)
        // success = (new File(strManyDirectories)).mkdirs();

        val myScreenshot = AShot().takeScreenshot(androiddriver)
        val screenshotFolder = Paths.get(directory, folder)
        if (Files.notExists(screenshotFolder))
            Files.createDirectory(screenshotFolder)
        // To save the screenshot in desired location


        ImageIO.write(myScreenshot.getImage(), "PNG",
                File(screenshotFolder.toString() + File.separator + imagename + dateName + ".png"))


    }

    /**
     * Function to empty the  result and screenshot folder
     * @param
     * folder name
     */
    fun emptyFolder(filepath: String) {
        val file = File(filepath)
        val myFiles: Array<String>?
        if (file.isDirectory) {
            myFiles = file.list()
            for (i in myFiles!!.indices) {
                val myFile = File(file, myFiles[i])
                myFile.delete()
            }
        }

    }
}