package net.soft.petFinder.ui.fragments.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.qualifiers.ApplicationContext
import net.soft.features.getPetByCategoryUC.domain.models.Animal
import net.soft.petFinder.android.extension.PetFinderDrawable
import net.soft.petFinder.android.extension.PetFinderString
import net.soft.petFinder.databinding.ItemPetLayoutBinding
import javax.inject.Inject

class PetItemAdapter @Inject constructor(
    @ApplicationContext private val context: Context,
) : RecyclerView.Adapter<PetItemAdapter.ViewHolder>() {

    private var onItemClickListener: ((Animal) -> Unit)? = null

    fun appendList(subList: List<Animal>) {
        val updatedList = ArrayList(animals)
        updatedList.addAll(subList)
        animals = updatedList
    }

    fun clearData() {
        animals = emptyList()
    }

    private var animals: List<Animal>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private val differCallBack = object : DiffUtil.ItemCallback<Animal>() {
        override fun areItemsTheSame(oldItem: Animal, newItem: Animal) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Animal, newItem: Animal) =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, differCallBack)

    fun setOnItemClickListener(listener: (Animal) -> Unit) {
        onItemClickListener = listener
    }

    inner class ViewHolder(private val binding: ItemPetLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: Animal) = binding.apply {
            Glide.with(context).load(item.photos.small)
                .placeholder(PetFinderDrawable.ic_place_holder)
                .into(petImg)
            petNameValue.text = item.name.ifEmpty { context.getString(PetFinderString.na) }
            petGenderValue.text = item.gender.ifEmpty { context.getString(PetFinderString.na) }
            petTypeValue.text = item.type.ifEmpty { context.getString(PetFinderString.na) }

            root.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemPetLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent, false
            )
        )

    override fun getItemCount(): Int = animals.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = animals[position]
        holder.bindData(item)
    }
}