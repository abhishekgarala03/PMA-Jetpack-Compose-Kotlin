package app.abhigarala.pma.utils

fun String.isValidPassword(): Boolean {
    val regex = Regex(
        pattern = "^(?=.*[0-9])" + "(?=.*[a-z])" + "(?=.*[A-Z])" + "(?=.*[^A-Za-z0-9])" + ".{12,}$"
    )
    return regex.matches(this)
}