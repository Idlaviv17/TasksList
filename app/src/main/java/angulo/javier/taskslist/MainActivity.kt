package angulo.javier.taskslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var et_tarea: EditText
    lateinit var btn_agregar: Button
    lateinit var listview_tareas: ListView
    lateinit var lista_tareas: ArrayList<String>
    lateinit var adaptor: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_tarea = findViewById(R.id.et_tarea)
        btn_agregar = findViewById(R.id.btn_agregar)
        listview_tareas = findViewById(R.id.listview_tareas)

        lista_tareas = ArrayList()

        adaptor = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista_tareas)
        listview_tareas.adapter = adaptor

        btn_agregar.setOnClickListener {
            var tarea = et_tarea.text.toString()

            if(!tarea.isNullOrEmpty()) {
                lista_tareas.add(tarea)
                adaptor.notifyDataSetChanged()
                et_tarea.setText("")
            } else {
                Toast.makeText(this, "llenar campo", Toast.LENGTH_SHORT).show()
            }
        }

        listview_tareas.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            lista_tareas.removeAt(position)
            adaptor.notifyDataSetChanged()
        }
    }
}