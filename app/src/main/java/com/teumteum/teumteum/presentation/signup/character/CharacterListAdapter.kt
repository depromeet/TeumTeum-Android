package com.teumteum.teumteum.presentation.signup.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ItemCharacterListBinding

class CharacterListAdapter(private val itemClick: (Pair<Int, Int>) -> (Unit))
    : RecyclerView.Adapter<CharacterListAdapter.CharacterListViewHolder>() {

    private val characterList = mutableListOf(
        Pair(0, R.drawable.ic_ghost),
        Pair(1, R.drawable.ic_cat),
        Pair(2, R.drawable.ic_penguin),
        Pair(3, R.drawable.ic_star_character),
        Pair(4, R.drawable.ic_rabbit),
        Pair(5, R.drawable.ic_dog),
        Pair(6, R.drawable.ic_bear),
        Pair(7, R.drawable.ic_fox),
        Pair(8, R.drawable.ic_mouse),
        Pair(9, R.drawable.ic_raccoon),
        Pair(10, R.drawable.ic_water),
        Pair(11, R.drawable.ic_panda)
    )

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
        holder.onBind(characterList[position])
    }

    override fun getItemCount(): Int = characterList.size

    class CharacterListViewHolder(
        private val binding: ItemCharacterListBinding,
        private val itemClick: (Pair<Int, Int>) -> (Unit)
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Pair<Int, Int>) {
            binding.ivCharacter.setImageResource(item.second)

            binding.root.setOnClickListener {
                itemClick(item)
            }
        }
    }
}