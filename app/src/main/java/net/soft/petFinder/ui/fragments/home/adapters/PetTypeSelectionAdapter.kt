package net.soft.petFinder.ui.fragments.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import net.soft.features.getPetTypes.domain.enums.PetType
import net.soft.petFinder.databinding.ItemPetTypeSelectionLayoutBinding
import javax.inject.Inject

class PetTypeSelectionAdapter @Inject constructor(
    @ApplicationContext private val context: Context,
) : RecyclerView.Adapter<PetTypeSelectionAdapter.ViewHolder>() {

    private var lastItemClicked: Int = 0

    private var onItemClickListener: ((PetType) -> Unit)? = null

    var petTypeItems: List<PetType>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private val differCallBack = object : DiffUtil.ItemCallback<PetType>() {
        override fun areItemsTheSame(oldItem: PetType, newItem: PetType) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PetType, newItem: PetType) =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, differCallBack)

    fun setOnItemClickListener(listener: (PetType) -> Unit) {
        onItemClickListener = listener
    }

    fun getLasItemClicked() = petTypeItems[lastItemClicked]
    inner class ViewHolder(private val binding: ItemPetTypeSelectionLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: PetType) = binding.apply {
            if (lastItemClicked == layoutPosition) {
                itemTextSelection.isSelected = true
                onItemClickListener?.invoke(item)
            } else itemTextSelection.isSelected = false

            itemTextSelection.text = item.type

            root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) handleItemClick(position)
            }
        }
    }

    private fun handleItemClick(position: Int) {
        if (lastItemClicked != position) {
            notifyItemChanged(lastItemClicked)
            lastItemClicked = position
            notifyItemChanged(lastItemClicked)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemPetTypeSelectionLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )


    override fun getItemCount(): Int = petTypeItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = petTypeItems[position]
        holder.bindData(item)
    }
}