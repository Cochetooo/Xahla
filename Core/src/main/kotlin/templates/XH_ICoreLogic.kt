package templates

interface XH_ICoreLogic : XH_ILogic {
    /** Called on the startup of the program. */
    fun onAwake()       {}

    /** Called after everything else has been initialized. */
    fun onPostInit()    {}

    /** Called whenever the program is put in standby. */
    fun onPause()       {}
    /** Called when the program resumes. */
    fun onResume()      {}

    /** Called when the program is about to exit, after it has disposed. */
    fun onExit()        {}
}