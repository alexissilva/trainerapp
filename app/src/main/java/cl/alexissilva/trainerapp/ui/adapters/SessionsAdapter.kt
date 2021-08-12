package cl.alexissilva.trainerapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cl.alexissilva.trainerapp.R
import cl.alexissilva.trainerapp.databinding.SessionsRowItemBinding
import cl.alexissilva.trainerapp.domain.Session
import cl.alexissilva.trainerapp.domain.SessionStatus
import cl.alexissilva.trainerapp.ui.DefaultDiffUtil
import com.bumptech.glide.Glide
import java.time.format.DateTimeFormatter

class SessionsAdapter(
    private val context: Context,
    private val showStatus: Boolean = false,
    private val onItemClick: ((Session) -> Unit)? = null
) : RecyclerView.Adapter<SessionsAdapter.ViewHolder>() {

    companion object {
        const val DATE_FORMAT = "EEEE, d MMMM"
    }

    private var sessionList = mutableListOf<Session>()

    class ViewHolder(val binding: SessionsRowItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = SessionsRowItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val session = sessionList[position]

        val formattedDate = session.date.format(DateTimeFormatter.ofPattern(DATE_FORMAT))

        holder.binding.nameTextView.text = session.name
        holder.binding.dateTextView.text = formattedDate
        holder.binding.cardView.setOnClickListener {
                onItemClick?.invoke(session)
        }

        if (showStatus) {
            holder.binding.statusImageView.visibility = View.VISIBLE
            val drawableId: Int = when (session.status) {
                SessionStatus.PENDING -> {
                    R.drawable.ic_pending
                }
                SessionStatus.DONE -> {
                    R.drawable.ic_done
                }
                SessionStatus.SKIPPED -> {
                    R.drawable.ic_skip
                }
            }
            Glide.with(context)
                .asBitmap()
                .load(drawableId)
                .into(holder.binding.statusImageView)
        }
    }

    override fun getItemCount(): Int {
        return sessionList.size
    }

    fun setSessionList(newSessionList: List<Session>) {
        val diffCallback = DefaultDiffUtil(sessionList, newSessionList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        sessionList.clear()
        sessionList.addAll(newSessionList)
        diffResult.dispatchUpdatesTo(this)
    }


}