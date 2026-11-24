package com.apni.beyond.presentation.screens.gallery

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.content.res.ColorStateList
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.apni.beyond.data.model.ApodResponse

class ApodAdapter(
    private var items: List<ApodResponse>,
    private var savedIds: Set<String>,
    private var language: String,
    private var isDarkMode: Boolean,
    private val onSaveClick: (ApodResponse) -> Unit,
    private val onItemClick: (ApodResponse) -> Unit
) : RecyclerView.Adapter<ApodAdapter.ApodViewHolder>() {

    class ApodViewHolder(
        val layout: LinearLayout,
        val image: ImageView,
        val title: TextView,
        val date: TextView,
        val saveButton: Button
    ) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ApodViewHolder {

        val context = parent.context

        fun Int.dp(): Int =
            (this * context.resources.displayMetrics.density).toInt()

        val card = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(8.dp(), 8.dp(), 8.dp(), 8.dp())

            layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(5.dp(), 5.dp(), 5.dp(), 5.dp())
            }
        }

        val image = ImageView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                125.dp()
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
            background = GradientDrawable().apply {
                setColor(Color.DKGRAY)
                cornerRadius = 12.dp().toFloat()
            }
            clipToOutline = true
        }

        val title = TextView(context).apply {
            textSize = 13f
            typeface = Typeface.DEFAULT_BOLD
            maxLines = 2
            setPadding(2.dp(), 8.dp(), 2.dp(), 2.dp())
        }

        val date = TextView(context).apply {
            textSize = 11f
            setPadding(2.dp(), 3.dp(), 2.dp(), 2.dp())
        }

        val saveButton = Button(context).apply {
            textSize = 12f
            isAllCaps = false
            backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
            elevation = 0f
            minHeight = 36.dp()
        }

        card.addView(image)
        card.addView(title)
        card.addView(date)
        card.addView(saveButton)

        return ApodViewHolder(
            card, image, title, date, saveButton
        )
    }

    override fun onBindViewHolder(
        holder: ApodViewHolder,
        position: Int
    ) {
        val item = items[position]
        val id = item.postId?.toString() ?: item.date.orEmpty()
        val isSaved = savedIds.contains(id)

        val cardColor = if (isDarkMode) {
            Color.rgb(27, 41, 64)
        } else {
            Color.rgb(248, 246, 255)
        }

        val titleColor = if (isDarkMode) {
            Color.rgb(245, 243, 237)
        } else {
            Color.rgb(42, 43, 70)
        }

        val mutedColor = if (isDarkMode) {
            Color.rgb(169, 184, 201)
        } else {
            Color.rgb(105, 105, 130)
        }

        val buttonColor = if (isDarkMode) {
            Color.rgb(120, 157, 255)
        } else {
            Color.rgb(91, 105, 190)
        }

        holder.layout.background = GradientDrawable().apply {
            setColor(cardColor)
            cornerRadius = 16.dp(holder).toFloat()
        }

        holder.title.setTextColor(titleColor)
        holder.date.setTextColor(mutedColor)
        holder.saveButton.setTextColor(buttonColor)

        holder.title.text = item.title ?: "Untitled"
        holder.date.text = item.date ?: ""

        holder.image.load(item.hdUrl ?: item.url) {
            crossfade(true)
            placeholder(android.R.drawable.ic_menu_gallery)
            error(android.R.drawable.ic_menu_report_image)
        }

        holder.saveButton.text = when {
            language == "Bahasa Indonesia" && isSaved -> "Tersimpan"
            language == "Bahasa Indonesia" -> "Simpan"
            isSaved -> "Saved"
            else -> "Save"
        }

        holder.saveButton.setOnClickListener {
            onSaveClick(item)
        }

        holder.layout.setOnClickListener {
            onItemClick(item)
        }
    }

    private fun Int.dp(holder: ApodViewHolder): Int {
        return (this * holder.itemView.resources.displayMetrics.density).toInt()
    }

    override fun getItemCount(): Int = items.size

    fun updateData(
        newItems: List<ApodResponse>,
        newSavedIds: Set<String>,
        newLanguage: String,
        newIsDarkMode: Boolean
    ) {
        items = newItems
        savedIds = newSavedIds
        language = newLanguage
        isDarkMode = newIsDarkMode
        notifyDataSetChanged()
    }
}
