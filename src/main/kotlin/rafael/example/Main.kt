package rafael.example

import io.github.cdimascio.dotenv.dotenv
import rafael.example.models.User
import java.sql.DriverManager

fun main() {
    // Load the .env file
    val dotenv = dotenv()
    val dbUrl = dotenv["DB_URL"]
    val user = dotenv["DB_USER"]
    val secret = dotenv["DB_PASS"]

    // get the connection
    val connection = DriverManager
        .getConnection(dbUrl, user, secret)

    // prints true if the connection is valid
    println(connection.isValid(0))

    // the query is only prepared not executed
    val query = connection.prepareStatement("SELECT * FROM users")

    // the query is executed and results are fetched
    val result = query.executeQuery()

    // an empty list for holding the results
    val users = mutableListOf<User>()

    while(result.next()) {
        // getting the value of the id column
        val id = result.getInt("id")

        // getting the value of the name column
        val name = result.getString("name")

        /*
        constructing a User object and
        putting data into the list
        */
        users.add(User(id, name))
    }

    println(users)
}