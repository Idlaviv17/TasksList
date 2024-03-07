package angulo.javier.taskslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.room.Room

class MainActivity : AppCompatActivity() {
    lateinit var et_tarea: EditText
    lateinit var btn_agregar: Button
    lateinit var listview_tareas: ListView
    lateinit var lista_tareas: ArrayList<String>
    lateinit var adaptor: ArrayAdapter<String>
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_tarea = findViewById(R.id.et_tarea)
        btn_agregar = findViewById(R.id.btn_agregar)
        listview_tareas = findViewById(R.id.listview_tareas)

        lista_tareas = ArrayList()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "tareas-db"
        ).allowMainThreadQueries().build()

        cargarTareas()

        adaptor = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista_tareas)
        listview_tareas.adapter = adaptor

        btn_agregar.setOnClickListener {
            var tarea_str = et_tarea.text.toString()

            if(!tarea_str.isNullOrEmpty()) {
                var tarea = Tarea(desc = tarea_str)
                db.tareaDao().agregarTarea(tarea)
                lista_tareas.add(tarea_str)
                adaptor.notifyDataSetChanged()
                et_tarea.setText("")
            } else {
                Toast.makeText(this, "llenar campo", Toast.LENGTH_SHORT).show()
            }
        }

        listview_tareas.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            var tarea_desc = lista_tareas[position]

            var tarea = db.tareaDao().getTarea(tarea_desc)

            db.tareaDao().eliminarTarea(tarea)

            lista_tareas.removeAt(position)
            adaptor.notifyDataSetChanged()
        }
    }

    private fun cargarTareas() {
        var lista_db = db.tareaDao().obtenerTareas()
        for(tarea in lista_db) {
            lista_tareas.add(tarea.desc)
        }
    }
}