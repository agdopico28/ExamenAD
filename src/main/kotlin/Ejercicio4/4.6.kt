package Ejercicio4

import java.sql.Connection
import java.sql.DriverManager

fun main(){
    SelectTable4()
}
fun Conexion4() : Connection {
    val url = "jdbc:postgresql://89.36.214.106:5432/autoescola"
    val usuari = "autoescola"
    val password = "autoescola"
    return DriverManager.getConnection(url, usuari, password)
}

fun SelectTable4(){
    val con = Conexion4()
    val st = con.createStatement()
    val rs = st.executeQuery("select dni, count(dni) as num_p,sum(km) as kilom_total\n" +
            "from practiques group by dni order by sum(km)desc;")
    var count = 0
    while (rs.next()) {
        println("Alumno de baja:")
        println("Dni:" + rs.getString(1) + "\t")
        println("NUM_P::" + rs.getString(2) + "\t")
        println("km:" + rs.getString(3) + "\t\n")
        count++
    }
    print("\n\t Hay $count registros")
    st.close()
    con.close()
}