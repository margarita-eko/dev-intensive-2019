package ru.skillbranch.devintensive.ui.custom

import android.R.attr.radius
import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toRect
import ru.skillbranch.devintensive.R


/**
 * TODO: document your custom view class.
 */
class CircleImageView(context: Context, attrs: AttributeSet)  : ImageView(context, attrs) {

    private var cvColor: Int = Color.WHITE // TODO: use a default from R.color...
    private var cvBorderWidth: Float = resources.getDimension(R.dimen.border_width)
    private var bitmap: Bitmap?
    private val rectF: RectF = RectF()
    private val clipPath = Path()

    private val borderPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = cvColor
        strokeWidth = cvBorderWidth
        style = Paint.Style.STROKE
    }

    init {
        // Load attributes

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CircleImageView,0,0).apply {

            try {
                cvColor = getColor(R.styleable.CircleImageView_cv_borderColor, cvColor)
                cvBorderWidth = getDimension(R.styleable.CircleImageView_cv_borderWidth, cvBorderWidth)
            } finally {
                recycle()
            }
        }
        context.theme.obtainStyledAttributes(attrs,R.styleable.AppCompatImageView,0,0).apply {
            try {
                val srcDrawable =  getDrawable(R.styleable.AppCompatImageView_srcCompat)
                bitmap =srcDrawable?.toBitmap()
            } finally {
                recycle()
            }
        }
        //bitmap = BitmapFactory.decodeResource(resources,R.drawable.avatar_default)
        /*val a = context.obtainStyledAttributes(
            attrs, R.styleable.CircleImageView
        )

        cvColor = a.getColor(
            R.styleable.CircleImageView_cv_borderColor,
            cvColor
        )

        cvBorderWidth = a.getDimension(
            R.styleable.CircleImageView_cv_borderWidth,
            cvBorderWidth
        )

        a.recycle()*/
    }

    fun getBorderWidth():Int = cvBorderWidth.toInt()
    fun setBorderWidth(@Dimension dp:Int){
        cvBorderWidth = dp.toFloat()
        invalidate()
        requestLayout()
    }
    fun getBorderColor():Int = cvColor
    fun setBorderColor(hex:String){
        cvColor = Color.parseColor(hex)
        invalidate()
        requestLayout()
    }
    fun setBorderColor(@ColorRes colorId: Int){
        cvColor = ContextCompat.getColor(context,colorId)
        invalidate()
        requestLayout()
    }


    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.CircleImageView, defStyle, 0
        )

        cvColor = a.getColor(
            R.styleable.CircleImageView_cv_borderColor,
            cvColor
        )

        cvBorderWidth = a.getDimension(
            R.styleable.CircleImageView_cv_borderWidth,
            cvBorderWidth
        )


        a.recycle()

        /*// Set up a default TextPaint object
        textPaint = TextPaint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            textAlign = Paint.Align.LEFT
        }

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements()*/
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //setImageDrawable(drawable)

        /*val shader = BitmapShader(bitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = shader
        val radius = (rectF.height() / 2)
        canvas.drawRoundRect(rectF, radius, radius, paint)*/

        canvas.drawCircle(rectF.centerX(), rectF.centerY(), (rectF.height() / 2) - cvBorderWidth, borderPaint)
        if(bitmap!=null){
            val rect = Rect(rectF.left.toInt(),rectF.top.toInt(),rectF.right.toInt(),rectF.bottom.toInt())
            borderPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
            canvas.drawBitmap(bitmap!!,rect, rectF, borderPaint)
        }
        //canvas.drawBitmap(bitmap,0F,0F,borderPaint)
        /*canvas.apply {
            drawCircle(rectF.centerX(), rectF.centerY(), (rectF.height() / 2) - cvBorderWidth, borderPaint);
            clipPath.addCircle(rectF.centerX(), rectF.centerY(), (rectF.height() / 2), Path.Direction.CW);
            clipPath(clipPath)
        }*/

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val screenWidth = MeasureSpec.getSize(widthMeasureSpec)
        val screenHeight = MeasureSpec.getSize(heightMeasureSpec)
        rectF[0f, 0f, screenWidth.toFloat()] = screenHeight.toFloat()
    }
}
