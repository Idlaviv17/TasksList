package angulo.javier.taskslist

import android.app.AlertDialog
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
            Toast.makeText(this, "Task description cannot be empty", Toast.LENGTH_SHORT).show()
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
        val position = view.tag as Int
        val taskDesc = tasksArraylist[position]
        val task = db.taskDAO().getTask(taskDesc)

        showUpdateTaskDialog(task, position)
    }

    private fun showUpdateTaskDialog(task: Task, position: Int) {
        val editText = EditText(this)
        editText.setText(task.desc)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Update Task")
            .setMessage("Enter the updated task description:")
            .setView(editText)
            .setPositiveButton("Update") { _, _ ->
                val updatedDesc = editText.text.toString()
                if (updatedDesc.isNotEmpty()) {
                    task.desc = updatedDesc
                    db.taskDAO().updateTask(task)
                    tasksArraylist[position] = updatedDesc
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "Task description cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }
}