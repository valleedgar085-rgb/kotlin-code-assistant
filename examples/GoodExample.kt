/**
 * Example of well-written Kotlin code following best practices
 */

/**
 * Calculates the sum of a list of numbers.
 * 
 * @param numbers List of integers to sum
 * @return The sum of all numbers, or 0 if the list is null or empty
 */
fun calculateSum(numbers: List<Int>?): Int {
    return numbers?.sum() ?: 0
}

/**
 * Gets the user's name safely.
 * 
 * @param user The user object, which may be null
 * @return The user's name, or "Unknown" if the user is null
 */
fun getUserName(user: User?): String {
    return user?.name ?: "Unknown"
}

/**
 * Processes a list of users asynchronously using coroutines.
 * 
 * @param users List of users to process
 */
suspend fun processUsersAsync(users: List<User>) {
    users.forEach { user ->
        // Using coroutine delay instead of Thread.sleep
        kotlinx.coroutines.delay(100)
        println("Processing: ${user.name}")
    }
}

/**
 * Represents a user in the system.
 * 
 * @property name The user's name
 * @property email The user's email address
 */
data class User(
    val name: String,
    val email: String
) {
    init {
        require(name.isNotBlank()) { "Name cannot be blank" }
        require(email.contains("@")) { "Email must contain @" }
    }
}
