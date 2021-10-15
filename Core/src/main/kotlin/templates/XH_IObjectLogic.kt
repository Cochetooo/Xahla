package templates

interface XH_IObjectLogic : XH_ILogic {
    /** Called just before an object is gonna be deleted. */
    fun onDestroy() {}
}