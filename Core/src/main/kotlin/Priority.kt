@Target(AnnotationTarget.CLASS)
annotation class Priority(val level: PriorityLevel = PriorityLevel.NORMAL)

enum class PriorityLevel {
    LOWEST,
    LOW,
    NORMAL,
    HIGH,
    HIGHEST
}