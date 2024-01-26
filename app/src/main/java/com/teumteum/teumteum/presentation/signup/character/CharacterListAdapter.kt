package com.teumteum.teumteum.presentation.signup.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ItemCharacterListBinding

class CharacterListAdapter(private val itemClick: (Int) -> (Unit))
    : RecyclerView.Adapter<CharacterListAdapter.CharacterListViewHolder>() {

    private val characterList: HashMap<Int, Int> = hashMapOf(
        0 to R.drawable.ic_ghost,
        1 to R.drawable.ic_star_character,
        2 to R.drawable.ic_bear,
        3 to R.drawable.ic_raccoon,
        4 to R.drawable.ic_cat,
        5 to R.drawable.ic_rabbit,
        6 to R.drawable.ic_fox,
        7 to R.drawable.ic_water,
        8 to R.drawable.ic_penguin,
        9 to R.drawable.ic_dog,
        10 to R.drawable.ic_mouse,
        11 to R.drawable.ic_panda
    )

    fun getCharacterResource(key: Int): Int? {
        return characterList[key]
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterListAdapter.CharacterListViewHolder {
        val binding = ItemCharacterListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return CharacterListAdapter.CharacterListViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(
        holder: CharacterListAdapter.CharacterListViewHolder,
        position: Int
    ) {
        characterList[position]?.let { holder.onBind(position, it) }
    }

    override fun getItemCount(): Int = characterList.size

    class CharacterListViewHolder(
        private val binding: ItemCharacterListBinding,
        private val itemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int, res: Int) {
            binding.ivCharacter.setImageResource(res)
            binding.root.setOnSingleClickListener {
                itemClick(position)
            }
        }
    }
}