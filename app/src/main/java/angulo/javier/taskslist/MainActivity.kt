package angulo.javier.taskslist

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room

class MainActivity : AppCompatActivity() {
    lateinit var taskEditText: EditText
    lateinit var addBtn: Button
    lateinit var tasksListView: ListView
    lateinit var tasksArraylist: ArrayList<String>
    lateinit var adapter: CustomAdapter
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskEditText = findViewById(R.id.editTextTask)
        addBtn = findViewById(R.id.buttonAdd)
        tasksListView = findViewById(R.id.listViewTasks)

        tasksArraylist = ArrayList()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "tasks-db"
        ).allowMainThreadQueries().build()

        loadTasks()

        adapter = CustomAdapter(this, tasksArraylist)
        tasksListView.adapter = adapter
    }

    private fun loadTasks() {
        var dbList = db.taskDAO().getTasks()
        for(task in dbList) {
            tasksArraylist.add(task.desc)
        }
    }

    fun onAddButtonClick(view: View) {
        var taskStr = taskEditText.text.toString()

        if(!taskStr.isNullOrEmpty()) {
            var task = Task(desc = taskStr)
            db.taskDAO().addTask(task)
            tasksArraylist.add(taskStr)
            adapter.notifyDataSetChanged()
            taskEditText.setText("")
        } else {
            Toast.makeText(this, "There must be a task to add", Toast.LENGTH_SHORT).show()
        }
    }

    fun onDeleteButtonClick(view: View) {
        val position = view.tag as Int
        var taskDesc = tasksArraylist[position]
        var task = db.taskDAO().getTask(taskDesc)

        db.taskDAO().deleteTask(task)
        tasksArraylist.removeAt(position)
        adapter.notifyDataSetChanged()
    }

    fun onUpdateButtonClick(view: View) {
        // TODO Implement update task
        val position = view.tag as Int
        Toast.makeText(this, "Update Clicked at position $position", Toast.LENGTH_SHORT).show()
    }
}