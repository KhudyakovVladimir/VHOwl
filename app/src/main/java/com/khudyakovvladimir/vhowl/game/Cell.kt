package com.khudyakovvladimir.vhowl.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.khudyakovvladimir.vhowl.model.Sector
import javax.inject.Inject

class Cell @Inject constructor(
    private val x: Float,
    private val y: Float,
    private val row: Int,
    private val column : Int,
    private val width: Float,
    private val height: Float,
    var isAlive: Boolean
    ) {

    private val touchedRect = -1
    private val sector = Sector()
    private var cellsArray = emptyArray<Cell>()

    private var cellStateInNextGeneration: Boolean = true

    init {
        cellStateInNextGeneration = isAlive
    }

    private fun createMatrix() {
        for(i in 0..35) {
            //cellsArray[i] = Cell(i.toFloat(), 0F, cellSize, true)
        }
    }

    private fun detectTouch(sector: Sector, cellArrayList: ArrayList<Cell>): Boolean {
        for (i in 1..90) {
            if (cellArrayList[i].x == sector.row && cellArrayList[i].y == sector.column) {
                return true
            }
        }
        return false
    }



fun draw(canvas: Canvas) {
    isAlive = cellStateInNextGeneration

    val paint = Paint().apply {
        alpha = 255
        color = Color.WHITE
    }

    if (!isAlive) {
        paint.color = Color.GRAY
    }

    paint.strokeWidth = 3f

    canvas.drawRect(x, y, x + width, y+height, paint)
}


fun update(cells: Array<Array<Cell>>) {
    var aliveNeighbors = 0
    var lc = this.column - 1
    if (lc < 0) {
        lc = cells[0].size - 1
    }

    var rc = this.column + 1
    if (rc == cells[0].size) {
        rc = 0
    }

    var ur = this.row - 1
    if (ur < 0 ) {
        ur = cells.size - 1
    }

    var lr = this.row + 1
    if (lr == cells.size) {
        lr = 0
    }

    // upper left
    if (cells[ur][lc].isAlive) {
        aliveNeighbors ++
    }

    // upper center
    if(cells[ur][this.column].isAlive) {
        aliveNeighbors++
    }

    // upper right
    if(cells[ur][rc].isAlive) {
        aliveNeighbors++
    }

    // left
    if(cells[this.row][lc].isAlive) {
        aliveNeighbors ++
    }

    // right
    if(cells[this.row][rc].isAlive) {
        aliveNeighbors++
    }

    // lower left
    if(cells[lr][lc].isAlive) {
        aliveNeighbors ++
    }

    // lower center
    if(cells[lr][this.column].isAlive) {
        aliveNeighbors ++
    }

    // lower right
    if (cells[lr][rc].isAlive) {
        aliveNeighbors++
    }

    if (this.isAlive) {
        if (aliveNeighbors < 2) {
            cellStateInNextGeneration = false
        }

        if (aliveNeighbors == 2 || aliveNeighbors ==3) {
            cellStateInNextGeneration=true
        }

        if (aliveNeighbors > 3) {
            cellStateInNextGeneration = false
        }
    } else {
        if (aliveNeighbors == 3) {
            cellStateInNextGeneration = true
        }
    }
}

    fun check() {
        //Log.d("TAG", "${this.x}")
//        if (this.x > 1080) {
//            //Log.d("TAG", "TOUCH")
//        }
    }

//    fun update() {
//        var delta = 5F
//        if (this.x < 950) {
//            this?.x =  this?.x!! + delta
//            this?.y =  this?.y!! + delta
//            //Log.d("TAG", "x = $x")
//        }
//        if (this.x == 950F) {
//            //Log.d("TAG", "---Next---")
//            this.x = 0F
//            this.y = 0F
//        }
//    }

//    fun update() {
//        var delta = 1F
//        if (this.x < 950) {
//            this?.x =  this?.x!! + delta
//            this?.y =  this?.y!! + delta
//            //Log.d("TAG", "x = $x")
//        }
//        if (this.x == 950F) {
//            //Log.d("TAG", "---Next---")
//            this.x = 0F
//            this.y = 0F
//        }
//    }

//    fun draw(canvas: Canvas, bitmap: Bitmap) {
//        val paint = Paint()
//        paint.alpha = 255
//        paint.color = Color.YELLOW
//        paint.strokeWidth = 3f
//
//        //canvas.drawRect(x, y, x + width, y+height, paint)
//        //canvas.drawRect(x, y, x + cellSize - 1, y + cellSize - 1, paint)
//        canvas.drawBitmap(bitmap, x + 100, y + 100, paint)
//    }

//    fun draw(canvas: Canvas, bitmap: Bitmap) {
//        val paint = Paint()
//        paint.alpha = 255
//        paint.color = Color.YELLOW
//        paint.strokeWidth = 3f
//
//        for (i in 0..cellsArray.size - 1) {
//            val cell = cellsArray[i]
//            canvas.drawRect(cell.x, cell.y, cell.x + cellSize, cell.y + cellSize, paint)
//        }
//    }

}