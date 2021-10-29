package templates

import XH_Context

interface XH_IApp {

    var context: XH_Context

    fun build(pContext: Class<out XH_Context>, pApp: XH_ICoreLogic)
    fun start()
    fun stop()

}