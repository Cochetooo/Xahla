package templates

import XH_Context

interface XH_IApp : XH_ICoreLogic {

    abstract var context: XH_Context

    fun build(pContext: Class<out XH_Context>, pApp: XH_ICoreLogic, ups: Int = 50)
    fun start()
    fun stop()

}