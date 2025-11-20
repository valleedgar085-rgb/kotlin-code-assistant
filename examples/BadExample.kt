// Example code with potential issues for testing the analyzer

fun calculateSum(numbers: List<Int>?): Int {
    // Issue: Using !! operator
    var total = 0
    
    // Issue: Using var instead of val
    for (num in numbers!!) {
        total += num
    }
    
    // Issue: Thread.sleep instead of coroutines
    Thread.sleep(100)
    
    return total
}

fun getUserName(user: User?): String {
    // Issue: Explicit null check
    if (user != null) {
        return user.name
    }
    return "Unknown"
}

data class User(val name: String, val email: String)
