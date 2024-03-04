package com.brashidnia.provenance.tools.script

import java.io.File

fun main(vararg args: String) {
    val workingDir = System.getProperty("user.dir")
    println(workingDir)
//    val png = File("${workingDir}/scripts/src/main/resources/HASH_logo.png")
//    val png = File("${workingDir}/scripts/src/main/resources/KabobSwap.svg")
//    val png = File("${workingDir}/scripts/src/main/resources/bitcoin-btc-logo.svg")
//    val png = File("${workingDir}/scripts/src/main/resources/bitcoin-btc-logo.png")
    val png = File("${workingDir}/scripts/src/main/resources/cusd.kabob.coin.png")
    val pngBytes = png.readBytes()
    println("BYTES")
    println(pngBytes.toString(Charsets.UTF_8))
//    val encodedPngBytes = java.util.Base64.getEncoder().encodeToString(pngBytes)
    val encodedPngString = java.util.Base64.getEncoder().encodeToString(pngBytes)
    println(encodedPngString)
}