import org.lwjgl.nuklear.NkAllocator
import org.lwjgl.nuklear.NkBuffer
import org.lwjgl.nuklear.NkContext
import org.lwjgl.nuklear.NkDrawNullTexture
import org.lwjgl.nuklear.NkRect
import org.lwjgl.nuklear.NkUserFont
import org.lwjgl.nuklear.Nuklear.*
import org.lwjgl.system.MemoryStack.stackPush

class GUIContext(app: App) : ClientContext(app) {

    val nkContext: NkContext = NkContext.create()
    val defaultFont = NkUserFont.create()

    val cmds = NkBuffer.create()
    val null_texture = NkDrawNullTexture.create()

    val allocator = NkAllocator.create()

    override fun onInit() {
        super.onInit()
        nk_init(nkContext, allocator, defaultFont)

        stackPush().use {
            val rect = NkRect.mallocStack(it)
            rect.x(50.0f).y(50.0f).w(300.0f).h(200.0f)

            if (nk_begin(nkContext, "UI", rect, NK_WINDOW_TITLE or NK_WINDOW_BORDER or NK_WINDOW_MINIMIZABLE)) {

            }

            nk_end(nkContext)
        }
    }

}