package templates

interface XHR_ICoreRenderLogic : ICoreEngine {
    fun onRender() {}
    fun onPostRender() {}

    fun onResize() {}
}