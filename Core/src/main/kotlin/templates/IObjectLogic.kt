package templates

interface IObjectLogic : IEngine {
    /** Called just before an object is gonna be deleted. */
    fun onDestroy() {}
}