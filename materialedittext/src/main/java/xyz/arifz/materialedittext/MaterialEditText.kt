package xyz.arifz.materialedittext

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.fonts.FontFamily
import android.text.*
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import xyz.arifz.materialedittext.ExtensionFunctions.dpToPx
import xyz.arifz.materialedittext.ExtensionFunctions.spToPx

class MaterialEditText : TextInputLayout {
    private lateinit var textInputEditText: TextInputEditText
    private var hintForColor = ""
    private var isRequired = false

    init {
        setTheme()
    }

    constructor(context: Context) : super(context) {
        init(context, null, R.style.TextInputLayoutStyle)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs,
        R.style.TextInputLayoutStyle
    ) {
        init(context, attrs, R.style.TextInputLayoutStyle)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs, R.style.TextInputLayoutStyle)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        setupView(context)
        setupAttributes(context, attrs)
        initWatchers()
    }

    private fun setTheme() {
        boxBackgroundColor = ContextCompat.getColor(context, R.color.color_white)
        boxBackgroundMode = BOX_BACKGROUND_OUTLINE
        boxStrokeWidth = 3
        boxStrokeWidthFocused = 3
        boxStrokeColor = ContextCompat.getColor(context, R.color.color_blue_crayola)
        setBoxStrokeColorStateList(
            ContextCompat.getColorStateList(
                context,
                R.color.colorset_box_stroke
            )!!
        )
        setHintTextAppearance(R.style.TextInputLayoutHintTextStyle)
    }

    private fun setupView(context: Context) {
        textInputEditText = TextInputEditText(context)
        textInputEditText.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textInputEditText.background = null
        textInputEditText.setLines(1)
        textInputEditText.maxLines = 1
        textInputEditText.isSingleLine = true
        textInputEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        textInputEditText.setPadding(20, 20, 20, 20)
        addView(textInputEditText)
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText)
            try {
                var hint = a.getString(R.styleable.MaterialEditText_hint)
                isRequired = a.getBoolean(R.styleable.MaterialEditText_isRequired, false)
                if (isRequired) {
                    if (hint.isNullOrEmpty())
                        hint = ""
                    hint += " *"
                    hintForColor = hint
                    setHintAsteriskColor(Color.RED)

                } else {
                    hintForColor = hint ?: ""
                    setHint(hint)
                }

                val isReadOnly = a.getBoolean(R.styleable.MaterialEditText_isReadOnly, false)
                setReadOnly(isReadOnly)

                val radius = a.getFloat(R.styleable.MaterialEditText_radius, 10f)
                setCornerRadius(radius)
                setEditTextType(a.getInt(R.styleable.MaterialEditText_inputType, 2))
                setMaxLines(a.getInt(R.styleable.MaterialEditText_maxLines, -1))
                setIsHintFloating(a.getBoolean(R.styleable.MaterialEditText_isHintFloating, true))

            } catch (e: Exception) {
                e.printStackTrace()
            }
            a.recycle()
        }
    }

    private fun setIsHintFloating(status: Boolean) {
        if (!status) {
            textInputEditText.hint = hint
            hint = ""
        }
    }

    private fun setEditTextType(type: Int) {
        when (type) {
            InputTypeEnum.DIGIT.value -> {
                inputType = InputType.TYPE_CLASS_NUMBER
            }
            InputTypeEnum.NAME.value -> {
                rawInputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            }
            InputTypeEnum.ADDRESS.value -> {
                rawInputType = InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS
            }
            InputTypeEnum.EMAIL.value -> {
                rawInputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            }
            InputTypeEnum.PHONE.value -> {
                inputType = InputType.TYPE_CLASS_PHONE
            }
            InputTypeEnum.PASSWORD.value -> {
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            InputTypeEnum.DIGIT_FRACTIONAL.value -> {
                inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            }
        }
    }

    private fun setCornerRadius(radius: Float) {
        setBoxCornerRadii(radius, radius, radius, radius)
    }

    private fun setReadOnly(state: Boolean) {
        if (state) {
            this.isClickable = false
            this.isFocusable = false
            this.isFocusableInTouchMode = false
            this.textInputEditText.isCursorVisible = false
            this.textInputEditText.isClickable = false
            this.textInputEditText.isFocusable = false
            this.textInputEditText.isFocusableInTouchMode = false
            setReadOnlyColor()
        }
    }

    private fun setReadOnlyColor() {
        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_enabled, android.R.attr.state_enabled)
            ),
            intArrayOf(
                ContextCompat.getColor(context, R.color.color_light_grey),
                ContextCompat.getColor(context, R.color.color_light_grey)
            )
        )
        setBoxStrokeColorStateList(colorStateList)
        boxBackgroundColor = ContextCompat.getColor(context, R.color.color_white)
        editText?.setTextColor(ContextCompat.getColor(context, R.color.color_light_grey))
    }

    private fun initWatchers() {
        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                isErrorEnabled = false
            }

        })
    }

    fun setText(str: Editable?) {
        textInputEditText.text = str
    }

    var text: String?
        set(value) {
            if (!value.isNullOrEmpty())
                textInputEditText.text = Editable.Factory.getInstance().newEditable(value)
        }
        get() {
            return textInputEditText.text?.toString()
        }

    var filter: InputFilter?
        get() {
            return textInputEditText.filters?.get(0)
        }
        set(value) {
            textInputEditText.filters = arrayOf(value)
        }

    var inputType: Int
        get() {
            return textInputEditText.inputType
        }
        set(value) {
            textInputEditText.inputType = value
        }

    var rawInputType: Int
        get() {
            return textInputEditText.inputType
        }
        set(value) {
            textInputEditText.setRawInputType(value)
        }

    fun setMaxLines(maxLines: Int) {
        if (maxLines <= 1) {
            textInputEditText.layoutParams = android.widget.FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                50.dpToPx()
            )
            return
        }
        textInputEditText.gravity = Gravity.START
        textInputEditText.setLines(maxLines)
        textInputEditText.isSingleLine = false
        textInputEditText.maxLines = maxLines
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        textInputEditText.addTextChangedListener(watcher)
    }

    fun getTextInputEditText(): TextInputEditText {
        return textInputEditText
    }

    fun setHintFontFamily(fontFamily: Int) {
        fontFamily.let { ResourcesCompat.getFont(context, it) }.also { typeface = it }
    }

    fun setBoxWidth(size: Int) {
        boxStrokeWidth = size
        boxStrokeWidthFocused = size
    }

    fun setTextFontFamily(fontFamily: Int) {
        textInputEditText.typeface = fontFamily?.let { ResourcesCompat.getFont(context, it) }
    }

    fun setTextColor(textColorCode: String) {
        textInputEditText.setTextColor(Color.parseColor(textColorCode))
    }

    fun setTextSize(fontSizeSp: Int) {
        fontSizeSp.spToPx().let { textInputEditText.textSize = it.toFloat() }
    }

    private fun setHintAsteriskColor(color: Int) {
        val len = hintForColor.length
        val sb = SpannableStringBuilder(hintForColor)
        val asteriskColor = ForegroundColorSpan(color)
        if (len != 0) {
            sb.setSpan(asteriskColor, len - 1, len, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            super.setHint(sb)
        }
    }

    override fun setHint(hint: CharSequence?) {
        if (isRequired) {
            hintForColor = "$hint *"
            setHintAsteriskColor(Color.RED)
        } else {
            hintForColor = hint?.toString() ?: ""
            super.setHint(hint)
        }
    }
}