package com.khudyakovvladimir.vhowl.customview

class Position(val x: Int, val y: Int) {

    fun isOverlapped(pos: Position): Boolean {
        if(this.x == pos.x && this.y == pos.y) return true
        return false
    }

    fun isValid(): Boolean {
        if(x < 0 || x >= 15 || y < 0 || y >= 20) return false
        return true
    }
}