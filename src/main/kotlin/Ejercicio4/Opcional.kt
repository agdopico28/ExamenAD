package Ejercicio4

import java.sql.Connection
import java.sql.DriverManager

fun main(){
    SelectTable4O()
}
fun Conexion4O() : Connection {
    val url = "jdbc:postgresql://89.36.214.106:5432/autoescola"
    val usuari = "autoescola"
    val password = "autoescola"
    return DriverManager.getConnection(url, usuari, password)
}

fun SelectTable4O(){
    val con = Conexion4O()
    val st = con.createStatement()
    val rs = st.executeQuery("select practiques.dni, alumne.nom,count(practiques.dni) as num_p,sum(practiques.km) as kilom_total\n" +
            "from practiques inner join alumne where practiques.dni = alumne.dni group by dni order by sum(km)desc;")
    var count = 0
    while (rs.next()) {
        println("Alumno de baja:")
        println("Dni:" + rs.getString(1) + "\t")
        println("nom:" + rs.getString(2) + "\t")
        println("NUM_P::" + rs.getString(3) + "\t")
        println("km:" + rs.getString(4) + "\t\n")
        count++
    }
    print("\n\t Hay $count registros")
    st.close()
    con.close()
}