package com.teumteum.teumteum.presentation.familiar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.CustomCharacterViewBinding

class CharacterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: CustomCharacterViewBinding
    private var isCharacterSelected: Boolean = false
        set(value) {
            field = value
            binding.ivCheck.visibility = if (value) VISIBLE else INVISIBLE
        }

    init {
        binding = CustomCharacterViewBinding.inflate(LayoutInflater.from(context), this, true)
        attrs?.let { applyAttributes(context, it) }

        setOnClickListener {
            isCharacterSelected = !isCharacterSelected
        }
    }

    private fun applyAttributes(context: Context, attrs: AttributeSet) {
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.CustomCharacterView, 0, 0)
        try {
            val characterImageResId =
                typedArray.getResourceId(R.styleable.CustomCharacterView_characterImage, 0)
            val name = typedArray.getString(R.styleable.CustomCharacterView_name)
            val job = typedArray.getString(R.styleable.CustomCharacterView_job)
            val isSelected =
                typedArray.getBoolean(R.styleable.CustomCharacterView_isSelected, false)

            binding.ivCharacterSelf.setImageResource(characterImageResId)
            binding.tvCharacterName.text = name
            binding.tvCharacterJob.text = job
            this.isCharacterSelected = isSelected
        } finally {
            typedArray.recycle()
        }
    }
}