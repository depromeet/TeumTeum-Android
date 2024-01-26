package com.teumteum.teumteum.presentation.familiar.neighbor

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.CustomCharacterViewBinding

//todo - 코드 정리
class CharacterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: CustomCharacterViewBinding
    val characterImage: ImageView
    val characterName: TextView
    val characterJob: TextView

    var isCharacterSelected: Boolean = false
        set(value) {
            field = value
            binding.ivCheck.visibility = if (value) VISIBLE else INVISIBLE
        }

    override fun setOnClickListener(listener: OnClickListener?) {
        super.setOnClickListener(listener)
    }

    init {
        binding = CustomCharacterViewBinding.inflate(LayoutInflater.from(context), this, true)

        characterImage = binding.ivCharacterSelf
        characterName = binding.tvCharacterName
        characterJob = binding.tvCharacterJob

        attrs?.let { applyAttributes(context, it) }

        setOnSingleClickListener {
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

            binding.ivCharacterSelf.setImageResource(characterImageResId)
            binding.tvCharacterName.text = name
            binding.tvCharacterJob.text = job
            this.isCharacterSelected = isSelected
        } finally {
            typedArray.recycle()
        }
    }
}