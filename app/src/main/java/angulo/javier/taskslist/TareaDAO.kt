package angulo.javier.taskslist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TareaDAO {

    @Query("SELECT * FROM tareas")
    fun obtenerTareas(): List<Tarea>

    @Query("SELECT * FROM tareas WHERE `desc` = :descripcion")
    fun getTarea(descripcion: String): Tarea

    @Insert
    fun agregarTarea(tarea: Tarea)

    @Delete
    fun eliminarTarea(tarea: Tarea)
}