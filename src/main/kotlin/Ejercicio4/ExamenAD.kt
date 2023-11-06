package Ejercicio4

import java.sql.Connection
import java.sql.DriverManager

fun main(){
    SelectTable()
}
fun Conexion() : Connection {
    val url = "jdbc:postgresql://89.36.214.106:5432/autoescola"
    val usuari = "autoescola"
    val password = "autoescola"
    return DriverManager.getConnection(url, usuari, password)
}

fun SelectTable(){
    val con = Conexion()
    val st = con.createStatement()
    val rs = st.executeQuery("SELECT dni, nom, data_n, baixa FROM alumne WHERE baixa LIKE 'No' order by data_n desc")
    var count = 0
    while (rs.next()) {
        println("Alumno de baja:")
        println("Dni:" + rs.getString(1) + "\t")
        println("Nombre::" + rs.getString(2) + "\t")
        println("Fecha Nacimiento:" + rs.getTimestamp(3) + "\t")
        println("Baja:" + rs.getString(4) + "\t\n")
        count++
    }
    print("\n\t Hay $count registros")
    st.close()
    con.close()
}
