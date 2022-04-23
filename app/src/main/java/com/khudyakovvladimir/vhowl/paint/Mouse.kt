package com.khudyakovvladimir.vhowl.paint

class Mouse(
    var x: Float,
    var y: Float,
    var speed: Float,
    var radius: Float,
    var isAlive: Boolean
) {
    fun move() {
//        while (x > 100F) {
//            x -= 10F
//        }
        val delta = 5F
        if (x < 950) {
            x += delta
            //pixel.y =  pixel.y + delta
        }
        if (x == 950F) {
            x = 0F
            y = 0F
        }
    }
}