import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

fun main(args: Array<String>) {
    showMenu()
}

fun Conexion() : Connection {
    val url = "jdbc:mysql://172.17.0.2:3306/examen"
    val usuari = "root"
    val password = "secret"
    return DriverManager.getConnection(url, usuari, password)
}

fun showMenu() {
    val s = "Opciones: "

    println(" ")
    println(s)
    println("-".repeat(s.length))
    println("0- Exit")
    println("1- Create")
    println("2- Insert")
    println("3- Update")
    println("4- Select All")
    println("5- Select Nombre")
    println("6- Delete")
    println("7- Truncate")
    introduceNumber() //se ejecuta para introducir uno nuevo
}

private fun introduceNumber() {  //metodo para introducir numeros
    println(" ")
    println("Introdueix un numero (0 per acabar): ")
    val input = BufferedReader(InputStreamReader(System.`in`)).readLine()
    val num = input.toIntOrNull()
    if (num != null) {
        loopNumbers(num)


    } else {
        println("Entrada no valida. Introduix un numero válid.")
        println("")
        showMenu()
    }
}

fun loopNumbers(number: Int) {
    when (number) {
        0 -> { //asi se sale
            System.exit(0)

        }
        1 -> {
            CreateTable()
            SelectAllTable()
            showMenu()
        }
        2 -> {
            InsertTable()
            SelectAllTable()
            showMenu()
        }
        3 -> {
            UpdateTable()
            SelectAllTable()
            showMenu()
        }
        4 -> {
           SelectAllTable()
            showMenu()
        }
        5 -> {
            SelectNombreTable()
            showMenu()
        }
        6-> {
            DeleteTable()
            SelectAllTable()
            showMenu()
        }
        else -> {
           Truncate()
            SelectAllTable()
            showMenu()
        }
    }
}

//1.2
fun CreateTable(){
    val con = Conexion()

    val st = con.createStatement ()

    val sentSQL = "CREATE TABLE if not exists NADADOR(" +
            "nom varchar(30) PRIMARY KEY, " +
            "num_federat varchar(20) unique, " +
            "pais varchar(20), " +
            "edat int check (edat > 15), " +
            "genere varchar(20)" +
            ");"

    st.executeUpdate(sentSQL)
    st.close();
    con.close()
}

//1.3
fun InsertTable() {
    val con = Conexion()
    try {
        println("Añade una nueva fila")
        println("Introduce el nombre:")
        val nombre = Scanner(System.`in`).nextLine()
        println("Introduce el numero de federado:")
        val numero_f = Scanner(System.`in`).nextLine()
        println("Introduce la pais:")
        val pais = Scanner(System.`in`).nextLine()
        println("Introduce el edad:")
        val edat = Scanner(System.`in`).nextInt()
        println("Introduce el genero:")
        val genere = Scanner(System.`in`).nextLine()

        val st = con.prepareStatement("INSERT INTO NADADOR (nom, num_federat, pais, edat, genere) VALUES (?,?,?,?,?)");

        st.setString(1,nombre)
        st.setString(2,numero_f)
        st.setString(3,pais)
        st.setInt(4,edat)
        st.setString(5,genere)

        st.executeUpdate()

        println("Se ha ejecutado el insert correctamente")
        println()

        st.close()
        con.close()
    }catch (e: Exception){
        println("El nombre o el numero de federado esta repetido.")
    }

}

//1.4
fun UpdateTable(){
    val con = Conexion()

    println("Actualiza una fila")
    println("Introduce el nombre:")
    val nom = Scanner(System.`in`).nextLine()
    println("Introduce la nueva edad:")
    val edad = Scanner(System.`in`).nextInt()


    val st = con.prepareStatement("UPDATE NADADOR SET edat= ? where nom =?")

    st.setInt(1,edad)
    st.setString(2,nom)

    st.executeUpdate()
    st.close()
    con.close()
}

//1.5
fun SelectAllTable(){
    val con = Conexion()
    val st = con.createStatement()
    val rs = st.executeQuery("SELECT * FROM NADADOR")
    var count = 0
    while (rs.next()) {
        print("" + rs.getString(1) + "\t")
        print(rs.getString(2)+ "\t")
        print(rs.getString(3)+ "\t")
        print("${rs.getString(4)}\t")
        print(rs.getString(5)+ "\t")
        count++
    }
    print("\n\t Hay $count inserciones")
    st.close()
    con.close()
}

//1.6
fun SelectNombreTable(){
    val con = Conexion()

    println("Seleccionar una fila")
    println("Introduce el nombre:")
    val nom = Scanner(System.`in`).nextLine()



    val st = con.prepareStatement("SELECT * FROM NADADOR where nom = ?")

    st.setString(1,nom)
    val rs = st.executeQuery()

    var count = 0
    while (rs.next()) {
        print("" + rs.getString(1) + "\t")
        print(rs.getString(2)+ "\t")
        print(rs.getString(3)+ "\t")
        print("${rs.getString(4)}\t")
        print(rs.getString(5)+ "\t")
        count++
    }
    print("\n\t Hay $count inserciones")
    st.close()
    con.close()
}


//1.7
fun DeleteTable(){
    val con = Conexion()

    println("Borra una fila")
    println("Introduce el nombre:")
    val nom = Scanner(System.`in`).nextLine()

    val st = con.prepareStatement("DELETE FROM NADADOR WHERE nom = ?")

    st.setString(1,nom)

    st.executeUpdate()
    st.close()
    con.close()
}

//1.8
fun Truncate(){
    val con = Conexion()
    val st = con.createStatement()

    st.executeUpdate("TRUNCATE TABLE NADADOR")

    st.close()
    con.close()
}