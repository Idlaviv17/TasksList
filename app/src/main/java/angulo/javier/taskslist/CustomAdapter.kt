package angulo.javier.taskslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView

class CustomAdapter(private val context: Context, private val dataList: List<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_item_layout, null)

            holder = ViewHolder()
            holder.textViewItem = view.findViewById(R.id.textViewItem)
            holder.buttonDelete = view.findViewById(R.id.buttonDelete)
            holder.buttonUpdate = view.findViewById(R.id.buttonUpdate)

            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        holder.textViewItem.text = dataList[position]

        holder.buttonDelete.tag = position
        holder.buttonUpdate.tag = position

        return view!!
    }

    private class ViewHolder {
        lateinit var textViewItem: TextView
        lateinit var buttonDelete: ImageButton
        lateinit var buttonUpdate: ImageButton
    }
}