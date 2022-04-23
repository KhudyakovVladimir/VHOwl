package com.khudyakovvladimir.vhowl.utils
//
//import android.view.MotionEvent
//import android.widget.ImageView
//
//
//class PaintView : ImageView() {
//    private inner class Holder internal constructor(color: Int) {
//        var path: Path
//        var paint: Paint
//
//        init {
//            path = Path()
//            paint = Paint()
//            paint.setAntiAlias(true)
//            paint.setStrokeWidth(4f)
//            paint.setColor(color)
//            paint.setStyle(Paint.Style.STROKE)
//            paint.setStrokeJoin(Paint.Join.ROUND)
//            paint.setStrokeCap(Paint.Cap.ROUND)
//        }
//    }
//
//    private var color: Int = Color.WHITE
//    private val holderList: MutableList<Holder> = ArrayList()
//
//    constructor(context: Context?) : super(context) {
//        init()
//    }
//
//    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
//        init()
//    }
//
//    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
//        init()
//    }
//
//    private fun init() {
//        holderList.add(Holder(color))
//    }
//
//    protected fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        for (holder in holderList) {
//            canvas.drawPath(holder.path, holder.paint)
//        }
//    }
//
//    fun onTouchEvent(event: MotionEvent): Boolean {
//        val eventX = event.x
//        val eventY = event.y
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> {
//                holderList.add(Holder(color))
//                holderList[holderList.size - 1].path.moveTo(eventX, eventY)
//                return true
//            }
//            MotionEvent.ACTION_MOVE -> holderList[holderList.size - 1].path.lineTo(eventX, eventY)
//            MotionEvent.ACTION_UP -> {}
//            else -> return false
//        }
//        invalidate()
//        return true
//    }
//
//    fun resetPaths() {
//        for (holder in holderList) {
//            holder.path.reset()
//        }
//        invalidate()
//    }
//
//    fun setBrushColor(color: Int) {
//        this.color = color
//    }
//}